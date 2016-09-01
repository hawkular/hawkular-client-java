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
import org.hawkular.client.metrics.model.Order;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

public interface CounterClient {

    /**
     * Find tenant’s counter metric definitions.
     *
     * @param tags
     * @return
     */
    ClientResponse<List<Metric<Long>>> getCounters(Tags tags);

    /**
     * Create counter metric.
     *
     * @param overwrite
     * @param metric
     * @return
     */
    ClientResponse<Empty> createCounter(Boolean overwrite, Metric<Long> metric);

    /**
     * Fetches data points from one or more metrics that are determined using either a tags filter or a list of metric names.
     * The time range between start and end is divided into buckets of equal size (i.e., duration)
     * using either the buckets or bucketDuration parameter.
     * Functions are applied to the data points in each bucket to produce statistics or aggregated metrics.
     *
     * @param start
     * @param end
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @param tags
     * @param metricNames
     * @param stacked
     * @return
     */
    ClientResponse<List<NumericBucketPoint>> findCounterRateDataStats(
        Long start,
        Long end,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles,
        Tags tags,
        List<String> metricNames,
        Boolean stacked);

    /**
     * Add data points for multiple counters.
     *
     * @param counters
     * @return
     */
    ClientResponse<Empty> addCounterData(List<Metric<Long>> counters);

    /**
     * Fetches data points from one or more metrics that are determined using either a tags filter or a list of metric names.
     * The time range between start and end is divided into buckets of equal size (i.e., duration)
     * using either the buckets or bucketDuration parameter.
     * Functions are applied tothe data points in each bucket to produce statistics or aggregated metrics.
     *
     * @param start
     * @param end
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @param tags
     * @param metricNames
     * @param stacked
     * @return
     */
    ClientResponse<List<NumericBucketPoint>> findCounterStats(
        Long start,
        Long end,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles,
        Tags tags,
        List<String> metricNames,
        Boolean stacked);

    /**
     * Retrieve counter type’s tag values
     *
     * @param tags
     * @return
     */
    ClientResponse<Map<String, List<String>>> findCounterMetrics(Tags tags);

    /**
     * Retrieve a counter definition.
     *
     * @param id
     * @return
     */
    ClientResponse<Metric<Long>> getCounter(String id);

    /**
     * Retrieve counter rate data points.
     *
     * @param id
     * @param start
     * @param end
     * @param limit
     * @param order
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @return
     */
    ClientResponse<List<NumericBucketPoint>> findCounterRate(
        String id,
        Long start,
        Long end,
        Integer limit,
        Order order,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles);

    /**
     * Retrieve stats for counter rate data points.
     *
     * @param id
     * @param start
     * @param end
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @return
     */
    ClientResponse<List<NumericBucketPoint>> findCounterRateStats(
        String id,
        Long start,
        Long end,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles);

    /**
     * Retrieve counter data points.
     *
     * @param id
     * @param start
     * @param end
     * @param limit
     * @param order
     * @return
     */
    ClientResponse<List<DataPoint<Long>>> findCounterData(
        String id,
        Long start,
        Long end,
        Integer limit,
        Order order);

    /**
     * Add data for a single counter.
     *
     * @param id
     * @param data
     * @return
     */
    ClientResponse<Empty> createCounterData(String id, List<DataPoint<Long>> data);

    /**
     * Retrieve counter data points.
     *
     * @param id
     * @param start
     * @param end
     * @param fromEarliest
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @param limit
     * @param order
     * @return
     */
    ClientResponse<List<NumericBucketPoint>> findCounterMetricStats(
        String id,
        Long start,
        Long end,
        Boolean fromEarliest,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles,
        Integer limit,
        Order order);

    /**
     * Fetches data points and groups them into buckets based on one or more tag filters.
     * The data points in each bucket are then transformed into aggregated (i.e., bucket) data points.
     *
     * @param id
     * @param tags
     * @param start
     * @param end
     * @param percentiles
     * @return
     */
    ClientResponse<Map<String, String>> getCounterMetricStatsTags(
        String id,
        Tags tags,
        Long start,
        Long end,
        Percentiles percentiles);

    /**
     * Retrieve tags associated with the metric definition.
     *
     * @param id
     * @return
     */
    ClientResponse<Map<String, String>> getCounterMetricTags(String id);

    /**
     * Update tags associated with the metric definition.
     *
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> updateCountersMetricTags(String id, Map<String, String> tags);

    /**
     * Delete tags associated with the metric definition.
     *
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> deleteCounterMetricTags(String id, Tags tags);
}
