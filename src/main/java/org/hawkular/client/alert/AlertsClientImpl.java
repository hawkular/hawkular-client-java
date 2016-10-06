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

import static com.google.common.base.Preconditions.checkNotNull;

import org.hawkular.client.alert.clients.ActionsClient;
import org.hawkular.client.alert.clients.AlertClient;
import org.hawkular.client.alert.clients.DefaultActionsClient;
import org.hawkular.client.alert.clients.DefaultAlertClient;
import org.hawkular.client.alert.clients.DefaultEventsClient;
import org.hawkular.client.alert.clients.DefaultExportClient;
import org.hawkular.client.alert.clients.DefaultImportClient;
import org.hawkular.client.alert.clients.DefaultPluginsClient;
import org.hawkular.client.alert.clients.DefaultStatusClient;
import org.hawkular.client.alert.clients.DefaultTriggersClient;
import org.hawkular.client.alert.clients.EventsClient;
import org.hawkular.client.alert.clients.ExportClient;
import org.hawkular.client.alert.clients.ImportClient;
import org.hawkular.client.alert.clients.PluginsClient;
import org.hawkular.client.alert.clients.StatusClient;
import org.hawkular.client.alert.clients.TriggersClient;
import org.hawkular.client.core.ClientInfo;

public class AlertsClientImpl implements AlertsClient {

    private final AlertClient alert;
    private final ActionsClient actions;
    private final EventsClient events;
    private final ExportClient export;
    private final ImportClient imports;
    private final PluginsClient plugins;
    private final StatusClient status;
    private final TriggersClient triggers;

    public AlertsClientImpl(ClientInfo clientInfo) {
        checkNotNull(clientInfo);
        alert = new DefaultAlertClient(clientInfo);
        actions = new DefaultActionsClient(clientInfo);
        events = new DefaultEventsClient(clientInfo);
        export = new DefaultExportClient(clientInfo);
        imports = new DefaultImportClient(clientInfo);
        plugins = new DefaultPluginsClient(clientInfo);
        status = new DefaultStatusClient(clientInfo);
        triggers = new DefaultTriggersClient(clientInfo);
    }

    @Override
    public AlertClient alert() {
        return alert;
    }

    @Override
    public ActionsClient actions() {
        return actions;
    }

    @Override
    public EventsClient events() {
        return events;
    }

    @Override
    public ExportClient export() {
        return export;
    }

    @Override
    public ImportClient imports() {
        return imports;
    }

    @Override
    public PluginsClient plugins() {
        return plugins;
    }

    @Override
    public StatusClient status() {
        return status;
    }

    @Override
    public TriggersClient triggers() {
        return triggers;
    }
}
