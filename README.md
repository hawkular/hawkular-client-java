[![Build Status](https://travis-ci.org/hawkular/hawkular-client-java.svg?branch=master)](https://travis-ci.org/hawkular/hawkular-client-java)
[![Release Version](https://img.shields.io/maven-central/v/org.hawkular.client/hawkular-java-client.svg?maxAge=2592000)](https://mvnrepository.com/artifact/org.hawkular.client/hawkular-java-client)
[![License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]()

# Java client for [Hawkular](https://github.com/hawkular)
Example,
```java
HawkularClient client = HawkularClient.builder("my-tenant")
    .uri("http://localhost:8080")
    .basicAuthentication("jdoe", "password")
    .build();

System.out.println(client.metrics().tenant().getTenants()); // show all tenants
System.out.println(client.alerts().plugins().findActionPlugins()) // show all plugins
System.out.println(client.inventory().tenant.getTenant()); // get current tenant
```
See [unit tests](src/test/java/org/hawkular/client/test) for more examples.

### How to run unit tests?
You have set your hawkular server url in to `HAWKULAR_ENDPOINT` environment variable.(example: `export HAWKULAR_ENDPOINT=http://<hawkular-host>:8080`)

To run `mvn test`

Run with debug log: `mvn test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug -Dorg.slf4j.simpleLogger.logFile=target/test.log`
- http://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html

# Help Wanted
This project is under active development.  Pull requests are welcome.
