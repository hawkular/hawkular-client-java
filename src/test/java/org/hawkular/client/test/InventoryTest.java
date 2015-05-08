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

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {
    private static final String TENANT_ID = "tenant_" + RandomStringUtils.randomAlphabetic(8);
    private static final String ENVIRONMENT_ID = "environment_" + RandomStringUtils.randomAlphabetic(8);
    private static final Feed FEED =
            new Feed(TENANT_ID, ENVIRONMENT_ID, "feed_" + RandomStringUtils.randomAlphabetic(8));
    private static final ResourceType RESOURCE_TYPE =
            new ResourceType(TENANT_ID, "resource_type_" + RandomStringUtils.randomAlphabetic(8), "V:1.0");
    private static final Resource RESOURCE =
            new Resource(TENANT_ID, ENVIRONMENT_ID, FEED.getId(), "resource_" + RandomStringUtils.randomAlphabetic(8),
                    RESOURCE_TYPE);
    private static final MetricType METRIC_TYPE =
            new MetricType(TENANT_ID, "metri_type_" + RandomStringUtils.randomAlphabetic(8));
    private static final Metric METRIC =
            new Metric(TENANT_ID, ENVIRONMENT_ID, FEED.getId(), "metric_" + RandomStringUtils.randomAlphabetic(8),
                    METRIC_TYPE);

    public InventoryTest() throws Exception {
        super();
    }

    @Test(priority = 1)
    public void createTest() {
        Assert.assertTrue(client().inventory().createTenant(TENANT_ID));
        Assert.assertTrue(client().inventory().createEnvironment(TENANT_ID, ENVIRONMENT_ID));
        Assert.assertTrue(client().inventory().registerFeed(FEED));
        Assert.assertTrue(client().inventory().createResourceType(RESOURCE_TYPE));
        Assert.assertTrue(client().inventory().addResource(RESOURCE));
        Assert.assertTrue(client().inventory().createMetricType(METRIC_TYPE));
        Assert.assertTrue(client().inventory().createMetric(METRIC));
    }

    @Test(priority = 2)
    public void listTest() {
        //Tenant test
        List<Tenant> tenants = client().inventory().getTenants();
        boolean isTenantFound = false;
        for (Tenant tenant : tenants) {
            if (tenant.getId().equals(TENANT_ID)) {
                isTenantFound = true;
                break;
            }
        }
        Assert.assertTrue(isTenantFound);

        //Environment test
        Environment environmentRx = client().inventory().getEnvironment(TENANT_ID, ENVIRONMENT_ID);
        Assert.assertEquals(environmentRx, new Environment(TENANT_ID, ENVIRONMENT_ID));

        /** Feed returns null, disabled for now, https://github.com/hawkular/hawkular-inventory/pull/60*/
        //Feed Test
        /*Feed feedRx = client().inventory().getFeed(FEED);
        Assert.assertEquals(feedRx.getTenantId(), FEED.getTenantId());
        Assert.assertEquals(feedRx.getEnvironmentId(), FEED.getEnvironmentId());
        Assert.assertEquals(feedRx.getId(), FEED.getId());
        Assert.assertEquals(feedRx.getProperties(), FEED.getProperties());*/

        //ResourceType Test
        ResourceType resourceTypeRx = client().inventory().getResourceType(RESOURCE_TYPE);
        Assert.assertEquals(resourceTypeRx.getId(), RESOURCE_TYPE.getId());
        Assert.assertEquals(resourceTypeRx.getTenantId(), RESOURCE_TYPE.getTenantId());
        Assert.assertEquals(resourceTypeRx.getProperties(), RESOURCE_TYPE.getProperties());
        Assert.assertEquals(resourceTypeRx.getVersion(), RESOURCE_TYPE.getVersion());

        //Resource Test
        Resource resourceRx = client().inventory().getResource(RESOURCE);
        Assert.assertEquals(resourceRx.getTenantId(), RESOURCE.getTenantId());
        Assert.assertEquals(resourceRx.getEnvironmentId(), RESOURCE.getEnvironmentId());
        /** Feed id returns null, disabled for now*/
        //Assert.assertEquals(resourceRx.getFeedId(), RESOURCE.getFeedId());
        Assert.assertEquals(resourceRx.getId(), RESOURCE.getId());
        Assert.assertEquals(resourceRx.getProperties(), RESOURCE.getProperties());
        Assert.assertEquals(resourceRx.getType(), RESOURCE.getType());

        //MetricType Test
        MetricType metricTypeRx = client().inventory().getMetricType(METRIC_TYPE);
        Assert.assertEquals(metricTypeRx.getTenantId(), METRIC_TYPE.getTenantId());
        Assert.assertEquals(metricTypeRx.getProperties(), METRIC_TYPE.getProperties());
        Assert.assertEquals(metricTypeRx.getId(), METRIC_TYPE.getId());
        Assert.assertEquals(metricTypeRx.getUnit(), METRIC_TYPE.getUnit());

        //Metric Test
        Metric metricRx = client().inventory().getMetric(METRIC);
        Assert.assertEquals(metricRx.getTenantId(), METRIC.getTenantId());
        Assert.assertEquals(metricRx.getEnvironmentId(), METRIC.getEnvironmentId());
        /** Feed id returns null, disabled for now*/
        //Assert.assertEquals(metricRx.getFeedId(), METRIC.getFeedId());
        Assert.assertEquals(metricRx.getId(), METRIC.getId());
        Assert.assertEquals(metricRx.getProperties(), METRIC.getProperties());
        Assert.assertEquals(metricRx.getType(), METRIC.getType());
    }

    @Test(priority = 3)
    public void deleteTest() {
        Assert.assertTrue(client().inventory().deleteMetric(METRIC));
        Assert.assertTrue(client().inventory().deleteMetricType(METRIC_TYPE));
        Assert.assertTrue(client().inventory().deleteResource(RESOURCE));
        Assert.assertTrue(client().inventory().deleteResourceType(RESOURCE_TYPE));
        Assert.assertTrue(client().inventory().deleteFeed(FEED));
        Assert.assertTrue(client().inventory().deleteEnvironment(TENANT_ID, ENVIRONMENT_ID));
        Assert.assertTrue(client().inventory().deleteTenant(TENANT_ID));
    }
}
