#!/bin/sh
echo '[Git Hook] Executing Spotless check before commit'
mvn spotless:apply
exit $?
