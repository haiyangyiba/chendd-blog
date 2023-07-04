package cn.chendd.blog.admin.blog.requestdetail.service;

import cn.chendd.blog.admin.blog.requestdetail.model.SysRequestDetail;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDateDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.po.SysRequestDetailParam;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestDetailResult;
import cn.chendd.blog.admin.blog.requestdetail.vo.SysRequestMaxDay;
import cn.chendd.blog.base.filters.detail.RequestItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chendd
 * @date 2020/7/21 11:15
 */
public interface SysRequestDetailService extends IService<SysRequestDetail> {

    /**
     * 批量保存访问历史明细数据
     * @param dataList 数据集合
     */
    void batchSaveRequestDetail(List<SysRequestDetail> dataList);

    /**
     * 迁移某个日期的访问历史数据
     * @param beginDate 历史日期
     * @param date 更新日期
     */
    void transferRequestDetail(String beginDate , String date);

    /**
     * 查询访问明细分页
     * @param param 查询条件
     * @return 分页
     */
    IPage<SysRequestDetailResult> queryAccessManagePage(SysRequestDetailParam param);

    /**
     * 批量保存访问明细数据
     * @param dataList 集合
     * @param basePath 根路径
     */
    void saveRequestAccess(List<RequestItem> dataList , String basePath);

    /**
     * 查询今日和昨日访问数量
     * @param yestday 昨日访问数量
     * @return 访问数量
     */
    Integer queryRequestAccessYestday(String yestday);

    /**
     * 查询今日访问数量
     * @param today 今日日期
     * @return 访问数量
     */
    Integer queryRequestAccessToday(String today);

    /**
     * 查询今日外链访问数量
     * @param today 日期
     * @return 访问数量
     */
    Integer queryRequestAccessOutLinkDate(String today);

    /**
     * 根据日期查询访问明细
     * @param param 请求参数
     * @return 分页数据
     */
    IPage<SysRequestDetail> queryAccessRequestManagePage(SysRequestDateDetailParam param);

    /**
     * 查询系统单日最大访问
     * @return 单日最大访问数量
     */
    SysRequestMaxDay queryRequestAccessMaxDay();

    /**
     * 查询系统单总访问
     * @return 查询系统单总访问
     */
    Integer queryRequestAccessTotal();
}
