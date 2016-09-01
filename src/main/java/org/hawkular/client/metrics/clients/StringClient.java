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
import org.hawkular.client.core.jaxrs.Order;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Tags;

public interface StringClient {

    /**
     * Find tenant’s metric definitions.
     * @param tags
     * @return
     */
    ClientResponse<List<Metric>> findMetricsDefinitions(Tags tags);

    /**
     * Create string metric.
     * @param overwrite
     * @param metric
     * @return
     */
    ClientResponse<Empty> createStringMetric(Boolean overwrite, Metric<String> metric);

    /**
     * Add metric data for multiple string metrics in a single call.
     * @param metrics
     * @return
     */
    ClientResponse<Empty> createStringMetric(List<Metric<String>> metrics);

    /**
     * Retrieve string type’s tag values
     * @param tags
     * @return
     */
    ClientResponse<Map<String, List<String>>> findMetricTags(Tags tags);

    /**
     * Retrieve single metric definition.
     * @param id
     * @return
     */
    ClientResponse<Metric> getMetricDefinitions(String id);

    /**
     * Retrieve string data.
     * @param id
     * @param start
     * @param end
     * @param distinct
     * @param limit
     * @param order
     * @return
     */
    ClientResponse<List<DataPoint>> getMetricDefinitionsData(
        String id,
        Long start,
        Long end,
        Boolean distinct,
        Integer limit,
        Order order);

    /**
     * Add data for a single string metric.
     * @param id
     * @param dataPoints
     * @return
     */
    ClientResponse<Empty> createMetricDefinitionsData(String id, List<DataPoint<String>> dataPoints);

    /**
     * Retrieve tags associated with the metric definition.
     * @param id
     * @return
     */
    ClientResponse<Map<String, String>> findMetricDefinitionsTags(String id);

    /**
     * Update tags associated with the metric definition.
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> updateMetricDefinitionsTags(String id, Map<String, String> tags);

    /**
     * Delete tags associated with the metric definition.
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> deleteMetricDefinitionsTags(String id, Tags tags);
}
