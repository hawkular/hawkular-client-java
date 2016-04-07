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

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.hawkular.alerts.api.model.paging.Order;
import org.hawkular.client.BaseClient;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.RestFactory;
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

/**
 * Hawkular-Metrics client implementation
 * @author vnguyen, jkandasa
 *
 */
public class MetricsClientImpl extends BaseClient<MetricsRestApi> implements MetricsClient {

    public MetricsClientImpl(URI endpointUri, String username, String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<MetricsRestApi>(MetricsRestApi.class));
    }

    public MetricsClientImpl(URI endpointUri) throws Exception {
        super(endpointUri, new RestFactory<MetricsRestApi>(MetricsRestApi.class));
    }

    @Override
    public ClientResponse<String> ping() {
        return new ClientResponse<String>(String.class, restApi().ping(), RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Tenant>> getTenants() {
        return new ClientResponse<List<Tenant>>(Tenant.class, restApi().getTenants(),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<String> createTenant(Tenant tenant) {
        return new ClientResponse<String>(String.class, restApi().createTenant(tenant),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createMetric(String tenantId, Metric<?> metric) {
        return new ClientResponse<String>(String.class, restApi().createMetric(tenantId, metric),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Metric<?>>> findMetrics(MetricType<?> metricType, Tags tags, String id) {
        return new ClientResponse<List<Metric<?>>>(Metric.class,
                restApi().findMetrics(metricType, tags, id),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<String> addMetricsData(MixedMetricsRequest metricsRequest) {
        return new ClientResponse<String>(String.class, restApi().addMetricsData(metricsRequest),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createAvailabilityMetric(Metric<AvailabilityType> metric) {
        return new ClientResponse<String>(String.class, restApi().createAvailabilityMetric(metric),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Metric<AvailabilityType>>> findAvailabilityMetrics(Tags tags) {
        return new ClientResponse<List<Metric<AvailabilityType>>>(Metric.class, restApi()
                .findAvailabilityMetrics(tags), RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<Metric<AvailabilityType>> getAvailabilityMetric(String id) {
        return new ClientResponse<Metric<AvailabilityType>>(Metric.class, restApi().getAvailabilityMetric(id),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<Map<String, String>> getAvailabilityMetricTags(String id) {
        return new ClientResponse<Map<String, String>>(Map.class, restApi().getAvailabilityMetricTags(id),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateAvailabilityMetricTags(String id, Map<String, String> tags) {
        return new ClientResponse<String>(String.class, restApi().updateAvailabilityMetricTags(id, tags),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteAvailabilityMetricTags(String id, Tags tags) {
        return new ClientResponse<String>(String.class, restApi().deleteAvailabilityMetricTags(id, tags),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addAvailabilityDataForMetric(String id, List<DataPoint<AvailabilityType>> data) {
        return new ClientResponse<String>(String.class, restApi().addAvailabilityDataForMetric(id, data),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addAvailabilityData(List<Metric<AvailabilityType>> data) {
        return new ClientResponse<String>(String.class, restApi().addAvailabilityData(data),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<DataPoint<AvailabilityType>>> findAvailabilityData(String id, Long start, Long end,
            Integer bucketsCount, Duration bucketDuration, Boolean distinct, Integer limit, Order order) {
        return new ClientResponse<List<DataPoint<AvailabilityType>>>(DataPoint.class, restApi().findAvailabilityData(
                id, start, end,
                bucketsCount, bucketDuration, distinct, limit, order), RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<List<DataPoint<AvailabilityType>>> findAvailabilityData(String id) {
        return findAvailabilityData(id, null, null, null, null, null, null, null);
    }

    @Override
    public ClientResponse<String> createCounter(Metric<Long> metric) {
        return new ClientResponse<String>(String.class, restApi().createCounter(metric),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Metric<Long>>> findCounterMetrics(Tags tags) {
        return new ClientResponse<List<Metric<Long>>>(Map.class, restApi().findCounterMetrics(tags),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<Metric<Long>> getCounter(String id) {
        return new ClientResponse<Metric<Long>>(Metric.class, restApi().getCounter(id),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<Map<String, String>> getCounterMetricTags(String id) {
        return new ClientResponse<Map<String, String>>(Map.class, restApi().getCounterMetricTags(id),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateCountersMetricTags(String id, Map<String, String> tags) {
        return new ClientResponse<String>(String.class, restApi().updateCountersMetricTags(id, tags),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteCounterMetricTags(String id, Tags tags) {
        return new ClientResponse<String>(String.class, restApi().deleteCounterMetricTags(id, tags),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addCounterData(List<Metric<Long>> counters) {
        return new ClientResponse<String>(String.class, restApi().addCounterData(counters),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addCounterDataForMetric(String id, List<DataPoint<Long>> data) {
        return new ClientResponse<String>(String.class, restApi().addCounterDataForMetric(id, data),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<DataPoint<Long>>> findCounterData(String id, Long start, Long end,
            Boolean fromEarliest,
            Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Integer limit, Order order) {
        return new ClientResponse<List<DataPoint<Long>>>(DataPoint.class, restApi().findCounterData(id, start, end,
                fromEarliest, bucketsCount, bucketDuration, percentiles, limit, order),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<List<DataPoint<Long>>> findCounterData(String id) {
        return findCounterData(id, null, null, null, null, null, null, null, null);
    }

    @Override
    public ClientResponse<List<DataPoint<Long>>> findCounterRate(String id, Long start, Long end,
            Integer bucketsCount, Duration bucketDuration, Percentiles percentiles) {
        return new ClientResponse<List<DataPoint<Long>>>(DataPoint.class, restApi().findCounterRate(id, start, end,
                bucketsCount, bucketDuration, percentiles), RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterDataStats(Long start, Long end, Integer bucketsCount,
            Duration bucketDuration, Percentiles percentiles, Tags tags, List<String> metricNames, Boolean stacked) {
        return new ClientResponse<List<NumericBucketPoint>>(NumericBucketPoint.class, restApi().findCounterDataStats(
                start, end, bucketsCount, bucketDuration, percentiles, tags, metricNames, stacked),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterRateDataStats(Long start, Long end,
            Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Tags tags,
            List<String> metricNames, Boolean stacked) {
        return new ClientResponse<List<NumericBucketPoint>>(NumericBucketPoint.class, restApi()
                .findCounterRateDataStats(start, end, bucketsCount, bucketDuration, percentiles, tags, metricNames,
                        stacked), RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<String> createGaugeMetric(Metric<Double> metric) {
        return new ClientResponse<String>(String.class, restApi().createGaugeMetric(metric),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Metric<Double>>> findGaugeMetrics(Tags tags) {
        return new ClientResponse<List<Metric<Double>>>(Metric.class, restApi()
                .findGaugeMetrics(tags), RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<Metric<Double>> getGaugeMetric(String id) {
        return new ClientResponse<Metric<Double>>(Metric.class, restApi().getGaugeMetric(id),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<Map<String, String>> getGaugeMetricTags(String id) {
        return new ClientResponse<Map<String, String>>(Map.class, restApi().getGaugeMetricTags(id),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateGaugeMetricTags(String id, Map<String, String> tags) {
        return new ClientResponse<String>(String.class, restApi().updateGaugeMetricTags(id, tags),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteGaugeMetricTags(String id, Tags tags) {
        return new ClientResponse<String>(String.class, restApi().deleteGaugeMetricTags(id, tags),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addGaugeDataForMetric(String id, List<DataPoint<Double>> data) {
        return new ClientResponse<String>(String.class, restApi().addGaugeDataForMetric(id, data),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addGaugeData(List<Metric<Double>> gauges) {
        return new ClientResponse<String>(String.class, restApi().addGaugeData(gauges),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<DataPoint<Double>>> findGaugeDataWithId(String id, Long start, Long end,
            Boolean fromEarliest, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles,
            Integer limit, Order order) {
        return new ClientResponse<List<DataPoint<Double>>>(DataPoint.class, restApi().findGaugeDataWithId(id, start,
                end,
                fromEarliest, bucketsCount, bucketDuration, percentiles, limit, order),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<List<DataPoint<Double>>> findGaugeDataWithId(String id) {
        return findGaugeDataWithId(id, null, null, null, null, null, null, null, null);
    }

    @Override
    public ClientResponse<List<DataPoint<Double>>> findGaugeData(Long start, Long end, Integer bucketsCount,
            Duration bucketDuration,
            Percentiles percentiles, Tags tags, List<String> metricNames, Boolean stacked) {
        return new ClientResponse<List<DataPoint<Double>>>(DataPoint.class, restApi().findGaugeData(start, end,
                bucketsCount, bucketDuration, percentiles, tags, metricNames, stacked),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<List<Object>> findGaugeDataPeriods(String id, Long start, Long end, double threshold,
            String operator) {
        return new ClientResponse<List<Object>>(Object.class, restApi().findGaugeDataPeriods(id, start, end,
                threshold, operator), RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

}
