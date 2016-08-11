/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hawkular.alerts.api.json.JacksonDeserializer;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.client.metrics.mixins.MetricsJacksonConfig;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.json.DetypedPathDeserializer;
import org.hawkular.inventory.json.InventoryJacksonConfig;
import org.hawkular.inventory.json.mixins.model.CanonicalPathMixin;
import org.hawkular.inventory.paths.CanonicalPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ClientResponse<T> {
    private static final Logger _logger = LoggerFactory.getLogger(ClientResponse.class);
    private int statusCode;
    private String errorMsg;
    private T entity;
    private boolean success = false;

    public ClientResponse(Class<?> clazz, Response response, int statusCode) {
        this(clazz, null, response, statusCode, null, null);
    }

    public ClientResponse(Class<?> clazz, Class<?> parametrizedClazz, Response response, int statusCode) {
        this(clazz, parametrizedClazz, response, statusCode, null, null);
    }

    public ClientResponse(Class<?> clazz, Response response, int statusCode, String tenantId) {
        this(clazz, null, response, statusCode, tenantId, null);
    }

    public ClientResponse(Class<?> clazz, Response response, int statusCode, boolean isEntityList) {
        this(clazz, null, response, statusCode, null, isEntityList == true ? List.class : null);
    }

    public ClientResponse(Class<?> clazz, Class<?> parametrizedClazz, Response response, int statusCode, boolean isEntityList) {
        this(clazz, parametrizedClazz, response, statusCode, null, isEntityList == true ? List.class : null);
    }

    public ClientResponse(Class<?> clazz, Response response, int statusCode, String tenantId, boolean isEntityList) {
        this(clazz, null, response, statusCode, tenantId, isEntityList == true ? List.class : null);
    }

    public ClientResponse(Class<?> clazz, Response response, int statusCode,
                          @SuppressWarnings("rawtypes") Class<? extends Collection> collectionType) {
        this(clazz, null, response, statusCode, null, collectionType);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ClientResponse(Class<?> clazz, Class<?> parametrizedClazz, Response response, int statusCode, String tenantId,
            Class<? extends Collection> collectionType) {
        try {
            this.setStatusCode(response.getStatus());
            if (response.getStatus() == statusCode) {
                this.setSuccess(true);
                if (clazz.getName().equalsIgnoreCase(String.class.getName())) {
                    this.setEntity((T) response.readEntity(clazz));
                } else if (clazz.getName().equalsIgnoreCase(Condition.class.getName())) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonConditions = response.readEntity(String.class);
                    JsonNode rootNode = objectMapper.readTree(jsonConditions);
                    List<Condition> conditions = new ArrayList<>();
                    if (!(null == jsonConditions || jsonConditions.trim().isEmpty())) {
                        for (JsonNode conditionNode : rootNode) {
                            Condition condition = JacksonDeserializer.deserializeCondition(conditionNode);
                            if (condition == null) {
                                this.setSuccess(false);
                                this.setErrorMsg("Bad json conditions: " + jsonConditions);
                                return;
                            }
                            conditions.add(condition);
                        }
                    }
                    this.setEntity((T) conditions);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    InventoryJacksonConfig.configure(objectMapper);
                    MetricsJacksonConfig.configure(objectMapper);
                    if (clazz.getName().equalsIgnoreCase(Tenant.class.getName())) {
                        objectMapper.addMixIn(CanonicalPath.class, CanonicalPathMixin.class);
                    } else if (tenantId != null) {
                        DetypedPathDeserializer.setCurrentCanonicalOrigin(CanonicalPath.of()
                                .tenant(tenantId).get());
                    }

                    if (collectionType != null) {
                        String entity = response.readEntity(String.class);
                        if (parametrizedClazz == null) {
                            this.setEntity(objectMapper.readValue(entity, objectMapper.getTypeFactory().constructCollectionType(collectionType, clazz)));
                        } else {
                            JavaType javaTypeObj = objectMapper.getTypeFactory().constructParametrizedType(clazz, clazz, parametrizedClazz);
                            CollectionType collectionTypeObj = objectMapper.getTypeFactory().constructCollectionType(collectionType, javaTypeObj);

                            this.setEntity(objectMapper.readValue(entity, collectionTypeObj));
                        }

                    } else {
                        this.setEntity((T) objectMapper.readValue(response.readEntity(String.class), clazz));
                    }
                }
            } else {
                this.setErrorMsg(response.readEntity(String.class));
            }
        } catch (JsonParseException e) {
            _logger.error("Error, ", e);
        } catch (JsonMappingException e) {
            _logger.error("Error, ", e);
        } catch (IOException e) {
            _logger.error("Error, ", e);
        } finally {
            response.close();
            if (_logger.isDebugEnabled()) {
                _logger.debug("Client response:{}", this.toString());
            }
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @SuppressWarnings("unchecked")
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Status Code:").append(this.statusCode);
        builder.append(", Is Success:").append(this.success);
        builder.append(", Error Message:").append(this.errorMsg == null ? "-" : this.errorMsg);
        if (this.entity instanceof Object[]) {
            builder.append(", Entity:").append(Arrays.toString((T[]) this.entity));
        } else {
            builder.append(", Entity:[").append(this.entity).append("]");
        }
        return builder.toString();
    }
}
