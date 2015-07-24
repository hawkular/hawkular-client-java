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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hawkular.client.inventory.json.IdJSON;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
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
    Response pingTime();

    //Ping
    @GET
    @Path("/")
    Response pingHello();

    //TenantJson
    @GET
    @Path("/tenant")
    Response getTenant();

    @PUT
    @Path("/tenant")
    Response updateTenant(Tenant.Update update);

    @DELETE
    @Path("/tenant")
    Response deleteTenant();

    //Environment
    @GET
    @Path("/environments")
    Response getEnvironments();

    @GET
    @Path("/environments/{environmentId}")
    Response getEnvironment(@PathParam("environmentId") String environmentId);

    @POST
    @Path("/environments")
    Response createEnvironment(Environment.Blueprint environmentBlueprint);

    @PUT
    @Path("/environments/{environmentId}")
    Response updateEnvironment(
            @PathParam("environmentId") String environmentId,
            Environment.Update update);

    @DELETE
    @Path("/environments/{environmentId}")
    Response deleteEnvironment(@PathParam("environmentId") String environmentId);

    //Metrics Types
    @GET
    @Path("/metricTypes")
    Response getMetricTypes();

    @GET
    @Path("/metricTypes/{metricTypeId}")
    Response getMetricType(@PathParam("metricTypeId") String metricTypeId);

    @POST
    @Path("/metricTypes")
    Response createMetricType(MetricType.Blueprint metricType);

    @PUT
    @Path("/metricTypes/{metricTypeId}")
    Response updateMetricType(@PathParam("metricTypeId") String metricTypeId, MetricType.Update metricUpdate);

    @DELETE
    @Path("/metricTypes/{metricTypeId}")
    Response deleteMetricType(@PathParam("metricTypeId") String metricTypeId);

    //Metrics
    @POST
    @Path("/{environmentId}/metrics")
    Response createMetric(
            @PathParam("environmentId") String environmentId,
            Metric.Blueprint metric);

    @POST
    @Path("/{environmentId}/{feedId}/metrics")
    Response createMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            Metric.Blueprint metric);

    @GET
    @Path("/{environmentId}/metrics/{metricId}")
    Response getMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("metricId") String metricId);

    @GET
    @Path("/{environmentId}/{feedId}/metrics/{metricId}")
    Response getMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("metricId") String metricId);

    @GET
    @Path("/{environmentId}/metrics")
    Response getMetrics(
            @PathParam("environmentId") String environmentId);

    @GET
    @Path("/{environmentId}/{feedId}/metrics")
    Response getMetrics(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId);

    @PUT
    @Path("/{environmentId}/metrics/{metricId}")
    Response updateMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("metricId") String metricId,
            Metric.Update metric);

    @PUT
    @Path("/{environmentId}/{feedId}/metrics/{metricId}")
    Response updateMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("metricId") String metricId,
            Metric.Update metric);

    @DELETE
    @Path("/{environmentId}/metrics/{metricId}")
    Response deleteMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("metricId") String metricId);

    @DELETE
    @Path("/{environmentId}/{feedId}/metrics/{metricId}")
    Response deleteMetric(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("metricId") String metricId);

    //ResourceTypes
    @GET
    @Path("/resourceTypes")
    Response getResourceTypes();

    @GET
    @Path("/resourceTypes/{resourceTypeId}")
    Response getResourceType(
            @PathParam("resourceTypeId") String resourceTypeId);

    @GET
    @Path("/resourceTypes/{resourceTypeId}/metricTypes")
    Response getMetricTypes(
            @PathParam("resourceTypeId") String resourceTypeId);

    @GET
    @Path("/resourceTypes/{resourceTypeId}/resources")
    Response getResources(
            @PathParam("resourceTypeId") String resourceTypeId);

    @POST
    @Path("/resourceTypes")
    Response createResourceType(ResourceType.Blueprint resourceType);

    @PUT
    @Path("/resourceTypes/{resourceTypeId}")
    Response updateResourceType(
            @PathParam("resourceTypeId") String resourceTypeId,
            ResourceType.Update update);

    @DELETE
    @Path("/resourceTypes/{resourceTypeId}")
    Response deleteResourceType(
            @PathParam("resourceTypeId") String resourceTypeId);

    @POST
    @Path("/resourceTypes/{resourceTypeId}/metricTypes")
    Response addMetricType(
            @PathParam("resourceTypeId") String resourceTypeId,
            IdJSON metricTypeId);

    @DELETE
    @Path("/resourceTypes/{resourceTypeId}/metricTypes/{metricTypeId}")
    Response removeMetricType(
            @PathParam("resourceTypeId") String resourceTypeId,
            @PathParam("metricTypeId") String metricTypeId);

    //Resource
    @POST
    @Path("/{environmentId}/resources")
    Response addResource(
            @PathParam("environmentId") String environmentId,
            Resource.Blueprint resource);

    @POST
    @Path("/{environmentId}/{feedId}/resources")
    Response addResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            Resource.Blueprint resource);

    @GET
    @Path("/{environmentId}/resources")
    Response getResourcesByType(
            @PathParam("environmentId") String environmentId,
            @QueryParam("typeId") String typeId,
            @QueryParam("typeVersion") String typeVersion,
            @QueryParam("feedless") @DefaultValue("false") boolean feedless);

    @GET
    @Path("/{environmentId}/{feedId}/resources")
    Response getResourcesByType(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @QueryParam("typeId") String typeId,
            @QueryParam("typeVersion") String typeVersion);

    @GET
    @Path("/{environmentId}/resources/{resourceId}")
    Response getResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("resourceId") String resourceId);

    @GET
    @Path("/{environmentId}/{feedId}/resources/{resourceId}")
    Response getResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("resourceId") String resourceId);

    @PUT
    @Path("/{environmentId}/resources/{resourceId}")
    Response updateResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("resourceId") String resourceId,
            Resource.Update update);

    @PUT
    @Path("/{environmentId}/{feedId}/resources/{resourceId}")
    Response updateResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("resourceId") String resourceId,
            Resource.Update update);

    @DELETE
    @Path("/{environmentId}/resources/{resourceId}")
    Response deleteResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("resourceId") String resourceId);

    @DELETE
    @Path("/{environmentId}/{feedId}/resources/{resourceId}")
    Response deleteResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("resourceId") String resourceId);

    @POST
    @Path("/{environmentId}/resources/{resourceId}/metrics/")
    Response addMetricToResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("resourceId") String resourceId,
            Collection<String> metricIds);

    @POST
    @Path("/{environmentId}/{feedId}/resources/{resourceId}/metrics/")
    Response addMetricToResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("resourceId") String resourceId,
            Collection<String> metricIds);

    @GET
    @Path("/{environmentId}/resources/{resourceId}/metrics")
    Response listMetricsOfResource(
            @PathParam("environmentId") String environmentID,
            @PathParam("resourceId") String resourceId);

    @GET
    @Path("/{environmentId}/{feedId}/resources/{resourceId}/metrics")
    Response listMetricsOfResource(
            @PathParam("environmentId") String environmentID,
            @PathParam("feedId") String feedId,
            @PathParam("resourceId") String resourceId);

    @GET
    @Path("/{environmentId}/resources/{resourceId}/metrics/{metricId}")
    Response getMetricOfResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("resourceId") String resourceId,
            @PathParam("metricId") String metricId);

    @GET
    @Path("/{environmentId}/{feedId}/resources/{resourceId}/metrics/{metricId}")
    Response getMetricOfResource(
            @PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId,
            @PathParam("resourceId") String resourceId,
            @PathParam("metricId") String metricId);

    //Feed
    @POST
    @Path("/{environmentId}/feeds")
    Response registerFeed(@PathParam("environmentId") String environmentId,
            Feed.Blueprint feed);

    @GET
    @Path("/{environmentId}/feeds")
    Response getAllFeeds(@PathParam("environmentId") String environmentId);

    @GET
    @Path("/{environmentId}/feeds/{feedId}")
    Response getFeed(@PathParam("environmentId") String environmentId,
            @PathParam("feedId") String feedId);

    @PUT
    @Path("/{environmentId}/feeds/{feedId}")
    Response updateFeed(
            @PathParam("environmentId") String environmentId, @PathParam("feedId") String feedId, Feed.Update update);

    @DELETE
    @Path("/{environmentId}/feeds/{feedId}")
    Response deleteFeed(
            @PathParam("environmentId") String environmentId, @PathParam("feedId") String feedId);
}
