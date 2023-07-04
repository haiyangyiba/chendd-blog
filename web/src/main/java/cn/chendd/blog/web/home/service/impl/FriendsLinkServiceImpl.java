package cn.chendd.blog.web.home.service.impl;

import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.blog.web.home.client.FriendsLinkClient;
import cn.chendd.blog.web.home.service.FriendsLinkService;
import cn.chendd.core.result.BaseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友情连接service接口实现
 *
 * @author chendd
 * @date 2021/12/22 21:03
 */
@Service
public class FriendsLinkServiceImpl implements FriendsLinkService {

    @Resource
    private FriendsLinkClient friendsLinkClient;

    @Override
    public List<FriendsLinkDto> queryFriendsLink() {
        BaseResult<List<FriendsLinkDto>> result = this.friendsLinkClient.queryFriendsLink();
        return result.getData();
    }
}
