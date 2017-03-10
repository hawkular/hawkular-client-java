/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
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

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://github.com/hawkular/hawkular-inventory/blob/master/hawkular-inventory-rest-api/src/main/java/org/hawkular/inventory/rest/RestPing.java
 */
public class JaxRsResource implements Comparable<JaxRsResource> {

    private static final Pattern NAME_PATTERN = Pattern.compile("(/[a-zA-Z]+)");

    private Set<String> methods = new HashSet<>();
    private String uri;

    public JaxRsResource() {

    }

    public JaxRsResource(String uri) {
        this.uri = uri;
    }

    public boolean addMethod(String method) {
        return methods.add(method);
    }

    public Set<String> getMethods() {
        return methods;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String getUriPattern(String uri) {
        Matcher matcher = NAME_PATTERN.matcher(uri);
        if (matcher.find()) {
            return matcher.group();
        }

        return uri;
    }

    @Override
    public int compareTo(JaxRsResource that) {
        if (this == that) {
            return 0;
        }

        String other = getUriPattern(that.getUri());
        String current = getUriPattern(this.uri);

        return current.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JaxRsResource))
            return false;

        JaxRsResource that = (JaxRsResource)o;

        return !(uri != null ? !uri.equals(that.uri) : that.uri != null);

    }

    @Override
    public int hashCode() {
        return uri != null ? uri.hashCode() : 0;
    }
}
