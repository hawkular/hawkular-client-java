/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
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

import java.util.List;
import java.util.Map;

import org.hawkular.alerts.api.model.paging.Order;
import org.hawkular.client.ClientResponse;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.MixedMetricsRequest;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.Tenant;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

public interface MetricsClient {

    public enum RESPONSE_CODE {
        GET_SUCCESS(200),
        CREATE_SUCCESS(201),
        ADD_SUCCESS(201),
        REGISTER_SUCCESS(201),
        UPDATE_SUCCESS(204),
        DELETE_SUCCESS(204),
        REMOVE_SUCCESS(204);

        private int code;

        private RESPONSE_CODE(int code) {
            this.code = code;
        }

        public int value() {
            return this.code;
        }
    }

    /* Availability Api*/

    ClientResponse<String> createAvailabilityMetric(Metric<AvailabilityType> metric);

    ClientResponse<List<Metric<AvailabilityType>>> findAvailabilityMetrics(Tags tags);

    ClientResponse<Metric<AvailabilityType>> getAvailabilityMetric(String id);

    ClientResponse<Map<String, String>> getAvailabilityMetricTags(String id);

    ClientResponse<String> updateAvailabilityMetricTags(String id, Map<String, String> tags);

    ClientResponse<String> deleteAvailabilityMetricTags(String id, Tags tags);

    ClientResponse<String> addAvailabilityDataForMetric(String id, List<DataPoint<AvailabilityType>> data);

    ClientResponse<String> addAvailabilityData(List<Metric<AvailabilityType>> data);

    ClientResponse<List<DataPoint<AvailabilityType>>> findAvailabilityData(
            String id,
            Long start,
            Long end,
            Integer bucketsCount,
            Duration bucketDuration,
            Boolean distinct,
            Integer limit,
            Order order);

    ClientResponse<List<DataPoint<AvailabilityType>>> findAvailabilityData(String id);

    /* Counter Api */

    ClientResponse<String> createCounter(Metric<Long> metric);

    ClientResponse<List<Metric<Long>>> findCounterMetrics(Tags tags);

    ClientResponse<Metric<Long>> getCounter(String id);

    ClientResponse<Map<String, String>> getCounterMetricTags(String id);

    ClientResponse<String> updateCountersMetricTags(String id, Map<String, String> tags);

    ClientResponse<String> deleteCounterMetricTags(String id, Tags tags);

    ClientResponse<String> addCounterData(List<Metric<Long>> counters);

    ClientResponse<String> addCounterDataForMetric(String id, List<DataPoint<Long>> data);

    ClientResponse<List<DataPoint<Long>>> findCounterData(
            String id,
            Long start,
            Long end,
            Boolean fromEarliest,
            Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles,
            Integer limit,
            Order order);

    ClientResponse<List<DataPoint<Long>>> findCounterData(String id);

    ClientResponse<List<DataPoint<Long>>> findCounterRate(
            String id,
            Long start,
            Long end,
            Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles);

    ClientResponse<List<NumericBucketPoint>> findCounterDataStats(
            final Long start,
            final Long end,
            Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles,
            Tags tags,
            List<String> metricNames,
            Boolean stacked);

    ClientResponse<List<NumericBucketPoint>> findCounterRateDataStats(
            final Long start,
            final Long end,
            Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles,
            Tags tags,
            List<String> metricNames,
            Boolean stacked);

    /* Gauge Api */

    ClientResponse<String> createGaugeMetric(Metric<Double> metric);

    ClientResponse<List<Metric<Double>>> findGaugeMetrics(Tags tags);

    ClientResponse<Metric<Double>> getGaugeMetric(String id);

    ClientResponse<Map<String, String>> getGaugeMetricTags(String id);

    ClientResponse<String> updateGaugeMetricTags(String id, Map<String, String> tags);

    ClientResponse<String> deleteGaugeMetricTags(String id, Tags tags);

    ClientResponse<String> addGaugeDataForMetric(String id, List<DataPoint<Double>> data);

    ClientResponse<String> addGaugeData(List<Metric<Double>> gauges);

    ClientResponse<List<DataPoint<Double>>> findGaugeDataWithId(
            String id,
            Long start,
            Long end,
            Boolean fromEarliest,
            Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles,
            Integer limit,
            Order order);

    ClientResponse<List<DataPoint<Double>>> findGaugeDataWithId(String id);

    ClientResponse<List<DataPoint<Double>>> findGaugeData(
            final Long start,
            final Long end,
            Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles,
            Tags tags,
            List<String> metricNames,
            Boolean stacked);

    ClientResponse<List<Object>> findGaugeDataPeriods(
            String id,
            Long start,
            Long end,
            double threshold,
            String operator);

    ClientResponse<String> ping();

    /* tenant API*/
    ClientResponse<List<Tenant>> getTenants();

    ClientResponse<String> createTenant(Tenant tenant);

    /* Metrics Api*/
    ClientResponse<String> createMetric(String tenantId, Metric<?> metric);

    ClientResponse<List<Metric<?>>> findMetrics(MetricType<?> metricType, Tags tags, String id);

    ClientResponse<String> addMetricsData(MixedMetricsRequest metricsRequest);

}
