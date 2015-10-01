package org.hawkular.client.test.alerts;

import java.util.ArrayList;
import java.util.List;

import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.AvailabilityCondition.Operator;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ConditionsTest extends BaseTest {

    public ConditionsTest() throws Exception {
        super();
    }

    @Test
    public void testConditions() {
        String triggerId = "New-Trigger-" + getRandomId();
        String dataId = "Data-ID-new-trigger";
        //Create New trigger to add condition
        Trigger triggerNew = new Trigger(triggerId, dataId);
        ClientResponse<Trigger> triggerCreateResult = client().alerts().createTrigger(triggerNew);
        Reporter.log("Trigger Creation Status:" + triggerCreateResult, true);
        Assert.assertTrue(triggerCreateResult.isSuccess());

        //Create Condition
        AvailabilityCondition availabilityCondition = new AvailabilityCondition(triggerId, dataId, Operator.DOWN);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(availabilityCondition);
        client().alerts().setConditions(triggerId, Mode.FIRING.name(), conditions);
    }
}
