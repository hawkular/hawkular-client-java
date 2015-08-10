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
package org.hawkular.client.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hawkular.client.HawkularClient;
import org.hawkular.metrics.core.api.MetricId;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Reporter;

public class BaseTest {

    private HawkularClient client;

    public BaseTest() throws Exception {
        init();
    }
    public void init() throws Exception {
        URI endpoint = getEndpointFromEnv();
        Reporter.log(endpoint.toString());
        //TODO: this authentication detail created for temporary purpose only. Should be changed later
        //we may use the default one as jode/password
        client = new HawkularClient(endpoint, "jdoe", "password");
    }

    private static URI getEndpointFromEnv() throws URISyntaxException {
        String endpoint = System.getenv("HAWKULAR_ENDPOINT");
        if (StringUtils.trimToNull(endpoint) == null) {
            Reporter.log("HAWKULAR_ENDPOINT env not defined. Defaulting to 'localhost'");
            endpoint = "http://localhost:8080";
        }
        return new URI(endpoint);
    }

    /**
     * Return the main Hawkular client
     * @return HawkularClient
     */
    public HawkularClient client() {
        return client;
    }

    public static Tenant randomTenant() {
        Tenant tenant = new Tenant(RandomStringUtils.randomAlphanumeric(8).toLowerCase());
        return tenant;
    }

    public static MetricId randomMetricId() {
        return new MetricId(RandomStringUtils.randomAlphanumeric(8).toLowerCase());
    }

//    public static AvailabilityMetric randomAvailabilityMetric() {
//        MetricId id = new MetricId(RandomStringUtils.randomAlphanumeric(8));
//        AvailabilityMetric avail = new AvailabilityMetric(id);
//        return avail;
//    }
}
