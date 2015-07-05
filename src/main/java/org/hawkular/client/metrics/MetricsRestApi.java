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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hawkular.client.metrics.model.AvailabilityDataPoint;
import org.hawkular.client.metrics.model.CounterDataPoint;
import org.hawkular.client.metrics.model.GaugeDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.metrics.model.TenantParam;


@Path("/hawkular/metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MetricsRestApi {

    @GET
    @Path ("/tenants")
    List<TenantParam> getTenants();

    @POST
    @Path ("/tenants")
    Response createTenant(TenantParam tenant);

    @POST
    @Path ("/gauges")
    void createGaugeMetric(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                                            MetricDefinition definition);

    @GET
    @Path ("/gauges/{id}")
    MetricDefinition getGaugeMetric(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                    @PathParam("id")                 String metricId);


    @POST
    @Path ("/gauges/{id}/data")
    void addGaugeData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                      @PathParam("id")                 String metricId,
                                                       List<GaugeDataPoint> data);

    @GET
    @Path ("/gauges/{id}/data")
    List<GaugeDataPoint> getGaugeData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                      @PathParam("id") String metricId
                                           );

    @GET
    @Path ("/gauges/{id}/data")
    List<GaugeDataPoint> getGaugeData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                      @PathParam("id")                 String metricId,
                                      @QueryParam("start")             long startTime,
                                      @QueryParam("end")               long endTime
                                           );

    @POST
    @Path("/availability")
    void createAvailability(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                                             MetricDefinition definition);
    @GET
    @Path("/availability/{id}")
    MetricDefinition getAvailabilityMetric(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                           @PathParam("id")                 String metricId);

    @GET
    @Path("/availability/{tags}")
    String findAvailabilityByTags(@PathParam("tenantId")String tenantId, @QueryParam("tags") String csvTags);


    @POST
    @Path("/availability/{metricId}/data")
    void addAvailabilityData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                             @PathParam("metricId")           String metricId,
                                                              List<AvailabilityDataPoint> data);

    @GET
    @Path("/availability/{metricId}/data")
    List<AvailabilityDataPoint> getAvailabilityData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                                    @PathParam("metricId")           String metricId);


    @POST
    @Path("/counters")
    void createCounter(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                                        MetricDefinition metricDefinition);
    @GET
    @Path("/counters/{metricId}")
    MetricDefinition getCounter(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                @PathParam("metricId")           String metricId);

    @POST
    @Path("/counters/{metricId}/data")
    void addCounterData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                        @PathParam("metricId")           String metricId,
                                                         List<CounterDataPoint> data);

    @GET
    @Path("/counters/{metricId}/data")
    List<CounterDataPoint> getCounterData(@HeaderParam ("Hawkular-Tenant") String tenantId,
                                          @PathParam("metricId")           String metricId);
}
