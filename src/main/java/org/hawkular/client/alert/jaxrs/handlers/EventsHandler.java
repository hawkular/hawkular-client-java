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

import org.hawkular.alerts.api.model.event.Event;

/**
 * Events API
 * http://www.hawkular.org/docs/rest/rest-alerts.html#_events
 */
@Path("/hawkular/alerts/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EventsHandler {

    @GET
    @Path("/")
    Response findEvents(
        @QueryParam("startTime") final Long startTime,
        @QueryParam("endTime") final Long endTime,
        @QueryParam("eventIds") final String eventIds,
        @QueryParam("triggerIds") final String triggerIds,
        @QueryParam("categories") final String categories,
        @QueryParam("tags") final String tags,
        @QueryParam("thin") final Boolean thin);

    @POST
    @Path("/")
    Response createEvent(Event event);

    @PUT
    @Path("/delete")
    Response deleteEvents(
        @QueryParam("startTime") final Long startTime,
        @QueryParam("endTime") final Long endTime,
        @QueryParam("eventIds") final String eventIds,
        @QueryParam("triggerIds") final String triggerIds,
        @QueryParam("categories") final String categories,
        @QueryParam("tags") final String tags);

    @GET
    @Path("/event/{eventId}")
    Response getEvent(@PathParam("eventId") final String eventId, @QueryParam("thin") final Boolean thin);

    @DELETE
    @Path("/tags")
    Response deleteTags(@QueryParam("eventIds") final String eventIds,
                        @QueryParam("tagNames") final String tagNames);

    @PUT
    @Path("/tags")
    Response createTags(@QueryParam("eventIds") final String eventIds,
                        @QueryParam("tags") final String tagNames);

    @DELETE
    @Path("/{eventId}")
    Response deleteEvent(@PathParam("eventId") final String eventId);
}
