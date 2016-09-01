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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.DataPointGenerator;
import org.hawkular.client.test.utils.MetricGenerator;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.client.test.utils.TagGenerator;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.Percentile;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TODO: ADD MORE CHECKS
 */
public class CounterTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(CounterTest.class);

    private final DataPointGenerator<Long> dataPointGenerator = new DataPointGenerator<Long>() {
        private AtomicLong value = new AtomicLong(1000L);

        @Override
        protected Long getValue(Random random) {
            value.getAndAccumulate(100L, (left, right) -> left + random.nextLong());
            return value.longValue();
        }
    };

    private Integer originalCounterCount = 0;
    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final Metric<Long> expectedMetric = MetricGenerator.generate(MetricType.COUNTER, tags.getTags(), metricName, dataPointGenerator.generator(10));

    @Test
    public void getCountersCount() {
        ClientResponse<List<Metric<Long>>> response = client()
            .metrics()
            .counter()
            .getCounters(tags);

        if (response.getStatusCode() == ResponseCodes.NO_CONTENT_204.value()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(response.isSuccess());
            Assert.assertNotNull(response.getEntity());
            Assert.assertTrue(response.getEntity().size() > 0);

            originalCounterCount = response.getEntity().size();
        }
    }

    @Test(dependsOnMethods = "getCountersCount")
    public void createCounter() {
        LOG.info("Testing with MetricName == {}", metricName);

        ClientResponse<Empty> response = client()
            .metrics()
            .counter()
            .createCounter(true, expectedMetric);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createCounter")
    public void getCounters() {
        ClientResponse<List<Metric<Long>>> response = client()
            .metrics()
            .counter()
            .getCounters(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertTrue(originalCounterCount == (response.getEntity().size() - 1));

        Optional<Metric<Long>> value = response.getEntity().stream()
            .filter(a -> a.equals(expectedMetric))
            .findFirst();

        Assert.assertTrue(value.isPresent());
    }

    @Test(dependsOnMethods = "getCounters")
    public void findCounterRateDataStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .counter()
            .findCounterRateDataStats(null, null, null, duration, new Percentiles(Arrays.asList(percentile)), null, Arrays.asList(metricName), true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getCounters")
    public void addCounterData() {
        Metric<Long> metric = MetricGenerator.generate(MetricType.COUNTER, tags.getTags(), metricName + "1", dataPointGenerator.generator(10));

        ClientResponse<Empty> response = client()
            .metrics()
            .counter()
            .addCounterData(Arrays.asList(metric));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "getCounters")
    public void findCounterStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .counter()
            .findCounterStats(null, null, null, duration, new Percentiles(Arrays.asList(percentile)), null, Arrays.asList(metricName), true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "getCounters")
    public void findCounterMetrics() {
        ClientResponse<Map<String, List<String>>> response = client()
            .metrics()
            .counter()
            .findCounterMetrics(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    @Test(dependsOnMethods = "getCounters")
    public void getCounter() {
        ClientResponse<Metric<Long>> response = client()
            .metrics()
            .counter()
            .getCounter(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getCounters")
    public void findCounterRate() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .counter()
            .findCounterRate(metricName, null, null, null, null, null, duration, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getCounters")
    public void findCounterRateStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .counter()
            .findCounterRateStats(metricName, null, null, null, duration, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getCounters")
    public void findCounterData() {
        ClientResponse<List<DataPoint<Long>>> response = client()
            .metrics()
            .counter()
            .findCounterData(metricName, null, null, 1, Order.ASC);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getCounters")
    public void createCounterData() {
        ClientResponse<Empty> response = client()
            .metrics()
            .counter()
            .createCounterData(metricName, dataPointGenerator.generator(10));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createCounterData")
    public void findCounterMetricStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .counter()
            .findCounterMetricStats(metricName, null, null, true, null, duration, new Percentiles(Arrays.asList(percentile)), 1, Order.ASC);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "createCounterData", enabled = false)
    public void getCounterMetricStatsTags() {
        Percentile percentile = new Percentile("90.0");

        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .counter()
            .getCounterMetricStatsTags(metricName, tags, null, null, new Percentiles(Arrays.asList(percentile)));

        //TODO: Not sure what populates this... as always get back 204 - no content
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "createCounterData")
    public void getCounterMetricTags() {
        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .counter()
            .getCounterMetricTags(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(tags.getTags(), response.getEntity());
    }

    @Test(dependsOnMethods = "getCounterMetricTags")
    public void updateCountersMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .counter()
            .updateCountersMetricTags(metricName, TagGenerator.generateMap("updatedNamespace", "updatedPod"));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateCountersMetricTags")
    public void deleteCounterMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .counter()
            .deleteCounterMetricTags(metricName, TagGenerator.generate("updatedNamespace", "updatedPod"));

        Assert.assertTrue(response.isSuccess());
    }
}
