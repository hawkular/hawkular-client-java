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
package org.hawkular.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Objects;

import org.hawkular.client.alert.AlertsClient;
import org.hawkular.client.alert.AlertsClientImpl;
import org.hawkular.client.inventory.InventoryClient;
import org.hawkular.client.inventory.InventoryClientImpl;
import org.hawkular.client.metrics.MetricsClient;
import org.hawkular.client.metrics.MetricsClientImpl;

import com.google.common.base.MoreObjects;

public class HawkularClient {
    public static final String KEY_HEADER_TENANT = "Hawkular-Tenant";
    public static final String KEY_HEADER_AUTHORIZATION = "Authorization";

    private MetricsClient metricsClient;
    private InventoryClient inventoryClient;
    private AlertsClient alertsClient;
    private URI endpointUri;

    public HawkularClient(URI endpointUri, String username, String password) throws Exception {
        this(endpointUri, username, password, null);
    }

    public HawkularClient(URI endpointUri) throws Exception {
        this(endpointUri, null, null, null);
    }

    public HawkularClient(URI endpointUri, HashMap<String, Object> headers) throws Exception {
        this(endpointUri, null, null, headers);
    }

    public HawkularClient(URI endpointUri, String username, String password, HashMap<String, Object> headers)
            throws Exception {
        this.endpointUri = endpointUri;
        if (username != null) {
            metricsClient = new MetricsClientImpl(endpointUri, username, password);
            inventoryClient = new InventoryClientImpl(endpointUri, username, password);
            alertsClient = new AlertsClientImpl(endpointUri, username, password);
        } else {
            metricsClient = new MetricsClientImpl(endpointUri);
            inventoryClient = new InventoryClientImpl(endpointUri);
            alertsClient = new AlertsClientImpl(endpointUri);
        }
        //Load headers
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                updateHeader(key, headers.get(key));
            }
        }
    }

    public MetricsClient metrics() {
        return metricsClient;
    }

    public InventoryClient inventory() {
        return inventoryClient;
    }

    public AlertsClient alerts() {
        return alertsClient;
    }

    public int hashcode() {
        return Objects.hash(endpointUri.hashCode());
    }

    public void updateHeader(String key, Object value) {
        RestRequestFilter.updateHeader(key, value);
    }

    public void removeHeader(String key) {
        RestRequestFilter.removeHeader(key);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("endpoint", endpointUri)
                .toString();
    }

}
