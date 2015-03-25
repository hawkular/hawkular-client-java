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
import java.util.Optional;

import org.testng.Assert;
import org.apache.commons.lang3.RandomStringUtils;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TenantTest extends BaseTest {

    public TenantTest() throws Exception {
        super();
    }

    @Test
    public void createTenants() throws Exception {
        List<Tenant> tenantsBefore = client().metrics().findTenants();
        Reporter.log("Before: " + tenantsBefore.toString());

        Tenant tenant = new Tenant();
        tenant.setId(RandomStringUtils.randomAlphabetic(5));
        client().metrics().createTenant(tenant);

        List<Tenant> tenantsAfter = client().metrics().findTenants();
        Reporter.log("After: " + tenantsAfter.toString());

        Assert.assertTrue(tenantsBefore.size() == tenantsAfter.size()-1);

        Optional<Tenant> value = tenantsAfter
                .stream()
                .filter(a -> a.getId().equals(tenant.getId()))
                .findFirst();

      Assert.assertTrue(value.isPresent());

    }

}
