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
package org.hawkular.client.inventory.clients;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.inventory.jaxrs.handlers.BulkCreateHandler;
import org.hawkular.client.inventory.model.ElementType;
import org.hawkular.inventory.paths.CanonicalPath;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultBulkCreateClient extends BaseClient<BulkCreateHandler> implements BulkCreateClient {

    public DefaultBulkCreateClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<BulkCreateHandler>(BulkCreateHandler.class));
    }

    @Override
    public ClientResponse<Map<ElementType, Map<CanonicalPath, Integer>>> create(Map<String, Map<ElementType, List<Object>>> entities, String at) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().create(entities, at);
            JavaType javaType = mapResolver().get(Map.class, ElementType.class, Map.class, CanonicalPath.class, Integer.class);

            return new DefaultClientResponse<Map<ElementType, Map<CanonicalPath, Integer>>>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
