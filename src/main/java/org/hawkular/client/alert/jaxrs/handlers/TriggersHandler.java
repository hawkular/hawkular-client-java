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

import java.util.List;

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

import org.hawkular.alerts.api.json.GroupConditionsInfo;
import org.hawkular.alerts.api.json.GroupMemberInfo;
import org.hawkular.alerts.api.json.UnorphanMemberInfo;
import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.CompareCondition;
import org.hawkular.alerts.api.model.condition.StringCondition;
import org.hawkular.alerts.api.model.condition.ThresholdCondition;
import org.hawkular.alerts.api.model.condition.ThresholdRangeCondition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.trigger.FullTrigger;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;

@Path("/hawkular/alerts/triggers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TriggersHandler {

    @GET
    @Path("/")
    Response findTriggers(@QueryParam("triggerIds") final String triggerIds,
                          @QueryParam("tags") final String tags,
                          @QueryParam("thin") final Boolean thin);

    @POST
    @Path("/")
    Response createTrigger(final Trigger trigger);

    @POST
    @Path("/groups")
    Response createGroupTrigger(final Trigger groupTrigger);

    @POST
    @Path("/groups/members")
    Response createGroupMember(final GroupMemberInfo groupMember);

    @POST
    @Path("/groups/members/{memberId}/orphan")
    Response orphanMemberTrigger(@PathParam("memberId") final String memberId);

    @POST
    @Path("/groups/members/{memberId}/unorphan")
    Response unorphanMemberTrigger(@PathParam("memberId") final String memberId,
                                   final UnorphanMemberInfo unorphanMemberInfo);

    @DELETE
    @Path("/groups/{groupId}")
    Response deleteGroupTrigger(@PathParam("groupId") final String groupId,
                                @QueryParam("keepNonOrphans") final boolean keepNonOrphans,
                                @QueryParam("keepOrphans") final boolean keepOrphans);

    @PUT
    @Path("/groups/{groupId}")
    Response updateGroupTrigger(@PathParam("groupId") final String groupId, final Trigger groupTrigger);

    @PUT
    @Path("/groups/{groupId}/conditions/{triggerMode}")
    Response setGroupConditions(@PathParam("groupId") final String groupId,
                                @PathParam("triggerMode") final String triggerMode,
                                GroupConditionsInfo groupConditionsInfo);

    @POST
    @Path("/groups/{groupId}/dampenings")
    Response createGroupDampening(@PathParam("groupId") final String groupId, final Dampening dampening);

    @DELETE
    @Path("/groups/{groupId}/dampenings/{dampeningId}")
    Response deleteGroupDampening(@PathParam("groupId") final String groupId,
                                  @PathParam("dampeningId") final String dampeningId);

    @PUT
    @Path("/groups/{groupId}/dampenings/{dampeningId}")
    Response updateGroupDampening(@PathParam("groupId") final String groupId,
                                  @PathParam("dampeningId") final String dampeningId,
                                  final Dampening dampening);

    @GET
    @Path("/groups/{groupId}/members")
    Response findGroupMembers(@PathParam("groupId") final String groupId,
                              @QueryParam("includeOrphans") final boolean includeOrphans);

    @POST
    @Path("/trigger")
    Response createFullTrigger(final FullTrigger fullTrigger);

    @GET
    @Path("/trigger/{triggerId}")
    Response getFullTriggerById(@PathParam("triggerId") final String triggerId);

    @DELETE
    @Path("/{triggerId}")
    Response deleteTrigger(@PathParam("triggerId") final String triggerId);

    @GET
    @Path("/{triggerId}")
    Response getTrigger(@PathParam("triggerId") final String triggerId);

    @PUT
    @Path("/{triggerId}")
    Response updateTrigger(@PathParam("triggerId") final String triggerId, final Trigger trigger);

    @GET
    @Path("/{triggerId}/conditions")
    Response getTriggerConditions(@PathParam("triggerId") final String triggerId);

    @PUT
    @Path("/{triggerId}/conditions/{triggerMode}")
    Response setAvailabilityCondition(
        @PathParam("triggerId") final String triggerId, @PathParam("triggerMode") final String triggerMode,
        List<AvailabilityCondition> conditions);

    @PUT
    @Path("/{triggerId}/conditions/{triggerMode}")
    Response setCompareCondition(
        @PathParam("triggerId") final String triggerId, @PathParam("triggerMode") final String triggerMode,
        List<CompareCondition> conditions);

    @PUT
    @Path("/{triggerId}/conditions/{triggerMode}")
    Response setStringCondition(
        @PathParam("triggerId") final String triggerId, @PathParam("triggerMode") final String triggerMode,
        List<StringCondition> conditions);

    @PUT
    @Path("/{triggerId}/conditions/{triggerMode}")
    Response setThresholdCondition(
        @PathParam("triggerId") final String triggerId, @PathParam("triggerMode") final String triggerMode,
        List<ThresholdCondition> conditions);

    @PUT
    @Path("/{triggerId}/conditions/{triggerMode}")
    Response setThresholdRangeCondition(
        @PathParam("triggerId") final String triggerId, @PathParam("triggerMode") final String triggerMode,
        List<ThresholdRangeCondition> conditions);

    @GET
    @Path("/{triggerId}/dampenings")
    Response getTriggerDampenings(@PathParam("triggerId") final String triggerId);

    @POST
    @Path("/{triggerId}/dampenings")
    Response createDampening(@PathParam("triggerId") final String triggerId, final Dampening dampening);

    @GET
    @Path("/{triggerId}/dampenings/mode/{triggerMode}")
    Response getTriggerModeDampenings(@PathParam("triggerId") final String triggerId,
                                      @PathParam("triggerMode") final Mode triggerMode);

    @DELETE
    @Path("/{triggerId}/dampenings/{dampeningId}")
    Response deleteDampening(@PathParam("triggerId") final String triggerId,
                             @PathParam("dampeningId") final String dampeningId);

    @GET
    @Path("/{triggerId}/dampenings/{dampeningId}")
    Response getDampening(@PathParam("triggerId") final String triggerId,
                          @PathParam("dampeningId") final String dampeningId);

    @PUT
    @Path("/{triggerId}/dampenings/{dampeningId}")
    Response updateDampening(@PathParam("triggerId") final String triggerId,
                             @PathParam("dampeningId") final String dampeningId,
                             final Dampening dampening);
}
