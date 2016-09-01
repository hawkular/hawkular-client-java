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

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.param.TagsConverter;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.client.test.BTG;
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
import org.hawkular.metrics.model.TaggedBucketPoint;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GaugeTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(GaugeTest.class);

    private final DataPointGenerator<Double> dataPointGenerator = new DataPointGenerator<Double>() {
        @Override
        protected Double getValue(Random random) {
            return random.nextDouble();
        }
    };

    private Integer originalGaugeCount = 0;
    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final Metric<Double> expectedMetric = MetricGenerator.generate(MetricType.GAUGE, tags.getTags(), metricName, dataPointGenerator.generator(10));

    @Test
    public void findGaugeMetricsCount() {
        ClientResponse<List<Metric<Double>>> response = client()
            .metrics()
            .gauge()
            .findGaugeMetrics(tags);

        if (response.getStatusCode() == ResponseCodes.NO_CONTENT_204.value()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(response.isSuccess());
            Assert.assertNotNull(response.getEntity());
            Assert.assertTrue(response.getEntity().size() > 0);

            originalGaugeCount = response.getEntity().size();
        }
    }

    @Test(dependsOnMethods = "findGaugeMetricsCount")
    public void createGaugeMetric() {
        LOG.info("Testing with MetricName == {}", metricName);

        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .createGaugeMetric(true, expectedMetric);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createGaugeMetric")
    public void findGaugeMetrics() {
        ClientResponse<List<Metric<Double>>> response = client()
            .metrics()
            .gauge()
            .findGaugeMetrics(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertTrue(originalGaugeCount == (response.getEntity().size() - 1));

        Optional<Metric<Double>> value = response.getEntity().stream()
            .filter(a -> a.equals(expectedMetric))
            .findFirst();

        Assert.assertTrue(value.isPresent());
    }

    @Test(dependsOnMethods = "createGaugeMetric")
    public void addGaugeData() {
        Metric<Double> metric = MetricGenerator.generate(MetricType.GAUGE, tags.getTags(), metricName + "1", dataPointGenerator.generator(10));

        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .addGaugeData(Arrays.asList(metric));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void findGaugeRateStats() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .gauge()
            .findGaugeRateStats(
                start, end, 1, duration, new Percentiles(Arrays.asList(percentile)), null, Arrays.asList(metricName), true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void findGaugeStats() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .gauge()
            .findGaugeStats(
                start, end, null, duration, new Percentiles(Arrays.asList(percentile)), null, Arrays.asList(metricName), true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeMetricTagValues() {
        ClientResponse<Map<String, List<String>>> response = client()
            .metrics()
            .gauge()
            .getGaugeMetricTagValues(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertTrue(response.getEntity().containsKey(TagGenerator.POD_NAMESPACE));
        Assert.assertTrue(response.getEntity().get(TagGenerator.POD_NAMESPACE).contains(podNamespace));
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeMetric() {
        ClientResponse<Metric<Double>> response = client()
            .metrics()
            .gauge()
            .getGaugeMetric(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(expectedMetric, response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData", enabled = false)
    public void findGaugeDataPeriods() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        ClientResponse<List<Long[]>> response = client()
            .metrics()
            .gauge()
            .findGaugeDataPeriods(metricName, start, end, 1.0, "eq");

        //TODO: Not sure what populates this... as always get back 204 - no content
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData", enabled = false)
    public void getGaugeRate() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        ClientResponse<List<DataPoint<Double>>> response = client()
            .metrics()
            .gauge()
            .getGaugeRate(metricName, start, end, 1, Order.ASC);

        //TODO: Not sure what populates this... as always get back 204 - no content
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData", enabled = true)
    public void getGaugeRateStats() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .gauge()
            .getGaugeRateStats(metricName, start, end, null, duration, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void findGaugeDataWithId() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        ClientResponse<List<DataPoint<Double>>> response = client()
            .metrics()
            .gauge()
            .findGaugeDataWithId(metricName, start, end, true, 1, Order.ASC);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void addGaugeDataForMetric() {
        BTG ts = new BTG();
        DataPoint<Double> point = new DataPoint<Double>(ts.nextMilli(), 10.0, tags.getTags());

        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .addGaugeDataForMetric(metricName, Arrays.asList(point));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "addGaugeData", enabled = false)
    public void getGaugeStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<DataPoint<Double>>> response = client()
            .metrics()
            .gauge()
            .getGaugeStats(
                metricName, null, null, true, null, duration, new Percentiles(Arrays.asList(percentile)));

        //TODO: Not sure what populates this... as always get back 204 - no content
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeStatsTags() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        Percentile percentile = new Percentile("90.0");

        ClientResponse<Map<String, TaggedBucketPoint>> response = client()
            .metrics()
            .gauge()
            .getGaugeStatsTags(metricName, tags, start, end, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        String tagsKey = new TagsConverter().toString(tags);
        Assert.assertTrue(response.getEntity().containsKey(tagsKey));

        TaggedBucketPoint bucket = response.getEntity().get(tagsKey);
        Assert.assertNotNull(bucket);
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeMetricTags() {
        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .gauge()
            .getGaugeMetricTags(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getGaugeMetricTags")
    public void updateGaugeMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .updateGaugeMetricTags(metricName, TagGenerator.generateMap(RandomStringGenerator.getRandomId(), RandomStringGenerator.getRandomId()));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateGaugeMetricTags")
    public void deleteGaugeMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .deleteGaugeMetricTags(metricName, tags);

        Assert.assertTrue(response.isSuccess());
    }
}
