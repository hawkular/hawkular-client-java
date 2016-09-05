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

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.metrics.jaxrs.handlers.CounterHandler;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.TaggedBucketPoint;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultCounterClient extends BaseClient<CounterHandler> implements CounterClient {

    public DefaultCounterClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultCounterClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<CounterHandler>(CounterHandler.class));
    }

    @Override
    public ClientResponse<List<Metric<Long>>> getCounters(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getCounters(tags);
            JavaType javaType = collectionResolver().get(List.class, Metric.class, Long.class);

            return new DefaultClientResponse<List<Metric<Long>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createCounter(Boolean overwrite, Metric<Long> metric) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createCounter(overwrite, metric);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterRateDataStats(
        String start, String end, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Tags tags, List<String> metricNames, Boolean stacked) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterRateDataStats(start, end, bucketsCount, bucketDuration, percentiles, tags, metricNames, stacked);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<List<NumericBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addCounterData(List<Metric<Long>> counters) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addCounterData(counters);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterStats(
        String start, String end, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Tags tags, List<String> metricNames, Boolean stacked) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterStats(start, end, bucketsCount, bucketDuration, percentiles, tags, metricNames, stacked);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<List<NumericBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, List<String>>> findCounterMetrics(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterMetrics(tags);
            JavaType javaType = mapResolver().get(Map.class, String.class, List.class, String.class);

            return new DefaultClientResponse<Map<String, List<String>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Metric<Long>> getCounter(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getCounter(id);
            JavaType javaType = simpleResolver().get(Metric.class, Long.class);

            return new DefaultClientResponse<Metric<Long>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterRate(
        String id, String start, String end, Integer limit, Order order, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterRate(id, start, end, limit, order, bucketsCount, bucketDuration, percentiles);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<List<NumericBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterRateStats(
        String id, String start, String end, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterRateStats(id, start, end, bucketsCount, bucketDuration, percentiles);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<List<NumericBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<DataPoint<Long>>> findCounterData(String id, String start, String end, Integer limit, Order order) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterData(id, start, end, limit, order);
            JavaType javaType = collectionResolver().get(List.class, DataPoint.class, Long.class);

            return new DefaultClientResponse<List<DataPoint<Long>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createCounterData(String id, List<DataPoint<Long>> data) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createCounterData(id, data);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<NumericBucketPoint>> findCounterMetricStats(
        String id, String start, String end, Boolean fromEarliest, Integer bucketsCount, Duration bucketDuration, Percentiles percentiles, Integer limit, Order order) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findCounterMetricStats(id, start, end, fromEarliest, bucketsCount, bucketDuration, percentiles, limit, order);
            JavaType javaType = collectionResolver().get(List.class, NumericBucketPoint.class);

            return new DefaultClientResponse<List<NumericBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, TaggedBucketPoint>> getCounterMetricStatsTags(String id, Tags tags, String start, String end, Percentiles percentiles) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getCounterMetricStatsTags(id, tags, start, end, percentiles);
            JavaType javaType = mapResolver().get(Map.class, String.class, TaggedBucketPoint.class);

            return new DefaultClientResponse<Map<String, TaggedBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, String>> getCounterMetricTags(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getCounterMetricTags(id);
            JavaType javaType = mapResolver().get(Map.class, String.class, String.class);

            return new DefaultClientResponse<Map<String, String>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateCountersMetricTags(String id, Map<String, String> tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateCountersMetricTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteCounterMetricTags(String id, Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteCounterMetricTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
