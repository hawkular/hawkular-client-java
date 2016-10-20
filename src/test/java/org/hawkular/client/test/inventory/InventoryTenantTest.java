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

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.inventory.api.model.Tenant;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory"})
public class InventoryTenantTest extends BaseTest {

    @Test
    public void createTenant() {
        Tenant.Update update = new Tenant.Update.Builder()
            .withName(BaseTest.HEADER_TENANT)
            .withProperty("FirstName", "MrTest")
            .build();

        ClientResponse<Empty> response = client()
            .inventory()
            .tenant()
            .createTenant(update);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createTenant")
    public void getTenant() {
        ClientResponse<Tenant> response = client()
            .inventory()
            .tenant()
            .getTenant();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }
}
