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
package org.hawkular.client.metrics.clients;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.metrics.jaxrs.handlers.PingHandler;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultPingClient extends BaseClient<PingHandler> implements PingClient {

    public DefaultPingClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultPingClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<PingHandler>(PingHandler.class));
    }

    @Override
    public ClientResponse<Map<String, String>> ping() {
        Response serverResponse = null;

        try {
            serverResponse = restApi().ping();
            JavaType javaType = mapResolver().get(Map.class, String.class, String.class);

            return new DefaultClientResponse<Map<String, String>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
