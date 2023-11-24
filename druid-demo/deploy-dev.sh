#!/bin/bash
PROG_NAME=$0
ACTION=$1
START_WAIT_TIME=2
STOP_WAIT_TIME=1

JAVA_HOME=java
PID_FILE=pid_file
APP_LOG=start.log
APP_NAME=druid-demo-1.0.0.jar
JVM_OPTS="-Xms512M -Xmx512M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./log/dump/"
AGENT_OPTS="-javaagent:/home/agent/pinpoint-agent-2.4.2/pinpoint-bootstrap.jar -Dpinpoint.agentId=druid-demo-node1 -Dpinpoint.agentName=druid-demo-node1 -Dpinpoint.applicationName=druid-demo"
#AGENT_OPTS="-javaagent:/home/agent/skywalking-agent/skywalking-agent.jar=agent.service_name=demo::druid-demo,collector.backend_service=10.10.0.4:11800"


usage() {
  echo "Usage: $PROG_NAME {start|stop|restart|status}"
  exit 2
}

start_app() {
  dir="./logs/dump"
  if [ ! -d "$dir" ];then
    mkdir -p  $dir
    echo "dump folder create success"
  else
    echo "dump folder existed"
  fi

  PID=`ps -ef |grep java|grep $APP_NAME|grep -v grep|awk '{print $2}'`
  if [ -f "$PID_FILE" ] || [ x"$PID" != x"" ]; then
    echo "The Application is running..."
    exit 0
  fi
  echo "start app service in ${START_WAIT_TIME} seconds..."
  nohup ${JAVA_HOME} ${AGENT_OPTS} ${JVM_OPTS} -jar ${APP_NAME} -Dspring.config.location=config/application-dev.yml --spring.profiles.active=durid 2>&1 >> ${APP_LOG} &
  echo $! > ${PID_FILE}
}

stop_app() {
  echo "stop app in ${STOP_WAIT_TIME} seconds..."
  if [ -f "$PID_FILE" ]; then
    kill -9 `cat $PID_FILE`
    rm $PID_FILE
  else
    echo "App $PID_FILE does not exist, do noting"
  fi
}

start() {
  echo "start app jar"
  start_app
  sleep ${START_WAIT_TIME}
  echo "Start $APP_NAME success..."
}

stop() {
  echo "stop app jar"
  stop_app
  sleep ${STOP_WAIT_TIME}
  echo "Stop $APP_NAME success..."
}

restart(){
   echo "stop app jar"
   stop_app
   sleep ${STOP_WAIT_TIME}
   echo "Stop $APP_NAME success..."
   echo "start app jar"
   start_app
   sleep ${START_WAIT_TIME}
   echo "Start $APP_NAME success..."
}

function status()
{
  PID=`ps -ef |grep java|grep $APP_NAME|grep -v grep|wc -l`
  if [ $PID != 0 ];then
    echo "$APP_NAME is running..."
  else
    echo "$APP_NAME is not running..."
  fi
}

case "$ACTION" in
  start)
    start
  ;;
  stop)
    stop
  ;;
  restart)
    restart
  ;;
  status)
    status
  ;;
  *)
    usage
  ;;
esac
