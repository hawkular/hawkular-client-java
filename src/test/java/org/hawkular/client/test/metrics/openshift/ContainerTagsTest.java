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

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.hawkular.client.test.utils.JSONHelper;
import org.hawkular.metrics.model.Metric;
import org.testng.annotations.Test;

@Test(groups = { "openshift" })
public class ContainerTagsTest extends OpenshiftBaseTest {

    private final Metric<?> expectedDef;

    public ContainerTagsTest() throws Exception {
        super();
        File jsonFile = FileUtils.getFile("/openshift/single-container-metric-tags.json");
        if (jsonFile.exists()) {
            expectedDef = JSONHelper.load(Metric.class, jsonFile);
        } else {
            expectedDef = null;
        }
    }

    @Test
    public void verifyContainerTagsTest() {
        System.out.println(expectedDef);
    }
}
