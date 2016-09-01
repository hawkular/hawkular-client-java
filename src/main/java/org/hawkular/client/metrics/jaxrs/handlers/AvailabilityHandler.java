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
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Tags;

/**
 * Availability API
 * http://www.hawkular.org/docs/rest/rest-metrics.html#_availability
 */
@Path("/hawkular/metrics/availability")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AvailabilityHandler {

    @GET
    @Path("/")
    Response findAvailabilityMetrics(@QueryParam("tags") Tags tags);

    @POST
    @Path("/")
    Response createAvailabilityMetric(@QueryParam("overwrite") Boolean overwrite, Metric<AvailabilityType> metric);

    @POST
    @Path("/raw")
    Response addAvailabilityData(List<Metric<AvailabilityType>> data);

    @GET
    @Path("/tags/{tags}")
    Response getGaugeTags(@PathParam("tags") Tags tags);

    @GET
    @Path("/{id}")
    Response getAvailabilityMetric(@PathParam("id") String id);

    @GET
    @Path("/{id}/raw")
    Response findAvailabilityData(
        @PathParam("id") String id,
        @QueryParam("start") String start,
        @QueryParam("end") String end,
        @QueryParam("distinct") Boolean distinct,
        @QueryParam("limit") Integer limit,
        @QueryParam("order") Order order);

    @POST
    @Path("/{id}/raw")
    Response addAvailabilityDataForMetric(@PathParam("id") String id, List<DataPoint<AvailabilityType>> data);

    @GET
    @Path("/{id}/stats")
    Response findAvailabilityStats(
        @PathParam("id") String id,
        @QueryParam("start") String start,
        @QueryParam("end") String end,
        @QueryParam("buckets") Integer buckets,
        @QueryParam("bucketDuration") Duration bucketDuration);

    @GET
    @Path("/{id}/tags")
    Response getAvailabilityMetricTags(@PathParam("id") String id);

    @PUT
    @Path("/{id}/tags")
    Response updateAvailabilityMetricTags(@PathParam("id") String id, Map<String, String> tags);

    @DELETE
    @Path("/{id}/tags/{tags}")
    Response deleteAvailabilityMetricTags(@PathParam("id") String id, @PathParam("tags") Tags tags);
}
