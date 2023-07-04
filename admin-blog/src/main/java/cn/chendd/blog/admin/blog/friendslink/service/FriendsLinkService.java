package cn.chendd.blog.admin.blog.friendslink.service;

import cn.chendd.blog.admin.blog.friendslink.model.FriendsLink;
import cn.chendd.blog.admin.blog.friendslink.po.FriendsLinkParam;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 友情链接Service接口定义
 *
 * @author chendd
 * @date 2020/6/27 21:45
 */
public interface FriendsLinkService extends IService<FriendsLink> {


    /**
     * 友链管理列表查询
     * @param title 标题
     * @return 列表数据
     */
    List<FriendsLinkDto> queryFriendsLink(String title);

    /**
     * 保存友链站点信息
     * @param friendsLink 站点对象
     * @return 保存结果
     */
    FriendsLink saveFriendsLink(FriendsLinkParam friendsLink);

    /**
     * 删除友链数据
     * @param id id
     */
    void removeFriendsLink(Long id);

    /**
     * 查询排名前limit个的友情链接数据
     * @return 友情链接数据
     */
    List<FriendsLinkNewestDto> queryFriendsLinkNewest();

    /**
     * 查询友情连接数据，包含启用与禁用的数据
     * @return 友情连接数据
     */
    List<FriendsLinkDto> queryFriendsLink();

    /**
     * 保存友链访问数据
     * @param id id
     * @param value
     */
    void saveFriendsLinkAccess(Long id, int value);
}
