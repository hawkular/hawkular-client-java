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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hawkular.metrics.core.api.Availability;
import org.hawkular.metrics.core.api.AvailabilityMetric;
import org.hawkular.metrics.core.api.NumericData;
import org.hawkular.metrics.core.api.NumericMetric;
import org.hawkular.metrics.core.api.Tenant;

@Path("/hawkular-metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MetricsRestApi {

    @GET
    @Path ("/tenants")
    List<Tenant> findTenants();

    @POST
    @Path ("/tenants")
    void createTenant(Tenant tenant);

    @POST
    @Path ("/{tenantId}/metrics/numeric")
    void createNumericMetric(@PathParam ("tenantId") String tenantId, NumericMetric metric);

    @POST
    @Path ("/{tenantId}/metrics/numeric/{id}/data")
    void addNumericMetricData(  @PathParam("tenantId") String tenantId,
                                @PathParam("id") String metricId,
                                List<NumericData> data);

    @GET
    @Path ("/{tenantId}/metrics/numeric/{id}/data")
    List<NumericData> getNumericMetricData(@PathParam("tenantId") String tenantId,
                                           @PathParam("id") String metricId,
                                           @QueryParam("start") long startTime,
                                           @QueryParam("end") long endTime
                                           );

    @GET
    @Path("/{tenantId}/metrics/numeric/{id}/tags")
    List<NumericData> getNumericMetricData(String tenantId, String metricId);

    @GET
    @Path("/{tenantId}/metrics/numeric/{id}/tags")
    NumericMetric getNumericMetricTags(@PathParam ("tenantId") String tenantId,
                                       @PathParam("id") String metricId);

    @POST
    @Path("/{tenantId}/metrics/availability")
    void createAvailability(@PathParam("tenantId") String tenantId, AvailabilityMetric metric);

    @GET
    @Path("/{tenantId}/availability")
    String findAvailabilityByTags(@PathParam("tenantId")String tenantId, @QueryParam("tags") String csvTags);

    @POST
    @Path("/{tenantId}/metrics/availability/{metricId}/data")
    void addAvailabilityData(@PathParam("tenantId")String tenantId,
                                         @PathParam("metricId") String metricId,
                                         List<Availability> data);

    @GET
    @Path("/{tenantId}/metrics/availability/{metricId}/data")
    List<Availability> getAvailabilityData(@PathParam("tenantId") String tenantId,
                                           @PathParam("metricId") String metricId);
}
