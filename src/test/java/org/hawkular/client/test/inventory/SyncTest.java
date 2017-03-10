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

import java.util.EnumSet;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.InventoryStructure;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.SyncConfiguration;
import org.hawkular.inventory.api.model.SyncRequest;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory"}, dependsOnGroups = "inventory-bulkcreate")
public class SyncTest extends BulkCreateBaseTest {

    @Test
    public void synchronize() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(feedId)
            .get();

        Feed.Blueprint feed = Feed.Blueprint.builder()
            .withId(feedId)
            .withName("feed-test-synced")
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        ResourceType.Blueprint resourceType = ResourceType.Blueprint
            .builder()
            .withId(resourceTypeId)
            .withName("resource-type-synced")
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        Resource.Blueprint resource = Resource.Blueprint
            .builder()
            .withId(resourceId)
            .withName("resource-synced")
            .withResourceTypePath(resourceTypeId)
            .withProperty("rand", RandomStringGenerator.getRandomId())
            .build();

        EnumSet<SegmentType> enumSet = EnumSet.of(SegmentType.f, SegmentType.r, SegmentType.rt);
        SyncConfiguration config = new SyncConfiguration(enumSet, true);
        InventoryStructure<Feed.Blueprint> structure = InventoryStructure.Offline
            .of(feed)
            .addChild(resourceType)
            .addChild(resource)
            .build();

        SyncRequest<Feed.Blueprint> request = new SyncRequest<Feed.Blueprint>(config, structure);

        ClientResponse<Empty> response = client()
            .inventory()
            .sync()
            .synchronize(path, null, request);

        Assert.assertTrue(response.isSuccess());
    }
}
