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

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.hawkular.metrics.core.api.Availability;
import org.hawkular.metrics.core.api.AvailabilityType;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class AvailabilityMetricTest extends BaseTest {

    // System.currentTimeMillis() may return the same value when being called successively
    // we add/subtract this multiplier to each call to "space out the time
    private AtomicLong multiplier = new AtomicLong(Duration.ofHours(1).toMillis());

    private final Tenant tenant = randomTenant();
    private final List<Availability> expectedAvailability = ImmutableList.of(down(), up(), up(), down());

    public AvailabilityMetricTest() throws Exception {
        super();
    }

    @Test
    public void addAvailDataTest() throws Exception {
        client().metrics().addAvailabilityData(tenant.getId(), "apache", expectedAvailability);
    }

    @Test(dependsOnMethods="addAvailDataTest")
    public void getAvailabilityTest() throws Exception {
        List<Availability> actual = client().metrics().getAvailabilityData(tenant.getId(), "apache");
        Assert.assertEquals(actual, expectedAvailability);
    }

    private Availability up() {
        return new Availability(System.currentTimeMillis() - multiplier.getAndAdd(1000), AvailabilityType.UP);
    }

    private Availability down() {
        return new Availability(System.currentTimeMillis() - multiplier.getAndAdd(1000), AvailabilityType.DOWN);
    }
}
