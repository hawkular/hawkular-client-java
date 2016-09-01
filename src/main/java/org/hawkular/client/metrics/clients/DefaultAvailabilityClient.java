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
import org.hawkular.client.metrics.jaxrs.handlers.AvailabilityHandler;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.metrics.model.AvailabilityBucketPoint;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Tags;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultAvailabilityClient extends BaseClient<AvailabilityHandler> implements AvailabilityClient {

    public DefaultAvailabilityClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultAvailabilityClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<AvailabilityHandler>(AvailabilityHandler.class));
    }

    @Override
    public ClientResponse<List<Metric<AvailabilityType>>> findAvailabilityMetrics(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findAvailabilityMetrics(tags);
            JavaType javaType = collectionResolver().get(List.class, Metric.class, AvailabilityType.class);

            return new DefaultClientResponse<List<Metric<AvailabilityType>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createAvailabilityMetric(Boolean overwrite, Metric<AvailabilityType> metric) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createAvailabilityMetric(overwrite, metric);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addAvailabilityData(List<Metric<AvailabilityType>> data) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addAvailabilityData(data);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, List<String>>> getGaugeTags(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getGaugeTags(tags);
            JavaType javaType = mapResolver().get(Map.class, String.class, List.class, String.class);

            return new DefaultClientResponse<Map<String, List<String>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Metric<AvailabilityType>> getAvailabilityMetric(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getAvailabilityMetric(id);
            JavaType javaType = simpleResolver().get(Metric.class, AvailabilityType.class);

            return new DefaultClientResponse<Metric<AvailabilityType>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<DataPoint<AvailabilityType>>> findAvailabilityData(
        String id, Long start, Long end, Boolean distinct, Integer limit, Order order) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findAvailabilityData(id, start, end, distinct, limit, order);
            JavaType javaType = collectionResolver().get(List.class, DataPoint.class, AvailabilityType.class);

            return new DefaultClientResponse<List<DataPoint<AvailabilityType>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addAvailabilityDataForMetric(String id, List<DataPoint<AvailabilityType>> data) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addAvailabilityDataForMetric(id, data);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<AvailabilityBucketPoint>> findAvailabilityStats(
        String id, Long start, Long end, Integer buckets, Duration bucketDuration) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findAvailabilityStats(id, start, end, buckets, bucketDuration);
            JavaType javaType = collectionResolver().get(List.class, AvailabilityBucketPoint.class);

            return new DefaultClientResponse<List<AvailabilityBucketPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, String>> getAvailabilityMetricTags(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getAvailabilityMetricTags(id);
            JavaType javaType = mapResolver().get(Map.class, String.class, String.class);

            return new DefaultClientResponse<Map<String, String>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateAvailabilityMetricTags(String id, Map<String, String> tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateAvailabilityMetricTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteAvailabilityMetricTags(String id, Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteAvailabilityMetricTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
