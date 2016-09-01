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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.DataPointGenerator;
import org.hawkular.client.test.utils.MetricGenerator;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.client.test.utils.TagGenerator;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.param.Tags;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * Insert and retrieve data
 *
 * @author vnguyen
 */
public class OldAvailabilityMetricTest extends BaseTest {

    private final DataPointGenerator<AvailabilityType> dataPointGenerator = new DataPointGenerator<AvailabilityType>() {
        private List<AvailabilityType> values = Arrays.asList(AvailabilityType.DOWN, AvailabilityType.UP, AvailabilityType.UNKNOWN);
        private AtomicInteger counter = new AtomicInteger(0);

        @Override
        protected AvailabilityType getValue(Random random) {
            if (counter.get() >= values.size()) {
                counter.set(0);
            }

            int index = counter.getAndIncrement();
            return values.get(index);
        }
    };

    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final List<DataPoint<AvailabilityType>> expectedData = dataPointGenerator.generator(3, tags.getTags());
    private final Metric<AvailabilityType> expectedDefinition = MetricGenerator.generate(MetricType.AVAILABILITY, tags.getTags(), metricName, expectedData);
    private final Metric<AvailabilityType> metric2 = MetricGenerator.generate(MetricType.AVAILABILITY, tags.getTags(), metricName, expectedData);

    @Test
    public void createDefinition() throws Exception {
        Reporter.log("Creating: " + expectedDefinition.toString(), true);
        ClientResponse<Empty> resp = client().metrics().availability().createAvailabilityMetric(true, expectedDefinition);

        Assert.assertTrue(resp.isSuccess());
    }

    // Known failure.  See https://issues.jboss.org/browse/HWKMETRICS-169
    @Test(dependsOnMethods = "createDefinition", enabled = false)
    public void getDefinition() throws Exception {
        Metric<AvailabilityType> actual = client().metrics().availability().getAvailabilityMetric(expectedDefinition.getId()).getEntity();
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedDefinition);

    }

    @Test(dependsOnMethods = "createDefinition")
    public void addData() throws Exception {
        Reporter.log("Adding: " + expectedData.toString(), true);
        client().metrics().availability().addAvailabilityDataForMetric(metric2.getId(), expectedData);
    }

    @Test(dependsOnMethods = "addData")
    public void getData() throws Exception {
        List<DataPoint<AvailabilityType>> actual = client().metrics().availability().findAvailabilityData(metric2.getId(), null, null, null, null, null).getEntity();

        //Sort so that equals can match
        Collections.sort(actual, new Comparator<DataPoint<AvailabilityType>>() {
            @Override
            public int compare(DataPoint<AvailabilityType> o1, DataPoint<AvailabilityType> o2) {
                return new Long(o1.getTimestamp()).compareTo(new Long(o2.getTimestamp()));
            }
        });

        Assert.assertEquals(actual.size(), expectedData.size());

        Reporter.log("Expected: " + expectedData.toString(), true);
        Reporter.log("Actual: " + actual.toString(), true);

        Assert.assertEquals(actual, expectedData);
    }
}
