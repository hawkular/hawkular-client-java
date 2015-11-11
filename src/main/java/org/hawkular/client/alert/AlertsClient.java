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
import java.util.Set;

import org.hawkular.alerts.api.json.GroupMemberInfo;
import org.hawkular.alerts.api.json.UnorphanMemberInfo;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.data.Data;
import org.hawkular.alerts.api.model.event.Alert;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.alert.model.AlertsParams;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface AlertsClient {
    public enum RESPONSE_CODE {
        GET_SUCCESS(200),
        CREATE_SUCCESS(200),
        ADD_SUCCESS(201),
        REGISTER_SUCCESS(201),
        UPDATE_SUCCESS(200),
        DELETE_SUCCESS(200),
        REMOVE_SUCCESS(204);

        private int code;

        private RESPONSE_CODE(int code) {
            this.code = code;
        }

        int value() {
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

    ClientResponse<List<Trigger>> findTriggers();

    ClientResponse<List<Trigger>> findTriggersByTag(String category, String name);

    ClientResponse<List<Trigger>> findGroupMembers(String groupId, boolean includeOrphans);

    ClientResponse<Trigger> createTrigger(Trigger trigger);

    ClientResponse<String> createGroupTrigger(Trigger groupTrigger);

    ClientResponse<String> createGroupMember(GroupMemberInfo groupMember);

    ClientResponse<Trigger> getTrigger(String triggerId);

    ClientResponse<String> updateTrigger(String triggerId, Trigger trigger);

    ClientResponse<String> updateGroupTrigger(String groupId, Trigger groupTrigger);

    ClientResponse<String> orphanMemberTrigger(String memberId);

    ClientResponse<String> unorphanMemberTrigger(String memberId, UnorphanMemberInfo unorphanMemberInfo);

    ClientResponse<String> deleteTrigger(String triggerId);

    ClientResponse<String> deleteGroupTrigger(String groupId, boolean keepNonOrphans, boolean keepOrphans);

    ClientResponse<List<Dampening>> getTriggerDampenings(String triggerId);

    ClientResponse<List<Dampening>> getTriggerModeDampenings(String triggerId, Mode triggerMode);

    ClientResponse<Dampening> getDampening(String triggerId, String dampeningId);

    ClientResponse<Dampening> createDampening(String triggerId, Dampening dampening);

    ClientResponse<Dampening> createGroupDampening(String groupId, Dampening dampening);

    ClientResponse<Dampening> updateDampening(String triggerId, String dampeningId, Dampening dampening);

    ClientResponse<Dampening> updateGroupDampening(String groupId, String dampeningId, Dampening dampening);

    ClientResponse<String> deleteDampening(String triggerId, String dampeningId);

    ClientResponse<String> deleteGroupDampening(String groupId, String dampeningId);

    ClientResponse<List<Condition>> getTriggerConditions(Trigger trigger);

    ClientResponse<List<Condition>> getTriggerConditions(String triggerId);

    ClientResponse<List<Condition>> setConditions(String triggerId, String triggerMode,
            List<Condition> conditions);

    ClientResponse<List<Condition>> setGroupConditions(String groupId, String triggerMode,
            String jsonGroupConditionsInfo);

    //Alerts

    ClientResponse<List<Alert>> findAlerts(
            Long startTime, Long endTime, String alertIds, String triggerIds,
            String statuses, String severities, String tags, Boolean thin);

    ClientResponse<List<Alert>> findAlerts();

    ClientResponse<List<Alert>> findAlerts(AlertsParams alertsParams);

    ClientResponse<String> ackAlert(String alertId, String ackBy, String ackNotes);

    ClientResponse<String> ackAlerts(String alertIds, String ackBy, String ackNotes);

    ClientResponse<String> deleteAlert(String alertId);

    ClientResponse<Integer> deleteAlerts(
            Long startTime, Long endTime, String alertIds, String triggerIds,
            String statuses, String severities, String tags);

    ClientResponse<Integer> deleteAlerts(AlertsParams alertsParams);

    ClientResponse<Alert> getAlert(String alertId, Boolean thin);

    ClientResponse<String> resolveAlert(String alertId, String resolvedBy, String resolvedNotes);

    ClientResponse<String> resolveAlerts(String alertIds, String resolvedBy, String resolvedNotes);

    ClientResponse<String> sendData(Data data);

    ClientResponse<String> reloadAlerts();

    ClientResponse<String> reloadTrigger(String triggerId);

    //Actions

    ClientResponse<Map<String, Set<String>>> findActions();

    ClientResponse<List<String>> findActionsByPlugin(String actionPlugin);

    ClientResponse<Map<String, String>> createAction(Map<String, String> actionProperties);

    ClientResponse<Map<String, String>> updateAction(String actionPlugin, String actionId,
            Map<String, String> actionProperties);

    ClientResponse<String> deleteAction(String actionPlugin, String actionId);

    //Plugins

    ClientResponse<String[]> findActionPlugins();

    ClientResponse<String[]> getActionPlugin(String actionPlugin);
}
