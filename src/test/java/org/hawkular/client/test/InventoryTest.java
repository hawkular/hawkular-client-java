/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.client.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.hawkular.client.inventory.IdWrapper;
import org.hawkular.inventory.api.Resource;
import org.hawkular.inventory.api.ResourceType;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest{
    private static final String TENANT_ID = "tenant_"+ RandomStringUtils.randomAlphabetic(8);
    private final Resource expectedResource;

    public InventoryTest() throws Exception {
        super();
        expectedResource = generateTestResource();
    }

    private Resource generateTestResource() {
        Resource resource = new Resource();
        resource.setId("res_" + RandomStringUtils.randomAlphabetic(8));
        resource.addParameter("url", "http://hawkular.org/");
        resource.setType(ResourceType.URL);
        return resource;
    }

    @Test(priority=10)
    public void addResource() throws Exception {
        IdWrapper actualId =client().inventory().addResource(TENANT_ID, expectedResource);
        Assert.assertEquals(actualId.getId(), expectedResource.getId());
    }

    @Test(priority=10, dependsOnMethods={"addResource"})
    public void getResource() throws Exception {
        Resource actualResource = client().inventory().getResource(TENANT_ID, expectedResource.getId());
        Reporter.log(actualResource.toString());
        Assert.assertEquals(actualResource, expectedResource);
    }
}
