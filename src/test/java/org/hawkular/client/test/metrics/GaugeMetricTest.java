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

import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.GaugeDataGenerator;
import org.hawkular.client.test.utils.MetricDefGenerator;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

//@Test(groups = { "known-failure" }, description = "HWKMETRICS-51")
public class GaugeMetricTest extends BaseTest {

    private final Metric<Double> expectedMetric = MetricDefGenerator.genGaugeDef();

    private final List<DataPoint<Double>> expectedData1 = GaugeDataGenerator.gen(4);

    public GaugeMetricTest() throws Exception {
        super();
    }

    @Test
    public void createDefinition() throws Exception {
        Reporter.log(expectedMetric.toString(), true);
        client().metrics().createGaugeMetric(expectedMetric);
    }

    @Test(dependsOnMethods = "createDefinition")
    public void getDefinition() throws Exception {
        Metric<Double> actualMetric = client().metrics().getGaugeMetric(
                expectedMetric.getId()).getEntity();
        Reporter.log(actualMetric.toString(), true);

        Assert.assertEquals(actualMetric, expectedMetric);

        Assert.assertEquals(actualMetric.getTags(), expectedMetric.getTags());

    }

    @Test(dependsOnMethods = "getDefinition")
    public void addData() {
        Reporter.log("Adding: " + expectedData1.toString(), true);
        client().metrics().addGaugeDataForMetric(expectedMetric.getId(), expectedData1);
    }

    @Test(dependsOnMethods = "addData")
    public void getData() throws Exception {
        List<?> actual = client().metrics().findGaugeDataWithId(expectedMetric.getId()).getEntity();
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedData1);
    }

}
