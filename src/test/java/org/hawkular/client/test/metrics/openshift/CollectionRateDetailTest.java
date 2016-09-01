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
package org.hawkular.client.test.metrics.openshift;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.hawkular.metrics.model.DataPoint;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 *Find value=0 datapoints
 * @author vnguyen
 */
@Test(groups = { "openshift" })
public class CollectionRateDetailTest extends OpenshiftBaseTest {

    public CollectionRateDetailTest() throws Exception {
        super();
    }

    @Test
    public void findZeroValuesTest() throws Exception {
        String project = "zproject9";
        String container = "stress";
        String testID = "namespace: " + project + ", container: " + container;

        // for easy identification in the log
        String metricID = super.getMetricID(project, container, METRIC_SUFFIX.CPU_USAGE);

        // Get a lot of data in the last few days
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(36).toMillis();
        long end = start + Duration.ofHours(36).toMillis();

        Duration timeBucket = Duration.ofHours(1);
        getData(metricID, testID, start, end, timeBucket);

        // get data in the last hour
        start = now - Duration.ofHours(1).toMillis();
        end = now;
        timeBucket = Duration.ofMinutes(1);
        getData(metricID, testID, start, end, timeBucket);
    }

    private void getData(String metricID, String testID, long start, long end, Duration timeBucket) {
        Reporter.log("Fetching large data set... may take a couple minutes", true);
        List<DataPoint<Double>> rawData = client().metrics().gauge()
                .findGaugeDataWithId(metricID, String.valueOf(start), String.valueOf(end), null, null, null).getEntity();

        Assert.assertNotNull(rawData, testID);
        Reporter.log("raw datapoints: " + rawData.size(), true);

        List<Long> zeroList = findZeroValues(rawData);

        Assert.assertTrue(zeroList == null || zeroList.size() == 0, testID);

        Map<Long, Integer> hist = OpenshiftBaseTest.makeHistogram(rawData, timeBucket);

        Double[] result = hist.entrySet().stream()
                .map(x -> new Double(x.getValue()))
                .toArray(size -> new Double[size]);

        double[] d = ArrayUtils.toPrimitive(result);

        // drop the first and last as they are usually outliers
        double[] samples = Arrays.copyOfRange(d, 1, d.length - 1);
        DescriptiveStatistics stats = new DescriptiveStatistics(samples);

        Reporter.log(hist.toString(), true);
        Reporter.log("size: " + stats.getN(), true);
        Reporter.log("min/max: " + stats.getMin() + "/" + stats.getMax(), true);
        Reporter.log("mean: " + stats.getMean(), true);
        Reporter.log("variance: " + stats.getVariance(), true);
        Reporter.log("stddev: " + stats.getStandardDeviation(), true);
    }

    private List<Long> findZeroValues(List<DataPoint<Double>> rawData) {
        List<Long> zeroList = rawData
                .stream()
                .filter(p -> !(p.getValue() != 0))
                .map(p -> p.getTimestamp())
                .collect(Collectors.toList());
        return zeroList;
    }
}
