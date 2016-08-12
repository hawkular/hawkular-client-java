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

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.param.Tags;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * Fetch raw metric data of select containers and aggregate them by time
 * slot to see metric collection rates over a period of time
 * @author vnguyen
 */
@Test(groups = { "openshift" })
public class OpenshiftCollectionHistTest extends BaseTest {
    public static final String TENANT_ID = "heapster";

    public OpenshiftCollectionHistTest() throws Exception {
        super();
    }

    //TODO: Test currently fails due to no metrics found...
    @Test
    public void getMetricDefs() throws Exception {

        printHist("stress_cpu",
                getHistogram("zproject9", "stress", "cpu/usage"),
                false);

        printHist("stress_mem",
                getHistogram("zproject9", "stress", "memory/usage"),
                false);

        printHist("cass_cpu",
                getHistogram("default", "hawkular-cassandra", "cpu/usage"),
                false);

        printHist("cass_mem",
                getHistogram("default", "hawkular-cassandra", "memory/usage"),
                false);

        printHist("hawk_cpu",
                getHistogram("default", "hawkular-metrics", "cpu/usage"),
                false);

        printHist("hawk_mem",
                getHistogram("default", "hawkular-metrics", "memory/usage"),
                false);
    }

    public String getPodUID(String podNamespace, String containerName) {
        Map<String, String> tagsMap = new HashMap<String, String>();
        tagsMap.put("container_name", containerName);
        tagsMap.put("pod_namespace", podNamespace);

        Tags tags = new Tags(tagsMap);

        List<Metric<?>> defs = super.client().metrics().findMetrics(MetricType.GAUGE, tags, null).getEntity();
        Assert.assertNotNull(defs, "namespace: " + podNamespace + ", container: " + containerName);
        Assert.assertTrue(defs.size() > 1);
        return defs.get(0).getTags().get("pod_id");
    }

    public Map<Long, Integer> getHistogram(String podNamespace, String containerName, String metricName) {
        String podId = getPodUID(podNamespace, containerName);
        String metricID = containerName + "/" + podId + "/" + metricName;

        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(36).toMillis();
        long dur = start + Duration.ofHours(36).toMillis();

        List<DataPoint<Double>> rawData = client().metrics().findGaugeDataWithId(metricID, start, dur, null, null,
                null, null, null, null).getEntity();

        Assert.assertNotNull(rawData, "namespace: " + podNamespace + ", container: " + containerName);

        Duration timeBucket = Duration.ofHours(1);

        return OpenshiftBaseTest.makeHistogram(rawData, timeBucket);
    }

    /**
     * Save histogram data to file in csv format
     * @param filename file name with csv extension
     * @param histogram
     * @param printToStdout set to true to also log the output in TestNG Reporter standard out
     */
    public static void printHist(String filename, Map<Long, Integer> histogram, boolean printToStdout) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream(filename + ".csv"));

            for (Map.Entry<Long, Integer> e : histogram.entrySet()) {
                Date date = new Date(e.getKey());
                String s = format.format(date) + "," + e.getValue();
                out.println(s);
                if (printToStdout) {
                    Reporter.log(s, true);
                }
            }
        } catch (Exception e) {
            Assert.fail("File IO error", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}
