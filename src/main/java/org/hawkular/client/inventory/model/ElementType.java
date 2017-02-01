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
package org.hawkular.client.inventory.model;

import org.hawkular.inventory.api.model.AbstractElement;
import org.hawkular.inventory.api.model.DataEntity;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.MetadataPack;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.OperationType;
import org.hawkular.inventory.api.model.Relationship;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.paths.SegmentType;

/**
 * https://github.com/hawkular/hawkular-inventory/blob/master/hawkular-inventory-rest-api/src/main/java/org/hawkular/inventory/rest/RestBulk.java#L547
 */
public enum ElementType {
    environment(Environment.class, Environment.Blueprint.class, SegmentType.e),
    resourceType(ResourceType.class, ResourceType.Blueprint.class, SegmentType.rt),
    metricType(MetricType.class, MetricType.Blueprint.class, SegmentType.mt),
    operationType(OperationType.class, OperationType.Blueprint.class, SegmentType.ot),
    feed(Feed.class, Feed.Blueprint.class, SegmentType.f),
    metric(Metric.class, Metric.Blueprint.class, SegmentType.m),
    resource(Resource.class, Resource.Blueprint.class, SegmentType.r),
    dataEntity(DataEntity.class, DataEntity.Blueprint.class, SegmentType.d),
    metadataPack(MetadataPack.class, MetadataPack.Blueprint.class, SegmentType.mp),
    relationship(Relationship.class, Relationship.Blueprint.class, SegmentType.r);

    final Class<? extends AbstractElement<?, ?>> elementType;
    final Class<? extends AbstractElement.Blueprint> blueprintType;
    final SegmentType segmentType;

    ElementType(Class<? extends AbstractElement<?, ?>> elementType, Class<? extends AbstractElement.Blueprint> blueprintType,
                SegmentType segmentType) {
        this.elementType = elementType;
        this.blueprintType = blueprintType;
        this.segmentType = segmentType;
    }

    public static ElementType ofSegmentType(SegmentType type) {
        for (ElementType et : ElementType.values()) {
            if (et.segmentType.equals(type)) {
                return et;
            }
        }
        return null;
    }

    public static ElementType ofBlueprintType(Class<?> type) {
        for (ElementType et : ElementType.values()) {
            if (et.blueprintType.equals(type)) {
                return et;
            }
        }
        return null;
    }
}
