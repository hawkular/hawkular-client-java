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

import org.hawkular.client.metrics.model.AvailabilityDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.AvailabilityDataGenerator;
import org.hawkular.metrics.core.api.AvailabilityType;
import org.testng.Assert;
import org.testng.Reporter;
//import org.hawkular.metrics.core.api.Availability;
import org.testng.annotations.Test;

/**
 * Insert and retrieve data
 * @author vnguyen
 *
 */
public class AvailabilityMetricTest extends BaseTest {

    private final MetricDefinition expectedDefinition = AvailabilityDataGenerator.genDef();

    private final MetricDefinition metric2 = AvailabilityDataGenerator.genDef();
    private final List<AvailabilityDataPoint> expectedData
            = AvailabilityDataGenerator.gen(
                    AvailabilityType.DOWN,
                    AvailabilityType.UP,
                    AvailabilityType.UNKNOWN);

    public AvailabilityMetricTest() throws Exception {
        super();
    }

    @Test
    public void createDefinition() throws Exception {
        Reporter.log("Creating: " + expectedDefinition.toString(), true);
        client().metrics().createAvailabilityMetric(expectedDefinition.getTenantId(), expectedDefinition);
    }

    // Known failure.  See https://issues.jboss.org/browse/HWKMETRICS-169
    @Test (dependsOnMethods="createDefinition", enabled=false)
    public void getDefinition() throws Exception {
        MetricDefinition actual =
                client().metrics().getAvailabilityMetric(expectedDefinition.getTenantId(),
                                                         expectedDefinition.getId());
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedDefinition);

    }

    @Test
    public void addData() throws Exception {
        Reporter.log("Adding: " + expectedData.toString(), true);
        client().metrics().addAvailabilityData(metric2.getTenantId(), metric2.getId(), expectedData);
    }


    @Test(dependsOnMethods="addData")
    public void getData() throws Exception {
        List<AvailabilityDataPoint> actual =
                client().metrics().getAvailabilityData(metric2.getTenantId(), metric2.getId());

        Assert.assertEquals(actual.size(), expectedData.size());

        Reporter.log("Expected: " + expectedData.toString(), true);
        Reporter.log("Actual: " + actual.toString(), true);

        Assert.assertEquals(actual, expectedData);
    }
}
