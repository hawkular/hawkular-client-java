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
package org.hawkular.client.alert;

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

import org.hawkular.alerts.api.json.GroupMemberInfo;
import org.hawkular.alerts.api.json.UnorphanMemberInfo;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.data.Data;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */

@Path("/hawkular/alerts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AlertsRestApi {

    //Triggers
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/alerts
    ///rest/TriggersHandler.java
    @GET
    @Path("/triggers/")
    Response findTriggers();

    @GET
    @Path("/triggers/tag")
    Response findTriggersByTag(@QueryParam("name") final String name, @QueryParam("value") final String value);

    @GET
    @Path("/triggers/groups/{groupId}/members")
    Response findGroupMembers(@PathParam("groupId") final String groupId,
            @QueryParam("includeOrphans") final boolean includeOrphans);

    @POST
    @Path("/triggers/")
    Response createTrigger(final Trigger trigger);

    @POST
    @Path("/triggers/groups")
    Response createGroupTrigger(final Trigger groupTrigger);

    @POST
    @Path("/triggers/groups/members")
    Response createGroupMember(final GroupMemberInfo groupMember);

    @GET
    @Path("/triggers/{triggerId}")
    Response getTrigger(@PathParam("triggerId") final String triggerId);

    @PUT
    @Path("/triggers/{triggerId}")
    Response updateTrigger(@PathParam("triggerId") final String triggerId, final Trigger trigger);

    @PUT
    @Path("/triggers/groups/{groupId}")
    Response updateGroupTrigger(@PathParam("groupId") final String groupId, final Trigger groupTrigger);

    @POST
    @Path("/triggers/groups/members/{memberId}/orphan")
    Response orphanMemberTrigger(@PathParam("memberId") final String memberId);

    @POST
    @Path("/triggers/groups/members/{memberId}/unorphan")
    Response unorphanMemberTrigger(@PathParam("memberId") final String memberId,
            final UnorphanMemberInfo unorphanMemberInfo);

    @DELETE
    @Path("/triggers/{triggerId}")
    Response deleteTrigger(@PathParam("triggerId") final String triggerId);

    @DELETE
    @Path("/triggers/groups/{groupId}")
    Response deleteGroupTrigger(@PathParam("groupId") final String groupId,
            @QueryParam("keepNonOrphans") final boolean keepNonOrphans,
            @QueryParam("keepOrphans") final boolean keepOrphans);

    @GET
    @Path("/triggers/{triggerId}/dampenings")
    Response getTriggerDampenings(@PathParam("triggerId") final String triggerId);

    @GET
    @Path("/triggers/{triggerId}/dampenings/mode/{triggerMode}")
    Response getTriggerModeDampenings(@PathParam("triggerId") final String triggerId,
            @PathParam("triggerMode") final Mode triggerMode);

    @GET
    @Path("/triggers/{triggerId}/dampenings/{dampeningId}")
    Response getDampening(@PathParam("triggerId") final String triggerId,
            @PathParam("dampeningId") final String dampeningId);

    @POST
    @Path("/triggers/{triggerId}/dampenings")
    Response createDampening(@PathParam("triggerId") final String triggerId, final Dampening dampening);

    @POST
    @Path("/triggers/groups/{groupId}/dampenings")
    Response createGroupDampening(@PathParam("groupId") final String groupId, final Dampening dampening);

    @PUT
    @Path("/triggers/{triggerId}/dampenings/{dampeningId}")
    Response updateDampening(@PathParam("triggerId") final String triggerId,
            @PathParam("dampeningId") final String dampeningId, final Dampening dampening);

    @PUT
    @Path("/triggers/groups/{groupId}/dampenings/{dampeningId}")
    Response updateGroupDampening(@PathParam("groupId") final String groupId,
            @PathParam("dampeningId") final String dampeningId, final Dampening dampening);

    @DELETE
    @Path("/triggers/{triggerId}/dampenings/{dampeningId}")
    Response deleteDampening(@PathParam("triggerId") final String triggerId,
            @PathParam("dampeningId") final String dampeningId);

    @DELETE
    @Path("/triggers/groups/{groupId}/dampenings/{dampeningId}")
    Response deleteGroupDampening(@PathParam("groupId") final String groupId,
            @PathParam("dampeningId") final String dampeningId);

    @GET
    @Path("/triggers/{triggerId}/conditions")
    Response getTriggerConditions(@PathParam("triggerId") final String triggerId);

    @PUT
    @Path("/triggers/{triggerId}/conditions/{triggerMode}")
    Response setConditions(@PathParam("triggerId") final String triggerId,
            @PathParam("triggerMode") final String triggerMode, List<Condition> conditions);

    @PUT
    @Path("/triggers/groups/{groupId}/conditions/{triggerMode}")
    Response setGroupConditions(@PathParam("groupId") final String groupId,
            @PathParam("triggerMode") final String triggerMode, String jsonGroupConditionsInfo);

    //Alerts
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular
    ///alerts/rest/AlertsHandler.java
    @GET
    @Path("/")
    Response findAlerts(
            @QueryParam("startTime") final Long startTime,
            @QueryParam("endTime") final Long endTime,
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("triggerIds") final String triggerIds,
            @QueryParam("statuses") final String statuses,
            @QueryParam("severities") final String severities,
            @QueryParam("tags") final String tags,
            @QueryParam("thin") final Boolean thin);

    @PUT
    @Path("/ack/{alertId}")
    Response ackAlert(
            @PathParam("alertId") final String alertId,
            @QueryParam("ackBy") final String ackBy,
            @QueryParam("ackNotes") final String ackNotes);

    @PUT
    @Path("/ack")
    Response ackAlerts(
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("ackBy") final String ackBy,
            @QueryParam("ackNotes") final String ackNotes);

    @DELETE
    @Path("/{alertId}")
    Response deleteAlert(@PathParam("alertId") final String alertId);

    @PUT
    @Path("/delete")
    Response deleteAlerts(
            @QueryParam("startTime") final Long startTime,
            @QueryParam("endTime") final Long endTime,
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("triggerIds") final String triggerIds,
            @QueryParam("statuses") final String statuses,
            @QueryParam("severities") final String severities,
            @QueryParam("tags") final String tags);

    @GET
    @Path("/alert/{alertId}")
    Response getAlert(
            @PathParam("alertId") final String alertId,
            @QueryParam("thin") final Boolean thin);

    @PUT
    @Path("/resolve/{alertId}")
    Response resolveAlert(
            @PathParam("alertId") final String alertId,
            @QueryParam("resolvedBy") final String resolvedBy,
            @QueryParam("resolvedNotes") final String resolvedNotes);

    @PUT
    @Path("/resolve")
    Response resolveAlerts(
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("resolvedBy") final String resolvedBy,
            @QueryParam("resolvedNotes") final String resolvedNotes);

    @POST
    @Path("/data")
    Response sendData(final Data mixedData);

    @GET
    @Path("/reload")
    Response reloadAlerts();

    @GET
    @Path("/reload/{triggerId}")
    Response reloadTrigger(@PathParam("triggerId") final String triggerId);

    //Actions
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/
    //alerts/rest/ActionsHandler.java
    @GET
    @Path("/actions/")
    Response findActions();

    @GET
    @Path("/actions/plugin/{actionPlugin}")
    Response findActionsByPlugin(@PathParam("actionPlugin") final String actionPlugin);

    @POST
    @Path("/actions/")
    Response createAction(final Map<String, String> actionProperties);

    @GET
    @Path("/actions/{actionPlugin}/{actionId}")
    Response getAction(final String actionPlugin, @PathParam("actionId") final String actionId);

    @PUT
    @Path("/actions/{actionPlugin}/{actionId}")
    Response updateAction(
            final String actionPlugin,
            @PathParam("actionId") final String actionId,
            final Map<String, String> actionProperties);

    @DELETE
    @Path("/actions/{actionPlugin}/{actionId}")
    Response deleteAction(final String actionPlugin, @PathParam("actionId") final String actionId);

    //Plugins
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/alerts
    ///rest/ActionPluginHandler.java
    @GET
    @Path("/plugins/")
    Response findActionPlugins();

    @GET
    @Path("/plugins/{actionPlugin}")
    Response getActionPlugin(@PathParam("actionPlugin") final String actionPlugin);
}
