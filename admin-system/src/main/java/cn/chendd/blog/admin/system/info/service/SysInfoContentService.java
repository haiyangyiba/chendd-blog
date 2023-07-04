package cn.chendd.blog.admin.system.info.service;

import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.admin.system.info.po.SysInfoContentManageParam;
import cn.chendd.blog.admin.system.info.vo.SysInfoContentManageResult;
import cn.chendd.blog.client.infocontent.MaintenanceInfoVo;
import cn.chendd.blog.client.infocontent.SysInfoProjectResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 系统内容信息表Service接口定义
 * @author chendd
 * @date 2020/08/29 11:46
 */
public interface SysInfoContentService extends IService<SysInfoContent> {

    /**
     * 按条件查询所有内容信息数据
     * @param param 查询条件
     * @return 列表数据
     */
    List<SysInfoContentManageResult> queryInfoContentList(SysInfoContentManageParam param);

    /**
     * 保存系统内容数据
     * @param sysInfoContent 内容数据
     * @return 是否保存成功
     */
    Integer saveSysInfoContent(SysInfoContent sysInfoContent);

    /**
     * 删除内容数据
     * @param id id
     * @return 删除影响行数
     */
    Integer removeInfoContent(Long id);

    /**
     * 查看内容数据
     * @param id id
     * @return 内容数据对象
     */
    SysInfoContent viewSysInfoContent(Long id);

    /**
     * 根据需要查询的关键字标识数组查询一组系统内容数据：系统介绍
     * @param key 查询标识
     * @return 数据集
     */
    List<SysInfoContent> querySystemInfo(String key);

    /**
     * 根据需要查询的关键字标识数组查询一组系统内容数据：系统介绍
     * @param key 查询标识
     * @return 数据集
     */
    List<SysInfoContent> querySystemProject(String key);

    /**
     * 根据key查询系统内容数据
     * @param key 系统内容
     * @return 系统内容
     */
    SysInfoProjectResult queryInfoContentByKey(String key);

    /**
     * 根据自定义页面标识查询返回数据，为了让自定义页面扩展性更高故而使用Map对象类型
     * @param page 页面标识
     * @return 自定义页面
     */
    Map<String , Object> queryCustomPage(String page);

    /**
     * 获取点赞最多的前N篇文章
     * @return 文章列表
     */
    List<MaintenanceInfoVo.ArticleRecord> queryArticleForParise();

    /**
     * 获取评论最多的前N篇文章
     * @return 文章列表
     */
    List<MaintenanceInfoVo.ArticleRecord> queryArticleForComment();
}
