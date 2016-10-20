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
import java.util.List;
import java.util.Map;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.inventory.model.ElementType;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.paths.CanonicalPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory", "inventory-bulkcreate"})
public class BulkCreateTest extends BulkCreateBaseTest {

    @Test
    public void createEnvironmentAndFeed() {
        CanonicalPath tenantPath = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .get();

        Environment.Blueprint environment = Environment.Blueprint
            .builder()
            .withId(environmentId)
            .withName("environment-test")
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        Feed.Blueprint feed = Feed.Blueprint.builder()
            .withId(feedId)
            .withName("feed-test")
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        Map<ElementType, List<Object>> values = new HashMap<ElementType, List<Object>>();
        values.put(ElementType.environment, Arrays.asList(environment));
        values.put(ElementType.feed, Arrays.asList(feed));

        Map<String, Map<ElementType, List<Object>>> entities = new HashMap<String, Map<ElementType, List<Object>>>();
        entities.put(tenantPath.toString(), values);

        ClientResponse<Map<ElementType, Map<CanonicalPath, Integer>>> response = client()
            .inventory()
            .bulkCreate()
            .create(entities);

        checkResponse(response);

        Assert.assertTrue(response.getEntity().containsKey(ElementType.environment));
        Assert.assertTrue(response.getEntity().containsKey(ElementType.feed));
    }

    @Test(dependsOnMethods = "createEnvironmentAndFeed")
    public void createResourceTypeUnderFeed() {
        CanonicalPath feedPath = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(feedId)
            .get();

        ResourceType.Blueprint resourceType = ResourceType.Blueprint
            .builder()
            .withId(resourceTypeId)
            .withName("resource-type")
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        Map<ElementType, List<Object>> values = new HashMap<ElementType, List<Object>>();
        values.put(ElementType.resourceType, Arrays.asList(resourceType));

        Map<String, Map<ElementType, List<Object>>> entities = new HashMap<String, Map<ElementType, List<Object>>>();
        entities.put(feedPath.toString(), values);

        ClientResponse<Map<ElementType, Map<CanonicalPath, Integer>>> response = client()
            .inventory()
            .bulkCreate()
            .create(entities);

        checkResponse(response);

        Assert.assertTrue(response.getEntity().containsKey(ElementType.resourceType));
    }

    @Test(dependsOnMethods = "createResourceTypeUnderFeed")
    public void createResourceUnderFeedResourceType() {
        CanonicalPath resourceTypePath = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(feedId)
            .get();

        Resource.Blueprint resource = Resource.Blueprint
            .builder()
            .withId(resourceId)
            .withName("resource")
            .withResourceTypePath(resourceTypeId)
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        Map<ElementType, List<Object>> values = new HashMap<ElementType, List<Object>>();
        values.put(ElementType.resource, Arrays.asList(resource));

        Map<String, Map<ElementType, List<Object>>> entities = new HashMap<String, Map<ElementType, List<Object>>>();
        entities.put(resourceTypePath.toString(), values);

        ClientResponse<Map<ElementType, Map<CanonicalPath, Integer>>> response = client()
            .inventory()
            .bulkCreate()
            .create(entities);

        checkResponse(response);

        Assert.assertTrue(response.getEntity().containsKey(ElementType.resource));
    }

    private void checkResponse(ClientResponse<Map<ElementType, Map<CanonicalPath, Integer>>> response) {
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        for (Map.Entry<ElementType, Map<CanonicalPath, Integer>> current : response.getEntity().entrySet()) {
            for (Map.Entry<CanonicalPath, Integer> innerCurrent : current.getValue().entrySet()) {
                Assert.assertEquals(innerCurrent.getValue(), Integer.valueOf(201));
            }
        }
    }
}
