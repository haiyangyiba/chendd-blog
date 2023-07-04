#####创建打包命令
在右侧Maven Project处，选中chendd-blog（root）项目的Lifecycle随便选择一项右键，增加一个Run命令
clean compile package -Dmaven.test.skip=true -f pom.xml

#####区分环境打包
打包有两种区分环境打包的实现，可在打包命令中传递当前打包的环境，传递的参数优先级别高于配置文件中配置的当前默认环境
命令，参考打包命令如下：\
（1）不传递环境参数的打包命令，受配置文件中的activeByDefault标签参数控制，默认为开发环境，可修改其他环境的此参数实现分环境打包：\
clean compile package -Dmaven.test.skip=true -f pom.xml\
（2）传递环境参数的打包命令，该参数值优先于配置文件中的activeByDefault参数值，故可以无需通过修改配置文件的方式，采用从打包命令处设置打包的环境参数：\
clean compile package -Dmaven.test.skip=true -Pprod -f pom.xml（即使用-P参数，-Pprod标识打包prod生产环境对应的参数文件）\

#####注意事项
（1）由于区分环境打包策略使用了多份高度相似的配置文件，即application.yml文件同名的存在了多个，导致了在application.yml文件中输入参数时IDEA无法提示。\
解决方式：可将src/main/resources目录下的test与prod目录备份后删掉，只保留此路面下的application.yml文件，IDEA即可提示。

