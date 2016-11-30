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
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.inventory.api.model.AbstractElement;
import org.hawkular.inventory.api.model.Change;
import org.hawkular.inventory.api.model.IdentityHash;
import org.hawkular.inventory.paths.CanonicalPath;
import org.hawkular.inventory.paths.SegmentType;

public interface SingleEntityClient {

    /**
     * Deletes an inventory entity on the given location.
     *
     * @param path
     * @return
     */
    ClientResponse<Empty> deleteEntity(CanonicalPath path, String at);

    /**
     * Reads an inventory entity on the given location.
     * TODO: Response should be a: http://www.hawkular.org/docs/rest/rest-inventory.html#AbstractElement
     *
     * @param path
     * @return
     */
    ClientResponse<Map> getEntity(CanonicalPath path, String at);

    /**
     * Updates an entity. The path is actually a canonical path.
     * The format of the accepted JSON object is governed by the type of the entity being updated. If you’re updating an environment,
     * look for EnvironmentUpdate type, etc.
     *
     * @param path
     * @param update
     * @return
     */
    ClientResponse<Empty> updateEntity(CanonicalPath path, String at, AbstractElement.Update update);

    /**
     * Obtains the history of the entity.
     *
     * @param path
     * @param from
     * @param to
     * @return
     */
    ClientResponse<List<Change<?>>> getHistory(CanonicalPath path, String from, String to);

    /**
     * Obtains the identity tree hash of the entity.
     *
     * @param path
     * @return
     */
    ClientResponse<IdentityHash.Tree> getEntityHash(CanonicalPath path, String at);

    /**
     * Creates a new entity
     * TODO: Response should be a: http://www.hawkular.org/docs/rest/rest-inventory.html#AbstractElement
     *
     * @param path
     * @param type
     * @param entity
     * @return
     */
    ClientResponse<Map> createEntity(CanonicalPath path, SegmentType type, String at, AbstractElement.Blueprint entity);
}
