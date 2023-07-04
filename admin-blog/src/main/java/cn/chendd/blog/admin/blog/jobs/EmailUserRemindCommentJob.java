package cn.chendd.blog.admin.blog.jobs;

import cn.chendd.blog.admin.blog.article.vo.RemindUserCommentResult;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 邮件通知留言：通知给被回复的人员
 *
 * @author chendd
 * @date 2022/4/11 16:02
 */
@Slf4j
public class EmailUserRemindCommentJob extends AbstractQuartzJob {

    @Override
    protected BaseResult doExecute(JobExecutionContext context) {
        //检查每个周期的时间范围内的留言评论数量，定时频率10分钟一轮
        SysCommentService sysCommentService = SpringBeanFactory.getBean(SysCommentService.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND , 0);
        String end = DateFormat.format(calendar.getTime(), Constant.DATE_HH_MM_PATTERN);
        calendar.add(Calendar.MINUTE , -10);
        String begin = DateFormat.format(calendar.getTime(), Constant.DATE_HH_MM_PATTERN);
        List<RemindUserCommentResult> resultList = sysCommentService.queryRemindUserComment(begin, end);
        //调用模板引擎生成html
        if (CollectionUtils.isNotEmpty(resultList)) {
            MailParamConfiguration mailConfig = SpringBeanFactory.getBean(MailParamConfiguration.class);
            MailSend send = SpringBeanFactory.getBean(MailSend.class);
            FreeMarkerConfigurer configurer = SpringBeanFactory.getBean(FreeMarkerConfigurer.class);
            Map<Long, List<RemindUserCommentResult>> dataMap = resultList.stream().collect(Collectors.groupingBy(RemindUserCommentResult::getReplyUserId));
            StringBuilder messageBuilder = new StringBuilder();
            for (Map.Entry<Long, List<RemindUserCommentResult>> entry : dataMap.entrySet()) {
                List<RemindUserCommentResult> list = entry.getValue();
                try {
                    Template template = configurer.getConfiguration().getTemplate("用户留言回复.html");
                    Map<String, Object> params = Maps.newHashMap();
                    RemindUserCommentResult item = list.get(0);
                    params.put("userSource" , item.getUserSource());
                    params.put("realName" , item.getRealName());
                    params.put("resultList" , list);
                    String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
                    //调用发送邮件Api
                    MailParam param = MailParam.builder()
                            .title("您的留言有新的回复")
                            .toAddress(Lists.newArrayList(item.getEmail()))
                            .bccAddress(Lists.newArrayList(mailConfig.getMailRemindBcc()))
                            .content(content)
                            .build();
                    send.send(param);
                    messageBuilder.append("邮件已发送").append("；");
                } catch (IOException | TemplateException e) {
                    log.error("用户留言邮件提醒定时任务出现错误：" , e);
                    messageBuilder.append("发送出现错误：").append(e.getMessage()).append("；");
                }
            }
            return new SuccessResult<>(messageBuilder.toString());
        }
        return new SuccessResult<>("未找到符合提醒的用户留言数据");
    }

}
