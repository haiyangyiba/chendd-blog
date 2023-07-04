package cn.chendd.blog.admin.blog.jobs;

import cn.chendd.blog.admin.blog.article.service.ArticlePraisesService;
import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.admin.blog.comment.service.SysCommentService;
import cn.chendd.blog.admin.blog.jobs.config.MailParamConfiguration;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.mail.MailParam;
import cn.chendd.core.mail.MailSend;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 邮件通知点赞：通知给管理员
 *
 * @author chendd
 * @date 2022/4/11 16:02
 */
public class EmailRemindPraiseAndCommentJob extends AbstractQuartzJob {

    @Override
    protected BaseResult doExecute(JobExecutionContext context) {
        //检查每个周期的时间范围内的点赞数量，定时频率10分钟一轮
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND , 0);
        String end = DateFormat.format(calendar.getTime(), Constant.DATE_HH_MM_PATTERN);
        calendar.add(Calendar.MINUTE , -10);
        String begin = DateFormat.format(calendar.getTime(), Constant.DATE_HH_MM_PATTERN);
        List<RemindPrausesAndCommentResult> praisesList = SpringBeanFactory.getBean(ArticlePraisesService.class).queryRemindPraises(begin, end);
        List<RemindPrausesAndCommentResult> commentList = SpringBeanFactory.getBean(SysCommentService.class).queryRemindComment(begin, end);
        //判断当前是否存在符合条件的数据
        if (CollectionUtils.isNotEmpty(praisesList) || CollectionUtils.isNotEmpty(commentList)) {
            String content;
            try {
                FreeMarkerConfigurer configurer = SpringBeanFactory.getBean(FreeMarkerConfigurer.class);
                Template template = configurer.getConfiguration().getTemplate("点赞评论提醒.html");
                HashMap<String, Object> dataMap = Maps.newHashMap();
                String title = String.format("【%s至%s】" , begin , StringUtils.substringAfter(end , " "));
                dataMap.put("praisesTitle" , title + "的点赞");
                dataMap.put("praisesList" , praisesList);
                dataMap.put("commentTitle" , title + "的留言");
                dataMap.put("commentList" , commentList);
                content = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);
            } catch (IOException | TemplateException e) {
                content = ExceptionUtils.getStackTrace(e);
            }
            //调用发送邮件Api
            MailParamConfiguration mailConfig = SpringBeanFactory.getBean(MailParamConfiguration.class);
            MailSend send = SpringBeanFactory.getBean(MailSend.class);
            MailParam param = MailParam.builder()
                    .title(mailConfig.getMailRemindTitle())
                    .content(content)
                    .toAddress(Lists.newArrayList(mailConfig.getMailRemindTo()))
                    .build();
            send.send(param);
            return new SuccessResult<>(String.format("邮件已发送：点赞条数 = %d ，评论条数 = %d" , praisesList.size() , commentList.size()));
        }
        return new SuccessResult<>("未找到符合提醒的点赞与评论数据");
    }

}
