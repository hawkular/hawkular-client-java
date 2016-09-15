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

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.alerts.api.model.action.Action;
import org.hawkular.alerts.api.model.action.ActionDefinition;
import org.hawkular.client.alert.jaxrs.handlers.ActionsHandler;
import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultActionsClient extends BaseClient<ActionsHandler> implements ActionsClient {

    public DefaultActionsClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultActionsClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<ActionsHandler>(ActionsHandler.class));
    }

    @Override
    public ClientResponse<Map<String, List<String>>> findActions() {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findActions();
            JavaType javaType = mapResolver().get(Map.class, String.class, List.class, String.class);

            return new DefaultClientResponse<Map<String, List<String>>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<ActionDefinition> createAction(ActionDefinition definition) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createAction(definition);
            JavaType javaType = simpleResolver().get(ActionDefinition.class);

            return new DefaultClientResponse<ActionDefinition>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<ActionDefinition> updateAction(ActionDefinition definition) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().updateAction(definition);
            JavaType javaType = simpleResolver().get(ActionDefinition.class);

            return new DefaultClientResponse<ActionDefinition>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<Action>> getActionHistory(
        Long startTime, Long endTime, String actionPlugins, String actionIds, String alertIds, String results, Boolean thin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getActionHistory(startTime, endTime, actionPlugins, actionIds, alertIds, results, thin);
            JavaType javaType = collectionResolver().get(List.class, Action.class);

            return new DefaultClientResponse<List<Action>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Integer> deleteActionHistory(
        Long startTime, Long endTime, String actionPlugins, String actionIds, String alertIds, String results) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteActionHistory(startTime, endTime, actionPlugins, actionIds, alertIds, results);
            JavaType javaType = simpleResolver().get(Integer.class);

            return new DefaultClientResponse<Integer>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<List<String>> findActionsByPlugin(String actionPlugin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findActionsByPlugin(actionPlugin);
            JavaType javaType = collectionResolver().get(List.class, String.class);

            return new DefaultClientResponse<List<String>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteAction(String actionPlugin, String actionId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteAction(actionPlugin, actionId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<ActionDefinition> getAction(String actionPlugin, String actionId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getAction(actionPlugin, actionId);
            JavaType javaType = simpleResolver().get(ActionDefinition.class);

            return new DefaultClientResponse<ActionDefinition>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
