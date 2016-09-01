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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.DataPointGenerator;
import org.hawkular.client.test.utils.MetricGenerator;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.client.test.utils.TagGenerator;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.param.Tags;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

//@Test(groups = { "known-failure" }, description = "HWKMETRICS-51")
public class OldGaugeMetricTest extends BaseTest {

    private final DataPointGenerator<Double> dataPointGenerator = new DataPointGenerator<Double>() {
        @Override
        protected Double getValue(Random random) {
            return random.nextDouble();
        }
    };

    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final List<DataPoint<Double>> expectedData1 = dataPointGenerator.generator(10, tags.getTags());
    private final Metric<Double> expectedMetric = MetricGenerator.generate(MetricType.GAUGE, tags.getTags(), metricName, expectedData1);

    @Test
    public void createDefinition() throws Exception {
        Reporter.log(expectedMetric.toString(), true);
        client().metrics().gauge().createGaugeMetric(true, expectedMetric);
    }

    @Test(dependsOnMethods = "createDefinition")
    public void getDefinition() throws Exception {
        Metric<Double> actualMetric = client().metrics().gauge().getGaugeMetric(
                expectedMetric.getId()).getEntity();
        Reporter.log(actualMetric.toString(), true);

        Assert.assertEquals(actualMetric, expectedMetric);

        Assert.assertEquals(actualMetric.getTags(), expectedMetric.getTags());

    }

    @Test(dependsOnMethods = "getDefinition")
    public void addData() {
        Reporter.log("Adding: " + expectedData1.toString(), true);
        client().metrics().gauge().addGaugeDataForMetric(expectedMetric.getId(), expectedData1);
    }

    @Test(dependsOnMethods = "addData")
    public void getData() throws Exception {
        List<DataPoint<Double>> actual = client().metrics().gauge().findGaugeDataWithId(expectedMetric.getId(), null, null, null, null, null).getEntity();

        //Sort so that equals can match
        Collections.sort(actual, new Comparator<DataPoint<Double>>() {
            @Override
            public int compare(DataPoint<Double> o1, DataPoint<Double> o2) {
                return new Long(o1.getTimestamp()).compareTo(new Long(o2.getTimestamp()));
            }
        });

        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedData1);
    }

}
