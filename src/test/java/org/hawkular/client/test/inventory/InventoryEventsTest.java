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
package org.hawkular.client.test.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.HawkularClient;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.inventory.api.Action;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory"})
public class InventoryEventsTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryEventsTest.class);

    private static class UpdateEntityRunnable implements Runnable {

        private HawkularClient client;

        public UpdateEntityRunnable(HawkularClient client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String entityId = "entity-id-" + RandomStringGenerator.getRandomId();

            CanonicalPath tenantPath = CanonicalPath.of()
                .tenant(BaseTest.HEADER_TENANT)
                .get();

            Map<String, Set<CanonicalPath>> outgoing = new HashMap<String, Set<CanonicalPath>>();
            outgoing.put("customRel", new HashSet<CanonicalPath>(Arrays.asList(tenantPath)));

            Environment.Blueprint createBlueprint = Environment.Blueprint
                .builder()
                .withId(entityId)
                .withOutgoingRelationships(outgoing)
                .build();

            ClientResponse<Map> responseCreate = client
                .inventory()
                .singleEntity()
                .createEntity(tenantPath, SegmentType.e, null, createBlueprint);

            Assert.assertTrue(responseCreate.isSuccess());

            CanonicalPath enviromentPath = CanonicalPath.of()
                .tenant(BaseTest.HEADER_TENANT)
                .environment(entityId)
                .get();

            Environment.Update updateBlueprint = Environment.Update
                .builder()
                .withName("Updated")
                .build();

            ClientResponse<Empty> responseUpdate = client
                .inventory()
                .singleEntity()
                .updateEntity(enviromentPath, null, updateBlueprint);

            Assert.assertTrue(responseUpdate.isSuccess());

            LOG.info("Updated {}", entityId);
        }
    }

    @Test
    public void getEvents() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new UpdateEntityRunnable(client()));

        ClientResponse<List<Map>> response = client()
            .inventory()
            .events()
            .getEvents(SegmentType.e, Action.Enumerated.UPDATED);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertNotNull(response.getEntity().get(0));
        Assert.assertTrue(response.getEntity().get(0).size() > 0);
    }
}
