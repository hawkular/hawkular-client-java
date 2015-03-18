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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hawkular.inventory.api.MetricDefinition;
import org.hawkular.inventory.api.Resource;


@Path("/hawkular/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface InventoryRestApi {

    @GET
    @Path("/")
    //TODO: Should return 'StringWrapper', which is currently not available with 'inventory-api'
    public String ping();

    @POST
    @Path("/{tenantId}/resources")
    //TODO: Should return 'IdWrapper', which is currently not available with 'inventory-api'
    public String addResource(@PathParam("tenantId") String tenantId,
            Resource definition);

    @GET
    @Path("/{tenantId}/resources")
    public Collection<Resource> getResourcesByType(@PathParam("tenantId") String tenantId,
            @QueryParam("type") String type);    

    @GET
    @Path("/{tenantId}/resources/{uid}")
    public Resource getResource(@PathParam("tenantId") String tenantId, @PathParam
            ("uid") String uid);

    @DELETE
    @Path("/{tenantId}/resources/{uid}")
    public boolean deleteResource(@PathParam("tenantId") String tenantId, @PathParam
            ("uid") String uid);

    @POST
    @Path("/{tenantId}/resources/{resourceId}/metrics")
    public boolean addMetricToResource(@PathParam("tenantId") String tenantId,
            @PathParam("resourceId") String resourceId,
            Collection<MetricDefinition> payload);

    @GET
    @Path("/{tenantId}/resources/{resourceId}/metrics")
    public Collection<MetricDefinition> listMetricsOfResource(@PathParam("tenantId") String tenantId,
            @PathParam("resourceId") String resourceId);

    @GET
    @Path("/{tenantId}/resources/{resourceId}/metrics/{metricId}")
    public MetricDefinition getMetricOfResource(@PathParam("tenantId") String tenantId,
            @PathParam("resourceId") String resourceId,
            @PathParam("metricId") String metricId);
    @PUT
    @Path("/{tenantId}/resources/{resourceId}/metrics/{metricId}")
    public boolean updateMetricOfResource(@PathParam("tenantId") String tenantId,
            @PathParam("resourceId") String resourceId,
            MetricDefinition payload);
}
