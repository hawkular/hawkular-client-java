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
import org.hawkular.client.metrics.jaxrs.handlers.MetricHandler;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.MixedMetricsRequest;
import org.hawkular.metrics.model.param.Tags;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultMetricClient extends BaseClient<MetricHandler> implements MetricClient {

    public DefaultMetricClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultMetricClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<MetricHandler>(MetricHandler.class));
    }

    @Override
    public ClientResponse<List<Metric<?>>> findMetrics(MetricType<?> metricType, Tags tags, String id) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findMetrics(metricType, tags, id);
            JavaType javaType = collectionResolver().get(List.class, Metric.class);

            return new DefaultClientResponse<List<Metric<?>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createMetric(Boolean overwrite, Metric<?> metric) {

        Response serverResponse = null;

        try {
            serverResponse = restApi().createMetric(overwrite, metric);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_201);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addMetricsData(MixedMetricsRequest metricsRequest) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addMetricsData(metricsRequest);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Map<String, List<String>>> findMetricsTags(Tags tags, MetricType<?> metricType) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findMetricsTags(tags, metricType);
            JavaType javaType = mapResolver().get(Map.class, String.class, List.class, String.class);

            return new DefaultClientResponse<Map<String, List<String>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
