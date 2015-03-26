[![Build Status](https://travis-ci.org/Hawkular-QE/hawkular-java-client.svg?branch=master)](https://travis-ci.org/Hawkular-QE/hawkular-java-client)
# Java client for [Hawkular](https://github.com/hawkular/hawkular)
Example,
```java
HawkularClient hawkular = new HawkularClient("http://209.132.178.218:18080/", "", "");
System.out.println(hawkular.metrics().findTenants()); // show all tenants
System.out.println(hawkular.inventory().pinger()); // 'Hello World'
```
See [unit tests](src/test/java/org/hawkular/client/test) for more examples.

# Help Wanted
This project is under active development.  Pull requests are welcome.
