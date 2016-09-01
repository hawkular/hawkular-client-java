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
package org.hawkular.client.test.utils;

import java.util.List;
import java.util.Random;

import org.hawkular.client.test.BTG;
import org.hawkular.metrics.model.DataPoint;

import com.google.common.collect.ImmutableList.Builder;

public class DataPointGenerator<T> {

    public List<DataPoint<T>> generator(int size) {
        return build(size);
    }

    private List<DataPoint<T>> build(int size) {
        BTG ts = new BTG();
        Random random = new Random();

        Builder<DataPoint<T>> builder = new Builder<>();
        for (int i = 0; i < size; i++) {
            DataPoint<T> point = new DataPoint<T>(ts.nextMilli(), getValue(random));
            builder.add(point);
        }
        return builder.build();
    }

    protected T getValue(Random random) {
        return null;
    }
}
