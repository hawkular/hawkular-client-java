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

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.metrics.jaxrs.handlers.GaugeHandler;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.TaggedBucketPoint;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultGaugeClient extends BaseClient<GaugeHandler> implements GaugeClient {

    public DefaultGaugeClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<>(GaugeHandler.class));
    }

    @Override
    public ClientResponse<List<Metric<Double>>> findGaugeMetrics(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findGaugeMetrics(tags);
            JavaType javaType = collectionResolver().get(List.class, Metric.class, Double.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createGaugeMetric(Boolean overwrite, Metric<Double> metric) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createGaugeMetric(overwrite, metric);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findGaugeRateStats(
        String start, String end, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Tags tags, List<String> metricNames, Boolean stacked) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findGaugeRateStats(start, end, bucketsCount, bucketDuration, percentiles, tags, metricNames, stacked);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addGaugeData(List<Metric<Double>> gauges) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addGaugeData(gauges);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findGaugeStats(
        String start, String end, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Tags tags, List<String> metricNames, Boolean stacked) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findGaugeStats(start, end, bucketsCount, bucketDuration, percentiles, tags, metricNames, stacked);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, List<String>>> getGaugeMetricTagValues(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeMetricTagValues(tags);
            JavaType javaType = mapResolver().get(Map.class, String.class, List.class, String.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Metric<Double>> getGaugeMetric(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeMetric(id);
            JavaType javaType = simpleResolver().get(Metric.class, Double.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Long[]>> findGaugeDataPeriods(String id, String start, String end, Double threshold, String operator) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findGaugeDataPeriods(id, start, end, threshold, operator);
            JavaType javaType = collectionResolver().get(List.class, Long[].class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<DataPoint<Double>>> getGaugeRate(String id, String start, String end, Integer limit, Order order) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeRate(id, start, end, limit, order);
            JavaType javaType = collectionResolver().get(List.class, DataPoint.class, Double.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> getGaugeRateStats(
        String id, String start, String end, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeRateStats(id, start, end, bucketsCount, bucketDuration, percentiles);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<DataPoint<Double>>> findGaugeDataWithId(
        String id, String start, String end, Boolean fromEarliest, Integer limit, Order order) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findGaugeDataWithId(id, start, end, fromEarliest, limit, order);
            JavaType javaType = collectionResolver().get(List.class, DataPoint.class, Double.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addGaugeDataForMetric(String id, List<DataPoint<Double>> data) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addGaugeDataForMetric(id, data);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> getGaugeStats(
        String id, String start, String end, Boolean fromEarliest, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeStats(id, start, end, fromEarliest, bucketsCount, bucketDuration, percentiles);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, TaggedBucketPoint>> getGaugeStatsTags(String id, Tags tags, String start, String end, Percentiles percentiles) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeStatsTags(id, tags, start, end, percentiles);
            JavaType javaType = mapResolver().get(Map.class, String.class, TaggedBucketPoint.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, String>> getGaugeMetricTags(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeMetricTags(id);
            JavaType javaType = mapResolver().get(Map.class, String.class, String.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateGaugeMetricTags(String id, Map<String, String> tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateGaugeMetricTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteGaugeMetricTags(String id, Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteGaugeMetricTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
