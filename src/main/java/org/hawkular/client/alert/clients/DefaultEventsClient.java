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

import org.hawkular.alerts.api.model.event.Event;
import org.hawkular.client.alert.jaxrs.handlers.EventsHandler;
import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;

import com.fasterxml.jackson.databind.JavaType;

public class DefaultEventsClient extends BaseClient<EventsHandler> implements EventsClient {

    public DefaultEventsClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<>(EventsHandler.class));
    }

    @Override
    public ClientResponse<List<Event>> findEvents(
        Long startTime, Long endTime, String eventIds, String triggerIds, String categories, String tags, Boolean thin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().findEvents(startTime, endTime, eventIds, triggerIds, categories, tags, thin);
            JavaType javaType = collectionResolver().get(List.class, Event.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Event> createEvent(Event event) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createEvent(event);
            JavaType javaType = simpleResolver().get(Event.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Integer> deleteEvents(
        Long startTime, Long endTime, String eventIds, String triggerIds, String categories, String tags) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteEvents(startTime, endTime, eventIds, triggerIds, categories, tags);
            JavaType javaType = simpleResolver().get(Integer.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Event> getEvent(String eventId, Boolean thin) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().getEvent(eventId, thin);
            JavaType javaType = simpleResolver().get(Event.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteTags(String eventIds, String tagNames) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteTags(eventIds, tagNames);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> createTags(String eventIds, String tagNames) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().createTags(eventIds, tagNames);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.CREATE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    @Override
    public ClientResponse<Empty> deleteEvent(String eventId) {
        Response serverResponse = null;

        try {
            serverResponse = restApi().deleteEvent(eventId);
            JavaType javaType = simpleResolver().get(Empty.class);

            return new DefaultClientResponse<>(javaType, serverResponse, ResponseCodes.DELETE_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }
}
