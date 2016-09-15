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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

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

    public MetricsClientImpl(URI endpointUri) throws Exception {
        this(endpointUri, null, null);
    }

    public MetricsClientImpl(URI endpointUri, String username, String password) {
        checkArgument(endpointUri != null, "EndpointUri is null");

        availability = new DefaultAvailabilityClient(endpointUri, username, password);
        counter = new DefaultCounterClient(endpointUri, username, password);
        gauge = new DefaultGaugeClient(endpointUri, username, password);
        metric = new DefaultMetricClient(endpointUri, username, password);
        string = new DefaultStringClient(endpointUri, username, password);
        tenant = new DefaultTenantClient(endpointUri, username, password);
        ping = new DefaultPingClient(endpointUri, username, password);
        status = new DefaultStatusClient(endpointUri, username, password);
    }

    @Override
    public AvailabilityClient availability() {
        checkNotNull(availability != null, "AvailabilityClient is null");
        return availability;
    }

    @Override
    public CounterClient counter() {
        checkNotNull(counter != null, "CounterClient is null");
        return counter;
    }

    @Override
    public GaugeClient gauge() {
        checkNotNull(gauge != null, "GaugeClient is null");
        return gauge;
    }

    @Override
    public MetricClient metric() {
        checkNotNull(metric != null, "MetricClient is null");
        return metric;
    }

    @Override
    public StringClient string() {
        checkNotNull(string != null, "StringClient is null");
        return string;
    }

    @Override
    public TenantClient tenant() {
        checkNotNull(tenant != null, "TenantClient is null");
        return tenant;
    }

    @Override
    public PingClient ping() {
        checkNotNull(ping != null, "PingClient is null");
        return ping;
    }

    @Override
    public StatusClient status() {
        checkNotNull(status != null, "StatusClient is null");
        return status;
    }

}
