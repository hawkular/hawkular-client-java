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

import org.hawkular.alerts.api.model.event.Event;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;

public interface EventsClient {

    /**
     * Get events with optional filtering.
     * @param startTime
     * @param endTime
     * @param eventIds
     * @param triggerIds
     * @param categories
     * @param tags
     * @param thin
     * @return
     */
    ClientResponse<List<Event>> findEvents(
        final Long startTime,
        final Long endTime,
        final String eventIds,
        final String triggerIds,
        final String categories,
        final String tags,
        final Boolean thin);

    /**
     * Create a new Event.
     * @param event
     * @return
     */
    ClientResponse<Event> createEvent(Event event);

    /**
     * Delete events with optional filtering.
     * @param startTime
     * @param endTime
     * @param eventIds
     * @param triggerIds
     * @param categories
     * @param tags
     * @return
     */
    ClientResponse<Integer> deleteEvents(
        final Long startTime,
        final Long endTime,
        final String eventIds,
        final String triggerIds,
        final String categories,
        final String tags);

    /**
     * Get an existing Event.
     * @param eventId
     * @param thin
     * @return
     */
    ClientResponse<Event> getEvent(final String eventId, final Boolean thin);

    /**
     * Remove tags from existing Events.
     * @param eventIds
     * @param tagNames
     * @return
     */
    ClientResponse<Empty> deleteTags(final String eventIds, final String tagNames);

    /**
     * Add tags to existing Events.
     * @param eventIds
     * @param tagNames
     * @return
     */
    ClientResponse<Empty> createTags(final String eventIds, final String tagNames);

    /**
     * Delete an existing Event.
     * @param eventId
     * @return
     */
    ClientResponse<Empty> deleteEvent(final String eventId);
}
