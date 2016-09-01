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
import java.util.concurrent.atomic.AtomicLong;

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

public class OldCounterMetricTest extends BaseTest {

    private final DataPointGenerator<Long> dataPointGenerator = new DataPointGenerator<Long>() {
        private AtomicLong value = new AtomicLong(1000L);

        @Override
        protected Long getValue(Random random) {
            value.getAndAccumulate(100L, (left, right) -> left + random.nextLong());
            return value.longValue();
        }
    };

    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final List<DataPoint<Long>> expectedData = dataPointGenerator.generator(10);
    private final Metric<Long> expectedDefinition = MetricGenerator.generate(MetricType.COUNTER, tags.getTags(), metricName, expectedData);

    @Test
    public void createDefinition() throws Exception {
        Reporter.log("Creating: " + expectedDefinition.toString(), true);
        client().metrics().counter().createCounter(true, expectedDefinition);
    }

    @Test(dependsOnMethods = "createDefinition")
    public void getDefinition() throws Exception {
        Metric<Long> actual =
            client().metrics().counter().getCounter(expectedDefinition.getId()).getEntity();
        Reporter.log("Got: " + actual, true);
        Assert.assertEquals(actual, expectedDefinition);
    }

    @Test(dependsOnMethods = "getDefinition")
    public void addData() throws Exception {
        Reporter.log("Adding: " + expectedData, true);
        client().metrics().counter().createCounterData(expectedDefinition.getId(), expectedData);
    }

    @Test(dependsOnMethods = "addData", enabled = false)
    public void getData() throws Exception {
        List<DataPoint<Long>> actual = client().metrics().counter().findCounterData(expectedDefinition.getId(), null, null, null, null).getEntity();

        //Sort so that equals can match
        Collections.sort(actual, new Comparator<DataPoint<Long>>() {
            @Override
            public int compare(DataPoint<Long> o1, DataPoint<Long> o2) {
                return new Long(o1.getTimestamp()).compareTo(new Long(o2.getTimestamp()));
            }
        });

        Reporter.log("Got: " + actual.toString(), true);

        Assert.assertEquals(actual, expectedData);
    }
}
