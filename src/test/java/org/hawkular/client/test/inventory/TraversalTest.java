/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
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

import java.util.List;
import java.util.Map;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.hawkular.inventory.api.paging.Order;
import org.hawkular.inventory.paths.CanonicalPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory"}, dependsOnGroups = "inventory-bulkcreate")
public class TraversalTest extends BulkCreateBaseTest {

    @Test
    public void getTraversalForTenant() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .get();

        ClientResponse<List<Map>> response = client()
            .inventory()
            .traversal()
            .getTraversal(path, null, null, 10, null, Order.Direction.ASCENDING,
                          null, null, null, null, null, null, null, null, null, null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertNotNull(response.getEntity().get(0));
        Assert.assertTrue(response.getEntity().get(0).size() > 0);
    }

    @Test
    public void getTraversalForResource() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(feedId)
            .resource(resourceId)
            .get();

        ClientResponse<List<Map>> response = client()
            .inventory()
            .traversal()
            .getTraversal(path, null, null, 10, null, Order.Direction.ASCENDING,
                          null, null, null, null, null, null, null, null, null, null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertNotNull(response.getEntity().get(0));
        Assert.assertTrue(response.getEntity().get(0).size() > 0);
    }

    @Test
    public void getTraversalForPropertyByFilter() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .get();

        ClientResponse<List<Map>> response = client()
            .inventory()
            .traversal()
            .getTraversal(path, null, null, 10, null, Order.Direction.ASCENDING,
                          null, null, null, null, "rand", null, null, null, null, null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertNotNull(response.getEntity().get(0));
        Assert.assertTrue(response.getEntity().get(0).size() > 0);
    }
}
