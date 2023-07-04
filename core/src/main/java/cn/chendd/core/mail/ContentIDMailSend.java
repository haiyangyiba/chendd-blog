package cn.chendd.core.mail;

import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 设置支持内容ID的邮件发送
 *
 * @author chendd
 * @date 2022/4/18 22:32
 */
public class ContentIDMailSend extends MailSend {

    @Override
    protected Multipart getMultipart() {
        return new MimeMultipart("related");
    }

    @Override
    protected void setMailAttachment(MailParam param, Message msg, Multipart multipart) throws MessagingException, UnsupportedEncodingException {
        //设置邮件发送附件
        super.setMailAttachment(param, msg, multipart);
        //判断是否存在附件内容ID
        Map<String, DataSource> attachmentsContentID = param.getAttachmentsContentID();
        if (attachmentsContentID == null || attachmentsContentID.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, DataSource>> entrySet = attachmentsContentID.entrySet();
        int size = entrySet.size();
        //限制当前存在附件附件，且当ContentID的数量 大于 附件个数时为异常逻辑
        if (size == 0) {
            return;
        }
        //设置 ContentID 的数据
        for (Map.Entry<String, DataSource> entry : entrySet) {
            MimeBodyPart gifBodyPart = new MimeBodyPart();
            gifBodyPart.setDataHandler(new DataHandler(entry.getValue()));
            //cid的值
            gifBodyPart.setContentID(entry.getKey());
            multipart.addBodyPart(gifBodyPart);
        }
    }
}
