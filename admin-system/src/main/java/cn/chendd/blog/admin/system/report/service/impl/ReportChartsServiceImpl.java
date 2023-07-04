package cn.chendd.blog.admin.system.report.service.impl;

import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.admin.system.report.mapper.ReportChartsMapper;
import cn.chendd.blog.admin.system.report.service.ReportChartsService;
import cn.chendd.blog.admin.system.report.vo.HomepageChartsResult;
import cn.chendd.blog.admin.system.report.vo.NameValueResult;
import cn.chendd.blog.admin.system.sysmenu.mapper.SysMenuMapper;
import cn.chendd.blog.admin.system.sysmenu.model.SysMenu;
import cn.chendd.blog.admin.system.sysuser.mapper.SysUserMapper;
import cn.chendd.blog.admin.system.sysuser.model.SysUser;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.utils.EnumUtil;
import cn.chendd.core.utils.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 统计图表接口实现
 * @author chendd
 * @date 2021/6/26
 */
@Service
public class ReportChartsServiceImpl implements ReportChartsService {

    @Resource
    private ReportChartsMapper reportChartsMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    @Cacheable(key = "'queryHomepageCharts'" , cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
    public HomepageChartsResult queryHomepageCharts() {
        HomepageChartsResult result = new HomepageChartsResult();
        //查询用户来源统计，并将数据的名称name转换为文本text显示
        List<NameValueResult> userSources = this.reportChartsMapper.queryUserSourcesCharts();
        userSources.forEach(item -> item.setName(EnumUtil.getInstanceByName(item.getName() , EnumUserSource.class).getText()));
        result.setUserSources(userSources);
        //查询文章按年份统计
        List<NameValueResult> articleYears = this.reportChartsMapper.queryArticleYears();
        result.setArticleYears(articleYears);
        //查询文章访问量统计
        List<NameValueResult> articleVisits = this.reportChartsMapper.queryArticleVisits();
        result.setArticleVisits(articleVisits);
        //查询一级菜单的文章个数统计
        /*List<NameValueResult> articleNumbers = this.queryWebMenus();*/
        result.setArticleNumbers(Lists.newArrayList());
        //各种数量的统计：文章数量、用户数量、评论数量、点赞个数
        List<NameValueResult> amountNumbers = this.queryAmountNumbers();
        result.setAmountNumbers(amountNumbers);
        //推荐标签
        List<NameValueResult> tagNames = this.reportChartsMapper.queryTagNameList();
        for (NameValueResult tag : tagNames) {
            String color = RandomUtil.getRandomColor();
            String fontSize = RandomUtil.getRandomFontSize();
            String style = String.format("color:%s;font-size:%s;" , color , fontSize);
            JSONObject json = new JSONObject();
            json.put("name" , tag.getName());
            json.put("style" , style);
            json.put("count" , tag.getValue());
            tag.setName(json.toJSONString());
        }
        result.setTagNames(tagNames);
        return result;
    }

    /**
     * 一些其它的参数统计
     *
     * @return 数据集合
     */
    private List<NameValueResult> queryAmountNumbers() {
        List<NameValueResult> dataList = Lists.newArrayList();
        //查询用户数量
        Integer userCount = this.sysUserMapper.selectCount(new QueryWrapper<SysUser>());
        dataList.add(new NameValueResult("用户数量" , userCount.toString()));
        //查询文章数量
        Integer articleCount = this.sysMenuMapper.queryArticleCount();
        dataList.add(new NameValueResult("文章数量" , articleCount.toString()));
        //查询评论数量
        Integer articleCommentCount = this.reportChartsMapper.queryArticleCommentCount();
        dataList.add(new NameValueResult("评论数量" , articleCommentCount.toString()));
        //查询点赞数量
        Integer articlePraisesCount = this.reportChartsMapper.queryArticlePraisesCount();
        dataList.add(new NameValueResult("点赞数量" , articlePraisesCount.toString()));
        return dataList;
    }

    /**
     * 查询前台菜单所属的所有文章信息
     * @return 菜单对应的文章数量
     */
    private List<NameValueResult> queryWebMenus() {
        List<NameValueResult> dataList = Lists.newArrayList();
        //查询一级菜单
        List<SysMenu> rootList = this.sysMenuMapper.queryWebMenus();
        //查询一级菜单下的全部子菜单
        List<SysMenu> menuList = this.sysMenuMapper.selectList(new QueryWrapper<SysMenu>());
        //过滤一级节点下的全部子节点
        rootList.forEach(root -> {
            List<Long> menuIdList = Lists.newArrayList();
            for (SysMenu menu : menuList) {
                if (StringUtils.startsWithIgnoreCase(menu.getMenuKey() , root.getMenuKey())) {
                    menuIdList.add(menu.getMenuId());
                }
            }
            if (menuIdList.isEmpty()) {
                dataList.add(new NameValueResult(root.getMenuName() , BigDecimal.ZERO.toString()));
            } else {
                //查询菜单下所有的子集菜单对应的文章数量
                Integer count = this.sysMenuMapper.queryArticleCountByType(menuIdList);
                dataList.add(new NameValueResult(root.getMenuName() , String.valueOf(count)));
            }
        });
        return dataList;
    }

}
