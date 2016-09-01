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
import org.hawkular.metrics.model.AvailabilityBucketPoint;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Tags;

public interface AvailabilityClient {

    /**
     * Find tenant’s metric definitions.
     *
     * @param tags
     * @return
     */
    ClientResponse<List<Metric<AvailabilityType>>> findAvailabilityMetrics(Tags tags);

    /**
     * Create availability metric.
     *
     * @param overwrite
     * @param metric
     * @return
     */
    ClientResponse<Empty> createAvailabilityMetric(Boolean overwrite, Metric<AvailabilityType> metric);

    /**
     * Add metric data for multiple availability metrics in a single call.
     *
     * @param data
     * @return
     */
    ClientResponse<Empty> addAvailabilityData(List<Metric<AvailabilityType>> data);

    /**
     * Retrieve gauge type’s tag values
     *
     * @param tags
     * @return
     */
    ClientResponse<Map<String, List<String>>> getGaugeTags(Tags tags);

    /**
     * Retrieve single metric definition.
     *
     * @param id
     * @return
     */
    ClientResponse<Metric<AvailabilityType>> getAvailabilityMetric(String id);

    /**
     * Retrieve availability data.
     *
     * @param id
     * @param start
     * @param end
     * @param distinct
     * @param limit
     * @param order
     * @return
     */
    ClientResponse<List<DataPoint<AvailabilityType>>> findAvailabilityData(
        String id,
        Long start,
        Long end,
        Boolean distinct,
        Integer limit,
        Order order);

    /**
     * Add data for a single availability metric.
     *
     * @param id
     * @param data
     * @return
     */
    ClientResponse<Empty> addAvailabilityDataForMetric(String id, List<DataPoint<AvailabilityType>> data);

    /**
     * Retrieve availability data.
     *
     * @param id
     * @param start
     * @param end
     * @param buckets
     * @param bucketDuration
     * @return
     */
    ClientResponse<List<AvailabilityBucketPoint>> findAvailabilityStats(
        String id,
        Long start,
        Long end,
        Integer buckets,
        Duration bucketDuration);

    /**
     * Retrieve tags associated with the metric definition.
     *
     * @param id
     * @return
     */
    ClientResponse<Map<String, String>> getAvailabilityMetricTags(String id);

    /**
     * Update tags associated with the metric definition.
     *
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> updateAvailabilityMetricTags(String id, Map<String, String> tags);

    /**
     * Delete tags associated with the metric definition.
     *
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> deleteAvailabilityMetricTags(String id, Tags tags);

}
