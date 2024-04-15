#!/bin/bash

ALLDATA_HOME=/opt/module/alldata

# Define hostnames for services
REDIS_HOST="16gmaster" # Redis服务器的主机名
RABBITMQ_HOST="16gmaster" # RabbitMQ服务器的主机名
MYSQL_HOST="16gmaster" # MySQL服务器的主机名

# Define hostnames for application components
SLAVE_HOST="16gslave"
MASTER_HOST="16gmaster"
DATA_HOST="16gdata"

function check_service_running() {
    SERVICE=$1
    HOST=$2
    echo "Checking $SERVICE on $HOST..."
    if ! ssh $HOST "ps aux | grep $SERVICE | grep -v grep" > /dev/null; then
        echo "$SERVICE is not running on $HOST. Aborting startup sequence."
        return 1
    fi
    return 0
}

function start_service() {
    # Check if Redis is running
    if ! check_service_running "redis-server" $REDIS_HOST; then
        return
    fi

    # Check if RabbitMQ is running
    if ! ssh $RABBITMQ_HOST "systemctl is-active rabbitmq-server" | grep -qx 'active'; then
        echo "RabbitMQ is not running on $RABBITMQ_HOST. Aborting startup sequence."
        return
    fi

    # Check if MySQL is running
    if ! check_service_running "mysqld" $MYSQL_HOST; then
        return
    fi

    echo "All required services are running. Continuing with startup."

    # Start up the application components
    ssh $SLAVE_HOST "cd $ALLDATA_HOME/16gslave/eureka && sh eureka.sh"
    sleep 10
    ssh $MASTER_HOST "cd $ALLDATA_HOME/16gmaster/config && sh config.sh"
    sleep 10
    ssh $DATA_HOST "cd $ALLDATA_HOME/16gdata/gateway && sh gateway.sh"
    sleep 10
    ssh $SLAVE_HOST "cd $ALLDATA_HOME/16gslave && sh start16gslave.sh"
    sleep 10
    ssh $MASTER_HOST "cd $ALLDATA_HOME/16gmaster && sh start16gmaster.sh"
    sleep 10
    ssh $DATA_HOST "cd $ALLDATA_HOME/16gdata && sh start16gdata.sh"
    sleep 10
}

function stop_service() {
    ssh $MASTER_HOST "cd $ALLDATA_HOME/16gmaster && sh stop16gmaster.sh"
    sleep 10
    ssh $DATA_HOST "cd $ALLDATA_HOME/16gdata && sh stop16gdata.sh"
    sleep 10
    ssh $SLAVE_HOST "cd $ALLDATA_HOME/16gslave && sh stop16gslave.sh"
    sleep 10
}

function status_service() {
    ssh $MASTER_HOST "cd $ALLDATA_HOME/16gmaster && sh status16gmaster.sh"
    sleep 10
    ssh $DATA_HOST "cd $ALLDATA_HOME/16gdata && sh status16gdata.sh"
    sleep 10
    ssh $SLAVE_HOST "cd $ALLDATA_HOME/16gslave && sh status16gslave.sh"
    sleep 10
}

case "$1" in
    start)
        start_service
        ;;
    stop)
        stop_service
        ;;
    status)
        status_service
        ;;
    *)
        echo "Usage: $0 {start|stop|status}"
        exit 1
esac
