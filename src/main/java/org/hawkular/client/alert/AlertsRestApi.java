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
import org.hawkular.alerts.api.model.data.MixedData;
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
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/alerts/rest/TriggersHandler.java
    @GET
    @Path("/triggers/")
    public Response findTriggers();

    @GET
    @Path("/triggers/tag")
    public Response findTriggersByTag(@QueryParam("name") final String name, @QueryParam("value") final String value);

    @GET
    @Path("/triggers/groups/{groupId}/members")
    public Response findGroupMembers(@PathParam("groupId") final String groupId,
            @QueryParam("includeOrphans") final boolean includeOrphans);

    @POST
    @Path("/triggers/")
    public Response createTrigger(final Trigger trigger);

    @POST
    @Path("/triggers/groups")
    public Response createGroupTrigger(final Trigger groupTrigger);

    @POST
    @Path("/triggers/groups/members")
    public Response createGroupMember(final GroupMemberInfo groupMember);

    @GET
    @Path("/triggers/{triggerId}")
    public Response getTrigger(@PathParam("triggerId") final String triggerId);

    @PUT
    @Path("/triggers/{triggerId}")
    public Response updateTrigger(@PathParam("triggerId") final String triggerId, final Trigger trigger);

    @PUT
    @Path("/triggers/groups/{groupId}")
    public Response updateGroupTrigger(@PathParam("groupId") final String groupId, final Trigger groupTrigger);

    @POST
    @Path("/triggers/groups/members/{memberId}/orphan")
    public Response orphanMemberTrigger(@PathParam("memberId") final String memberId);

    @POST
    @Path("/triggers/groups/members/{memberId}/unorphan")
    public Response unorphanMemberTrigger(@PathParam("memberId") final String memberId,
            final UnorphanMemberInfo unorphanMemberInfo);

    @DELETE
    @Path("/triggers/{triggerId}")
    public Response deleteTrigger(@PathParam("triggerId") final String triggerId);

    @DELETE
    @Path("/triggers/groups/{groupId}")
    public Response deleteGroupTrigger(@PathParam("groupId") final String groupId,
            @QueryParam("keepNonOrphans") final boolean keepNonOrphans,
            @QueryParam("keepOrphans") final boolean keepOrphans);

    @GET
    @Path("/triggers/{triggerId}/dampenings")
    public Response getTriggerDampenings(@PathParam("triggerId") final String triggerId);

    @GET
    @Path("/triggers/{triggerId}/dampenings/mode/{triggerMode}")
    public Response getTriggerModeDampenings(@PathParam("triggerId") final String triggerId,
            @PathParam("triggerMode") final Mode triggerMode);

    @GET
    @Path("/triggers/{triggerId}/dampenings/{dampeningId}")
    public Response getDampening(@PathParam("triggerId") final String triggerId,
            @PathParam("dampeningId") final String dampeningId);

    @POST
    @Path("/triggers/{triggerId}/dampenings")
    public Response createDampening(@PathParam("triggerId") final String triggerId, final Dampening dampening);

    @POST
    @Path("/triggers/groups/{groupId}/dampenings")
    public Response createGroupDampening(@PathParam("groupId") final String groupId, final Dampening dampening);

    @PUT
    @Path("/triggers/{triggerId}/dampenings/{dampeningId}")
    public Response updateDampening(@PathParam("triggerId") final String triggerId,
            @PathParam("dampeningId") final String dampeningId, final Dampening dampening);

    @PUT
    @Path("/triggers/groups/{groupId}/dampenings/{dampeningId}")
    public Response updateGroupDampening(@PathParam("groupId") final String groupId,
            @PathParam("dampeningId") final String dampeningId, final Dampening dampening);

    @DELETE
    @Path("/triggers/{triggerId}/dampenings/{dampeningId}")
    public Response deleteDampening(@PathParam("triggerId") final String triggerId,
            @PathParam("dampeningId") final String dampeningId);

    @DELETE
    @Path("/triggers/groups/{groupId}/dampenings/{dampeningId}")
    public Response deleteGroupDampening(@PathParam("groupId") final String groupId,
            @PathParam("dampeningId") final String dampeningId);

    @GET
    @Path("/triggers/{triggerId}/conditions")
    public Response getTriggerConditions(@PathParam("triggerId") final String triggerId);

    @PUT
    @Path("/triggers/{triggerId}/conditions/{triggerMode}")
    public Response setConditions(@PathParam("triggerId") final String triggerId,
            @PathParam("triggerMode") final String triggerMode, List<Condition> conditions);

    @PUT
    @Path("/triggers/groups/{groupId}/conditions/{triggerMode}")
    public Response setGroupConditions(@PathParam("groupId") final String groupId,
            @PathParam("triggerMode") final String triggerMode, String jsonGroupConditionsInfo);

    //Alerts
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/alerts/rest/AlertsHandler.java
    @GET
    @Path("/")
    public Response findAlerts(
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
    public Response ackAlert(
            @PathParam("alertId") final String alertId,
            @QueryParam("ackBy") final String ackBy,
            @QueryParam("ackNotes") final String ackNotes);

    @PUT
    @Path("/ack")
    public Response ackAlerts(
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("ackBy") final String ackBy,
            @QueryParam("ackNotes") final String ackNotes);

    @DELETE
    @Path("/{alertId}")
    public Response deleteAlert(@PathParam("alertId") final String alertId);

    @PUT
    @Path("/delete")
    public Response deleteAlerts(
            @QueryParam("startTime") final Long startTime,
            @QueryParam("endTime") final Long endTime,
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("triggerIds") final String triggerIds,
            @QueryParam("statuses") final String statuses,
            @QueryParam("severities") final String severities,
            @QueryParam("tags") final String tags);

    @GET
    @Path("/alert/{alertId}")
    public Response getAlert(
            @PathParam("alertId") final String alertId,
            @QueryParam("thin") final Boolean thin);

    @PUT
    @Path("/resolve/{alertId}")
    public Response resolveAlert(
            @PathParam("alertId") final String alertId,
            @QueryParam("resolvedBy") final String resolvedBy,
            @QueryParam("resolvedNotes") final String resolvedNotes);

    @PUT
    @Path("/resolve")
    public Response resolveAlerts(
            @QueryParam("alertIds") final String alertIds,
            @QueryParam("resolvedBy") final String resolvedBy,
            @QueryParam("resolvedNotes") final String resolvedNotes);

    @POST
    @Path("/data")
    public Response sendData(final MixedData mixedData);

    @GET
    @Path("/reload")
    public Response reloadAlerts();

    @GET
    @Path("/reload/{triggerId}")
    public Response reloadTrigger(@PathParam("triggerId") final String triggerId);

    //Actions
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/alerts/rest/ActionsHandler.java
    @GET
    @Path("/actions/")
    public Response findActions();

    @GET
    @Path("/actions/plugin/{actionPlugin}")
    public Response findActionsByPlugin(@PathParam("actionPlugin") final String actionPlugin);

    @POST
    @Path("/actions/")
    public Response createAction(final Map<String, String> actionProperties);

    @GET
    @Path("/actions/{actionPlugin}/{actionId}")
    public Response getAction(final String actionPlugin, @PathParam("actionId") final String actionId);

    @PUT
    @Path("/actions/{actionPlugin}/{actionId}")
    public Response updateAction(
            final String actionPlugin,
            @PathParam("actionId") final String actionId,
            final Map<String, String> actionProperties);

    @DELETE
    @Path("/actions/{actionPlugin}/{actionId}")
    public Response deleteAction(final String actionPlugin, @PathParam("actionId") final String actionId);

    //Plugins
    //https://github.com/hawkular/hawkular-alerts/blob/master/hawkular-alerts-rest/src/main/java/org/hawkular/alerts/rest/ActionPluginHandler.java
    @GET
    @Path("/plugins/")
    public Response findActionPlugins();

    @GET
    @Path("/plugins/{actionPlugin}")
    public Response getActionPlugin(@PathParam("actionPlugin") final String actionPlugin);
}
