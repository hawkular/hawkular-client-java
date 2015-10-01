package org.hawkular.client.test.alerts;

import org.hawkular.alerts.api.model.Severity;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class TriggerTest extends BaseTest {

    public TriggerTest() throws Exception {
        super();
    }

    @Test
    public void testTrigger() {
        String triggerId = "trigger-id-" + getRandomId();
        //Create Trigger Test
        Trigger sampleTrigger = new Trigger(triggerId, "Sample Trigger");
        ClientResponse<Trigger> creationResult = client().alerts().createTrigger(sampleTrigger);
        Reporter.log("Trigger Creation Status: " + creationResult, true);
        Assert.assertTrue(creationResult.isSuccess());
        Assert.assertEquals(creationResult.getEntity().getId(), sampleTrigger.getId());
        Assert.assertEquals(creationResult.getEntity().getId(), sampleTrigger.getName());

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
