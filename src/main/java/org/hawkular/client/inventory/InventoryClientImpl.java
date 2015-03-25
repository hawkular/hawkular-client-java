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
package org.hawkular.client.inventory;

import java.net.URI;

import org.hawkular.client.BaseClient;
import org.hawkular.client.InventoryClient;
import org.hawkular.client.RestFactory;
import org.hawkular.inventory.api.Resource;

public class InventoryClientImpl extends BaseClient<InventoryRestApi>implements InventoryClient {

    public InventoryClientImpl(URI endpointUri, String username,
            String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<InventoryRestApi>(InventoryRestApi.class));
    }

    @Override
    public String pinger() {
        StringWrapper obj = restApi().ping();
        return (obj != null && obj.getValue() != null) ? obj.getValue() : "";
    }

    @Override
    public Resource getResource(String tenantId, String type) {
        return restApi().getResource(tenantId, type);
    }

    @Override
    public IdWrapper addResource(String tenantId, Resource resource) {
        IdWrapper id = restApi().addResource(tenantId, resource);
        return id;
    }

}
