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
package org.hawkular.client.alert.clients;

import java.util.List;

import org.hawkular.alerts.api.json.GroupConditionsInfo;
import org.hawkular.alerts.api.json.GroupMemberInfo;
import org.hawkular.alerts.api.json.UnorphanMemberInfo;
import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.CompareCondition;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.condition.StringCondition;
import org.hawkular.alerts.api.model.condition.ThresholdCondition;
import org.hawkular.alerts.api.model.condition.ThresholdRangeCondition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.trigger.FullTrigger;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;

public interface TriggersClient {

    /**
     * Get triggers with optional filtering.
     *
     * @param triggerIds
     * @param tags
     * @param thin
     * @return
     */
    ClientResponse<List<Trigger>> findTriggers(String triggerIds, String tags, Boolean thin);

    /**
     * Create a new trigger.
     *
     * @param trigger
     * @return
     */
    ClientResponse<Trigger> createTrigger(Trigger trigger);

    /**
     * Create a new group trigger.
     *
     * @param groupTrigger
     * @return
     */
    ClientResponse<Trigger> createGroupTrigger(Trigger groupTrigger);

    /**
     * Create a new member trigger for a parent trigger.
     *
     * @param groupMember
     * @return
     */
    ClientResponse<Trigger> createGroupMember(GroupMemberInfo groupMember);

    /**
     * Make a non-orphan member trigger into an orphan.
     *
     * @param memberId
     * @return
     */
    ClientResponse<Empty> orphanMemberTrigger(String memberId);

    /**
     * Make a non-orphan member trigger into an orphan.
     *
     * @param memberId
     * @param unorphanMemberInfo
     * @return
     */
    ClientResponse<Empty> unorphanMemberTrigger(String memberId, UnorphanMemberInfo unorphanMemberInfo);

    /**
     * Delete a group trigger.
     *
     * @param groupId
     * @param keepNonOrphans
     * @param keepOrphans
     * @return
     */
    ClientResponse<Empty> deleteGroupTrigger(String groupId, boolean keepNonOrphans, boolean keepOrphans);

    /**
     * Update an existing group trigger definition and its member definitions.
     *
     * @param groupId
     * @param groupTrigger
     * @return
     */
    ClientResponse<Empty> updateGroupTrigger(String groupId, Trigger groupTrigger);

    /**
     * Set the conditions for the group trigger.
     *
     * @param groupId
     * @param triggerMode
     * @param groupConditionsInfo
     * @return
     */
    ClientResponse<List<Condition>> setGroupConditions(String groupId, String triggerMode, GroupConditionsInfo groupConditionsInfo);

    /**
     * Create a new group dampening.
     *
     * @param groupId
     * @param dampening
     * @return
     */
    ClientResponse<Dampening> createGroupDampening(String groupId, Dampening dampening);

    /**
     * Delete an existing group dampening definition.
     *
     * @param groupId
     * @param dampeningId
     * @return
     */
    ClientResponse<Empty> deleteGroupDampening(String groupId, String dampeningId);

    /**
     * Update an existing group dampening definition.
     *
     * @param groupId
     * @param dampeningId
     * @param dampening
     * @return
     */
    ClientResponse<Dampening> updateGroupDampening(String groupId, String dampeningId, Dampening dampening);

    /**
     * Find all Group Member Trigger Definitions.
     *
     * @param groupId
     * @param includeOrphans
     * @return
     */
    ClientResponse<List<Trigger>> findGroupMembers(String groupId, boolean includeOrphans);

    /**
     * Create a new full trigger (trigger, dampenings and conditions).
     *
     * @param fullTrigger
     * @return
     */
    ClientResponse<FullTrigger> createFullTrigger(FullTrigger fullTrigger);

    /**
     * Get an existing full trigger definition (trigger, dampenings and conditions).
     *
     * @param triggerId
     * @return
     */
    ClientResponse<FullTrigger> getFullTriggerById(String triggerId);

    /**
     * Delete an existing standard or group member trigger definition. This can not be used to delete a group trigger definition.
     *
     * @param triggerId
     * @return
     */
    ClientResponse<Empty> deleteTrigger(String triggerId);

    /**
     * Get an existing trigger definition.
     *
     * @param triggerId
     * @return
     */
    ClientResponse<Trigger> getTrigger(String triggerId);

    /**
     * Update an existing trigger definition.
     *
     * @param triggerId
     * @param trigger
     * @return
     */
    ClientResponse<Empty> updateTrigger(String triggerId, Trigger trigger);

    /**
     * Get all conditions for a specific trigger.
     *
     * @param triggerId
     * @return
     */
    ClientResponse<List<Condition>> getTriggerConditions(String triggerId);

    /**
     * Set the conditions for the trigger.
     *
     * @param triggerId
     * @param triggerMode
     * @param conditions
     * @return
     */
    ClientResponse<List<AvailabilityCondition>> setAvailabilityCondition(String triggerId, String triggerMode, List<AvailabilityCondition> conditions);

    /**
     * Set the conditions for the trigger.
     *
     * @param triggerId
     * @param triggerMode
     * @param conditions
     * @return
     */
    ClientResponse<List<CompareCondition>> setCompareCondition(String triggerId, String triggerMode, List<CompareCondition> conditions);

    /**
     * Set the conditions for the trigger.
     *
     * @param triggerId
     * @param triggerMode
     * @param conditions
     * @return
     */
    ClientResponse<List<StringCondition>> setStringCondition(String triggerId, String triggerMode, List<StringCondition> conditions);

    /**
     * Set the conditions for the trigger.
     *
     * @param triggerId
     * @param triggerMode
     * @param conditions
     * @return
     */
    ClientResponse<List<ThresholdCondition>> setThresholdCondition(String triggerId, String triggerMode, List<ThresholdCondition> conditions);

    /**
     * Set the conditions for the trigger.
     *
     * @param triggerId
     * @param triggerMode
     * @param conditions
     * @return
     */
    ClientResponse<List<ThresholdRangeCondition>> setThresholdRangeCondition(String triggerId, String triggerMode, List<ThresholdRangeCondition> conditions);

    /**
     * Get all Dampenings for a Trigger (1 Dampening per mode).
     *
     * @param triggerId
     * @return
     */
    ClientResponse<List<Dampening>> getTriggerDampenings(String triggerId);

    /**
     * Create a new dampening.
     *
     * @param triggerId
     * @param dampening
     * @return
     */
    ClientResponse<Dampening> createDampening(String triggerId, Dampening dampening);

    /**
     * Get dampening using triggerId and triggerMode.
     *
     * @param triggerId
     * @param triggerMode
     * @return
     */
    ClientResponse<List<Dampening>> getTriggerModeDampenings(String triggerId, Mode triggerMode);

    /**
     * Delete an existing dampening definition.
     *
     * @param triggerId
     * @param dampeningId
     * @return
     */
    ClientResponse<Empty> deleteDampening(String triggerId, String dampeningId);

    /**
     * Get an existing dampening.
     *
     * @param triggerId
     * @param dampeningId
     * @return
     */
    ClientResponse<Dampening> getDampening(String triggerId, String dampeningId);

    /**
     * Update an existing dampening definition.
     *
     * @param triggerId
     * @param dampeningId
     * @param dampening
     * @return
     */
    ClientResponse<Dampening> updateDampening(String triggerId, String dampeningId, Dampening dampening);
}
