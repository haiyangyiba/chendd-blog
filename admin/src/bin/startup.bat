Title = chendd-blog-examples-80

set JAVA_DEBUG=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=60606
set JAVA_OPTS=-Xmx256m -Xms256m -Xmn256m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../runtime
set JAVA_OPTS_LOGGC=-Xloggc:../logs/gc.log
set JAVA_OPTS_TEMPDIR=-Djava.io.tmpdir=../runtime
set JAVA_OPTS_ENCODING=-Dfile.encoding=utf-8
set JAVA_OPTS_TIMEZONE=-Duser.timezone=GMT+8
set JAVA_OPTS_CONF=-cp ../conf;../lib-local/*;../lib-project/*;../lib-jar/*
set JAVA_OPTS_MAIN_CLASS=cn.chendd.Bootstrap

chcp 65001

java %JAVA_DEBUG% %JAVA_OPTS% %JAVA_OPTS_LOGGC% %JAVA_OPTS_TEMPDIR% %JAVA_OPTS_ENCODING% %JAVA_OPTS_TIMEZONE% %JAVA_OPTS_CONF% %JAVA_OPTS_MAIN_CLASS%

