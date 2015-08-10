/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.client.test.inventory;

import org.hawkular.client.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InventoryEndpointsTest extends BaseTest {

    public InventoryEndpointsTest() throws Exception {
        super();
    }

    @Test(priority = 1)
    public void pingerTest() throws Exception {
        Assert.assertEquals(client().inventory().pingHello().getEntity().getDocumentation(),
                "http://www.hawkular.org/");
    }
}
