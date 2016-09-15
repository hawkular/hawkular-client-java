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
import java.util.Map;

import org.hawkular.alerts.api.model.action.Action;
import org.hawkular.alerts.api.model.action.ActionDefinition;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;

public interface ActionsClient {

    /**
     * Find all action ids grouped by plugin.
     *
     * @return
     */
    ClientResponse<Map<String, List<String>>> findActions();

    /**
     * Create a new ActionDefinition.
     *
     * @param definition
     * @return
     */
    ClientResponse<ActionDefinition> createAction(final ActionDefinition definition);

    /**
     * Update an existing ActionDefinition.
     *
     * @param definition
     * @return
     */
    ClientResponse<ActionDefinition> updateAction(final ActionDefinition definition);

    /**
     * Get actions from history with optional filtering.
     *
     * @param startTime
     * @param endTime
     * @param actionPlugins
     * @param actionIds
     * @param alertIds
     * @param results
     * @param thin
     * @return
     */
    ClientResponse<List<Action>> getActionHistory(final Long startTime,
                                                  final Long endTime,
                                                  final String actionPlugins,
                                                  final String actionIds,
                                                  final String alertIds,
                                                  final String results,
                                                  final Boolean thin);

    /**
     * Delete actions from history with optional filtering.
     *
     * @param startTime
     * @param endTime
     * @param actionPlugins
     * @param actionIds
     * @param alertIds
     * @param results
     * @return
     */
    ClientResponse<Integer> deleteActionHistory(final Long startTime,
                                                final Long endTime,
                                                final String actionPlugins,
                                                final String actionIds,
                                                final String alertIds,
                                                final String results);

    /**
     * Find all action ids of an specific action plugin.
     *
     * @param actionPlugin
     * @return
     */
    ClientResponse<List<String>> findActionsByPlugin(final String actionPlugin);

    /**
     * Delete an existing ActionDefinition.
     *
     * @param actionPlugin
     * @param actionId
     * @return
     */
    ClientResponse<Empty> deleteAction(final String actionPlugin, final String actionId);

    /**
     * Get an existing action definition.
     *
     * @param actionPlugin
     * @param actionId
     * @return
     */
    ClientResponse<ActionDefinition> getAction(final String actionPlugin, final String actionId);
}
