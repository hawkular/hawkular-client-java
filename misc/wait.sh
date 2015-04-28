#!/bin/bash
wget --retry-connrefused  --timeout=10 -t ${WAIT_TRIES:-1}  -w 5 --spider ${HAWKULAR_ENDPOINT:-http://localhost:8080}
exit $?
