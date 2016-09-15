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
package org.hawkular.client.metrics.clients;

import java.util.List;
import java.util.Map;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.MixedMetricsRequest;
import org.hawkular.metrics.model.param.Tags;

public interface MetricClient {

    /**
     * Find tenant’s metric definitions.
     * @param metricType
     * @param tags
     * @param id
     * @return
     */
    ClientResponse<List<Metric<?>>> findMetrics(MetricType<?> metricType, Tags tags, String id);

    /**
     * Create metric.
     * @param overwrite
     * @param metric
     * @return
     */
    ClientResponse<Empty> createMetric(Boolean overwrite, Metric<?> metric);

    /**
     * Add data points for multiple metrics in a single call.
     * @param metricsRequest
     * @return
     */
    ClientResponse<Empty> addMetricsData(MixedMetricsRequest metricsRequest);

    /**
     * Retrieve metrics' tag values
     * @param tags
     * @param metricType
     * @return
     */
    ClientResponse<Map<String, List<String>>> findMetricsTags(Tags tags, MetricType<?> metricType);
}
