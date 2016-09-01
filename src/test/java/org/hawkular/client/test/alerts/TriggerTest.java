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

import org.hawkular.alerts.api.model.Severity;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Test(groups = { "alerts" })
public class TriggerTest extends BaseTest {

    public TriggerTest() throws Exception {
        super();
    }

    @Test
    public void testTrigger() {
        String triggerId = "trigger-id-" + RandomStringGenerator.getRandomId();
        //Create Trigger Test
        Trigger sampleTrigger = new Trigger(triggerId, "Sample Trigger");
        ClientResponse<Trigger> creationResult = client().alerts().createTrigger(sampleTrigger);
        Reporter.log("Trigger Creation Status: " + creationResult, true);
        Assert.assertTrue(creationResult.isSuccess());
        Assert.assertEquals(creationResult.getEntity().getId(), sampleTrigger.getId());
        Assert.assertEquals(creationResult.getEntity().getName(), sampleTrigger.getName());

        //Update Trigger
        sampleTrigger.setName("Sample Trigger - Edited");
        sampleTrigger.setEnabled(false);
        sampleTrigger.setDescription("created from REST unit test");
        sampleTrigger.setSeverity(Severity.LOW);
        ClientResponse<String> updateResult = client().alerts().updateTrigger(triggerId, sampleTrigger);
        Reporter.log("Trigger Update Status: " + updateResult, true);
        Assert.assertTrue(updateResult.isSuccess());

        //Fetch this trigger from hawkular and validate
        ClientResponse<Trigger> getResult = client().alerts().getTrigger(triggerId);
        Reporter.log("Trigger Get Status: " + getResult, true);
        Assert.assertTrue(getResult.isSuccess());
        Trigger fromHawkular = getResult.getEntity();
        Assert.assertEquals(fromHawkular.getId(), sampleTrigger.getId());
        Assert.assertEquals(fromHawkular.getDescription(), sampleTrigger.getDescription());
        Assert.assertEquals(fromHawkular.getName(), sampleTrigger.getName());
        Assert.assertEquals(fromHawkular.getSeverity().name(), sampleTrigger.getSeverity().name());
        Assert.assertEquals(fromHawkular.isEnabled(), sampleTrigger.isEnabled());

        //Enable Trigger
        sampleTrigger.setEnabled(true);
        updateResult = client().alerts().updateTrigger(triggerId, sampleTrigger);
        Reporter.log("Trigger Update Status: " + updateResult, true);
        Assert.assertTrue(updateResult.isSuccess());
        getResult = client().alerts().getTrigger(triggerId);
        Reporter.log("Trigger Get Status: " + getResult, true);
        Assert.assertTrue(getResult.isSuccess());
        fromHawkular = getResult.getEntity();
        Assert.assertEquals(fromHawkular.isEnabled(), sampleTrigger.isEnabled());

        //Create No Id Trigger
        Trigger noIdTrigger = new Trigger();
        noIdTrigger.setName("No Id Trigger");
        noIdTrigger.setId(null);
        creationResult = client().alerts().createTrigger(noIdTrigger);
        Reporter.log("No Id Trigger Creation Status: " + creationResult, true);
        Assert.assertTrue(creationResult.isSuccess());
        noIdTrigger = creationResult.getEntity();
        Assert.assertNotNull(noIdTrigger.getId());

        //Delete Trigger
        ClientResponse<String> deleteResult = client().alerts().deleteTrigger(sampleTrigger.getId());
        Reporter.log("Trigger Deletion Status(sample Trigger): " + deleteResult, true);
        Assert.assertTrue(deleteResult.isSuccess());

        deleteResult = client().alerts().deleteTrigger(noIdTrigger.getId());
        Reporter.log("Trigger Deletion Status(No Id Trigger): " + deleteResult, true);
        Assert.assertTrue(deleteResult.isSuccess());
    }

}
