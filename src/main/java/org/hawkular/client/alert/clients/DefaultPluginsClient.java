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
package org.hawkular.client.alert.clients;

import java.util.List;

import javax.ws.rs.core.Response;

import org.hawkular.client.alert.jaxrs.handlers.PluginsHandler;
import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultPluginsClient extends BaseClient<PluginsHandler> implements PluginsClient {

    public DefaultPluginsClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<>(PluginsHandler.class));
    }

    @Override
    public ClientResponse<List<String>> findActionPlugins() {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findActionPlugins();
            JavaType javaType = collectionResolver().get(List.class, String.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<String>> getActionPlugin(String actionPlugin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getActionPlugin(actionPlugin);
            JavaType javaType = collectionResolver().get(List.class, String.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
