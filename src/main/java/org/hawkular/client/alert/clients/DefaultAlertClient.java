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

import javax.ws.rs.core.Response;

import org.hawkular.alerts.api.model.data.Data;
import org.hawkular.alerts.api.model.event.Alert;
import org.hawkular.client.alert.jaxrs.handlers.AlertHandler;
import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultAlertClient extends BaseClient<AlertHandler> implements AlertClient {

    public DefaultAlertClient(URI endpointUri) {
        this(endpointUri, null, null);
    }

    public DefaultAlertClient(URI endpointUri, String username, String password) {
        super(endpointUri, username, password, new RestFactory<AlertHandler>(AlertHandler.class));
    }

    @Override
    public ClientResponse<List<Alert>> findAlerts(
        Long startTime, Long endTime, String alertIds, String triggerIds, String statuses, String severities, String tags, Boolean thin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findAlerts(startTime, endTime, alertIds, triggerIds, statuses, severities, tags, thin);
            JavaType javaType = collectionResolver().get(List.class, Alert.class);

            return new DefaultClientResponse<List<Alert>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> ackAlerts(String alertIds, String ackBy, String ackNotes) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().ackAlerts(alertIds, ackBy, ackNotes);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> ackAlert(String alertId, String ackBy, String ackNotes) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().ackAlert(alertId, ackBy, ackNotes);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Alert> getAlert(String alertId, Boolean thin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getAlert(alertId, thin);
            JavaType javaType = simpleResolver().get(Alert.class);

            return new DefaultClientResponse<Alert>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> sendData(List<Data> datums) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().sendData(datums);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Integer> deleteAlerts(
        Long startTime, Long endTime, String alertIds, String triggerIds, String statuses, String severities, String tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteAlerts(startTime, endTime, alertIds, triggerIds, statuses, severities, tags);
            JavaType javaType = simpleResolver().get(Integer.class);

            return new DefaultClientResponse<Integer>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addNoteToAlert(String alertId, String user, String text) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addNoteToAlert(alertId, user, text);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> resolveAlerts(String alertIds, String resolvedBy, String resolvedNotes) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().resolveAlerts(alertIds, resolvedBy, resolvedNotes);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> resolveAlert(String alertId, String resolvedBy, String resolvedNotes) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().resolveAlert(alertId, resolvedBy, resolvedNotes);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.UPDATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteTags(String alertIds, String tagNames) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteTags(alertIds, tagNames);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> addTag(String alertIds, String tagNames) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().addTag(alertIds, tagNames);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteAlert(String alertId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteAlert(alertId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<Empty>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
