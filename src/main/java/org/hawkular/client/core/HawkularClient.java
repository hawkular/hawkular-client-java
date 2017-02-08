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
package org.hawkular.client.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.hawkular.client.alert.AlertsClient;
import org.hawkular.client.alert.AlertsClientImpl;
import org.hawkular.client.inventory.InventoryClient;
import org.hawkular.client.inventory.InventoryClientImpl;
import org.hawkular.client.metrics.MetricsClient;
import org.hawkular.client.metrics.MetricsClientImpl;

public class HawkularClient {

    static final String KEY_HEADER_TENANT = "Hawkular-Tenant";
    static final String KEY_HEADER_AUTHORIZATION = "Authorization";
    static final String KEY_HEADER_ADMIN_TOKEN ="Hawkular-Admin-Token";

    private MetricsClient metricsClient;
    private InventoryClient inventoryClient;
    private AlertsClient alertsClient;
    private ClientInfo clientInfo;

    public HawkularClient(ClientInfo clientInfo) {
        checkNotNull(clientInfo);
        checkArgument(clientInfo.getHeaders().containsKey(KEY_HEADER_TENANT), "Hawkular-Tenant header is missing");

        this.clientInfo = clientInfo;

        this.metricsClient = new MetricsClientImpl(clientInfo);
        this.inventoryClient = new InventoryClientImpl(clientInfo);
        this.alertsClient = new AlertsClientImpl(clientInfo);
    }

    /**
     * @deprecated Use {@link #HawkularClient(ClientInfo)} instead
     * @param endpointUri endpoint uri
     * @param headers map of header
     */
    @Deprecated
    public HawkularClient(URI endpointUri, Map<String, Object> headers) {
        this(new ClientInfo(endpointUri, Optional.empty(), Optional.empty(), headers));
    }

    /**
     * @deprecated Use {@link #HawkularClient(ClientInfo)} instead
     * @param endpointUri endpoint uri
     * @param username username for basic auth
     * @param password password for basic auth
     * @param headers map of header
     */
    @Deprecated
    public HawkularClient(URI endpointUri, String username, String password, Map<String, Object> headers) {
        this(new ClientInfo(endpointUri, Optional.ofNullable(username), Optional.ofNullable(password), headers));
    }

    public static HawkularClientBuilder builder(String tenant) {
        return new HawkularClientBuilder(tenant);
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

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public String getTenant() {
        return (String) clientInfo.getHeaders().get(KEY_HEADER_TENANT);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HawkularClient that = (HawkularClient) o;

        return clientInfo != null ? clientInfo.equals(that.clientInfo) : that.clientInfo == null;

    }

    @Override public int hashCode() {
        return clientInfo != null ? clientInfo.hashCode() : 0;
    }

    @Override public String toString() {
        return "HawkularClient{" +
                "metricsClient=" + metricsClient +
                ", inventoryClient=" + inventoryClient +
                ", alertsClient=" + alertsClient +
                ", clientInfo=" + clientInfo +
                '}';
    }
}
