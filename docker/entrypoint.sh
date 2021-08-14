#!/bin/bash

envsubst < /usr/local/tomcat/conf/context.template.xml > /usr/local/tomcat/conf/context.xml

while ! nc -z ${DB_HOST} ${DB_PORT}; 
do 
    echo "Waiting database ${DB_HOST} ${DB_PORT}"
    sleep 1;
done;

exec $@
