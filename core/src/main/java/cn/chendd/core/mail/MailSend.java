package cn.chendd.core.mail;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 邮件发送实现
 *
 * @author chendd
 * @date 2022/4/17 11:46
 */
@Component
public class MailSend {

    @Resource
    private MailConfig config;

    /**
     * 构种参数对象
     * @param config 参数配置
     * @return 发送对象
     */
    public MailSend build(MailConfig config) {
        this.config = config;
        return this;
    }

    @SneakyThrows
    public void send(MailParam param) {
        //根据属性，创建邮件会话
        Session session = Session.getDefaultInstance(config.build());
        //设置邮件发送时显示调试信息
        session.setDebug(config.getDebug());
        Message msg = new MimeMessage(session);
        Transport trans = null;
        //根据消息对象，设置邮件的发送人
        try {
            msg.setFrom(new InternetAddress(config.getUserAddress()));
            //设置邮件收件人
            this.setMailTo(param , msg);
            //设置邮件抄送人
            this.setMailCc(param , msg);
            //设置邮件密送人
            this.setMailBcc(param , msg);
            //设置主题
            msg.setSubject(param.getTitle());
            ///注意，这里的发送时间不能要，如果有则需要准确的时间，否则发送报错
            //msg.setSentDate(new Date());// 设置发送时间
            //添加邮件内容
            Multipart multipart = this.getMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(param.getContent() , config.getContentType());
            multipart.addBodyPart(bodyPart);
            //添加邮件附件
            this.setMailAttachment(param , msg , multipart);
            msg.setContent(multipart);
            msg.saveChanges();
            trans = session.getTransport("smtp");
            trans.connect(config.getHost() , config.getUserName() , config.getUserPwd());
            trans.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailRuntimeException(e);
        } finally {
            if (trans != null) {
                trans.close();
            }
        }
    }

    /**
     * 构造复合邮件发送对象
     * @return 发送对象
     */
    protected Multipart getMultipart() {
        return new MimeMultipart();
    }

    /**
     * 设置邮件接收人
     * @param param 邮件发送参数
     * @param msg 邮件发送对象
     * @throws MessagingException 发送消息异常
     */
    protected void setMailTo(MailParam param, Message msg) throws MessagingException {
        //设置邮件的接收人
        List<String> toAddress = param.getToAddress();
        if (toAddress != null && !toAddress.isEmpty()) {
            int lens = toAddress.size();
            InternetAddress[] tos = new InternetAddress[lens];
            for (int i = 0; i < lens; i++) {
                tos[i] = new InternetAddress(toAddress.get(i));
            }
            msg.setRecipients(Message.RecipientType.TO, tos);
        }
    }

    /**
     * 设置邮件抄送人
     * @param param 邮件发送参数
     * @param msg 邮件发送对象
     * @throws MessagingException 发送消息异常
     */
    protected void setMailCc(MailParam param, Message msg) throws MessagingException {
        List<String> ccAddress = param.getCcAddress();
        if(ccAddress != null && !ccAddress.isEmpty()){
            int size = ccAddress.size();
            InternetAddress[] ccAdds = new InternetAddress[size];
            for (int i = 0; i < size; i++) {
                String address = ccAddress.get(i);
                ccAdds[i] = new InternetAddress(address);
            }
            msg.setRecipients(Message.RecipientType.CC, ccAdds);
        }
    }

    /**
     * 设置邮件抄送人
     * @param param 邮件发送参数
     * @param msg 邮件发送对象
     * @throws MessagingException 发送消息异常
     */
    protected void setMailBcc(MailParam param, Message msg) throws MessagingException {
        List<String> bccAddress = param.getBccAddress();
        if(bccAddress != null && !bccAddress.isEmpty()){
            int size = bccAddress.size();
            InternetAddress[] bccAdds = new InternetAddress[size];
            for (int i = 0; i < size ; i++) {
                String address = bccAddress.get(i);
                bccAdds[i] = new InternetAddress(address);
            }
            msg.setRecipients(Message.RecipientType.BCC, bccAdds);
        }
    }

    /**
     * 设置邮件发送附件
     * @param param 邮件发送参数
     * @param msg 邮件发送对象
     * @param multipart 复合对象
     * @throws MessagingException 发送消息异常
     */
    protected void setMailAttachment(MailParam param, Message msg, Multipart multipart) throws MessagingException, UnsupportedEncodingException {
        List<DataSource> attachments = param.getAttachments();
        if (attachments == null || attachments.isEmpty()) {
            return ;
        }
        for (DataSource dataSource : attachments) {
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setDataHandler(new DataHandler(dataSource));
            ///此处可以发送ByteArrayDataSource、URL、FilePath
            //bodyPart.setDataHandler(new DataHandler(new URL("http://p1.qhimg.com/t0151320b1d0fc50be8.png")));//设置发送网络附件
            bodyPart.setFileName(MimeUtility.encodeText(dataSource.getName(), this.config.getContentEncoding() ,"B"));
            multipart.addBodyPart(bodyPart);
        }
    }

}
