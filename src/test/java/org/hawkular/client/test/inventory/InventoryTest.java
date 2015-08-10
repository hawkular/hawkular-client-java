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
package org.hawkular.client.test.inventory;

import org.apache.commons.lang3.RandomStringUtils;
import org.hawkular.client.test.BaseTest;
import org.hawkular.inventory.api.model.CanonicalPath;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricDataType;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.MetricUnit;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {
    private static String TENANT_ID;
    private static final String ENVIRONMENT_ID = "environment_" + RandomStringUtils.randomAlphabetic(8);
    private static Feed FEED;
    private static ResourceType RESOURCE_TYPE;
    private static Resource RESOURCE;
    private static MetricType METRIC_TYPE;
    private static Metric METRIC;

    public InventoryTest() throws Exception {
        super();
    }

    @BeforeClass
    public void loadVariables() {
        TENANT_ID = client().inventory().getTenant().getEntity().getId();
        FEED = new Feed(CanonicalPath.of().tenant(TENANT_ID).environment(ENVIRONMENT_ID)
                .feed("feed_" + RandomStringUtils.randomAlphabetic(8)).get());
        RESOURCE_TYPE = new ResourceType(CanonicalPath.of().tenant(TENANT_ID)
                .resourceType("resource_type_" + RandomStringUtils.randomAlphabetic(8)).get());
        RESOURCE = new Resource(CanonicalPath.of().tenant(TENANT_ID).environment(ENVIRONMENT_ID).feed(FEED.getId())
                .resource("resource_" + RandomStringUtils.randomAlphabetic(8)).get(), RESOURCE_TYPE);
        METRIC_TYPE = new MetricType(CanonicalPath.of().tenant(TENANT_ID)
                .metricType("metri_type_" + RandomStringUtils.randomAlphabetic(8)).get(), MetricUnit.NONE,
                MetricDataType.GAUGE);
        METRIC = new Metric(CanonicalPath.of().tenant(TENANT_ID).environment(ENVIRONMENT_ID).feed(FEED.getId())
                .metric("metric_" + RandomStringUtils.randomAlphabetic(8)).get(), METRIC_TYPE);
    }

    @Test(priority = 1)
    public void createTest() {
        Assert.assertTrue(client().inventory().createEnvironment(ENVIRONMENT_ID).isSuccess());
        Assert.assertTrue(client().inventory().registerFeed(FEED).isSuccess());
        Reporter.log(
                "Resource Type before creation: " + RESOURCE_TYPE.getId() + ", " + RESOURCE_TYPE.getProperties(), true);
        Assert.assertTrue(client().inventory().createResourceType(RESOURCE_TYPE).isSuccess());
        Assert.assertTrue(client().inventory().addResource(RESOURCE).isSuccess());
        Assert.assertTrue(client().inventory().createMetricType(METRIC_TYPE).isSuccess());
        Assert.assertTrue(client().inventory().createMetric(METRIC).isSuccess());
    }

    @Test(priority = 2)
    public void listTest() {
        //Tenant test
        Tenant tenant = client().inventory().getTenant().getEntity();
        Assert.assertTrue(tenant != null);

        //Environment test
        Environment environmentRx = client().inventory().getEnvironment(ENVIRONMENT_ID).getEntity();
        Assert.assertEquals(environmentRx,
                new Environment(CanonicalPath.of().tenant(TENANT_ID).environment(ENVIRONMENT_ID).get()));

        /** Feed returns null, disabled for now, https://github.com/hawkular/hawkular-inventory/pull/60*/
        //Feed Test
        /*Feed feedRx = client().inventory().getFeed(FEED);
        Assert.assertEquals(feedRx.getTenantId(), FEED.getTenantId());
        Assert.assertEquals(feedRx.getEnvironmentId(), FEED.getEnvironmentId());
        Assert.assertEquals(feedRx.getId(), FEED.getId());
        Assert.assertEquals(feedRx.getProperties(), FEED.getProperties());*/

        //ResourceType Test
        ResourceType resourceTypeRx = client().inventory().getResourceType(RESOURCE_TYPE).getEntity();
        Assert.assertEquals(resourceTypeRx.getId(), RESOURCE_TYPE.getId());
        Assert.assertEquals(resourceTypeRx.getTenantId(), RESOURCE_TYPE.getTenantId());
        Assert.assertEquals(resourceTypeRx.getProperties(), RESOURCE_TYPE.getProperties());

        //Resource Test
        Resource resourceRx = client().inventory().getResource(RESOURCE).getEntity();
        Assert.assertEquals(resourceRx.getTenantId(), RESOURCE.getTenantId());
        Assert.assertEquals(resourceRx.getEnvironmentId(), RESOURCE.getEnvironmentId());
        /** Feed id returns null, disabled for now*/
        //Assert.assertEquals(resourceRx.getFeedId(), RESOURCE.getFeedId());
        Assert.assertEquals(resourceRx.getId(), RESOURCE.getId());
        Assert.assertEquals(resourceRx.getProperties(), RESOURCE.getProperties());
        Assert.assertEquals(resourceRx.getType(), RESOURCE.getType());

        //MetricType Test
        MetricType metricTypeRx = client().inventory().getMetricType(METRIC_TYPE).getEntity();
        Assert.assertEquals(metricTypeRx.getTenantId(), METRIC_TYPE.getTenantId());
        Assert.assertEquals(metricTypeRx.getProperties(), METRIC_TYPE.getProperties());
        Assert.assertEquals(metricTypeRx.getId(), METRIC_TYPE.getId());
        Assert.assertEquals(metricTypeRx.getUnit(), METRIC_TYPE.getUnit());

        //Metric Test
        Metric metricRx = client().inventory().getMetric(METRIC).getEntity();
        Assert.assertEquals(metricRx.getTenantId(), METRIC.getTenantId());
        Assert.assertEquals(metricRx.getEnvironmentId(), METRIC.getEnvironmentId());
        /** Feed id returns null, disabled for now*/
        //Assert.assertEquals(metricRx.getFeedId(), METRIC.getFeedId());
        Assert.assertEquals(metricRx.getId(), METRIC.getId());
        Assert.assertEquals(metricRx.getProperties(), METRIC.getProperties());
        Assert.assertEquals(metricRx.getType(), METRIC.getType());
    }

    /**Deletion is not supported right now, returns 403. Communicate with dev to know who have access to delete */
    /* @Test(priority = 3)
     public void deleteTest() {
         Assert.assertTrue(client().inventory().deleteMetric(METRIC));
         Assert.assertTrue(client().inventory().deleteMetricType(METRIC_TYPE));
         Assert.assertTrue(client().inventory().deleteResource(RESOURCE));
         Assert.assertTrue(client().inventory().deleteResourceType(RESOURCE_TYPE));
         Assert.assertTrue(client().inventory().deleteFeed(FEED));
         Assert.assertTrue(client().inventory().deleteEnvironment(ENVIRONMENT_ID));
     }*/
}
