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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawkular.alerts.api.model.action.Action;
import org.hawkular.alerts.api.model.action.ActionDefinition;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "alerts" })
public class ActionsTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ActionsTest.class);

    private final String actionId = "New-Action-" + RandomStringGenerator.getRandomId();

    @Test(dependsOnGroups = "alerts-plugins")
    public void createAction() {
        LOG.info("Testing with Action == {}", actionId);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(PluginsTest.EMAIL_PLUGIN_PROPERTY_FROM, "unit-testing@hawkular-alerts.local");
        properties.put(PluginsTest.EMAIL_PLUGIN_PROPERTY_TO, "alerts@unit-testing.local");
        ActionDefinition definition = new ActionDefinition(BaseTest.HEADER_TENANT, PluginsTest.EMAIL_PLUGIN_NAME, actionId, properties);

        ClientResponse<ActionDefinition> response = client()
            .alerts()
            .actions()
            .createAction(definition);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "createAction")
    public void findActions() {
        ClientResponse<Map<String, List<String>>> response = client()
            .alerts()
            .actions()
            .findActions();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "findActions")
    public void updateAction() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(PluginsTest.EMAIL_PLUGIN_PROPERTY_FROM, "unit-testing@hawkular-alerts.local");
        properties.put(PluginsTest.EMAIL_PLUGIN_PROPERTY_TO, "updated-email@unit-testing.local");
        ActionDefinition definition = new ActionDefinition(BaseTest.HEADER_TENANT, PluginsTest.EMAIL_PLUGIN_NAME, actionId, properties);

        ClientResponse<ActionDefinition> response = client()
            .alerts()
            .actions()
            .updateAction(definition);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), definition);
    }

    @Test(dependsOnMethods = "updateAction")
    public void getActionHistory() {
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(1).toMillis();
        long end = now;

        ClientResponse<List<Action>> response = client()
            .alerts()
            .actions()
            .getActionHistory(start, end, PluginsTest.EMAIL_PLUGIN_NAME, null, null, null, false);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "getActionHistory")
    public void deleteActionHistory() {
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(1).toMillis();
        long end = now;

        ClientResponse<Integer> response = client()
            .alerts()
            .actions()
            .deleteActionHistory(start, end, PluginsTest.EMAIL_PLUGIN_NAME, null, null, null);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateAction")
    public void findActionsByPlugin() {
        ClientResponse<List<String>> response = client()
            .alerts()
            .actions()
            .findActionsByPlugin(PluginsTest.EMAIL_PLUGIN_NAME);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "updateAction")
    public void getAction() {
        ClientResponse<ActionDefinition> response = client()
            .alerts()
            .actions()
            .getAction(PluginsTest.EMAIL_PLUGIN_NAME, actionId);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getAction")
    public void deleteAction() {
        ClientResponse<Empty> response = client()
            .alerts()
            .actions()
            .deleteAction(PluginsTest.EMAIL_PLUGIN_NAME, actionId);

        Assert.assertTrue(response.isSuccess());
    }
}
