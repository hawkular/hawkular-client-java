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

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hawkular.client.metrics.model.Order;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

/**
 * Counter API
 * http://www.hawkular.org/docs/rest/rest-metrics.html#_counter
 */
@Path("/hawkular/metrics/counters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CounterHandler {

    @GET
    @Path("/")
    Response getCounters(@QueryParam("tags") Tags tags);

    @POST
    @Path("/")
    Response createCounter(@QueryParam("overwrite") Boolean overwrite, Metric<Long> metric);

    @GET
    @Path("/rate/stats")
    Response findCounterRateDataStats(
        @QueryParam("start") final Long start,
        @QueryParam("end") final Long end,
        @QueryParam("buckets") Integer bucketsCount,
        @QueryParam("bucketDuration") Duration bucketDuration,
        @QueryParam("percentiles") Percentiles percentiles,
        @QueryParam("tags") Tags tags,
        @QueryParam("metrics") List<String> metricNames,
        @QueryParam("stacked") Boolean stacked);

    @POST
    @Path("/raw")
    Response addCounterData(List<Metric<Long>> counters);

    @GET
    @Path("/stats")
    Response findCounterStats(
        @QueryParam("start") final Long start,
        @QueryParam("end") final Long end,
        @QueryParam("buckets") Integer bucketsCount,
        @QueryParam("bucketDuration") Duration bucketDuration,
        @QueryParam("percentiles") Percentiles percentiles,
        @QueryParam("tags") Tags tags,
        @QueryParam("metrics") List<String> metricNames,
        @QueryParam("stacked") Boolean stacked);

    @GET
    @Path("/tags/{tags}")
    Response findCounterMetrics(@PathParam("tags") Tags tags);

    @GET
    @Path("/{id}")
    Response getCounter(@PathParam("id") String id);

    @GET
    @Path("/{id}/rate")
    Response findCounterRate(
        @PathParam("id") String id,
        @QueryParam("start") Long start,
        @QueryParam("end") Long end,
        @QueryParam("limit") Integer limit,
        @QueryParam("order") Order order,
        @QueryParam("buckets") Integer bucketsCount,
        @QueryParam("bucketDuration") Duration bucketDuration,
        @QueryParam("percentiles") Percentiles percentiles);

    @GET
    @Path("/{id}/rate/stats")
    Response findCounterRateStats(
        @PathParam("id") String id,
        @QueryParam("start") Long start,
        @QueryParam("end") Long end,
        @QueryParam("buckets") Integer bucketsCount,
        @QueryParam("bucketDuration") Duration bucketDuration,
        @QueryParam("percentiles") Percentiles percentiles);

    @GET
    @Path("/{id}/raw")
    Response findCounterData(
        @PathParam("id") String id,
        @QueryParam("start") Long start,
        @QueryParam("end") Long end,
        @QueryParam("limit") Integer limit,
        @QueryParam("order") Order order);

    @POST
    @Path("/{id}/raw")
    Response createCounterData(@PathParam("id") String id, List<DataPoint<Long>> data);

    @GET
    @Path("/{id}/stats")
    Response findCounterMetricStats(
        @PathParam("id") String id,
        @QueryParam("start") Long start,
        @QueryParam("end") Long end,
        @QueryParam("fromEarliest") Boolean fromEarliest,
        @QueryParam("buckets") Integer bucketsCount,
        @QueryParam("bucketDuration") Duration bucketDuration,
        @QueryParam("percentiles") Percentiles percentiles,
        @QueryParam("limit") Integer limit,
        @QueryParam("order") Order order);

    @GET
    @Path("/{id}/stats/tags/{tags}")
    Response getCounterMetricStatsTags(
        @PathParam("id") String id,
        @PathParam("tags") Tags tags,
        @QueryParam("start") Long start,
        @QueryParam("end") Long end,
        @QueryParam("percentiles") Percentiles percentiles);

    @GET
    @Path("/{id}/tags")
    Response getCounterMetricTags(@PathParam("id") String id);

    @PUT
    @Path("/{id}/tags")
    Response updateCountersMetricTags(@PathParam("id") String id, Map<String, String> tags);

    @DELETE
    @Path("/{id}/tags/{tags}")
    Response deleteCounterMetricTags(@PathParam("id") String id, @PathParam("tags") Tags tags);

}
