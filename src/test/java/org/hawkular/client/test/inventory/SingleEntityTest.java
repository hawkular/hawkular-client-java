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
import java.util.Map;
import java.util.Set;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.IdentityHash;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"inventory"})
public class SingleEntityTest extends BaseTest {

    private final String entityId = "entity-id-" + RandomStringGenerator.getRandomId();

    @Test
    public void createEntity() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .get();

        Map<String, Set<CanonicalPath>> outgoing = new HashMap<String, Set<CanonicalPath>>();
        outgoing.put("customRel", new HashSet<CanonicalPath>(Arrays.asList(path)));

        Feed.Blueprint blueprint = Feed.Blueprint
            .builder()
            .withId(entityId)
            .withOutgoingRelationships(outgoing)
            .build();

        ClientResponse<Map> response = client()
            .inventory()
            .singleEntity()
            .createEntity(path, SegmentType.f, null, blueprint);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "createEntity")
    public void getEntity() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(entityId)
            .get();

        ClientResponse<Map> response = client()
            .inventory()
            .singleEntity()
            .getEntity(path, null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "getEntity")
    public void getEntityHash() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(entityId)
            .get();

        ClientResponse<IdentityHash.Tree> response = client()
            .inventory()
            .singleEntity()
            .getEntityHash(path, null);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertNotNull(response.getEntity().getHash());
    }

    @Test(dependsOnMethods = "getEntityHash")
    public void updateEntity() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(entityId)
            .get();

        Feed.Update blueprint = Feed.Update
            .builder()
            .withName("Updated")
            .build();

        ClientResponse<Empty> response = client()
            .inventory()
            .singleEntity()
            .updateEntity(path, null, blueprint);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateEntity")
    public void deleteEntity() {
        CanonicalPath path = CanonicalPath.of()
            .tenant(BaseTest.HEADER_TENANT)
            .feed(entityId)
            .get();

        ClientResponse<Empty> response = client()
            .inventory()
            .singleEntity()
            .deleteEntity(path, null);

        Assert.assertTrue(response.isSuccess());
    }
}
