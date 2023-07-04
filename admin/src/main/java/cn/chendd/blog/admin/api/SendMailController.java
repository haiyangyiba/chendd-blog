package cn.chendd.blog.admin.api;

import cn.chendd.blog.admin.blog.jobs.config.MailParamConfiguration;
import cn.chendd.blog.base.api.version.annotations.ApiVersion;
import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.mail.MailParam;
import cn.chendd.core.mail.MailSend;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.spring.SpringBeanFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * cache示例Controller
 *
 * @author chendd
 * @date 2021/6/14 18:28
 */
@RestController
@RequestMapping(value = "/mail" , produces = MediaType.ALL_VALUE)
@ApiVersion("1")
@Api(tags = "测试Email")
public class SendMailController extends BaseController {

    @PostMapping
    @ApiOperation(value = "发送Email" ,
            consumes = MediaType.ALL_VALUE,
            response = BaseResult.class
    )
    @ApiOperationSupport(order = 10)
    public void sendMail(@ApiParam("邮件标题") @RequestParam String title ,
                         @ApiParam("邮件内容") @RequestParam String content ,
                         @ApiParam("收件人") @RequestParam List<String> mailTos){
        MailParamConfiguration mailConfig = SpringBeanFactory.getBean(MailParamConfiguration.class);
        MailSend send = SpringBeanFactory.getBean(MailSend.class);
        MailParam param = MailParam.builder()
                .title(title)
                .content(content)
                .toAddress(mailTos)
                .build();
        send.send(param);
    }

}
