## 目录
<!-- TOC -->

# 1. 个人博客
> 欢迎来到陈冬冬的学习天地（个人博客） | 学习与经验整理分享平台
> 
> 如果你对此项目感兴趣，欢迎到 <b>[https://www.chendd.cn](https://www.chendd.cn) </b>留言
> 
> `注：本站属于个人的经验整理分享系统，非商业化站点，无盈利，为博主个人博客（如有图片、前端插件、后端组件等任何涉及版权等行为请联系，我将第一时间处理）。另，所有文章全部为原创，欢迎大家交流（留言/点赞），如有任何意见或建议还望不吝交流，本人将持续维护站点，助力于全面完善。`
>
# 2. 关于本站

## 2.1 页面展示
### (1) 前端首页多端展示
![前端首页多端展示](https://github.com/haiyangyiba/statics/blob/master/blog/web/home.png?raw=true)
### (2) 前端首页滚动展示
![前端首页滚动展示](https://github.com/haiyangyiba/statics/blob/master/blog/web/home.gif?raw=true)
### (3) 功能实现范围展示
![前端首页滚动展示](https://github.com/haiyangyiba/statics/blob/master/blog/function.png?raw=true)
### (4) 后端主页功能展示
![前端首页滚动展示](https://github.com/haiyangyiba/statics/blob/master/blog/admin/home.png?raw=true)

## 2.2 不忘初心
还记得才工作那时，人在各方面都是比较菜 的，实为一个弱比，对于互联网上能够访问的应用程序感觉非常高大上，这不禁最终上互 联网了，基本上全是企业内部的程序）。后来慢慢工作时间长了，菜鸟也开始成长了， 于是就开始在QQ空间日志模块开始总结，另一方面对 于外网服务器（公网服务器、IP）、域名这些等有了一些模糊的概念，就开始折腾了，入手了阿里云的服务器先了解了一番 ，后来又弄了个 域名也开始折腾折腾，这不就有了现在能访问的这个程序吗，总不能工作这么多年什么也没留下吧，也算是不忘初心吧。

## 2.3 关于本站
（1）本次系统的版本为个人博客的V2.0版本，较之于前面的老版本已间隔7-8之久，得益于个人技术栈的更新升级；<br/>
（2）本站V1.0版本上线于2014年，使用 Struts + Spring + JSP 开发完成，已经下线，可在新版V2.0版本的`历史上的本站`中查看相关界面功能；本站2.0开始编写于2019年，在工作中接触到了Spring Boot系技术，为了深度的学习之而在一系列学习和使用后展开的新一轮博客程序编写；<br/>
（3）本站所有前后端功能代码均为个人业余所写，耗费了大量的休息时间，然而仍受限于惰性与动力等因素制约，导致编码进展缓慢，然而为了我那万丈的雄心一如既往的砥砺前行，只为职业生涯锦上添花，同时秉持利他之心为IT技术行业的项目开源和技术文章贡献出个人的绵薄之力。<br/>

# 3. 项目模块
<!-- normal-begin -->
<table>
    <thead>
        <tr>
            <th width=130>模块范围</th>
            <th width=130>模块名称</th>
            <th>模块介绍</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td rowspan="4">独立模块</td>
          <td>core</td>
          <td>core作为核心模块的存在，从设计宗旨上来讲它仅依赖一些开源库，如Apache Commons、Google Guava、servlet-api、fastjson等，同时该模块也提供一些常见的工具类（如：DateUtil、BigDemicalUtil、IpUtil等）以及一些个人认为会被公共存在于多个项目中的功能性代码封装（如：Java Mail、Java图片处理、异常封装、统一结果集对象、公共枚举类等）。/td>
        </tr>
       <tr>
         <td>ueditor</td>
         <td>ueditor是百度开源的一款富文本编辑器，功能全面而强大，实际应用顺手（本站1.0版本也使用这款编辑器），之所以独立构建一个模块是考虑将来会存在重复使用，而且原始版本是使用jsp开发的，本站V1.0使用springmvc环境整合，本次使用spring boot环境整合，且改造了一些代码实现，具体会单独在文章中详细罗列，方便今后被独立应用。</td>
       </tr>
       <tr>
         <td>toolkit</td>
         <td>toolkit是以功能模块粒度的公共功能模块，目前包含有3个功能模块，分别是用户操作日志、定时任务、参数配置。用户操作日志是以用户行为动作为主导，记录用户相对应操作功能的行为以及涉及到的功能影响的数据，该功能实现较为科学与高大上，后续专门给出对应文章进行系列说明；定时任务则是使用quartz框架的实现封装，摒弃spring boot自带的quartz支持，采用更易于维护或扩展的配置实现，该模块同样会在后文中详细阐述；参数配置功能主要是将一些参数维护至数据库，通过特定的注解将参数注入至对应的component组件中，同时也支持参数的动态修改等细节实现，同样指得拥有；</td>
       </tr>
       <tr>
         <td>third-login</td>
         <td>third-login是第三方登录模块，整合所有的第三方登录接口实现，目前共计接入实现的有：腾讯QQ、支付宝、Gitee、百度、新浪微博、Github、Gitee，在本站1.0的基础上又增加了Gitee与GitHub两种实现，同时部分第三方登录的实现支持友情站点的接入（即，多传递一个友链站点的标识，可在登录授权成功后重定向至友链站点，同时传递回用户登录的信息，包含用户ID、昵称、头像地址，本站不记录友链站点授权登录的用户数据），大家可放心授权登录。</td>
       </tr>
       <tr>
         <td rowspan="8">父功能模块</td>
         <td>parent</td>
         <td>parent表示所有具体博客功能模块的父模块，正如它的定义的含义一样，旨在聚合一些功能模块和定义一些被继承的公共扩展的配置，比如所有的功能模块都需要依赖spring boot，logback、swagger、junit等环境，表示被继承的子模块均已经被支持这些公共的依赖库，只需要更多的关注业务功能模块之间的模块依赖即可。</td>
       </tr>
       <tr>
         <td>base</td>
         <td>base模块在这里与parent模块高度相识，在此单独将base模块独立，主要就是为了定义一些所有子功能模块会被使用到的功能逻辑代码（与parent定义的配置相类似，这里只是提取公共代码），本项目中的base模块包含有：Api接口编号版本管理、BaseController公共类、请求包装器Filter、BaseSwagger、thymeleaf自定义标签、spring公共组件定义等。</td>
       </tr>
       <tr>
         <td>web</td>
         <td>web模块是所有功能的展示前端，页面采用thymeleaf实现 + Bootstrap UBold主题实现，包含多种页面布局的数据展示。</td>
       </tr>
       <tr>
         <td>admin</td>
         <td>admin模块是所有功能的后端实现，除了web端的接口支持外，还另有一套管理功能的管理界面，目前继承了近20个常用功能和一些主题框架级别的细节实现。</td>
       </tr>
       <tr>
         <td>client-vo</td>
         <td>client-vo模块则显得可有可无了，它是admin与web间的桥梁，主要是提供一些功能的DTO，供admin提供接口时定义，同时也提供web调用时的使用。</td>
       </tr>
       <tr>
         <td>ansj</td>
         <td>ansj是一款开源的分词实现，在多年前（博客1.0的建站时代）曾经调研过一些分词的实现库，最终经过理论了解和简单实践，最终选定了这款进程内的分词实现，系统web端的全局搜一搜就是该功能的具体实现，可访问体验。</td>
       </tr>
       <tr>
         <td>admin-system</td>
         <td>admin-system则是admin模块下的子功能散列分布，主要是后端管理项目中的偏系统管理类的功能，如包含菜单管理、角色管理、用户管理等。</td>
       </tr>
       <tr>
         <td>admin-blog</td>
         <td>admin-blog则是admin模块下的子功能散列分布，主要是后端管理中的偏博客管理类的功能，如博客文章管理、友情连接管理、留言管理等。</td>
       </tr>
</tbody>
</table>
<!-- normal-end -->

# 4. 项目技术
本站V2.0版本实现技术在前端展示上以jQuery + Bootstrap4为主，后端展示以SpringBoot为主，框架搭建于写于2019年年底，各个插件或组件基本都是基于时下最新的版本开展，以下为各个具体技术描述：
## 4.1 前端
- jQuery v3.4.1
- Bootstrap v4.4.1 （ubold主题，付费下载于bootstrapmb网）
- 表格：Bootstrap-table v1.16.0
- 弹出框：Bootstrap-bootbox v5.4.0
- 富文本编辑器：simditor、ueditor
- 验证码：腾讯滑动验证码tcaptcha
- 模板引擎：art-template
- 图表：echarts <br/>
- 另有一些插件是前端页面（可见后台项目的页面）未使用到的，比如一些bootstrap插件如：bootstrap-select、bootstrap-wizard、 bootstrap-datetimepicker、nprogress、maxlength、jquery.form等。
## 4.2 后端
后端语言使用java，各项依赖和组件版本如下：

- spring boot 2.2.7.release
- spring-session-jdbc 2.3.0.release
- spring-boot-starter-thymeleaf 2.2.7.release
- mybatis-plus 3.3.1
- swagger-bootstrap-ui 1.9.6
- ansj_seg 5.1.6
- forest 1.5.0-rc5
# 5. 特色功能
1. SpringBoot 项目支持http和https，访问http时自动跳转至https；
2. SpringBoot 项目的多模块构建完美打包与部署，支持多环境打包，如生成环境打包命令：`clean compile package -Dmaven.test.skip=true -Pprod -f pom.xml`，即使用-P参数，-Pprod标识打包prod生产环境对应的参数文件，默认不加-P为开发环境；
3. SpringSession 管理用户，支持在前台和后台任一方登录后的用户单点支持，前后端分离；
4. Mybatis-Plus 的日志表分表；
5. 全站图片上传增加水印，包括 gif 图片，也可根据特殊规则不增加水印；
6. 共计接入6个第三方用户登录，也提供系统内部用户登录；
7. 腾讯滑动式验证码，gif 动态验证码；
8. 富文本编辑器 ueditor 的多处修改整合；
9. 博客文章的 PDF 导出下载（限制登录），支持 HTML 富文本段落和图片；
10. 用户留言评论后的邮件提醒；
11. 一些个人分享的独到且深度的开源项目学习实践案例；
12. ......

# 6. 项目部署
1. 使用MySQL数据库，创建数据库名称`chendd-blog`;
2. 使用MySQL Workbench 之类的客户端导入《[数据库脚本](https://github.com/haiyangyiba/statics/blob/master/blog/database.zip)》，并执行；
3. 使用IDEA导入项目代码，启动admin模块的Bootstrap启动类，默认端口8888；启动web模块的启动类，默认端口80和443，分别是http和https协议；
4. 先启动后端服务再启动前端，访问地址分别是`http://localhost:8888/`和`https://localhost`，登录用户和密码为：`admin`、`chenddblog`，验证码为 4 位数字和字母组合，为 gif 图片，每一帧都只显示3个字符；
5. 所有图片和附件上传的路径默认存储在`/app/BLOG_FILES`路径下，若为Windows则在部署程序的磁盘根路径下；
6. 项目功能运行时会将用户的操作写入操作日志表中，表为分表存储，可能会存在表不存在，可根据实际日期从`sys_operationlog`中拷贝生成即可；

# 7. 其它说明
1.  [本站介绍](https://www.chendd.cn/blog/page/website.html)
2.  [作者介绍](https://www.chendd.cn/blog/page/author.html)
3.  [历史上的本站](https://www.chendd.cn/blog/page/history.html)
4.  [友情链接](https://www.chendd.cn/blog/page/link.html)

### 有任何问题或者建议，可与作者联系，参考上述作者介绍。
