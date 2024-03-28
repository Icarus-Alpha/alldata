#!/bin/sh
source /etc/profile

# gateway单独启动
#echo "即将启动任务gateway"
#sh gateway.sh
#sleep 15s

echo "即将启动任务data-standard-service"
sh data-standard-service.sh

echo "即将启动任务data-visual-service"
sh data-visual-service.sh

echo "即将启动任务email-service"
sh email-service.sh

echo "即将启动任务file-service"
sh file-service.sh

echo "即将启动任务quartz-service"
sh quartz-service.sh


