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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hawkular.alerts.api.model.paging.Order;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.MixedMetricsRequest;
import org.hawkular.metrics.model.Tenant;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;

@Path("/hawkular/metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MetricsRestApi {

    /* Availability Api*/
    @POST
    @Path("/availability")
    Response createAvailabilityMetric(Metric<AvailabilityType> metric);

    @GET
    @Path("/availability")
    Response findAvailabilityMetrics(@QueryParam("tags") Tags tags);

    @GET
    @Path("/availability/{id}")
    Response getAvailabilityMetric(@PathParam("id") String id);

    @GET
    @Path("/availability/{id}/tags")
    Response getAvailabilityMetricTags(@PathParam("id") String id);

    @PUT
    @Path("/availability/{id}/tags")
    Response updateAvailabilityMetricTags(@PathParam("id") String id, Map<String, String> tags);

    @DELETE
    @Path("/availability/{id}/tags/{tags}")
    Response deleteAvailabilityMetricTags(@PathParam("id") String id, @PathParam("tags") Tags tags);

    @POST
    @Path("/availability/{id}/data")
    Response addAvailabilityDataForMetric(@PathParam("id") String id, List<DataPoint<AvailabilityType>> data);

    @POST
    @Path("/availability/data")
    Response addAvailabilityData(List<Metric<AvailabilityType>> data);

    @GET
    @Path("/availability/{id}/data")
    Response findAvailabilityData(
            @PathParam("id") String id,
            @QueryParam("start") Long start,
            @QueryParam("end") Long end,
            @QueryParam("buckets") Integer bucketsCount,
            @QueryParam("bucketDuration") Duration bucketDuration,
            @QueryParam("distinct") Boolean distinct,
            @QueryParam("limit") Integer limit,
            @QueryParam("order") Order order);

    /* Counter Api */

    @POST
    @Path("/counters")
    Response createCounter(Metric<Long> metric);

    @GET
    @Path("/counters/{tags}")
    Response findCounterMetrics(@QueryParam("tags") Tags tags);

    @GET
    @Path("/counters/{id}")
    Response getCounter(@PathParam("id") String id);

    @GET
    @Path("/counters/{id}/tags")
    Response getCounterMetricTags(@PathParam("id") String id);

    @PUT
    @Path("/counters/{id}/tags")
    Response updateCountersMetricTags(@PathParam("id") String id, Map<String, String> tags);

    @DELETE
    @Path("/counters/{id}/tags/{tags}")
    Response deleteCounterMetricTags(@PathParam("id") String id, @PathParam("tags") Tags tags);

    @POST
    @Path("/counters/data")
    Response addCounterData(List<Metric<Long>> counters);

    @POST
    @Path("/counters/{id}/data")
    Response addCounterDataForMetric(@PathParam("id") String id, List<DataPoint<Long>> data);

    @GET
    @Path("/counters/{id}/data")
    Response findCounterData(
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
    @Path("/counters/{id}/rate")
    Response findCounterRate(
            @PathParam("id") String id,
            @QueryParam("start") Long start,
            @QueryParam("end") Long end,
            @QueryParam("buckets") Integer bucketsCount,
            @QueryParam("bucketDuration") Duration bucketDuration,
            @QueryParam("percentiles") Percentiles percentiles);

    @GET
    @Path("/counters/data")
    Response findCounterDataStats(
            @QueryParam("start") final Long start,
            @QueryParam("end") final Long end,
            @QueryParam("buckets") Integer bucketsCount,
            @QueryParam("bucketDuration") Duration bucketDuration,
            @QueryParam("percentiles") Percentiles percentiles,
            @QueryParam("tags") Tags tags,
            @QueryParam("metrics") List<String> metricNames,
            @QueryParam("stacked") Boolean stacked);

    @GET
    @Path("/counters/rate")
    Response findCounterRateDataStats(
            @QueryParam("start") final Long start,
            @QueryParam("end") final Long end,
            @QueryParam("buckets") Integer bucketsCount,
            @QueryParam("bucketDuration") Duration bucketDuration,
            @QueryParam("percentiles") Percentiles percentiles,
            @QueryParam("tags") Tags tags,
            @QueryParam("metrics") List<String> metricNames,
            @QueryParam("stacked") Boolean stacked);

    /* Gauge Api */

    @POST
    @Path("/gauges")
    Response createGaugeMetric(Metric<Double> metric);

    @GET
    @Path("/gauges")
    Response findGaugeMetrics(@QueryParam("tags") Tags tags);

    @GET
    @Path("/gauges/{id}")
    Response getGaugeMetric(@PathParam("id") String id);

    @GET
    @Path("/gauges/{id}/tags")
    Response getGaugeMetricTags(@PathParam("id") String id);

    @PUT
    @Path("/gauges/{id}/tags")
    Response updateGaugeMetricTags(@PathParam("id") String id, Map<String, String> tags);

    @DELETE
    @Path("/gauges/{id}/tags/{tags}")
    Response deleteGaugeMetricTags(@PathParam("id") String id, @PathParam("tags") Tags tags);

    @POST
    @Path("/gauges/{id}/data")
    Response addGaugeDataForMetric(@PathParam("id") String id, List<DataPoint<Double>> data);

    @POST
    @Path("/gauges/data")
    Response addGaugeData(List<Metric<Double>> gauges);

    @GET
    @Path("/gauges/{id}/data")
    Response findGaugeDataWithId(
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
    @Path("/gauges/data")
    Response findGaugeData(
            @QueryParam("start") final Long start,
            @QueryParam("end") final Long end,
            @QueryParam("buckets") Integer bucketsCount,
            @QueryParam("bucketDuration") Duration bucketDuration,
            @QueryParam("percentiles") Percentiles percentiles,
            @QueryParam("tags") Tags tags,
            @QueryParam("metrics") List<String> metricNames,
            @QueryParam("stacked") Boolean stacked);

    @GET
    @Path("/gauges/{id}/periods")
    Response findGaugeDataPeriods(
            @PathParam("id") String id,
            @QueryParam("start") Long start,
            @QueryParam("end") Long end,
            @QueryParam("threshold") double threshold,
            @QueryParam("op") String operator);

    @GET
    @Path("/ping")
    Response ping();

    /* Tenant Api */
    @GET
    @Path("/tenants")
    Response getTenants();

    @POST
    @Path("/tenants")
    Response createTenant(Tenant tenant);

    /* Metrics Api*/
    @POST
    @Path("/metrics")
    Response createMetric(
            @HeaderParam("Hawkular-Tenant") String tenantId,
            Metric<?> metric);

    @GET
    @Path("/metrics")
    Response findMetrics(
            @QueryParam("type") MetricType<?> metricType,
            @QueryParam("tags") Tags tags,
            @QueryParam("id") String id);

    @POST
    @Path("/metrics/data")
    Response addMetricsData(MixedMetricsRequest metricsRequest);
}
