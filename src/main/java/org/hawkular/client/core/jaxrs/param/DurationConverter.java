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
package org.hawkular.client.core.jaxrs.param;

import javax.ws.rs.ext.ParamConverter;

import org.hawkular.metrics.model.param.Duration;

/**
 * https://github.com/hawkular/hawkular-metrics/blob/master/api/metrics-api-jaxrs/src/main/java/org/hawkular/metrics/api/jaxrs/param/DurationConverter.java
 * <p>
 * A JAX-RS {@link ParamConverter} for {@link Duration} parameters.
 *
 * @author Thomas Segismont
 */
public class DurationConverter implements ParamConverter<Duration> {

    @Override
    public Duration fromString(String value) {
        return new Duration(value);
    }

    @Override
    public String toString(Duration duration) {
        return duration.getValue() + Duration.UNITS.get(duration.getTimeUnit());
    }
}
