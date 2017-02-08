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
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.inventory.jaxrs.handlers.SingleEntityHandler;
import org.hawkular.inventory.api.model.AbstractElement;
import org.hawkular.inventory.api.model.Change;
import org.hawkular.inventory.api.model.IdentityHash;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultSingleEntityClient extends BaseClient<SingleEntityHandler> implements SingleEntityClient {

    public DefaultSingleEntityClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<SingleEntityHandler>(SingleEntityHandler.class));
    }

    @Override
    public ClientResponse<Empty> deleteEntity(CanonicalPath path, String at) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteEntity(path.toRelativePath().toString(), at);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_204);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map> getEntity(CanonicalPath path, String at) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getEntity(path.toRelativePath().toString(), at);
            JavaType javaType = simpleResolver().get(Map.class);

            return new DefaultClientResponse<Map>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateEntity(CanonicalPath path, String at, AbstractElement.Update update) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateEntity(path.toRelativePath().toString(), at, update);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_204);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Change<?>>> getHistory(CanonicalPath path, String from, String to) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getHistory(path.toRelativePath().toString(), from, to);
            JavaType javaType = collectionResolver().get(List.class, Change.class);

            return new DefaultClientResponse<List<Change<?>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<IdentityHash.Tree> getEntityHash(CanonicalPath path, String at) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getEntityHash(path.toRelativePath().toString(), at);
            JavaType javaType = simpleResolver().get(IdentityHash.Tree.class);

            return new DefaultClientResponse<IdentityHash.Tree>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map> createEntity(CanonicalPath path, SegmentType type, String at, AbstractElement.Blueprint entity) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createEntity(path.toRelativePath().toString(), type.getSimpleName(), at, entity);
            JavaType javaType = simpleResolver().get(Map.class);

            return new DefaultClientResponse<Map>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
