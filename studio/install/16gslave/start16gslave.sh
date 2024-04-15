#!/bin/sh
source /etc/profile

# eureka单独启动
#echo "即将启动任务eureka"
#sh eureka.sh

#sleep 10s


echo "即将启动任务data-market-service-mapping"
sh data-market-service-mapping/data-market-service-mapping.sh

echo "即将启动任务data-masterdata-service"
sh data-market-service-mapping/data-market-service-mapping.sh

echo "即将启动任务data-metadata-service-console"
sh data-metadata-service-console/data-metadata-service-console.sh

echo "即将启动任务data-quality-service"
sh data-quality-service/data-quality-service.sh

echo "即将启动任务workflow-service"
sh workflow-service/workflow-service.sh

echo "即将启动任务codegen-service"
sh codegen-service/codegen-service.sh