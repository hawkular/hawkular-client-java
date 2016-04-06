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
import org.hawkular.client.test.utils.CounterDataGenerator;
import org.hawkular.client.test.utils.MetricDefGenerator;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class CounterMetricTest extends BaseTest {

    private final Metric<Long> expectedDefinition = MetricDefGenerator.genCounterDef();
    private final List<DataPoint<Long>> expectedData = CounterDataGenerator.gen(10);

    public CounterMetricTest() throws Exception {
        super();
    }

    @Test
    public void createDefinition() throws Exception {
        Reporter.log("Creating: " + expectedDefinition.toString(), true);
        client().metrics().createCounter(expectedDefinition);
    }

    @Test(dependsOnMethods = "createDefinition")
    public void getDefinition() throws Exception {
        Metric<Long> actual =
                client().metrics().getCounter(expectedDefinition.getId()).getEntity();
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedDefinition);
    }

    @Test(dependsOnMethods = "getDefinition")
    public void addData() throws Exception {
        Reporter.log("Adding: " + expectedData, true);
        client().metrics().addCounterDataForMetric(expectedDefinition.getId(), expectedData);
    }

    @Test(dependsOnMethods = "addData")
    public void getData() throws Exception {
        List<DataPoint<Long>> actual =
                client().metrics().findCounterData(expectedDefinition.getId()).getEntity();
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedData);
    }
}
