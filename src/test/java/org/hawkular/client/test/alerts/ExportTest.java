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
package org.hawkular.client.test.alerts;

import org.hawkular.alerts.api.model.export.Definitions;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "alerts" })
public class ExportTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ExportTest.class);

    @Test(dependsOnGroups = "alerts-import")
    public void export() {
        LOG.info("Exporting");

        ClientResponse<Definitions> response = client()
            .alerts()
            .export()
            .export();

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertNotNull(response.getEntity().getActions());
        Assert.assertNotNull(response.getEntity().getTriggers());
        Assert.assertTrue(response.getEntity().getActions().size() > 0);
        Assert.assertTrue(response.getEntity().getTriggers().size() > 0);
    }
}
