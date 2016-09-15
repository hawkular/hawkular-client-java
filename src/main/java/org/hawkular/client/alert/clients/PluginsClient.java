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

import org.hawkular.client.core.ClientResponse;

public interface PluginsClient {

    /**
     * Find all action plugins.
     * @return
     */
    ClientResponse<List<String>> findActionPlugins();

    /**
     * Find list of properties to fill for a specific action plugin.
     * @param actionPlugin
     * @return
     */
    ClientResponse<List<String>> getActionPlugin(String actionPlugin);
}
