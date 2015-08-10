/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
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
import java.util.List;

import javax.ws.rs.core.Response;

import org.hawkular.inventory.api.model.CanonicalPath;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.json.InventoryJacksonConfig;
import org.hawkular.inventory.json.PathDeserializer;
import org.hawkular.inventory.json.mixins.CanonicalPathWithTenantMixin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientResponse<T> {
    private static final Logger _logger = LoggerFactory.getLogger(ClientResponse.class);
    private int statusCode;
    private String errorMsg;
    private T entity;
    private boolean success = false;

    public ClientResponse(Class<?> clazz, Response response, int statusCode) {
        this(clazz, response, statusCode, null, false);
    }

    public ClientResponse(Class<?> clazz, Response response, int statusCode, String tenantId) {
        this(clazz, response, statusCode, tenantId, false);
    }

    @SuppressWarnings("unchecked")
    public ClientResponse(Class<?> clazz, Response response, int statusCode, String tenantId, boolean isEntityList) {
        try {
            this.setStatusCode(response.getStatus());
            if (response.getStatus() == statusCode) {
                this.setSuccess(true);
                if (clazz.getName().equalsIgnoreCase(String.class.getName())) {
                    this.setEntity((T) response.readEntity(clazz));
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    InventoryJacksonConfig.configure(objectMapper);
                    if (clazz.getName().equalsIgnoreCase(Tenant.class.getName())) {
                        objectMapper.addMixIn(CanonicalPath.class, CanonicalPathWithTenantMixin.class);
                    } else if (tenantId != null) {
                        PathDeserializer.setCurrentCanonicalOrigin(CanonicalPath.of()
                                .tenant(tenantId).get());
                    }
                    if (isEntityList) {
                        this.setEntity(objectMapper.readValue(response.readEntity(String.class),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)));

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
}
