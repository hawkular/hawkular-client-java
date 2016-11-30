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

import javax.ws.rs.core.Response;

import org.hawkular.client.core.BaseClient;
import org.hawkular.client.core.ClientInfo;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.DefaultClientResponse;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.RestFactory;
import org.hawkular.client.inventory.jaxrs.handlers.TraversalHandler;
import org.hawkular.inventory.api.Relationships;
import org.hawkular.inventory.api.paging.Order;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;

public class DefaultTraversalClient extends BaseClient<TraversalHandler> implements TraversalClient {

    public DefaultTraversalClient(ClientInfo clientInfo) {
        super(clientInfo, new RestFactory<TraversalHandler>(TraversalHandler.class));
    }

    @Override
    public ClientResponse<List<Map>> getTraversal(CanonicalPath traversal, String at,
                                                  Integer page, Integer per_page, String sort, Order.Direction order,
                                                  SegmentType type, String id, String name, CanonicalPath cp, String propertyName,
                                                  String propertyValue, Relationships.WellKnown relatedBy, CanonicalPath relatedTo,
                                                  CanonicalPath relatedWith, String definedBy) {
        Response serverResponse = null;

        try {
            String filter = getFilter(type, id, name, cp, propertyName, propertyValue, relatedBy, relatedTo, relatedWith, definedBy);
            serverResponse = restApi().getTraversal(traversal.toRelativePath().toString(), at,
                                                    page, per_page, sort, order.getShortString(), filter);
            JavaType javaType = collectionResolver().get(List.class, Map.class);

            return new DefaultClientResponse<List<Map>>(javaType, serverResponse, ResponseCodes.GET_SUCCESS_200);
        } finally {
            if (serverResponse != null) {
                serverResponse.close();
            }
        }
    }

    private String getFilter(SegmentType type, String id, String name, CanonicalPath cp, String propertyName,
                             String propertyValue, Relationships.WellKnown relatedBy, CanonicalPath relatedTo,
                             CanonicalPath relatedWith, String definedBy) {
        StringBuilder filter = new StringBuilder();
        if (type != null) {
            filter.append("type=" + type.getSerialized() + ";");
        }

        if (!Strings.isNullOrEmpty(id)) {
            filter.append("id=" + id + ";");
        }

        if (!Strings.isNullOrEmpty(name)) {
            filter.append("name=" + name + ";");
        }

        if (cp != null) {
            filter.append("cp=" + cp.toString() + ";");
        }

        if (!Strings.isNullOrEmpty(propertyName)) {
            filter.append("propertyName=" + propertyName + ";");
        }

        if (!Strings.isNullOrEmpty(propertyValue)) {
            filter.append("propertyValue=" + propertyValue + ";");
        }

        if (relatedBy != null) {
            filter.append("relatedBy=" + relatedBy.name() + ";");
        }

        if (relatedTo != null) {
            filter.append("relatedTo=" + relatedTo.toString() + ";");
        }

        if (relatedWith != null) {
            filter.append("relatedWith=" + relatedWith.toString() + ";");
        }

        if (definedBy != null) {
            filter.append("definedBy=" + definedBy + ";");
        }

        //Remove last ;
        if (filter.length() > 0) {
            filter.setLength(filter.length() - 1);
        }

        return filter.toString();
    }
}
