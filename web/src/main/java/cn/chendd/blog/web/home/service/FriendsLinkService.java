package cn.chendd.blog.web.home.service;

import cn.chendd.blog.client.friendslink.FriendsLinkDto;

import java.util.List;

/**
 * 友情连接service定义
 *
 * @author chendd
 * @date 2021/12/22 21:02
 */
public interface FriendsLinkService {

    /**
     * 查询所有友情连接数据
     * @return 数据列表
     */
    List<FriendsLinkDto> queryFriendsLink();
}
