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

import javax.ws.rs.core.Response;

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
import org.hawkular.client.alert.jaxrs.handlers.TriggersHandler;
import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultTriggersClient extends BaseClient<TriggersHandler> implements TriggersClient {

    public DefaultTriggersClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<>(TriggersHandler.class));
    }

    @Override
    public ClientResponse<List<Trigger>> findTriggers(String triggerIds, String tags, Boolean thin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findTriggers(triggerIds, tags, thin);
            JavaType javaType = collectionResolver().get(List.class, Trigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Trigger> createTrigger(Trigger trigger) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createTrigger(trigger);
            JavaType javaType = simpleResolver().get(Trigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Trigger> createGroupTrigger(Trigger groupTrigger) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createGroupTrigger(groupTrigger);
            JavaType javaType = simpleResolver().get(Trigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Trigger> createGroupMember(GroupMemberInfo groupMember) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createGroupMember(groupMember);
            JavaType javaType = simpleResolver().get(Trigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> orphanMemberTrigger(String memberId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().orphanMemberTrigger(memberId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> unorphanMemberTrigger(String memberId, UnorphanMemberInfo unorphanMemberInfo) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().unorphanMemberTrigger(memberId, unorphanMemberInfo);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteGroupTrigger(String groupId, boolean keepNonOrphans, boolean keepOrphans) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteGroupTrigger(groupId, keepNonOrphans, keepOrphans);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateGroupTrigger(String groupId, Trigger groupTrigger) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateGroupTrigger(groupId, groupTrigger);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Condition>> setGroupConditions(String groupId, String triggerMode, GroupConditionsInfo groupConditionsInfo) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().setGroupConditions(groupId, triggerMode, groupConditionsInfo);
            JavaType javaType = collectionResolver().get(List.class, Condition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Dampening> createGroupDampening(String groupId, Dampening dampening) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createGroupDampening(groupId, dampening);
            JavaType javaType = simpleResolver().get(Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteGroupDampening(String groupId, String dampeningId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteGroupDampening(groupId, dampeningId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Dampening> updateGroupDampening(String groupId, String dampeningId, Dampening dampening) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateGroupDampening(groupId, dampeningId, dampening);
            JavaType javaType = simpleResolver().get(Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Trigger>> findGroupMembers(String groupId, boolean includeOrphans) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findGroupMembers(groupId, includeOrphans);
            JavaType javaType = collectionResolver().get(List.class, Trigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<FullTrigger> createFullTrigger(FullTrigger fullTrigger) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createFullTrigger(fullTrigger);
            JavaType javaType = simpleResolver().get(FullTrigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<FullTrigger> getFullTriggerById(String triggerId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getFullTriggerById(triggerId);
            JavaType javaType = simpleResolver().get(FullTrigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteTrigger(String triggerId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteTrigger(triggerId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Trigger> getTrigger(String triggerId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getTrigger(triggerId);
            JavaType javaType = simpleResolver().get(Trigger.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> updateTrigger(String triggerId, Trigger trigger) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateTrigger(triggerId, trigger);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Condition>> getTriggerConditions(String triggerId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getTriggerConditions(triggerId);
            JavaType javaType = collectionResolver().get(List.class, Condition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<AvailabilityCondition>> setAvailabilityCondition(
        String triggerId, String triggerMode, List<AvailabilityCondition> conditions) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().setAvailabilityCondition(triggerId, triggerMode, conditions);
            JavaType javaType = collectionResolver().get(List.class, AvailabilityCondition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<CompareCondition>> setCompareCondition(
        String triggerId, String triggerMode, List<CompareCondition> conditions) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().setCompareCondition(triggerId, triggerMode, conditions);
            JavaType javaType = collectionResolver().get(List.class, CompareCondition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<StringCondition>> setStringCondition(
        String triggerId, String triggerMode, List<StringCondition> conditions) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().setStringCondition(triggerId, triggerMode, conditions);
            JavaType javaType = collectionResolver().get(List.class, CompareCondition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<ThresholdCondition>> setThresholdCondition(
        String triggerId, String triggerMode, List<ThresholdCondition> conditions) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().setThresholdCondition(triggerId, triggerMode, conditions);
            JavaType javaType = collectionResolver().get(List.class, ThresholdCondition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<ThresholdRangeCondition>> setThresholdRangeCondition(
        String triggerId, String triggerMode, List<ThresholdRangeCondition> conditions) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().setThresholdRangeCondition(triggerId, triggerMode, conditions);
            JavaType javaType = collectionResolver().get(List.class, ThresholdRangeCondition.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Dampening>> getTriggerDampenings(String triggerId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getTriggerDampenings(triggerId);
            JavaType javaType = collectionResolver().get(List.class, Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Dampening> createDampening(String triggerId, Dampening dampening) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createDampening(triggerId, dampening);
            JavaType javaType = simpleResolver().get(Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Dampening>> getTriggerModeDampenings(String triggerId, Mode triggerMode) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getTriggerModeDampenings(triggerId, triggerMode);
            JavaType javaType = collectionResolver().get(List.class, Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteDampening(String triggerId, String dampeningId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteDampening(triggerId, dampeningId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Dampening> getDampening(String triggerId, String dampeningId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getDampening(triggerId, dampeningId);
            JavaType javaType = simpleResolver().get(Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Dampening> updateDampening(String triggerId, String dampeningId, Dampening dampening) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateDampening(triggerId, dampeningId, dampening);
            JavaType javaType = simpleResolver().get(Dampening.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
