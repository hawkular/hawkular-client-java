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
package org.hawkular.client.metrics.jaxrs.handlers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.MixedMetricsRequest;
import org.hawkular.metrics.model.param.Tags;

/**
 * Metrics API
 * http://www.hawkular.org/docs/rest/rest-metrics.html#_metric
 */
@Path("/hawkular/metrics/metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MetricHandler {

    @GET
    @Path("/")
    Response findMetrics(
        @QueryParam("type") MetricType<?> metricType,
        @QueryParam("tags") Tags tags,
        @QueryParam("id") String id);

    @POST
    @Path("/")
    Response createMetric(@QueryParam("overwrite") Boolean overwrite, Metric<?> metric);

    @POST
    @Path("/raw")
    Response addMetricsData(MixedMetricsRequest metricsRequest);

    @GET
    @Path("/tags/{tags}")
    Response findMetricsTags(@PathParam("tags") Tags tags, @QueryParam("type") MetricType<?> metricType);
}
