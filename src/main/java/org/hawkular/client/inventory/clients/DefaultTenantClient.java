/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
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

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.inventory.jaxrs.handlers.TenantHandler;
import org.hawkular.inventory.api.model.Relationship;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.paths.CanonicalPath;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultTenantClient extends BaseClient<TenantHandler> implements TenantClient {

    public DefaultTenantClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<TenantHandler>(TenantHandler.class));
    }

    @Override
    public ClientResponse<Tenant> getTenant(String at) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getTenant(at);
            JavaType javaType = simpleResolver().get(Tenant.class);

            return new DefaultClientResponse<Tenant>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createTenant(String at, Tenant.Update update) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createTenant(at, update);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_204);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Relationship>> createRelationship(String at, List<Relationship.Blueprint> blueprints) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createRelationship(at, blueprints);
            JavaType javaType = collectionResolver().get(List.class, Relationship.class);

            return new DefaultClientResponse<List<Relationship>>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Relationship>> getRelationships(CanonicalPath path, String at) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getRelationships(path.toRelativePath().toString(), at);
            JavaType javaType = collectionResolver().get(List.class, Relationship.class);

            return new DefaultClientResponse<List<Relationship>>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
