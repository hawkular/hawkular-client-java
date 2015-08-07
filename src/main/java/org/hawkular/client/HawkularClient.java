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

import java.net.URI;
import java.util.Objects;

import org.hawkular.client.inventory.InventoryClient;
import org.hawkular.client.inventory.InventoryClientImpl;
import org.hawkular.client.metrics.MetricsClient;
import org.hawkular.client.metrics.MetricsClientImpl;

import com.google.common.base.MoreObjects;

public class HawkularClient {
    private MetricsClient metricsClient;
    private InventoryClient inventoryClient;
    private URI endpointUri;

    public HawkularClient(URI endpointUri, String username, String password) throws Exception {
        this.endpointUri = endpointUri;
        metricsClient = new MetricsClientImpl(endpointUri, username, password);
        inventoryClient = new InventoryClientImpl(endpointUri, username, password);
    }

    public MetricsClient metrics() {
        return metricsClient;
    }

    public InventoryClient inventory() {
        return inventoryClient;
    }

    public int hashcode() {
        return Objects.hash(endpointUri.hashCode());
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("endpoint", endpointUri)
                .toString();
    }
}
