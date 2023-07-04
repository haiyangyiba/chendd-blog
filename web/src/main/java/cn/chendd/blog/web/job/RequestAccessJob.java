package cn.chendd.blog.web.job;

import cn.chendd.blog.base.filters.RequestDetailFilter;
import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.blog.web.home.service.RequestAccessService;
import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 资源访问定时任务
 *
 * @author chendd
 * @date 2022/9/11 17:15
 */
@Component
@Slf4j
public class RequestAccessJob {

    @Resource
    private RequestAccessService requestAccessService;

    @Scheduled(cron = "30 0/10 * * * ?")
    public void execute() {
        /*Queues.drain(RequestDetailFilter.REQUEST_ITEM_QUEUE , dataList , 100 , Duration.ofMinutes(1));*/
        BlockingQueue<RequestItem> tempQueue = Queues.newLinkedBlockingQueue(RequestDetailFilter.REQUEST_ITEM_QUEUE);
        int size = tempQueue.size();
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            RequestDetailFilter.REQUEST_ITEM_QUEUE.poll();
        }
        List<RequestItem> dataList = new ArrayList<>(tempQueue);
         requestAccessService.saveRequestAccess(dataList);
    }

}
