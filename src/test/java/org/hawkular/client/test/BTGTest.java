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
package org.hawkular.client.test;

import java.time.Duration;
import java.time.Instant;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class BTGTest {

    @Test
    public void simple() {
        Instant now = Instant.now();
        Duration window = Duration.ofHours(4);
        Duration defaultInc = Duration.ofSeconds(30);
        Reporter.log("Current: " + now.toString());

        BTG ts = new BTG(now);
        for(int i=0; i<4; i++) {
            Instant next = ts.next();
            Reporter.log(next.toString());
            Assert.assertEquals(next, now.minus(window).plus(defaultInc.multipliedBy(i)));

        }
    }
}
