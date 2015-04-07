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

import org.hawkular.metrics.core.api.Availability;
import org.hawkular.metrics.core.api.AvailabilityType;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Simulate a series of availability metric for a web server {(timestamp, status), (timestamp, status)}
 * Insert and retrieve data
 * @author vnguyen
 *
 */
public class AvailabilityMetricTest extends BaseTest {

    private BTG timestampGen = new BTG();

    private final Tenant tenant = randomTenant();
    private final List<Availability> expectedAvailability = ImmutableList.of(up(), down(), down());

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
        Assert.assertEquals(actual.size(), expectedAvailability.size());
        Reporter.log(actual.toString());
        Reporter.log(expectedAvailability.toString());
        // have to reserve list due to https://issues.jboss.org/browse/HWKMETRICS-51
        Assert.assertEquals(Lists.reverse(actual), expectedAvailability);
    }

    private Availability up() {
        return new Availability(timestampGen.nextMilli(), AvailabilityType.UP);
    }

    private Availability down() {
        return new Availability(timestampGen.nextMilli(), AvailabilityType.DOWN);
    }
}
