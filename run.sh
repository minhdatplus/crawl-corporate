#!/bin/sh
echo "********************************************************"
echo "Starting corporate-action-service "
echo "********************************************************"
java -jar -Dspring.profiles.active=dev corporate-action-service.jar
