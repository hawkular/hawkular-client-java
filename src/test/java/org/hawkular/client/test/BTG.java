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

import org.apache.http.annotation.NotThreadSafe;

/**
 * A Better-Timestamp-Generator helper class generates new timestamp value starting
 * from (current time - window) in 30 seconds increment to avoid getting almost
 * identical values when calling System.currentTimeMillis() in
 * rapid succession
 * @author vnguyen
 */
@NotThreadSafe
public class BTG {
    private Instant nextVal;
    private Duration window;
    private Duration increment = Duration.ofSeconds(30);

    public BTG() {
        this(Instant.now());
    }

    public BTG(Instant current) {
        window = Duration.ofHours(4);
        nextVal = current.minus(window);
    }

    public BTG withIncrement(Duration inc) {
        increment = inc;
        return this;
    }

    public long nextMilli() {
        return this.next().toEpochMilli();
    }

    public Instant next() {
        Instant ret = nextVal;
        nextVal = nextVal.plus(increment);
        //TODO: test when we use up all values within window
        return ret;
    }

}
