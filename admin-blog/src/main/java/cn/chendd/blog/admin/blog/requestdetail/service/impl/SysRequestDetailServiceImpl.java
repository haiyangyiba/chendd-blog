package cn.chendd.blog.admin.blog.requestdetail.service.impl;

import cn.chendd.blog.admin.blog.friendslink.service.FriendsLinkService;
import cn.chendd.blog.admin.blog.requestdetail.mapper.SysRequestDetailMapper;
import cn.chendd.blog.admin.blog.requestdetail.mapper.SysRequestStatisticsMapper;
import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestDetail;
import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestStatistics;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDateDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.service.SysRequestDetailService;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestDetailResult;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestMaxDay;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.base.filters.detail.RequestItem;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chendd
 * @date 2020/7/21 11:17
 */
@Service
@Slf4j
public class SysRequestDetailServiceImpl extends ServiceImpl<SysRequestDetailMapper, SysRequestDetail> implements SysRequestDetailService {

    @Resource
    private SysRequestStatisticsMapper sysRequestStatisticsMapper;

    @Resource
    private SysRequestDetailMapper sysRequestDetailMapper;
    
    @Resource
    private FriendsLinkService friendsLinkService;

    @Override
    public void batchSaveRequestDetail(List<SysRequestDetail> dataList) {
        super.saveBatch(dataList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(description = "系统请求-定时统计" , scope = LogScopeEnum.ALL)
    public void transferRequestDetail(String beginDate , String date) {
        //1、当统计表中不存在数据的时候再存储
        QueryWrapper dateQueryWrapper = new QueryWrapper<SysRequestStatistics>().eq("date" , date);
        int count = sysRequestStatisticsMapper.selectCount(dateQueryWrapper);
        log.info("日期 {} 获取到的数据条数 {}" , date , count);
        if(count == 0) {
            SysRequestStatistics statistics = new SysRequestStatistics();
            int dateCount = sysRequestDetailMapper.selectCount(new QueryWrapper<SysRequestDetail>().eq("createDate" , date));
            int dateIsOutLinkCount = sysRequestDetailMapper.selectCount(new QueryWrapper<SysRequestDetail>()
                    .likeRight("createDate" , date).eq("isOutLink" , Boolean.TRUE.toString()));
            statistics.setCount(dateCount);
            statistics.setDate(date);
            statistics.setOutLinkCount(dateIsOutLinkCount);
            sysRequestStatisticsMapper.insert(statistics);
        }
        //2、查询date对应数据的友情链接数据，更新至对应的访问个数；
        //3、删除beginDate 前对应的数据
        sysRequestDetailMapper.delete(new QueryWrapper<SysRequestDetail>().likeRight("createDate" , beginDate));
    }

    @Override
    @Log(description = "系统请求-查询" , scope = LogScopeEnum.METHOD_PARAMETER)
    public IPage<SysRequestDetailResult> queryAccessManagePage(SysRequestDetailParam param) {

        Page page = new Page(param.getPageNumber() , param.getPageSize());
        return sysRequestStatisticsMapper.queryAccessManagePage(page , param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRequestAccess(List<RequestItem> dataList , String basePath) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        //查询所有友链数据
        List<FriendsLinkDto> friendsLinkDtos = friendsLinkService.queryFriendsLink();
        List<SysRequestDetail> resultList = Lists.newArrayList();
        SysRequestDetail result;
        for (RequestItem item : dataList) {
            result = new SysRequestDetail();
            result.setId(IdWorker.getId());
            result.setUrl(item.getUrl());
            UserAgent userAgent = UserAgent.parseUserAgentString(item.getUserAgent());
            result.setOperatingSystem(userAgent.getOperatingSystem().toString());
            result.setBrowser(userAgent.getBrowser().toString());
            result.setIp(item.getIp());
            result.setCreateDate(item.getCreateDate());
            result.setCreateTime(item.getCreateTime());
            result.setReferer(item.getReferer());
            //是否为站内请求
            boolean outLink = ! StringUtils.contains(item.getReferer() , "chendd.cn");
            result.setIsOutLink(String.valueOf(outLink));
            if (outLink) {
                //是否为友链的请求来源，若是则更新友链的数据
                FriendsLinkDto link = friendsLinkDtos.stream().filter(element -> StringUtils.contains(item.getReferer(), element.getDomainTag())).findFirst().orElse(null);
                if (link != null) {
                    this.friendsLinkService.saveFriendsLinkAccess(link.getId() , 1);
                }
            }
            resultList.add(result);
        }
        super.saveBatch(resultList);
    }

    @Override
    @Cacheable(key = "'queryRequestAccessYestday_' + #yestday" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public Integer queryRequestAccessYestday(String yestday) {
        return this.sysRequestDetailMapper.queryRequestAccessDate(yestday);
    }

    @Override
    @Cacheable(key = "'queryRequestAccessToday_' + #today" , cacheNames = CacheNameConstant.AUTO_EXPIRED_10_MINUTE)
    public Integer queryRequestAccessToday(String today) {
        return this.sysRequestDetailMapper.queryRequestAccessDate(today);
    }

    @Override
    @Cacheable(key = "'queryRequestAccessOutLinkDate_' + #today" , cacheNames = CacheNameConstant.AUTO_EXPIRED_10_MINUTE)
    public Integer queryRequestAccessOutLinkDate(String today) {
        return this.sysRequestDetailMapper.queryRequestAccessOutLinkDate(today);
    }

    @Override
    @Log(description = "系统请求-按日期查询" , scope = LogScopeEnum.METHOD_PARAMETER)
    public IPage<SysRequestDetail> queryAccessRequestManagePage(SysRequestDateDetailParam param) {

        Page page = new Page(param.getPageNumber() , param.getPageSize());
        String sortName = param.getSortName();
        if (StringUtils.isEmpty(sortName)) {
            page.setOrders(OrderItem.descs("t1.createTime"));
        } else if (Constant.ORDER_DESC.equals(param.getSortOrder())) {
            page.setOrders(OrderItem.descs("t1." + sortName));
        } else if (Constant.ORDER_ASC.equals(param.getSortOrder())) {
            page.setOrders(OrderItem.ascs("t1." + sortName));
        }
        IPage<SysRequestDetail> pageFinder = sysRequestStatisticsMapper.queryAccessRequestManagePage(page, param);
        List<SysRequestDetail> records = pageFinder.getRecords();
        String ip , before , after;
        for (SysRequestDetail record : records) {
            ip = record.getIp();
            before = StringUtils.substringBefore(ip, ".");
            after = StringUtils.substringAfterLast(ip, ".");
            record.setIp(before.concat(".*.*.").concat(after));
        }
        return pageFinder;
    }

    @Override
    @Cacheable(key = "'queryRequestAccessMaxDay'" , cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
    public SysRequestMaxDay queryRequestAccessMaxDay() {
        return this.sysRequestStatisticsMapper.queryRequestAccessMaxDay();
    }

    @Override
    @Cacheable(key = "'queryRequestAccessTotal'" , cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
    public Integer queryRequestAccessTotal() {
        return this.sysRequestStatisticsMapper.queryRequestAccessTotal();
    }
}
