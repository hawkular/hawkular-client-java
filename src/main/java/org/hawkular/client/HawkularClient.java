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

import org.hawkular.client.inventory.InventoryClientImpl;
import org.hawkular.client.metrics.MetricsClientImpl;

public class HawkularClient {
    private MetricsClient metricsClient;
    private InventoryClient inventoryClient;

    public HawkularClient(String endpointUrl, String username, String password) throws Exception {
        metricsClient = new MetricsClientImpl(endpointUrl, username, password);
        inventoryClient = new InventoryClientImpl(endpointUrl, username, password);
    }

    public MetricsClient metrics() {
        return metricsClient;
    }

    public InventoryClient inventory() {
        return inventoryClient;
    }
}
