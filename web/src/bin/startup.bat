rem startup.bat
rem chcp 65001命令是设定cmd的编码是UTF-8，参考其它的cmd编码^
rem 编码          对应字符
rem GBK（默认）   936
rem 美国英语      437
rem utf-8         65001
chcp 65001

java -Xmx256m -Xms256m -Xmn256m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCTimeStamps -Xloggc:../logs/gc.log -Djava.io.tmpdir=../runtime -Dfile.encoding=utf-8 -Duser.timezone=GMT+8 -cp ../conf;../lib-local/*;../lib-project/*;../lib-jar/* cn.chendd.blog.Bootstrap

pause