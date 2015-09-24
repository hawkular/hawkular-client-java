package org.hawkular.client.alert;

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
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Tag;
import org.hawkular.alerts.api.model.trigger.Trigger;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */

@Path("/hawkular/alerts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AlertsRestApi {

    //Triggers
    @GET
    @Path("/triggers/")
    public Response findTriggers();

    @GET
    @Path("/triggers/tag")
    public Response findTriggersByTag(@QueryParam("category") final String category,
            @QueryParam("name") final String name);

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

    @GET
    @Path("/triggers/{triggerId}/conditions/{conditionId}")
    public Response getTriggerCondition(@PathParam("triggerId") final String triggerId,
            @PathParam("conditionId") final String conditionId);

    @PUT
    @Path("/triggers/{triggerId}/conditions/{triggerMode}")
    public Response setConditions(@PathParam("triggerId") final String triggerId,
            @PathParam("triggerMode") final Mode triggerMode, String jsonConditions);

    @PUT
    @Path("/triggers/groups/{groupId}/conditions/{triggerMode}")
    public Response setGroupConditions(@PathParam("groupId") final String groupId,
            @PathParam("triggerMode") final Mode triggerMode, String jsonGroupConditionsInfo);

    @POST
    @Path("/triggers/{triggerId}/conditions")
    public Response createCondition(@PathParam("triggerId") final String triggerId, String jsonCondition);

    @PUT
    @Path("/triggers/{triggerId}/conditions/{conditionId}")
    public Response updateCondition(@PathParam("triggerId") final String triggerId,
            @PathParam("conditionId") final String conditionId, String jsonCondition);

    @DELETE
    @Path("/triggers/{triggerId}/conditions/{conditionId}")
    public Response deleteCondition(@PathParam("triggerId") final String triggerId,
            @PathParam("conditionId") final String conditionId);

    @POST
    @Path("/triggers/tags")
    public Response createTag(final Tag tag);

    @POST
    @Path("/triggers/groups/tags")
    public Response createGroupTag(final Tag tag);

    @PUT
    @Path("/triggers/{triggerId}/tags")
    public Response deleteTags(@PathParam("triggerId") final String triggerId,
            @QueryParam("category") final String category, @QueryParam("name") final String name);

    @PUT
    @Path("/triggers/groups/{groupId}/tags")
    public Response deleteGroupTags(@PathParam("groupId") final String groupId,
            @QueryParam("category") final String category,
            @QueryParam("name") final String name);

    @GET
    @Path("/triggers/{triggerId}/tags")
    public Response getTriggerTags(@PathParam("triggerId") final String triggerId,
            @QueryParam("category") final String category);

}
