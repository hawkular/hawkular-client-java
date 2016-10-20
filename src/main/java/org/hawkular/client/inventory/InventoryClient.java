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
package org.hawkular.client.inventory;

import org.hawkular.client.inventory.clients.APIInfoClient;
import org.hawkular.client.inventory.clients.BulkCreateClient;
import org.hawkular.client.inventory.clients.EventsClient;
import org.hawkular.client.inventory.clients.GraphClient;
import org.hawkular.client.inventory.clients.SingleEntityClient;
import org.hawkular.client.inventory.clients.SyncClient;
import org.hawkular.client.inventory.clients.TenantClient;
import org.hawkular.client.inventory.clients.TraversalClient;

public interface InventoryClient {

    APIInfoClient apiInfo();

    BulkCreateClient bulkCreate();

    TraversalClient traversal();

    EventsClient events();

    GraphClient graph();

    SingleEntityClient singleEntity();

    SyncClient sync();

    TenantClient tenant();
}
