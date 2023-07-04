package cn.chendd.core.mail;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * 邮件发送参数设置
 *
 * @author chendd
 * @date 2022/4/17 11:47
 */
@Component
@PropertySource(encoding = "utf-8" , value = "classpath:config/mail/mail.yaml")
@Data
public class MailConfig {

    @Value("${host}")
    private String host;
    @Value("${port}")
    private String port;
    @Value("${auth}")
    private Boolean auth;
    @Value("${debug}")
    private Boolean debug;
    @Value("${address}")
    private String userAddress;
    @Value("${name}")
    private String userName;
    @Value("${pass}")
    private String userPwd;
    @Value("${type}")
    private String contentType;
    @Value("${encoding}")
    private String contentEncoding;
    @Value("${others}")
    private String others;

    @PostConstruct
    public void test() {
        System.out.println("MailConfig.MailConfig");
    }

    /**
     * 将配置的参数转换为Properties
     * @return 发送邮件参数
     */
    public Properties build() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", this.getHost());
        props.setProperty("mail.smtp.auth", this.getAuth().toString());
        props.setProperty("mail.smtp.port", this.getPort());
        /*使用某个邮箱特有的配置，其它相关参数*/
        String mailSendOthers = this.getOthers();
        if(StringUtils.isNotEmpty(mailSendOthers)){
            String[] params = mailSendOthers.split("\\|");
            for (String prop : params) {
                String[] ps = prop.split("=");
                props.setProperty(ps[0], ps[1]);
            }
        }
        return props;
    }

}
