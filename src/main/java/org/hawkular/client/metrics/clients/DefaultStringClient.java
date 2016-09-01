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
import org.hawkular.client.metrics.jaxrs.handlers.StringHandler;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Tags;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultStringClient extends BaseClient<StringHandler> implements StringClient {

    public DefaultStringClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultStringClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<StringHandler>(StringHandler.class));
    }

    @Override
    public ClientResponse<List<Metric>> findMetricsDefinitions(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findMetricsDefinitions(tags);
            JavaType javaType = collectionResolver().get(List.class, Metric.class);

            return new DefaultClientResponse<List<Metric>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createStringMetric(Boolean overwrite, Metric<String> metric) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createStringMetric(overwrite, metric);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createStringMetric(List<Metric<String>> metrics) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createStringMetric(metrics);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, List<String>>> findMetricTags(Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findMetricTags(tags);
            JavaType javaType = mapResolver().get(Map.class, String.class, List.class, String.class);

            return new DefaultClientResponse<Map<String, List<String>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Metric> getMetricDefinitions(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getMetricDefinitions(id);
            JavaType javaType = simpleResolver().get(Metric.class);

            return new DefaultClientResponse<Metric>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<DataPoint>> getMetricDefinitionsData(
        String id, String start, String end, Boolean distinct, Integer limit, Order order) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getMetricDefinitionsData(id, start, end, distinct, limit, order);
            JavaType javaType = collectionResolver().get(List.class, DataPoint.class);

            return new DefaultClientResponse<List<DataPoint>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createMetricDefinitionsData(String id, List<DataPoint<String>> dataPoints) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createMetricDefinitionsData(id, dataPoints);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, String>> findMetricDefinitionsTags(String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findMetricDefinitionsTags(id);
            JavaType javaType = mapResolver().get(Map.class, String.class, String.class);

            return new DefaultClientResponse<Map<String, String>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateMetricDefinitionsTags(String id, Map<String, String> tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateMetricDefinitionsTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteMetricDefinitionsTags(String id, Tags tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteMetricDefinitionsTags(id, tags);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
