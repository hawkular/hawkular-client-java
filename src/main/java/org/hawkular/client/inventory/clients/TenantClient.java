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

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.inventory.api.model.Relationship;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.paths.CanonicalPath;

public interface TenantClient {

    /**
     * Retrieves the details of the current tenant.
     *
     * @param at
     * @return
     */
    ClientResponse<Tenant> getTenant(String at);

    /**
     * Updates the properties of the tenant
     *
     * @param at
     * @param update
     * @return
     */
    ClientResponse<Empty> createTenant(String at, Tenant.Update update);

    /**
     * Creates new relationship(s) on a tenant
     *
     * @param at
     * @param blueprints
     * @return
     */
    ClientResponse<List<Relationship>> createRelationship(String at, List<Relationship.Blueprint> blueprints);

    /**
     * Retrieves tenant’s relationships
     *
     * @param path
     * @param at
     * @return
     */
    ClientResponse<List<Relationship>> getRelationships(CanonicalPath path, String at);
}
