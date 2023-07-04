#!/bin/sh
#!/bin/bash
#
# Copyright (c) 2022 by chendd 88911006
# 特别注意：运行该命令时的pwd应该为bin目录
# All rights reserved.

#项目jar名称
APP_NAME=chendd-admin

#项目主main函数类
APP_MAIN_CLASS="cn.chendd.blog.Bootstrap"

#JDK指定
JAVA_HOME=/app/jdk/jdk8

# 如果不指定jdk则使用默认
if [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]]; then
    javaexe="$JAVA_HOME/bin/java"
elif type -p java > /dev/null 2>&1; then
    javaexe=$(type -p java)
elif [[ -x "/usr/bin/java" ]];  then
    javaexe="/usr/bin/java"
else
    echo "Unable to find Java"
    exit 1
fi

#获取当前工作空间
SOURCE="$0"
while [ -h "$SOURCE"  ]; do
    DIR="$( cd -P "$( dirname "$SOURCE"  )" && pwd  )"
    SOURCE="$(readlink "$SOURCE")"
    [[ $SOURCE != /*  ]] && SOURCE="$DIR/$SOURCE"
done
WORKING_DIR="$(cd -P "$(dirname "$SOURCE")" && pwd)"

#项目目录
APP_HOME="$(dirname "$WORKING_DIR")"

#是否启用debug模式则设置为空
#APP_DEBUGE="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=60606"
APP_DEBUGE=""

#JVM参数  -Xms程序启动时占用内存大小 -Xmx程序运行期间最大可占用的内存大小 -Xss设定每个线程的堆栈大小   -Xmn 年轻代大小
JVM_OPTS="-Xmx1024m -Xms1024m -Xmn1024m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCTimeStamps -Xloggc:$APP_HOME/logs/gc.log -Djava.io.tmpdir=$APP_HOME/runtime -Dfile.encoding=utf-8 -Duser.timezone=GMT+8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$APP_HOME/runtime"

#项目配置文件路径
APP_CONF="$APP_HOME/conf:$APP_HOME/lib-project/*:$APP_HOME/lib-local/*:$APP_HOME/lib-jar/*"

pid=00000
#校验进程
APP_PID=$APP_HOME/$APP_NAME.pid

start(){
 checkpid
 if [ $? -eq 0 ]; then
    echo JDK路径: $JAVA_HOME
    echo 项目目录: $APP_HOME
    echo 项目名称: $APP_NAME
    echo 配置文件: $APP_CONF
    echo JVM参数: $JVM_OPTS

    if  [ ! -n "$APP_DEBUGE" ] ;then
        echo "关闭debug模式!"
    else
        echo "开启debug模式!"
    fi
    /bin/sh -c  "$javaexe $APP_DEBUGE $JVM_OPTS -cp $APP_CONF $APP_MAIN_CLASS > /dev/null 2>&1 & echo \$!" > "$APP_PID"
	echo "启动命令：$javaexe $APP_DEBUGE $JVM_OPTS -cp $APP_CONF $APP_MAIN_CLASS"
    echo "---------------------------------"
    ROOT_LOG_FILE="$APP_HOME/logs/admin/blog.root.log"
    echo "正在启动中...即将监控 $ROOT_LOG_FILE"
    echo "---------------------------------"
    for (( i = 1; i <= 3; ++i )); do
        if [[ -f "$ROOT_LOG_FILE" ]]; then
            /bin/sh -c "tail -f $ROOT_LOG_FILE"
            break
        fi
        echo "等待 $i 秒"
        sleep 1
    done

  else
      echo -e "\033[42;5m$APP_NAME is runing PID: $pid\033[0m"
  fi

}

status(){
   checkpid
   if [ $? -eq 0 ]; then
     echo -e "\033[41;5m$APP_NAME is not runing\033[0m"
   else
     echo -e "\033[42;5m$APP_NAME is runing PID: $pid\033[0m"
   fi
}

checkpid(){
   if [[ -f "$APP_PID" ]]; then
        pid=$(cat "$APP_PID")
        checkPidNum
        if [ $? -eq 0 ];then
        return 0;
        else
            return 1;
        fi
   else
        checkPidNum
        #return 0 表示不存在，1表示存在
        if [ $? -eq 0 ];then
        #echo "${?}"
        return 0;
        else
            return 1;
        fi
   fi
}

checkPidNum(){
   
    PIDNUM=`ps -ef|grep $APP_NAME|grep $APP_MAIN_CLASS|grep -v grep|awk '{print $2}'`

    if [ -z "${PIDNUM}" ]; then
        #echo "$APP_NAME $APP_MAIN_CLASS is not running."
        return 0
    else
        #echo "$APP_NAME $APP_MAIN_CLASS is running. PID: ${pid}"
        return 1
    fi
}
stop(){
    checkpid
    if [ $? -eq 0 ]; then
      echo -e "\033[41;5m$APP_NAME is not runing\033[0m"
    else
      #kill -9 $pid
      kill -15 $pid
      echo -e "\033[42;5m$APP_NAME is stoped. PID: ${pid}\033[0m"
    fi
}
restart(){
    stop
    sleep 1s
    start
}

case $1 in
          start) start;;
          stop)  stop;;
          restart)  restart;;
          status)  status;;
              *) echo -e "\033[41;5mrequire start|stop|restart|status\033[0m";;
esac
