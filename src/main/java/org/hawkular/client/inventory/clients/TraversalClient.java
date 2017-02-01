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
package org.hawkular.client.inventory.clients;

import java.util.List;
import java.util.Map;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.inventory.api.Relationships;
import org.hawkular.inventory.api.paging.Order;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;

public interface TraversalClient {

    /**
     * Retrieves a list of entities
     *
     * @param traversal
     * @param at
     * @param page
     * @param per_page
     * @param sort
     * @param order
     * @param type
     * @param id
     * @param name
     * @param cp
     * @param propertyName
     * @param propertyValue
     * @param relatedBy
     * @param relatedTo
     * @param relatedWith
     * @param definedBy
     * @return
     */
    ClientResponse<List<Map>> getTraversal(CanonicalPath traversal, String at,
                                           Integer page, Integer per_page, String sort, Order.Direction order,
                                           SegmentType type, String id, String name, CanonicalPath cp, String propertyName,
                                           String propertyValue, Relationships.WellKnown relatedBy, CanonicalPath relatedTo,
                                           CanonicalPath relatedWith, String definedBy);
}
