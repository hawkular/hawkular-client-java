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

import org.hawkular.client.HawkularClient;
import org.testng.Reporter;

public class BaseTest {

    private final HawkularClient client;

    public BaseTest() throws Exception {
        String endpoint = System.getProperty("hawkular.endpoint");
        if (endpoint == null) {
            throw new RuntimeException("-Dhawkular.endpoint not defined");
        }
        Reporter.log(endpoint);
        URI endpointUri = new URI(endpoint);
        client = new HawkularClient(endpointUri, "", "");
    }

    public HawkularClient client() {
        return client;
    }
}
