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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hawkular.alerts.api.model.action.ActionDefinition;
import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.export.Definitions;
import org.hawkular.alerts.api.model.export.ImportType;
import org.hawkular.alerts.api.model.trigger.FullTrigger;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "alerts", "alerts-import"})
public class ImportTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ImportTest.class);

    @Test(dependsOnGroups = "alerts-plugins")
    public void imports() {
        LOG.info("Importing");

        //Trigger
        String triggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();

        AvailabilityCondition condition = new AvailabilityCondition(triggerId, Mode.FIRING, "no-data-id", AvailabilityCondition.Operator.UP);
        Dampening dampening = Dampening.forRelaxedCount(BaseTest.HEADER_TENANT, triggerId, Mode.FIRING, 1, 2);
        Trigger trigger = new Trigger(BaseTest.HEADER_TENANT, triggerId, "import is up");
        FullTrigger fullTrigger = new FullTrigger(trigger, Arrays.asList(dampening), Arrays.asList(condition));

        //Alert
        String alertId = "New-Alert-" + RandomStringGenerator.getRandomId();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(PluginsTest.EMAIL_PLUGIN_PROPERTY_FROM, "unit-testing@hawkular-alerts.local");
        properties.put(PluginsTest.EMAIL_PLUGIN_PROPERTY_TO, "alerts@unit-testing.local");
        ActionDefinition definition = new ActionDefinition(BaseTest.HEADER_TENANT, PluginsTest.EMAIL_PLUGIN_NAME, alertId, properties);

        ClientResponse<Definitions> response = client()
            .alerts()
            .imports()
            .importDefinitions(ImportType.NEW.name(), new Definitions(Arrays.asList(fullTrigger), Arrays.asList(definition)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertNotNull(response.getEntity().getActions());
        Assert.assertNotNull(response.getEntity().getTriggers());
        Assert.assertTrue(response.getEntity().getActions().size() == 1);
        Assert.assertTrue(response.getEntity().getTriggers().size() == 1);
    }
}
