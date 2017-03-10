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
package org.hawkular.client.inventory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.inventory.clients.APIInfoClient;
import org.hawkular.client.inventory.clients.BulkCreateClient;
import org.hawkular.client.inventory.clients.DefaultAPIInfoClient;
import org.hawkular.client.inventory.clients.DefaultBulkCreateClient;
import org.hawkular.client.inventory.clients.DefaultEventsClient;
import org.hawkular.client.inventory.clients.DefaultGraphClient;
import org.hawkular.client.inventory.clients.DefaultSingleEntityClient;
import org.hawkular.client.inventory.clients.DefaultSyncClient;
import org.hawkular.client.inventory.clients.DefaultTenantClient;
import org.hawkular.client.inventory.clients.DefaultTraversalClient;
import org.hawkular.client.inventory.clients.EventsClient;
import org.hawkular.client.inventory.clients.GraphClient;
import org.hawkular.client.inventory.clients.SingleEntityClient;
import org.hawkular.client.inventory.clients.SyncClient;
import org.hawkular.client.inventory.clients.TenantClient;
import org.hawkular.client.inventory.clients.TraversalClient;

import com.google.common.base.MoreObjects;

public class InventoryClientImpl implements InventoryClient {

    private final APIInfoClient apiInfo;
    private final BulkCreateClient bulkCreate;
    private final TraversalClient traversal;
    private final EventsClient events;
    private final GraphClient graph;
    private final SingleEntityClient singleEntity;
    private final SyncClient sync;
    private final TenantClient tenant;

    public InventoryClientImpl(ClientInfo clientInfo) {
        checkNotNull(clientInfo);
        apiInfo = new DefaultAPIInfoClient(clientInfo);
        bulkCreate = new DefaultBulkCreateClient(clientInfo);
        traversal = new DefaultTraversalClient(clientInfo);
        events = new DefaultEventsClient(clientInfo);
        graph = new DefaultGraphClient(clientInfo);
        singleEntity = new DefaultSingleEntityClient(clientInfo);
        sync = new DefaultSyncClient(clientInfo);
        tenant = new DefaultTenantClient(clientInfo);
    }

    @Override
    public APIInfoClient apiInfo() {
        return apiInfo;
    }

    @Override
    public BulkCreateClient bulkCreate() {
        return bulkCreate;
    }

    @Override
    public TraversalClient traversal() {
        return traversal;
    }

    @Override
    public EventsClient events() {
        return events;
    }

    @Override
    public GraphClient graph() {
        return graph;
    }

    @Override
    public SingleEntityClient singleEntity() {
        return singleEntity;
    }

    @Override
    public SyncClient sync() {
        return sync;
    }

    @Override
    public TenantClient tenant() {
        return tenant;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("apiInfo", apiInfo)
            .add("bulkCreate", bulkCreate)
            .add("traversal", traversal)
            .add("events", events)
            .add("graph", graph)
            .add("singleEntity", singleEntity)
            .add("sync", sync)
            .add("tenant", tenant)
            .toString();
    }
}
