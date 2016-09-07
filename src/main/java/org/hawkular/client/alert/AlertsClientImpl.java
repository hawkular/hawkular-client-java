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

import java.net.URI;

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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class AlertsClientImpl implements AlertsClient {

    private final AlertClient alert;
    private final ActionsClient actions;
    private final EventsClient events;
    private final ExportClient export;
    private final ImportClient imports;
    private final PluginsClient plugins;
    private final StatusClient status;
    private final TriggersClient triggers;

    public AlertsClientImpl(URI endpointUri) throws Exception {
        this(endpointUri, null, null);
    }

    public AlertsClientImpl(URI endpointUri, String username, String password) {
        checkArgument(endpointUri != null, "EndpointUri is null");

        alert = new DefaultAlertClient(endpointUri, username, password);
        actions = new DefaultActionsClient(endpointUri, username, password);
        events = new DefaultEventsClient(endpointUri, username, password);
        export = new DefaultExportClient(endpointUri, username, password);
        imports = new DefaultImportClient(endpointUri, username, password);
        plugins = new DefaultPluginsClient(endpointUri, username, password);
        status = new DefaultStatusClient(endpointUri, username, password);
        triggers = new DefaultTriggersClient(endpointUri, username, password);
    }

    @Override
    public AlertClient alert() {
        checkNotNull(alert != null, "AlertClient is null");
        return alert;
    }

    @Override
    public ActionsClient actions() {
        checkNotNull(actions != null, "ActionsClient is null");
        return actions;
    }

    @Override
    public EventsClient events() {
        checkNotNull(events != null, "EventsClient is null");
        return events;
    }

    @Override
    public ExportClient export() {
        checkNotNull(export != null, "ExportClient is null");
        return export;
    }

    @Override
    public ImportClient imports() {
        checkNotNull(imports != null, "ImportClient is null");
        return imports;
    }

    @Override
    public PluginsClient plugins() {
        checkNotNull(plugins != null, "PluginsClient is null");
        return plugins;
    }

    @Override
    public StatusClient status() {
        checkNotNull(status != null, "StatusClient is null");
        return status;
    }

    @Override
    public TriggersClient triggers() {
        checkNotNull(triggers != null, "TriggersClient is null");
        return triggers;
    }
}
