package cn.chendd.blog.admin.blog.jobs.config;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 邮件发送参数配置类
 *
 * @author chendd
 * @date 2022/4/21 21:24
 */
@Data
@DbValueConfiguration
@Component
public class MailParamConfiguration {

    /**
     * 邮件提醒标题（系统点赞/留言提醒）
     */
    @DbValue(value = "mail.remind.title")
    private String mailRemindTitle;
    /**
     * 邮件接收人（系统点赞/留言提醒）
     */
    @DbValue(value = "mail.remind.to")
    private String mailRemindTo;
    /**
     * 邮件密送人（用户留言被回复提醒）
     */
    @DbValue(value = "mail.remind.bcc")
    private String mailRemindBcc;

}
