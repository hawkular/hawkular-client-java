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
import java.net.URISyntaxException;
import java.util.List;

import org.hawkular.client.MetricsClientInterface;
import org.hawkular.client.RestFactory;
import org.hawkular.metrics.core.api.Tenant;

/**
 * This is the entry point for Hawkular-Metrics client
 * @author vnguyen
 *
 */
public class MetricsClient implements MetricsClientInterface {
    private URI endpointURI;
    private MetricsRestApi restAPI;

    public MetricsClient(String endpointUrl, String username, String password) {
        this(endpointUrl, username, password, new RestFactory());
    }

    public MetricsClient(String endpointUrl, String username, String password,
            RestFactory restFactory) {
        try {
            endpointURI = new URI(endpointUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        restAPI = restFactory.createAPI(endpointURI, username, password);
    }

    @Override
    public List<Tenant> findTenants() {
        return restAPI.findTenants();
    }

    @Override
    public void createTenant(Tenant tenant) {
        restAPI.createTenant(tenant);
    }

    public static void main(String[] args) {
        MetricsClient client = new MetricsClient("http://209.132.178.218:18080/hawkular-metrics/", "", "");
        Tenant tenant = new Tenant();
        tenant.setId("foobar");
        client.createTenant(tenant);
        System.out.println(client.findTenants());
    }

}
