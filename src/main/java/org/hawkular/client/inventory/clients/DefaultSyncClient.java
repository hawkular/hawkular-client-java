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

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.inventory.jaxrs.handlers.SyncHandler;
import org.hawkular.inventory.api.model.SyncRequest;
import org.hawkular.inventory.paths.CanonicalPath;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultSyncClient extends BaseClient<SyncHandler> implements SyncClient {

    public DefaultSyncClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<SyncHandler>(SyncHandler.class));
    }

    @Override
    public ClientResponse<Empty> synchronize(CanonicalPath path, SyncRequest request) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().synchronize(path.toRelativePath().toString(), request);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_204);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
