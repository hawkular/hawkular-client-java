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
package org.hawkular.client.test.metrics;

import java.util.List;

import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.AvailabilityDataGenerator;
import org.hawkular.client.test.utils.MetricDefGenerator;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
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

    private final Metric<AvailabilityType> expectedDefinition = MetricDefGenerator.genAvailDef();

    private final Metric<AvailabilityType> metric2 = MetricDefGenerator.genAvailDef();
    private final List<DataPoint<AvailabilityType>> expectedData = AvailabilityDataGenerator.gen(
            AvailabilityType.DOWN,
            AvailabilityType.UP,
            AvailabilityType.UNKNOWN);

    public AvailabilityMetricTest() throws Exception {
        super();
    }

    @Test
    public void createDefinition() throws Exception {
        Reporter.log("Creating: " + expectedDefinition.toString(), true);
        client().metrics().createAvailabilityMetric(expectedDefinition);
    }

    // Known failure.  See https://issues.jboss.org/browse/HWKMETRICS-169
    @Test(dependsOnMethods = "createDefinition", enabled = false)
    public void getDefinition() throws Exception {
        Metric<AvailabilityType> actual =
                client().metrics().getAvailabilityMetric(expectedDefinition.getId()).getEntity();
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedDefinition);

    }

    @Test(dependsOnMethods = "createDefinition")
    public void addData() throws Exception {
        Reporter.log("Adding: " + expectedData.toString(), true);
        client().metrics().addAvailabilityDataForMetric(metric2.getId(), expectedData);
    }

    @Test(dependsOnMethods = "addData")
    public void getData() throws Exception {
        List<DataPoint<AvailabilityType>> actual =
                client().metrics().findAvailabilityData(metric2.getId()).getEntity();

        Assert.assertEquals(actual.size(), expectedData.size());

        Reporter.log("Expected: " + expectedData.toString(), true);
        Reporter.log("Actual: " + actual.toString(), true);

        Assert.assertEquals(actual, expectedData);
    }
}
