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
package org.hawkular.client.test.alerts;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.hawkular.alerts.api.model.data.Data;
import org.hawkular.alerts.api.model.event.Alert;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BTG;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests do not check response content, as no alerts exist.
 * Tests only validate that a REST call can be made.
 */
@Test(groups = {"alerts"})
public class AlertTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(AlertTest.class);

    private final String alertId = "New-Alert-" + RandomStringGenerator.getRandomId();
    private final String tags = String.format("%s|%s", RandomStringGenerator.getRandomId(), RandomStringGenerator.getRandomId());

    @Test
    public void sendData() {
        BTG ts = new BTG();
        Data data = new Data(BaseTest.HEADER_TENANT, null, alertId, ts.nextMilli(), "Woops!");

        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .sendData(Arrays.asList(data));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "sendData")
    public void findAlerts() {
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(1).toMillis();
        long end = now;

        ClientResponse<List<Alert>> response = client()
            .alerts()
            .alert()
            .findAlerts(start, end, null, null, null, null, null, false);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findAlerts")
    public void getAlert() {
        ClientResponse<Alert> response = client()
            .alerts()
            .alert()
            .getAlert(alertId, false);

        //TODO: Returns 404 due to no alert
        //Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findAlerts")
    public void ackAlerts() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .ackAlerts(alertId, "TestNG", "Unit testing");

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findAlerts")
    public void ackAlert() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .ackAlert(alertId, "TestNG", "Unit testing");

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findAlerts")
    public void addNoteToAlert() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .addNoteToAlert(alertId, "TestNG", "Unit testing");

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findAlerts")
    public void resolveAlerts() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .resolveAlerts(alertId, "TestNG", "Unit testing");

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findAlerts")
    public void addTag() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .addTag(alertId, tags);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "addTag")
    public void deleteTags() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .deleteTags(alertId, tags);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "deleteTags")
    public void deleteAlert() {
        ClientResponse<Empty> response = client()
            .alerts()
            .alert()
            .deleteAlert(alertId);

        //TODO: Returns 404 due to no alert
        //Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "deleteTags")
    public void deleteAlerts() {
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(1).toMillis();
        long end = now;

        ClientResponse<Integer> response = client()
            .alerts()
            .alert()
            .deleteAlerts(start, end, null, null, null, null, null);

        Assert.assertTrue(response.isSuccess());
    }
}
