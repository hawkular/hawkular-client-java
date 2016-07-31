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

import java.util.ArrayList;
import java.util.List;

import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.AvailabilityCondition.Operator;
import org.hawkular.alerts.api.model.condition.CompareCondition;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.condition.StringCondition;
import org.hawkular.alerts.api.model.condition.ThresholdCondition;
import org.hawkular.alerts.api.model.condition.ThresholdRangeCondition;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Test(groups = { "alerts" })
public class ConditionsTest extends BaseTest {

    public ConditionsTest() throws Exception {
        super();
    }

    @Test
    public void testAvailabilityCondition() {
        String triggerId = "New-Trigger-" + getRandomId();
        String dataId = "no-data-id";
        //Make Conditions
        AvailabilityCondition availabilityCondition = new AvailabilityCondition(
                triggerId, Mode.FIRING, dataId, Operator.DOWN);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(availabilityCondition);
        testCondition(triggerId, conditions, Mode.FIRING);
    }

    @Test
    public void testCompareCondition() {
        String triggerId = "New-Trigger-" + getRandomId();
        String dataId = "no-data-id";
        //Make Conditions
        CompareCondition compareCondition = new CompareCondition(
                triggerId, Mode.AUTORESOLVE, dataId, CompareCondition.Operator.GTE,
                1.0, "no-data-id-2");
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(compareCondition);
        testCondition(triggerId, conditions, Mode.AUTORESOLVE);
    }

    @Test
    public void testStringCondition() {
        String triggerId = "New-Trigger-" + getRandomId();
        String dataId = "no-data-id";
        //Make Conditions
        StringCondition stringCondition = new StringCondition(
                triggerId, Mode.FIRING, dataId, StringCondition.Operator.CONTAINS, "find-me", false);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(stringCondition);
        testCondition(triggerId, conditions, Mode.FIRING);
    }

    @Test
    public void testThresholdCondition() {
        String triggerId = "New-Trigger-" + getRandomId();
        String dataId = "no-data-id";
        //Make Conditions
        ThresholdCondition thresholdCondition = new ThresholdCondition(
                triggerId, Mode.FIRING, dataId, ThresholdCondition.Operator.LTE, 21.45);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(thresholdCondition);
        testCondition(triggerId, conditions, Mode.FIRING);
    }

    @Test
    public void testThresholdRangeCondition() {
        String triggerId = "New-Trigger-" + getRandomId();
        String dataId = "no-data-id";
        //Make Conditions
        ThresholdRangeCondition thresholdRangeCondition = new ThresholdRangeCondition(
                triggerId, Mode.FIRING, dataId, ThresholdRangeCondition.Operator.INCLUSIVE,
                ThresholdRangeCondition.Operator.INCLUSIVE, 21.45, 10.45, true);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(thresholdRangeCondition);
        testCondition(triggerId, conditions, Mode.FIRING);
    }

    private void testCondition(String triggerId, List<Condition> conditions, Mode mode) {
        //Create New trigger to add condition
        Trigger triggerNew = new Trigger(triggerId, "automation-unit-test");
        ClientResponse<Trigger> triggerCreateResult = client().alerts().createTrigger(triggerNew);
        Reporter.log("Trigger Creation Status:" + triggerCreateResult, true);
        Assert.assertTrue(triggerCreateResult.isSuccess());

        //Create Conditions
        ClientResponse<List<Condition>> conditionsResult = client().alerts().setConditions(triggerId,
                mode.name(), conditions);
        Reporter.log("Conditions Creation Status: " + conditionsResult, true);
        Assert.assertTrue(conditionsResult.isSuccess());

        //Get Conditions
        conditionsResult = client().alerts().getTriggerConditions(triggerId);
        Reporter.log("Conditions get status: " + conditionsResult, true);
        Assert.assertTrue(conditionsResult.isSuccess());

        //Clear Conditions
        conditions.clear();
        conditionsResult = client().alerts().setConditions(triggerId, mode.name(), conditions);
        Reporter.log("Clear Conditions Status: " + conditionsResult, true);
        Assert.assertTrue(conditionsResult.isSuccess());

        //Delete trigger
        ClientResponse<String> deleteResult = client().alerts().deleteTrigger(triggerId);
        Reporter.log("Trigger[" + triggerId + "] Delete Status: " + deleteResult, true);
        Assert.assertTrue(deleteResult.isSuccess());
    }
}
