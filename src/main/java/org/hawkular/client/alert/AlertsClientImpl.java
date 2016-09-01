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
package org.hawkular.client.alert;

import java.net.URI;
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
import org.hawkular.client.alert.model.AlertsParams;
import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.ClientResponseOld;
import org.hawkular.client.core.jaxrs.RestFactory;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class AlertsClientImpl extends BaseClient<AlertsRestApi> implements AlertsClient {

    public AlertsClientImpl(URI endpointUri, String username, String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<AlertsRestApi>(AlertsRestApi.class));
    }

    public AlertsClientImpl(URI endpointUri) throws Exception {
        this(endpointUri, null, null);
    }

    //Triggers
    @Override
    public ClientResponse<List<Trigger>> findTriggers() {
        return new ClientResponseOld<List<Trigger>>(Trigger.class, restApi().findTriggers(),
                                                    RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Trigger>> findTriggersByTag(String category, String name) {
        return new ClientResponseOld<List<Trigger>>(Trigger.class, restApi().findTriggersByTag(category, name),
                                                 RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Trigger>> findGroupMembers(String groupId, boolean includeOrphans) {
        return new ClientResponseOld<List<Trigger>>(Trigger.class, restApi().findGroupMembers(groupId, includeOrphans),
                                                 RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<Trigger> createTrigger(Trigger trigger) {
        return new ClientResponseOld<Trigger>(Trigger.class, restApi().createTrigger(trigger),
                                           RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createGroupTrigger(Trigger groupTrigger) {
        return new ClientResponseOld<String>(String.class, restApi().createTrigger(groupTrigger),
                                          RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createGroupMember(GroupMemberInfo groupMember) {
        return new ClientResponseOld<String>(String.class, restApi().createGroupMember(groupMember),
                                          RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Trigger> getTrigger(String triggerId) {
        return new ClientResponseOld<Trigger>(Trigger.class, restApi().getTrigger(triggerId),
                                           RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateTrigger(String triggerId, Trigger trigger) {
        return new ClientResponseOld<String>(String.class, restApi().updateTrigger(triggerId, trigger),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateGroupTrigger(String groupId, Trigger groupTrigger) {
        return new ClientResponseOld<String>(String.class, restApi().updateGroupTrigger(groupId, groupTrigger),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> orphanMemberTrigger(String memberId) {
        return new ClientResponseOld<String>(String.class, restApi().orphanMemberTrigger(memberId),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> unorphanMemberTrigger(String memberId, UnorphanMemberInfo unorphanMemberInfo) {
        return new ClientResponseOld<String>(String.class, restApi().unorphanMemberTrigger(memberId, unorphanMemberInfo),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteTrigger(String triggerId) {
        return new ClientResponseOld<String>(String.class, restApi().deleteTrigger(triggerId),
                                          RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteGroupTrigger(String groupId, boolean keepNonOrphans, boolean keepOrphans) {
        return new ClientResponseOld<String>(String.class, restApi().deleteGroupTrigger(groupId, keepNonOrphans,
                                                                                     keepOrphans), RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Dampening>> getTriggerDampenings(String triggerId) {
        return new ClientResponseOld<List<Dampening>>(Dampening.class, restApi().getTriggerDampenings(triggerId),
                                                   RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Dampening>> getTriggerModeDampenings(String triggerId, Mode triggerMode) {
        return new ClientResponseOld<List<Dampening>>(Dampening.class, restApi().getTriggerModeDampenings(triggerId,
                                                                                                       triggerMode),
                                                   RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<Dampening> getDampening(String triggerId, String dampeningId) {
        return new ClientResponseOld<Dampening>(Dampening.class, restApi().getDampening(triggerId, dampeningId),
                                             RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<Dampening> createDampening(String triggerId, Dampening dampening) {
        return new ClientResponseOld<Dampening>(Dampening.class, restApi().createDampening(triggerId, dampening),
                                             RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Dampening> createGroupDampening(String groupId, Dampening dampening) {
        return new ClientResponseOld<Dampening>(Dampening.class, restApi().createGroupDampening(groupId, dampening),
                                             RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Dampening> updateDampening(String triggerId, String dampeningId, Dampening dampening) {
        return new ClientResponseOld<Dampening>(Dampening.class, restApi().updateDampening(triggerId, dampeningId,
                                                                                        dampening),
                                             RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Dampening> updateGroupDampening(String groupId, String dampeningId, Dampening dampening) {
        return new ClientResponseOld<Dampening>(Dampening.class, restApi().updateGroupDampening(groupId, dampeningId,
                                                                                             dampening), RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteDampening(String triggerId, String dampeningId) {
        return new ClientResponseOld<String>(String.class, restApi().deleteDampening(triggerId, dampeningId),
                                          RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteGroupDampening(String groupId, String dampeningId) {
        return new ClientResponseOld<String>(String.class, restApi().deleteGroupDampening(groupId, dampeningId),
                                          RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Condition>> getTriggerConditions(Trigger trigger) {
        return getTriggerConditions(trigger.getId());
    }

    @Override
    public ClientResponse<List<Condition>> getTriggerConditions(String triggerId) {
        return new ClientResponseOld<List<Condition>>(Condition.class, restApi().getTriggerConditions(
            triggerId),
                                                   RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Condition>> setConditions(String triggerId, String triggerMode,
                                                         List<? extends Condition> conditions) {
        return new ClientResponseOld<List<Condition>>(Condition.class, restApi().setConditions(triggerId, triggerMode,
                                                                                            conditions), RESPONSE_CODE.UPDATE_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Condition>> setGroupConditions(String groupId, String triggerMode,
                                                              String jsonGroupConditionsInfo) {
        return new ClientResponseOld<List<Condition>>(Condition.class, restApi().setGroupConditions(groupId, triggerMode,
                                                                                                 jsonGroupConditionsInfo),
                                                   RESPONSE_CODE.UPDATE_SUCCESS.value(), List.class);
    }

    //Alert

    @Override
    public ClientResponse<List<Alert>> findAlerts(Long startTime, Long endTime, String alertIds, String triggerIds,
                                                  String statuses, String severities, String tags, Boolean thin) {
        return new ClientResponseOld<List<Alert>>(Alert.class, restApi().findAlerts(startTime, endTime, alertIds,
                                                                                 triggerIds, statuses, severities, tags, thin),
                                               RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Alert>> findAlerts() {
        return new ClientResponseOld<List<Alert>>(Alert.class, restApi().findAlerts(null, null, null,
                                                                                 null, null, null, null, null),
                                               RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<List<Alert>> findAlerts(AlertsParams alertsParams) {
        return new ClientResponseOld<List<Alert>>(Alert.class, restApi().findAlerts(
            alertsParams.getStartTime(),
            alertsParams.getEndTime(),
            alertsParams.getAlertIds(),
            alertsParams.getTriggerIds(),
            alertsParams.getStatuses(),
            alertsParams.getSeverities(),
            alertsParams.getTags(),
            alertsParams.getThin()),
                                               RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<String> ackAlert(String alertId, String ackBy, String ackNotes) {
        return new ClientResponseOld<String>(String.class, restApi().ackAlert(alertId, ackBy, ackNotes),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> ackAlerts(String alertIds, String ackBy, String ackNotes) {
        return new ClientResponseOld<String>(String.class, restApi().ackAlerts(alertIds, ackBy, ackNotes),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteAlert(String alertId) {
        return new ClientResponseOld<String>(String.class, restApi().deleteAlert(alertId),
                                          RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Integer> deleteAlerts(Long startTime, Long endTime, String alertIds, String triggerIds,
                                                String statuses, String severities, String tags) {
        return new ClientResponseOld<Integer>(Integer.class, restApi().deleteAlerts(startTime, endTime, alertIds,
                                                                                 triggerIds, statuses, severities, tags),
                                           RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Integer> deleteAlerts(AlertsParams alertsParams) {
        return new ClientResponseOld<Integer>(Integer.class, restApi().deleteAlerts(
            alertsParams.getStartTime(),
            alertsParams.getEndTime(),
            alertsParams.getAlertIds(),
            alertsParams.getTriggerIds(),
            alertsParams.getStatuses(),
            alertsParams.getSeverities(),
            alertsParams.getTags()),
                                           RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Alert> getAlert(String alertId, Boolean thin) {
        return new ClientResponseOld<Alert>(Alert.class, restApi().getAlert(alertId, thin),
                                         RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> resolveAlert(String alertId, String resolvedBy, String resolvedNotes) {
        return new ClientResponseOld<String>(String.class, restApi().resolveAlert(alertId, resolvedBy, resolvedNotes),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> resolveAlerts(String alertIds, String resolvedBy, String resolvedNotes) {
        return new ClientResponseOld<String>(String.class, restApi().resolveAlerts(alertIds, resolvedBy, resolvedNotes),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> sendData(List<Data> datums) {
        return new ClientResponseOld<String>(String.class, restApi().sendData(datums),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> reloadAlerts() {
        return new ClientResponseOld<String>(String.class, restApi().reloadAlerts(),
                                          RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> reloadTrigger(String triggerId) {
        return new ClientResponseOld<String>(String.class, restApi().reloadTrigger(triggerId),
                                          RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    //Actions

    @Override
    public ClientResponse<Map<String, Set<String>>> findActions() {
        return new ClientResponseOld<Map<String, Set<String>>>(Map.class, restApi().findActions(),
                                                            RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<String>> findActionsByPlugin(String actionPlugin) {
        return new ClientResponseOld<List<String>>(String.class, restApi().findActionsByPlugin(actionPlugin),
                                                RESPONSE_CODE.GET_SUCCESS.value(), List.class);
    }

    @Override
    public ClientResponse<Map<String, String>> createAction(Map<String, String> actionProperties) {
        return new ClientResponseOld<Map<String, String>>(Map.class, restApi().createAction(actionProperties),
                                                       RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<Map<String, String>> updateAction(String actionPlugin, String actionId,
                                                            Map<String, String> actionProperties) {
        return new ClientResponseOld<Map<String, String>>(Map.class, restApi().updateAction(actionPlugin, actionId,
                                                                                         actionProperties), RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteAction(String actionPlugin, String actionId) {
        return new ClientResponseOld<String>(String.class, restApi().deleteAction(actionPlugin, actionId),
                                          RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    //Plugins

    @Override
    public ClientResponse<String[]> findActionPlugins() {
        return new ClientResponseOld<String[]>(String[].class, restApi().findActionPlugins(),
                                            RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String[]> getActionPlugin(String actionPlugin) {
        return new ClientResponseOld<String[]>(String[].class, restApi().getActionPlugin(actionPlugin),
                                            RESPONSE_CODE.GET_SUCCESS.value());
    }

}
