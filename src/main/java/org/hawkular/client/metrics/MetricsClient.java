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

import org.hawkular.client.metrics.clients.AvailabilityClient;
import org.hawkular.client.metrics.clients.CounterClient;
import org.hawkular.client.metrics.clients.GaugeClient;
import org.hawkular.client.metrics.clients.MetricClient;
import org.hawkular.client.metrics.clients.PingClient;
import org.hawkular.client.metrics.clients.StringClient;
import org.hawkular.client.metrics.clients.TenantClient;

/**
 * http://www.hawkular.org/docs/rest/rest-metrics.html
 */
public interface MetricsClient {

    AvailabilityClient availability();

    CounterClient counter();

    GaugeClient gauge();

    MetricClient metric();

    StringClient string();

    TenantClient tenant();

    PingClient ping();
}
