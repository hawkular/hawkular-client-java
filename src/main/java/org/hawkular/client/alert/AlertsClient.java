package org.hawkular.client.alert;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawkular.alerts.api.json.GroupMemberInfo;
import org.hawkular.alerts.api.json.UnorphanMemberInfo;
import org.hawkular.alerts.api.model.condition.Alert;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.data.MixedData;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.ClientResponse;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface AlertsClient {
    public enum RESPONSE_CODE {
        GET_SUCCESS(200),
        CREATE_SUCCESS(201),
        ADD_SUCCESS(201),
        REGISTER_SUCCESS(201),
        UPDATE_SUCCESS(204),
        DELETE_SUCCESS(204),
        REMOVE_SUCCESS(204);

        private int code;

        private RESPONSE_CODE(int code) {
            this.code = code;
        }

        public int value() {
            return this.code;
        }
    }

    public enum TRIGGER_CONDITION_TYPE {
        AVAILABILITY_CONDITION,
        COMPARE_CONDITION,
        EXTERNAL_CONDITION,
        STRING_CONDITION,
        THRESHOLD_CONDITION,
        THRESHOLD_RANGE_CONDITION
    }

    //Triggers

    public ClientResponse<List<Trigger>> findTriggers();

    public ClientResponse<List<Trigger>> findTriggersByTag(String category, String name);

    public ClientResponse<List<Trigger>> findGroupMembers(String groupId, boolean includeOrphans);

    public ClientResponse<Trigger> createTrigger(Trigger trigger);

    public ClientResponse<String> createGroupTrigger(Trigger groupTrigger);

    public ClientResponse<String> createGroupMember(GroupMemberInfo groupMember);

    public ClientResponse<Trigger> getTrigger(String triggerId);

    public ClientResponse<String> updateTrigger(String triggerId, Trigger trigger);

    public ClientResponse<String> updateGroupTrigger(String groupId, Trigger groupTrigger);

    public ClientResponse<String> orphanMemberTrigger(String memberId);

    public ClientResponse<String> unorphanMemberTrigger(String memberId, UnorphanMemberInfo unorphanMemberInfo);

    public ClientResponse<String> deleteTrigger(String triggerId);

    public ClientResponse<String> deleteGroupTrigger(String groupId, boolean keepNonOrphans, boolean keepOrphans);

    public ClientResponse<List<Dampening>> getTriggerDampenings(String triggerId);

    public ClientResponse<List<Dampening>> getTriggerModeDampenings(String triggerId, Mode triggerMode);

    public ClientResponse<Dampening> getDampening(String triggerId, String dampeningId);

    public ClientResponse<Dampening> createDampening(String triggerId, Dampening dampening);

    public ClientResponse<Dampening> createGroupDampening(String groupId, Dampening dampening);

    public ClientResponse<Dampening> updateDampening(String triggerId, String dampeningId, Dampening dampening);

    public ClientResponse<Dampening> updateGroupDampening(String groupId, String dampeningId, Dampening dampening);

    public ClientResponse<String> deleteDampening(String triggerId, String dampeningId);

    public ClientResponse<String> deleteGroupDampening(String groupId, String dampeningId);

    public ClientResponse<List<Condition>> getTriggerConditions(Trigger trigger, TRIGGER_CONDITION_TYPE conditionType);

    public ClientResponse<List<Condition>> getTriggerConditions(String triggerId, TRIGGER_CONDITION_TYPE conditionType);

    public ClientResponse<List<Condition>> setConditions(String triggerId, String triggerMode, List<Condition> conditions);

    public ClientResponse<List<Condition>> setGroupConditions(String groupId, String triggerMode,
            String jsonGroupConditionsInfo);

    //Alerts

    public ClientResponse<List<Alert>> findAlerts(
            Long startTime, Long endTime, String alertIds, String triggerIds,
            String statuses, String severities, String tags, Boolean thin);

    public ClientResponse<String> findAlerts();

    public ClientResponse<String> ackAlert(String alertId, String ackBy, String ackNotes);

    public ClientResponse<String> ackAlerts(String alertIds, String ackBy, String ackNotes);

    public ClientResponse<String> deleteAlert(String alertId);

    public ClientResponse<Integer> deleteAlerts(
            Long startTime, Long endTime, String alertIds, String triggerIds,
            String statuses, String severities, String tags);

    public ClientResponse<Alert> getAlert(String alertId, Boolean thin);

    public ClientResponse<String> resolveAlert(String alertId, String resolvedBy, String resolvedNotes);

    public ClientResponse<String> resolveAlerts(String alertIds, String resolvedBy, String resolvedNotes);

    public ClientResponse<String> sendData(MixedData mixedData);

    public ClientResponse<String> reloadAlerts();

    public ClientResponse<String> reloadTrigger(String triggerId);

    //Actions

    public ClientResponse<Map<String, Set<String>>> findActions();

    public ClientResponse<List<String>> findActionsByPlugin(String actionPlugin);

    public ClientResponse<Map<String, String>> createAction(Map<String, String> actionProperties);

    public ClientResponse<Map<String, String>> updateAction(String actionPlugin, String actionId,
            Map<String, String> actionProperties);

    public ClientResponse<String> deleteAction(String actionPlugin, String actionId);

    //Plugins

    public ClientResponse<String[]> findActionPlugins();

    public ClientResponse<String[]> getActionPlugin(String actionPlugin);
}
