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
package org.hawkular.client.test.metrics;

import java.util.List;
import java.util.Optional;

import org.hawkular.client.metrics.model.TenantParam;
import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class TenantTest extends BaseTest {

    public TenantTest() throws Exception {
        super();
    }

    @Test(priority=5)
    public void createTenants() throws Exception {
        List<TenantParam> tenantsBefore = client().metrics().getTenants();
        Reporter.log("Teant list Before: " + tenantsBefore.toString(), true);

        final Tenant expectedTenant = randomTenant();
        client().metrics().createTenant(expectedTenant);

        List<TenantParam> tenantsAfter = client().metrics().getTenants();
        Reporter.log("Tenant list After: " + tenantsAfter.toString(), true);

        Assert.assertTrue(tenantsBefore.size() == tenantsAfter.size()-1);

        Optional<TenantParam> value = tenantsAfter
                .stream()
                .filter(a -> a.getId().equals(expectedTenant.getId()))
                .findFirst();

        Assert.assertTrue(value.isPresent());
    }

}
