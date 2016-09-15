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

import org.hawkular.alerts.api.model.data.Data;
import org.hawkular.alerts.api.model.event.Alert;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;

public interface AlertClient {

    /**
     * Get alerts with optional filtering.
     * @param startTime
     * @param endTime
     * @param alertIds
     * @param triggerIds
     * @param statuses
     * @param severities
     * @param tags
     * @param thin
     * @return
     */
    ClientResponse<List<Alert>> findAlerts(
        final Long startTime,
        final Long endTime,
        final String alertIds,
        final String triggerIds,
        final String statuses,
        final String severities,
        final String tags,
        final Boolean thin);

    /**
     * Set one or more alerts Acknowledged.
     * @param alertIds
     * @param ackBy
     * @param ackNotes
     * @return
     */
    ClientResponse<Empty> ackAlerts(final String alertIds, final String ackBy, final String ackNotes);

    /**
     * Set one alert Acknowledged.
     * @param alertId
     * @param ackBy
     * @param ackNotes
     * @return
     */
    ClientResponse<Empty> ackAlert(final String alertId, final String ackBy, final String ackNotes);

    /**
     * Get an existing Alert.
     * @param alertId
     * @param thin
     * @return
     */
    ClientResponse<Alert> getAlert(final String alertId, final Boolean thin);

    /**
     * Send data for alert processing/condition evaluation.
     * @param datums
     * @return
     */
    ClientResponse<Empty> sendData(final List<Data> datums);

    /**
     * Delete alerts with optional filtering.
     * @param startTime
     * @param endTime
     * @param alertIds
     * @param triggerIds
     * @param statuses
     * @param severities
     * @param tags
     * @return
     */
    ClientResponse<Integer> deleteAlerts(
        final Long startTime,
        final Long endTime,
        final String alertIds,
        final String triggerIds,
        final String statuses,
        final String severities,
        final String tags);

    /**
     * Add a note into an existing Alert.
     * @param alertId
     * @param user
     * @param text
     * @return
     */
    ClientResponse<Empty> addNoteToAlert(final String alertId, final String user, final String text);

    /**
     * Set one or more alerts resolved.
     * @param alertIds
     * @param resolvedBy
     * @param resolvedNotes
     * @return
     */
    ClientResponse<Empty> resolveAlerts(final String alertIds, final String resolvedBy, final String resolvedNotes);

    /**
     * Set one alert Resolved.
     * @param alertId
     * @param resolvedBy
     * @param resolvedNotes
     * @return
     */
    ClientResponse<Empty> resolveAlert(final String alertId, final String resolvedBy, final String resolvedNotes);

    /**
     * Remove tags from existing Alerts.
     * @param alertIds
     * @param tagNames
     * @return
     */
    ClientResponse<Empty> deleteTags(final String alertIds, final String tagNames);

    /**
     * Add tags to existing Alerts.
     * @param alertIds
     * @param tagNames
     * @return
     */
    ClientResponse<Empty> addTag(final String alertIds, final String tagNames);

    /**
     * Delete an existing Alert.
     * @param alertId
     * @return
     */
    ClientResponse<Empty> deleteAlert(final String alertId);
}
