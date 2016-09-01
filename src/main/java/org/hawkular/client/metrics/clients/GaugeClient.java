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
import org.hawkular.metrics.model.TaggedBucketPoint;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

public interface GaugeClient {

    /**
     * Find tenant’s metric definitions.
     *
     * @param tags
     * @return
     */
    ClientResponse<List<Metric<Double>>> findGaugeMetrics(Tags tags);

    /**
     * Create gauge metric.
     *
     * @param overwrite
     * @param metric
     * @return
     */
    ClientResponse<Empty> createGaugeMetric(Boolean overwrite, Metric<Double> metric);

    /**
     * Find data for multiple metrics.
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
    ClientResponse<List<NumericBucketPoint>> findGaugeRateStats(
        Long start,
        Long end,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles,
        Tags tags,
        List<String> metricNames,
        Boolean stacked);

    /**
     * Add data for multiple gauge metrics in a single call.
     *
     * @param gauges
     * @return
     */
    ClientResponse<Empty> addGaugeData(List<Metric<Double>> gauges);

    /**
     * Find stats for multiple metrics.
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
    ClientResponse<List<NumericBucketPoint>> findGaugeStats(
        Long start,
        Long end,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles,
        Tags tags,
        List<String> metricNames,
        Boolean stacked);

    /**
     * Retrieve gauge type’s tag values
     *
     * @param tags
     * @return
     */
    ClientResponse<Map<String, List<String>>> getGaugeMetricTagValues(Tags tags);

    /**
     * Retrieve single metric definition.
     *
     * @param id
     * @return
     */
    ClientResponse<Metric<Double>> getGaugeMetric(String id);

    /**
     * Find condition periods.
     *
     * @param id
     * @param start
     * @param end
     * @param threshold
     * @param operator
     * @return
     */
    ClientResponse<List<Long[]>> findGaugeDataPeriods(
        String id,
        Long start,
        Long end,
        Double threshold,
        String operator);

    /**
     * Retrieve gauge rate data points.
     *
     * @param id
     * @param start
     * @param end
     * @param limit
     * @param order
     * @return
     */
    ClientResponse<List<DataPoint<Double>>> getGaugeRate(
        String id,
        Long start,
        Long end,
        Integer limit,
        Order order);

    /**
     * Retrieve stats for gauge rate data points.
     *
     * @param id
     * @param start
     * @param end
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @return
     */
    ClientResponse<List<NumericBucketPoint>> getGaugeRateStats(
        String id,
        Long start,
        Long end,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles);

    /**
     * Retrieve raw gauge data.
     *
     * @param id
     * @param start
     * @param end
     * @param fromEarliest
     * @param limit
     * @param order
     * @return
     */
    ClientResponse<List<DataPoint<Double>>> findGaugeDataWithId(
        String id,
        Long start,
        Long end,
        Boolean fromEarliest,
        Integer limit,
        Order order);

    /**
     * Add data for a single gauge metric.
     *
     * @param id
     * @param data
     * @return
     */
    ClientResponse<Empty> addGaugeDataForMetric(String id, List<DataPoint<Double>> data);

    /**
     * Retrieve gauge data.
     *
     * @param id
     * @param start
     * @param end
     * @param fromEarliest
     * @param bucketsCount
     * @param bucketDuration
     * @param percentiles
     * @return
     */
    ClientResponse<List<DataPoint<Double>>> getGaugeStats(
        String id,
        Long start,
        Long end,
        Boolean fromEarliest,
        Integer bucketsCount,
        Duration bucketDuration,
        Percentiles percentiles);

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
    ClientResponse<Map<String, TaggedBucketPoint>> getGaugeStatsTags(
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
    ClientResponse<Map<String, String>> getGaugeMetricTags(String id);

    /**
     * Update tags associated with the metric definition.
     *
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> updateGaugeMetricTags(String id, Map<String, String> tags);

    /**
     * Delete tags associated with the metric definition.
     *
     * @param id
     * @param tags
     * @return
     */
    ClientResponse<Empty> deleteGaugeMetricTags(String id, Tags tags);
}
