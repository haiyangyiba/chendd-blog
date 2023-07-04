package cn.chendd.core.mail;

import lombok.Builder;
import lombok.Data;

import javax.activation.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 邮件发送时的发送参数
 *
 * @author chendd
 * @date 2022/4/17 11:44
 */
@Data
@Builder
public class MailParam {

    /**
     * 邮件标题
     */
    private String title;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 发送人
     */
    private String fromAddress;
    /**
     * 发送附件地址
     */
    private List<DataSource> attachments;
    /**
     * 发送邮件ContentID
     */
    private Map<String , DataSource> attachmentsContentID;
    /**
     * 接收人地址
     */
    private List<String> toAddress;
    /**
     * 抄送人
     */
    private List<String> ccAddress;
    /**
     * 密送人（暗抄送/密送）
     */
    private List<String> bccAddress;

}
