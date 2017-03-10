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
package org.hawkular.client.test.metrics;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.metrics.model.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"metrics"})
public class MetricsTenantTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsTenantTest.class);

    private Integer originalTenantCount = 0;
    private Tenant expectedTenant = new Tenant(RandomStringGenerator.getRandomId());

    @Test
    public void getTentantsCount() {
        LOG.info("Testing with Tenant == {}", expectedTenant.getId());

        ClientResponse<List<Tenant>> response = client()
            .metrics()
            .tenant()
            .getTenants();

        if (response.getStatusCode() == ResponseCodes.NO_CONTENT_204.value()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(response.isSuccess());
            Assert.assertNotNull(response.getEntity());
            Assert.assertTrue(response.getEntity().size() > 0);

            originalTenantCount = response.getEntity().size();
        }
    }

    @Test(dependsOnMethods = "getTentantsCount")
    public void createTenants() throws Exception {
        ClientResponse<Empty> response = client()
            .metrics()
            .tenant()
            .createTenant(true, expectedTenant);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createTenants")
    public void getTentant() {
        ClientResponse<List<Tenant>> response = client()
            .metrics()
            .tenant()
            .getTenants();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        Optional<Tenant> value = response.getEntity().stream()
            .filter(a -> a.equals(expectedTenant))
            .findFirst();

        Assert.assertTrue(value.isPresent());
    }

    @Test(dependsOnMethods = "getTentant")
    public void deleteTenant() throws Exception {
        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .tenant()
            .deleteTenant(expectedTenant.getId());

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() == 1);
    }
}
