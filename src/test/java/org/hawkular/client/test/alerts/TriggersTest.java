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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawkular.alerts.api.json.GroupConditionsInfo;
import org.hawkular.alerts.api.json.GroupMemberInfo;
import org.hawkular.alerts.api.json.UnorphanMemberInfo;
import org.hawkular.alerts.api.model.Severity;
import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.trigger.FullTrigger;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"alerts"})
public class TriggersTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(TriggersTest.class);

    private final String triggerId = "trigger-id-" + RandomStringGenerator.getRandomId();
    private final String fullTriggerId = "New-Trigger-" + RandomStringGenerator.getRandomId();
    private final String groupTriggerId = "trigger-id-" + RandomStringGenerator.getRandomId();

    private Trigger trigger = new Trigger(BaseTest.HEADER_TENANT, triggerId, "Sample Trigger", null, null);
    private Trigger noIdTrigger = new Trigger(BaseTest.HEADER_TENANT, "id", "No Id Trigger", null, null);
    private Trigger groupTrigger = new Trigger(BaseTest.HEADER_TENANT, groupTriggerId, "Sample Group Trigger", null, null);
    private Dampening strictDampening = Dampening.forStrict(BaseTest.HEADER_TENANT, triggerId, Mode.FIRING, 1);
    private Dampening groupDampening = Dampening.forStrict(BaseTest.HEADER_TENANT, groupTriggerId, Mode.FIRING, 1);

    @Test
    public void createTrigger() {
        LOG.info("Testing with Triggers == {}", triggerId);

        ClientResponse<Trigger> response = client()
            .alerts()
            .triggers()
            .createTrigger(trigger);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), trigger);
    }

    @Test(dependsOnMethods = "createTrigger")
    public void findTriggers() {
        ClientResponse<List<Trigger>> response = client()
            .alerts()
            .triggers()
            .findTriggers(triggerId, null, false);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() == 1);
        Assert.assertEquals(response.getEntity().get(0), trigger);
    }

    @Test(dependsOnMethods = "findTriggers")
    public void updateTrigger() {
        trigger.setName("Sample Trigger - Edited");
        trigger.setEnabled(false);
        trigger.setDescription("created from REST unit test");
        trigger.setSeverity(Severity.LOW);

        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .updateTrigger(triggerId, trigger);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateTrigger")
    public void getTrigger() {
        //Fetch this trigger from hawkular and validate
        ClientResponse<Trigger> response = client()
            .alerts()
            .triggers()
            .getTrigger(triggerId);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), trigger);
    }

    @Test(dependsOnMethods = "getTrigger")
    public void updateTriggerToEnabled() {
        trigger.setEnabled(true);

        ClientResponse<Empty> updateResponse = client()
            .alerts()
            .triggers()
            .updateTrigger(triggerId, trigger);

        Assert.assertTrue(updateResponse.isSuccess());

        ClientResponse<Trigger> getResponse = client()
            .alerts()
            .triggers()
            .getTrigger(triggerId);

        Assert.assertTrue(getResponse.isSuccess());
        Assert.assertNotNull(getResponse.getEntity());
        Assert.assertEquals(getResponse.getEntity(), trigger);
    }

    @Test(dependsOnMethods = "updateTriggerToEnabled")
    public void createTriggerNoId() {
        noIdTrigger.setId(null);

        ClientResponse<Trigger> response = client()
            .alerts()
            .triggers()
            .createTrigger(noIdTrigger);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());

        //Update ID to allow for simple comparison
        noIdTrigger.setId(response.getEntity().getId());

        Assert.assertEquals(response.getEntity(), noIdTrigger);
    }

    @Test(dependsOnMethods = "createTriggerNoId")
    public void deleteTrigger() {
        ClientResponse<Empty> deleteTriggerResponse = client()
            .alerts()
            .triggers()
            .deleteTrigger(trigger.getId());

        Assert.assertTrue(deleteTriggerResponse.isSuccess());

        ClientResponse<Empty> deleteNoIdTriggerResponse = client()
            .alerts()
            .triggers()
            .deleteTrigger(noIdTrigger.getId());

        Assert.assertTrue(deleteNoIdTriggerResponse.isSuccess());
    }

    @Test
    public void createFullTrigger() {
        LOG.info("Testing with FullTriggers == {}", fullTriggerId);

        AvailabilityCondition condition = new AvailabilityCondition(fullTriggerId, Mode.FIRING, "no-data-id", AvailabilityCondition.Operator.UP);
        Dampening dampening = Dampening.forRelaxedCount(BaseTest.HEADER_TENANT, fullTriggerId, Mode.FIRING, 1, 2);
        Trigger trigger = new Trigger(BaseTest.HEADER_TENANT, fullTriggerId, "trigger is up");
        FullTrigger fullTrigger = new FullTrigger(trigger, Arrays.asList(dampening), Arrays.asList(condition));

        ClientResponse<FullTrigger> response = client()
            .alerts()
            .triggers()
            .createFullTrigger(fullTrigger);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), fullTrigger);
    }

    @Test(dependsOnMethods = "createFullTrigger")
    public void getFullTriggerById() {
        ClientResponse<FullTrigger> response = client()
            .alerts()
            .triggers()
            .getFullTriggerById(fullTriggerId);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "createFullTrigger")
    public void getTriggerDampenings() {
        ClientResponse<List<Dampening>> response = client()
            .alerts()
            .triggers()
            .getTriggerDampenings(fullTriggerId);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() == 1);
    }

    @Test(dependsOnMethods = "createFullTrigger")
    public void getTriggerModeDampenings() {
        ClientResponse<List<Dampening>> response = client()
            .alerts()
            .triggers()
            .getTriggerModeDampenings(fullTriggerId, Mode.FIRING);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() == 1);
    }

    @Test(dependsOnMethods = "findTriggers")
    public void createDampening() {
        ClientResponse<Dampening> response = client()
            .alerts()
            .triggers()
            .createDampening(triggerId, strictDampening);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), strictDampening);
    }

    @Test(dependsOnMethods = "createDampening")
    public void getDampening() {
        ClientResponse<Dampening> response = client()
            .alerts()
            .triggers()
            .getDampening(fullTriggerId, strictDampening.getDampeningId());

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), strictDampening);
    }

    @Test(dependsOnMethods = "getDampening")
    public void updateDampening() {
        strictDampening.setEvalTrueSetting(5);

        ClientResponse<Dampening> response = client()
            .alerts()
            .triggers()
            .updateDampening(triggerId, strictDampening.getDampeningId(), strictDampening);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), strictDampening);
    }

    @Test(dependsOnMethods = "updateDampening")
    public void deleteDampening() {
        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .deleteDampening(triggerId, strictDampening.getDampeningId());

        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void createGroupTrigger() {
        Map<String, String> dataIdMap = new HashMap<String, String>();
        dataIdMap.put("$SystemAvailability$", "Member1SystemAvailability");

        groupTrigger.setDataIdMap(dataIdMap);

        ClientResponse<Trigger> response = client()
            .alerts()
            .triggers()
            .createGroupTrigger(groupTrigger);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), groupTrigger);
    }

    @Test(dependsOnMethods = "createGroupTrigger")
    public void setGroupConditions() throws IOException {
        AvailabilityCondition availabilityCondition = new AvailabilityCondition(groupTriggerId, Mode.FIRING, "no-data-id", AvailabilityCondition.Operator.DOWN);
        availabilityCondition.setDataId("$SystemAvailability$");

        ClientResponse<List<Condition>> response = client()
            .alerts()
            .triggers()
            .setGroupConditions(groupTriggerId, Mode.FIRING.name(), new GroupConditionsInfo(availabilityCondition, null));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() == 1);
        Assert.assertEquals(response.getEntity().get(0).getClass(), AvailabilityCondition.class);
    }

    @Test(dependsOnMethods = "setGroupConditions")
    public void createGroupMember() {
        Map<String, String> dataIdMap = new HashMap<String, String>();
        dataIdMap.put("$SystemAvailability$", "Member1SystemAvailability");

        GroupMemberInfo member = new GroupMemberInfo(groupTriggerId, "Member1", "Member1", null, null, null, dataIdMap);

        ClientResponse<Trigger> response = client()
            .alerts()
            .triggers()
            .createGroupMember(member);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "createGroupMember")
    public void findGroupMembers() {
        ClientResponse<List<Trigger>> response = client()
            .alerts()
            .triggers()
            .findGroupMembers(groupTriggerId, true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "findGroupMembers")
    public void updateGroupTrigger() {
        groupTrigger.setDescription("Updated desc");

        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .updateGroupTrigger(groupTriggerId, groupTrigger);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findGroupMembers")
    public void createGroupDampening() {
        ClientResponse<Dampening> response = client()
            .alerts()
            .triggers()
            .createGroupDampening(groupTriggerId, groupDampening);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), groupDampening);
    }

    @Test(dependsOnMethods = "createGroupDampening")
    public void updateGroupDampening() {
        groupDampening.setEvalTotalSetting(5);

        ClientResponse<Dampening> response = client()
            .alerts()
            .triggers()
            .updateGroupDampening(groupTriggerId, groupDampening.getDampeningId(), groupDampening);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), groupDampening);
    }

    @Test(dependsOnMethods = "updateGroupTrigger")
    public void orphanMemberTrigger() {
        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .orphanMemberTrigger("Member1");

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "orphanMemberTrigger")
    public void unorphanMemberTrigger() {
        Map<String, String> dataIdMap = new HashMap<String, String>();
        dataIdMap.put("$SystemAvailability$", "Member1SystemAvailability");

        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .unorphanMemberTrigger("Member1", new UnorphanMemberInfo(null, null, dataIdMap));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "unorphanMemberTrigger")
    public void deleteGroupDampening() {
        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .deleteGroupDampening(groupTriggerId, groupDampening.getDampeningId());

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "deleteGroupDampening")
    public void deleteGroupTrigger() {
        ClientResponse<Empty> response = client()
            .alerts()
            .triggers()
            .deleteGroupTrigger(groupTriggerId, false, false);

        Assert.assertTrue(response.isSuccess());
    }
}
