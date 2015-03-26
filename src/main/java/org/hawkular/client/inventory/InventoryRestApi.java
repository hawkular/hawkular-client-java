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
package org.hawkular.client.inventory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hawkular.client.inventory.model.IdJSON;
import org.hawkular.client.inventory.model.MetricJSON;
import org.hawkular.client.inventory.model.MetricTypeJSON;
import org.hawkular.client.inventory.model.MetricTypeUpdateJSON;
import org.hawkular.client.inventory.model.MetricUpdateJSON;
import org.hawkular.client.inventory.model.ResourceJSON;
import org.hawkular.client.inventory.model.ResourceTypeJSON;
import org.hawkular.client.inventory.model.StringValue;
import org.hawkular.client.inventory.model.StringWrapper;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */

@Path("/hawkular/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface InventoryRestApi {    //PingHandler
    @GET
    @Path("/ping")
    StringValue ping();

    //Ping
    @GET
    @Path("/")
    StringWrapper pinger();

    //Tenant
    @GET
    @Path("/tenants")
    List<Tenant> getTenants();

    @POST
    @Path("/tenants")
    Response createTenant(IdJSON tenantId);

    @PUT
    @Path("/tenants/{tenantId}")
    Response updateTenant(@PathParam("tenantId") String tenantId, Map<String, Object> properties);

    @DELETE
    @Path("/tenants/{tenantId}")
    Response deleteTenant(@PathParam("tenantId") String tenantId);

    //Environment
    @GET
    @Path("/{tenantId}/environments")
    Set<Environment> getEnvironments(@PathParam("tenantId") String tenantId);

    @GET
    @Path("/{tenantId}/environments/{environmentId}")
    Environment getEnvironment(@PathParam("tenantId") String tenantId,
                               @PathParam("environmentId") String environmentId);

    @POST
    @Path("/{tenantId}/environments")
    Response createEnvironment(@PathParam("tenantId") String tenantId, IdJSON environmentId);

    @PUT
    @Path("/{tenantId}/environments/{environmentId}")
    Response updateEnvironment(@PathParam("tenantId") String tenantId, @PathParam("environmentId") String environmentId,
                               Map<String, Object> properties);
    @DELETE
    @Path("/{tenantId}/environments/{environmentId}")
    Response deleteEnvironment(@PathParam("tenantId") String tenantId,
                               @PathParam("environmentId") String environmentId);

    //Metrics Types
    @GET
    @Path("/{tenantId}/metricTypes")
    Set<MetricType> getMetricTypes(@PathParam("tenantId") String tenantId);

    @GET
    @Path("/{tenantId}/metricTypes/{metricTypeId}")
    MetricType getMetricType(@PathParam("tenantId") String tenantId, @PathParam("metricTypeId") String metricTypeId);

    @POST
    @Path("/{tenantId}/metricTypes")
    Response createMetricType(@PathParam("tenantId") String tenantId, MetricTypeJSON metricType);

    @PUT
    @Path("/{tenantId}/metricTypes/{metricTypeId}")
    Response updateMetricType(@PathParam("tenantId") String tenantId, @PathParam("metricTypeId") String metricTypeId,
                              MetricTypeUpdateJSON update);

    @DELETE
    @Path("/{tenantId}/metricTypes/{metricTypeId}")
    Response deleteMetricType(@PathParam("tenantId") String tenantId,
                              @PathParam("metricTypeId") String metricTypeId);

    //Metrics
    @POST
    @Path("/{tenantId}/{environmentId}/metrics")
    Response createMetric(@PathParam("tenantId") String tenantId,
                          @PathParam("environmentId") String environmentId,
                          MetricJSON metric);

    @GET
    @Path("/{tenantId}/{environmentId}/metrics/{metricId}")
    Metric getMetric(@PathParam("tenantId") String tenantId,
                     @PathParam("environmentId") String environmentId,
                     @PathParam("metricId") String metricId);

    @GET
    @Path("/{tenantId}/{environmentId}/metrics")
    Set<Metric> getMetrics(@PathParam("tenantId") String tenantId,
                           @PathParam("environmentId") String environmentId);

    @PUT
    @Path("/{tenantId}/{environmentId}/metrics/{metricId}")
    Response updateMetric(@PathParam("tenantId") String tenantId,
                          @PathParam("environmentId") String environmentId,
                          @PathParam("metricId") String metricId,
                          MetricUpdateJSON updates);

    @DELETE
    @Path("/{tenantId}/{environmentId}/metrics/{metricId}")
    Response deleteMetric(@PathParam("tenantId") String tenantId,
                          @PathParam("environmentId") String environmentId,
                          @PathParam("metricId") String metricId);

    //ResourceTypes
    @GET
    @Path("/{tenantId}/resourceTypes")
    Set<ResourceType> getResourceTypes(@PathParam("tenantId") String tenantId);

    @GET
    @Path("/{tenantId}/resourceTypes/{resourceTypeId}")
    ResourceType getResourceType(@PathParam("tenantId") String tenantId,
                                 @PathParam("resourceTypeId") String resourceTypeId);

    @GET
    @Path("/{tenantId}/resourceTypes/{resourceTypeId}/metricTypes")
    Set<MetricType> getMetricTypes(@PathParam("tenantId") String tenantId,
                                   @PathParam("resourceTypeId") String resourceTypeId);

    @GET
    @Path("/{tenantId}/resourceTypes/{resourceTypeId}/resources")
    Set<Resource> getResources(@PathParam("tenantId") String tenantId,
                               @PathParam("resourceTypeId") String resourceTypeId);

    @POST
    @Path("/{tenantId}/resourceTypes")
    Response createResourceType(@PathParam("tenantId") String tenantId, ResourceTypeJSON resourceType);

    @DELETE
    @Path("/{tenantId}/resourceTypes/{resourceTypeId}")
    Response deleteResourceType(@PathParam("tenantId") String tenantId,
                                @PathParam("resourceTypeId") String resourceTypeId);

    @POST
    @Path("/{tenantId}/resourceTypes/{resourceTypeId}/metricTypes")
    Response addMetricType(@PathParam("tenantId") String tenantId,
                           @PathParam("resourceTypeId") String resourceTypeId,
                           IdJSON metricTypeId);

    @DELETE
    @Path("/{tenantId}/resourceTypes/{resourceTypeId}/metricTypes/{metricTypeId}")
    Response removeMetricType(@PathParam("tenantId") String tenantId,
                              @PathParam("resourceTypeId") String resourceTypeId,
                              @PathParam("metricTypeId") String metricTypeId);

    //Resource
    @POST
    @Path("/{tenantId}/{environmentId}/resources")
    Response addResource(@PathParam("tenantId") String tenantId,
                       @PathParam("environmentId") String environmentId,
                       ResourceJSON resource);

    @GET
    @Path("/{tenantId}/{environmentId}/resources")
    Set<Resource> getResourcesByType(@PathParam("tenantId") String tenantId,
                                     @PathParam("environmentId") String environmentId,
                                     String typeId,
                                     String typeVersion);

    @GET
    @Path("/{tenantId}/{environmentId}/resources/{resourceId}")
    Resource getResource(@PathParam("tenantId") String tenantId,
                         @PathParam("environmentId") String environmentId,
                         @PathParam("resourceId") String uid);


    @DELETE
    @Path("/{tenantId}/{environmentId}/resources/{resourceId}")
    Response deleteResource(@PathParam("tenantId") String tenantId,
                            @PathParam("environmentId") String environmentId,
                            @PathParam("resourceId") String resourceId);

    @POST
    @Path("/{tenantId}/{environmentId}/resources/{resourceId}/metrics/")
    Response addMetricToResource(@PathParam("tenantId") String tenantId,
                                 @PathParam("environmentId") String environmentId,
                                 @PathParam("resourceId") String resourceId,
                                 Collection<String> metricIds);

    @GET
    @Path("/{tenantId}/{environmentId}/resources/{resourceId}/metrics")
    Set<Metric> listMetricsOfResource(@PathParam("tenantId") String tenantId,
                                      @PathParam("environmentId") String environmentID,
                                      @PathParam("resourceId") String resourceId);

    @GET
    @Path("/{tenantId}/{environmentId}/resources/{resourceId}/metrics/{metricId}")
    Metric getMetricOfResource(@PathParam("tenantId") String tenantId,
                               @PathParam("environmentId") String environmentId,
                               @PathParam("resourceId") String resourceId,
                               @PathParam("metricId") String metricId);

}
