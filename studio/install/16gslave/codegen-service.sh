#!/bin/sh
source /etc/profile

cd $(dirname $0)


#codegen-service
nohup java -jar -Xms128m -Xmx2048m -XX:PermSize=128M -XX:MaxPermSize=256M -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+ExplicitGCInvokesConcurrent -XX:MaxInlineLevel=15 ./codegen-service/codegen-service.jar --server.port=8830  > codegen-service.log 2>&1 &