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
import java.util.List;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.inventory.api.model.Relationship;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.paths.CanonicalPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory"}, dependsOnGroups = "inventory-bulkcreate")
public class InventoryTenantTest extends BulkCreateBaseTest {

    private final String entityId = "entity-id-" + RandomStringGenerator.getRandomId();

    @Test
    public void createTenant() {
        Tenant.Update update = new Tenant.Update.Builder()
            .withName(BaseTest.HEADER_TENANT)
            .withProperty("FirstName", "MrTest")
            .build();

        ClientResponse<Empty> response = client()
            .inventory()
            .tenant()
            .createTenant(null, update);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createTenant")
    public void getTenant() {
        ClientResponse<Tenant> response = client()
            .inventory()
            .tenant()
            .getTenant(null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getTenant")
    public void createRelationship() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(feedId)
            .get();

        Relationship.Blueprint blueprint = Relationship.Blueprint
            .builder()
            .withName(entityId)
            .withOtherEnd(path)
            .build();

        ClientResponse<List<Relationship>> response = client()
            .inventory()
            .tenant()
            .createRelationship(null, Arrays.asList(blueprint));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "createRelationship")
    public void getRelationships() {
        CanonicalPath path = CanonicalPath.empty()
            .get();

        ClientResponse<List<Relationship>> response = client()
            .inventory()
            .tenant()
            .getRelationships(path, null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }
}
