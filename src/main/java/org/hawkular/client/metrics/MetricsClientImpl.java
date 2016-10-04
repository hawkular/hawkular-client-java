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
package org.hawkular.client.metrics;

import static com.google.common.base.Preconditions.checkNotNull;

import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.metrics.clients.AvailabilityClient;
import org.hawkular.client.metrics.clients.CounterClient;
import org.hawkular.client.metrics.clients.DefaultAvailabilityClient;
import org.hawkular.client.metrics.clients.DefaultCounterClient;
import org.hawkular.client.metrics.clients.DefaultGaugeClient;
import org.hawkular.client.metrics.clients.DefaultMetricClient;
import org.hawkular.client.metrics.clients.DefaultPingClient;
import org.hawkular.client.metrics.clients.DefaultStatusClient;
import org.hawkular.client.metrics.clients.DefaultStringClient;
import org.hawkular.client.metrics.clients.DefaultTenantClient;
import org.hawkular.client.metrics.clients.GaugeClient;
import org.hawkular.client.metrics.clients.MetricClient;
import org.hawkular.client.metrics.clients.PingClient;
import org.hawkular.client.metrics.clients.StatusClient;
import org.hawkular.client.metrics.clients.StringClient;
import org.hawkular.client.metrics.clients.TenantClient;

public class MetricsClientImpl implements MetricsClient {

    private final AvailabilityClient availability;
    private final CounterClient counter;
    private final GaugeClient gauge;
    private final DefaultMetricClient metric;
    private final DefaultStringClient string;
    private final DefaultTenantClient tenant;
    private final DefaultPingClient ping;
    private final DefaultStatusClient status;

    public MetricsClientImpl(ClientInfo clientInfo) {
        checkNotNull(clientInfo);
        availability = new DefaultAvailabilityClient(clientInfo);
        counter = new DefaultCounterClient(clientInfo);
        gauge = new DefaultGaugeClient(clientInfo);
        metric = new DefaultMetricClient(clientInfo);
        string = new DefaultStringClient(clientInfo);
        tenant = new DefaultTenantClient(clientInfo);
        ping = new DefaultPingClient(clientInfo);
        status = new DefaultStatusClient(clientInfo);
    }

    @Override
    public AvailabilityClient availability() {
        return availability;
    }

    @Override
    public CounterClient counter() {
        return counter;
    }

    @Override
    public GaugeClient gauge() {
        return gauge;
    }

    @Override
    public MetricClient metric() {
        return metric;
    }

    @Override
    public StringClient string() {
        return string;
    }

    @Override
    public TenantClient tenant() {
        return tenant;
    }

    @Override
    public PingClient ping() {
        return ping;
    }

    @Override
    public StatusClient status() {
        return status;
    }

}
