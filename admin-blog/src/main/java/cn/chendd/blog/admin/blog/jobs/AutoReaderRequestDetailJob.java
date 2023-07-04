package cn.chendd.blog.admin.blog.jobs;

import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestDetail;
import cn.chendd.blog.admin.blog.requestdetail.service.SysRequestDetailService;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.quartz.jobs.AbstractQuartzJob;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Lists;
import org.quartz.JobExecutionContext;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 自动读取特定日志文件数据
 *
 * @author chendd
 * @date 2020/7/20 21:57
 */
@Slf4j
public class AutoReaderRequestDetailJob extends AbstractQuartzJob {

    @Override
    protected BaseResult doExecute(JobExecutionContext context) {
        String logFolder = SpringBeanFactory.getEnvironment().getProperty("log.path.dev");
        String detailFolder = logFolder + File.separator + "access-thread";
        File detail = new File(detailFolder);
        String fileName = String.format("log-access-%s.log" , DateFormat.format(new Date() , "yyyy-MM-dd.HH.mm"));
        File files[] = detail.listFiles((file, name) -> fileName.compareTo(name) >= 0);
        if (files == null) {
            log.warn("目录 {} 中不存在文件 {}" , detail.getAbsolutePath() , fileName);
            return new SuccessResult<>("不存在需要解析的日志文件");
        }
        List<String> resultList = Lists.newArrayList();
        for (File file : files) {
            //读取文件中的内容，解析存储入库
            try {
                List<String> dataLines = FileUtils.readLines(file, Charsets.UTF_8);
                this.disposeDataLines(dataLines , file);
            } catch (IOException e) {
                String message = String.format("读取日志 %s 文件出现错误：" , file.getName());
                log.error(message , e);
                resultList.add(message + e.getMessage());
            }
        }
        return new SuccessResult<>(resultList);
    }

    /**
     * 将数据入库并删除日志文件
     * @param dataLines 按行读取的文件数据
     * @param file 日志文件
     */
    private void disposeDataLines(List<String> dataLines , File file) {
        List<SysRequestDetail> dataList = Lists.newArrayList();
        for (String line : dataLines) {
            SysRequestDetail requestItem = JSON.parseObject(line, SysRequestDetail.class);
            dataList.add(requestItem);
        }
        SysRequestDetailService sysRequestDetailService = SpringBeanFactory.getBean(SysRequestDetailService.class);
        sysRequestDetailService.batchSaveRequestDetail(dataList);
        boolean delete = file.delete();
        log.info("定时保存系统访问的日志文件并删除，删除结果：{}" , delete);
    }

}
