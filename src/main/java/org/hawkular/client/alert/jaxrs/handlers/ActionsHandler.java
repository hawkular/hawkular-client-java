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
package org.hawkular.client.alert.jaxrs.handlers;

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

import org.hawkular.alerts.api.model.action.ActionDefinition;

/**
 * Actions API
 * http://www.hawkular.org/docs/rest/rest-alerts.html#_actions
 */
@Path("/hawkular/alerts/actions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ActionsHandler {

    @GET
    @Path("/")
    Response findActions();

    @POST
    @Path("/")
    Response createAction(final ActionDefinition definition);

    @PUT
    @Path("/")
    Response updateAction(final ActionDefinition definition);

    @GET
    @Path("/history")
    Response getActionHistory(@QueryParam("startTime") final Long startTime,
                              @QueryParam("endTime") final Long endTime,
                              @QueryParam("actionPlugins") final String actionPlugins,
                              @QueryParam("actionIds") final String actionIds,
                              @QueryParam("alertIds") final String alertIds,
                              @QueryParam("results") final String results,
                              @QueryParam("thin") final Boolean thin);

    @PUT
    @Path("/history/delete")
    Response deleteActionHistory(@QueryParam("startTime") final Long startTime,
                                 @QueryParam("endTime") final Long endTime,
                                 @QueryParam("actionPlugins") final String actionPlugins,
                                 @QueryParam("actionIds") final String actionIds,
                                 @QueryParam("alertIds") final String alertIds,
                                 @QueryParam("results") final String results);

    @GET
    @Path("/plugin/{actionPlugin}")
    Response findActionsByPlugin(@PathParam("actionPlugin") final String actionPlugin);

    @DELETE
    @Path("/{actionPlugin}/{actionId}")
    Response deleteAction(@PathParam("actionPlugin") final String actionPlugin, @PathParam("actionId") final String actionId);

    @GET
    @Path("/{actionPlugin}/{actionId}")
    Response getAction(@PathParam("actionPlugin") final String actionPlugin, @PathParam("actionId") final String actionId);
}
