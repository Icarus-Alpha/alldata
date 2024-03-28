#!/bin/sh
source /etc/profile

pid=$(ps -ef | grep data-market-service-mapping.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务data-market-service-mapping: $pid"

kill -9 $pid

echo "已杀死任务: $pid"

pid=$(ps -ef | grep data-masterdata-service.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务data-masterdata-service: $pid"

kill -9 $pid

echo "已杀死任务: $pid"

pid=$(ps -ef | grep data-metadata-service-console.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务data-metadata-service-console: $pid"

kill -9 $pid

echo "已杀死任务: $pid"

pid=$(ps -ef | grep data-quality-service.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务data-quality-service: $pid"

kill -9 $pid

echo "已杀死任务: $pid"

pid=$(ps -ef | grep workflow-service.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务workflow-service: $pid"

kill -9 $pid

echo "已杀死任务: $pid"

pid=$(ps -ef | grep eureka.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务eureka: $pid"

kill -9 $pid

echo "已杀死任务: $pid"

pid=$(ps -ef | grep codegen-service.jar | grep -Ev 'color=auto' | awk '{print $2}')

echo "即将杀死任务codegen-service: $pid"

kill -9 $pid

echo "已杀死任务: $pid"