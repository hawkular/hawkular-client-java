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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.param.Tags;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

/**
 * Verify container metric definitions<p>
 * Test expects 1-container pod
 * @author vnguyen
 *
 */
@Test(groups = { "openshift" })
public class MetricDefinitionTest extends OpenshiftBaseTest {

    private static final List<String> expectedMetricIDs = ImmutableList.of(
            "memory/page_faults",
            "cpu/usage",
            "memory/usage",
            "memory/major_page_faults",
            "memory/working_set",
            "cpu/limit",
            "uptime",
            "memory/limit");

    public MetricDefinitionTest() throws Exception {
        super();
    }

    //TODO: Test currently fails due to no metrics found...
    @Test
    public void verifyMetricDefinitionForPod() {
        String project = "default";
        String container = "hawkular-metrics";

        Map<String, String> tagsMap = new HashMap<String, String>();
        tagsMap.put("container_name", container);
        tagsMap.put("pod_namespace", project);

        Tags tags = new Tags(tagsMap);

        List<Metric<?>> defs = client().metrics().findMetrics(null, tags, null).getEntity();
        Reporter.log(defs.toString(), true);

        Assert.assertTrue(defs != null && defs.size() == expectedMetricIDs.size());

        // collect all metric IDs
        List<String> metricIDs = defs.stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        Reporter.log(metricIDs.toString(), true);

        // pad expected metric id with container name + pod id
        String podId = defs.get(0).getTags().get("pod_id");
        String idPrefix = container + "/" + podId + "/";
        List<String> expectedIDs = expectedMetricIDs
                .stream()
                .map(item -> idPrefix + item)
                .collect(Collectors.toList());
        Collections.sort(metricIDs);
        Collections.sort(expectedIDs);

        Assert.assertEquals(metricIDs, expectedIDs);
    }

    @Test
    public void verifyTags() {
        //TBD
    }
}
