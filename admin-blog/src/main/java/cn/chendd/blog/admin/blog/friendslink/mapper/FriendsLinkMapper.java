package cn.chendd.blog.admin.blog.friendslink.mapper;

import cn.chendd.blog.admin.blog.friendslink.model.FriendsLink;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 友情链接Mapper
 *
 * @author chendd
 * @date 2020/6/27 21:47
 */
public interface FriendsLinkMapper extends BaseMapper<FriendsLink> {

    /**
     * 查询排名前limit个的友情链接数据
     * @param limit 前limit条数据
     * @return 友情链接数据
     */
    List<FriendsLinkNewestDto> queryFriendsLinkNewest(@Param("limit") int limit);

    /**
     * 查询友情连接数据，包含启用与禁用的数据
     * @param title 标题
     * @return 友情连接数据
     */
    List<FriendsLinkDto> queryFriendsLink(String title);

}
