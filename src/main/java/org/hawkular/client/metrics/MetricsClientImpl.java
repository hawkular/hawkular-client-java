/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.client.metrics;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.hawkular.client.BaseClient;
import org.hawkular.client.MetricsClient;
import org.hawkular.client.RestFactory;
import org.hawkular.metrics.core.api.Tenant;

/**
 * Hawkular-Metrics client implementation
 * @author vnguyen
 *
 */
public class MetricsClientImpl extends BaseClient<MetricsRestApi> implements MetricsClient {

    public MetricsClientImpl(URI endpointUri, String username, String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<MetricsRestApi>(MetricsRestApi.class));
    }

    @Override
    public List<Tenant> findTenants() {
        List<Tenant> list = restApi().findTenants();
        return list == null ? new ArrayList<Tenant>() : list;
    }

    @Override
    public void createTenant(Tenant tenant) {
        restApi().createTenant(tenant);
    }
}
