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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.alerts.trigger.utils.SetConitionMethod;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Test(groups = {"alerts"})
public class TriggersConditionsTest extends BaseTest {

    @Test
    public void setAvailabilityCondition() throws IOException {
        String triggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();

        @SuppressWarnings("unchecked")
        SetConitionMethod method = (triggerId1, triggerMode, conditions) -> client()
            .alerts()
            .triggers()
            .setAvailabilityCondition(triggerId1, triggerMode, conditions);

        AvailabilityCondition availabilityCondition = new AvailabilityCondition(triggerId, Mode.FIRING, "no-data-id", Operator.DOWN);

        executeConditionTest(triggerId, Arrays.asList(availabilityCondition), Mode.FIRING, method);
    }

    @Test
    public void setCompareCondition() {
        String triggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();

        @SuppressWarnings("unchecked")
        SetConitionMethod method = (triggerId1, triggerMode, conditions) -> client()
            .alerts()
            .triggers()
            .setCompareCondition(triggerId1, triggerMode, conditions);

        CompareCondition compareCondition =
            new CompareCondition(triggerId, Mode.AUTORESOLVE, "no-data-id", CompareCondition.Operator.GTE, 1.0, "no-data-id-2");

        executeConditionTest(triggerId, Arrays.asList(compareCondition), Mode.AUTORESOLVE, method);
    }

    @Test
    public void setStringCondition() {
        String triggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();

        @SuppressWarnings("unchecked")
        SetConitionMethod method = (triggerId1, triggerMode, conditions) -> client()
            .alerts()
            .triggers()
            .setStringCondition(triggerId1, triggerMode, conditions);

        StringCondition stringCondition =
            new StringCondition(triggerId, Mode.FIRING, "no-data-id", StringCondition.Operator.CONTAINS, "find-me", false);

        executeConditionTest(triggerId, Arrays.asList(stringCondition), Mode.FIRING, method);
    }

    @Test
    public void setThresholdCondition() {
        String triggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();

        @SuppressWarnings("unchecked")
        SetConitionMethod method = (triggerId1, triggerMode, conditions) -> client()
            .alerts()
            .triggers()
            .setThresholdCondition(triggerId1, triggerMode, conditions);

        ThresholdCondition thresholdCondition =
            new ThresholdCondition(triggerId, Mode.FIRING, "no-data-id", ThresholdCondition.Operator.LTE, 21.45);

        executeConditionTest(triggerId, Arrays.asList(thresholdCondition), Mode.FIRING, method);
    }

    @Test
    public void setThresholdRangeCondition() {
        String triggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();

        @SuppressWarnings("unchecked")
        SetConitionMethod method = (triggerId1, triggerMode, conditions) -> client()
            .alerts()
            .triggers()
            .setThresholdRangeCondition(triggerId1, triggerMode, conditions);

        ThresholdRangeCondition thresholdRangeCondition =
            new ThresholdRangeCondition(triggerId, Mode.FIRING, "no-data-id", ThresholdRangeCondition.Operator.INCLUSIVE,
                                        ThresholdRangeCondition.Operator.INCLUSIVE, 21.45, 10.45, true);

        executeConditionTest(triggerId, Arrays.asList(thresholdRangeCondition), Mode.FIRING, method);
    }

    private void executeConditionTest(String triggerId, List<Condition> conditions, Mode mode, SetConitionMethod callback) {
        //Create New trigger to add condition
        Trigger triggerNew = new Trigger(triggerId, "automation-unit-test");

        ClientResponse<Trigger> triggerCreateResult = client()
            .alerts()
            .triggers()
            .createTrigger(triggerNew);

        Assert.assertTrue(triggerCreateResult.isSuccess());
        Assert.assertNotNull(triggerCreateResult.getEntity());

        //Create Conditions
        @SuppressWarnings("unchecked")
        ClientResponse<List<Condition>> setConditionsResult = callback.setConditions(triggerId, mode.name(), conditions);

        Assert.assertTrue(setConditionsResult.isSuccess());
        Assert.assertNotNull(setConditionsResult.getEntity());
        Assert.assertTrue(setConditionsResult.getEntity().size() > 0);

        //Get Conditions
        ClientResponse<List<Condition>> getConditionsResult = client()
            .alerts()
            .triggers()
            .getTriggerConditions(triggerId);

        Assert.assertTrue(getConditionsResult.isSuccess());
        Assert.assertNotNull(getConditionsResult.getEntity());
        Assert.assertTrue(getConditionsResult.getEntity().size() > 0);

        //Clear Conditions
        @SuppressWarnings("unchecked")
        ClientResponse<List<Condition>> setConditionsResult2 = callback.setConditions(triggerId, mode.name(), new ArrayList<Condition>());

        Assert.assertTrue(setConditionsResult2.isSuccess());
        Assert.assertNotNull(setConditionsResult2.getEntity());
        Assert.assertTrue(setConditionsResult2.getEntity().size() == 0);

        //Delete trigger
        ClientResponse<Empty> deleteResult = client()
            .alerts()
            .triggers()
            .deleteTrigger(triggerId);

        Assert.assertTrue(deleteResult.isSuccess());
    }
}
