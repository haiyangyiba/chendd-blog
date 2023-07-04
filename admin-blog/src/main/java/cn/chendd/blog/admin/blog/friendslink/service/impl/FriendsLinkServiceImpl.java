package cn.chendd.blog.admin.blog.friendslink.service.impl;

import cn.chendd.blog.admin.blog.friendslink.mapper.FriendsLinkCountMapper;
import cn.chendd.blog.admin.blog.friendslink.mapper.FriendsLinkMapper;
import cn.chendd.blog.admin.blog.friendslink.model.FriendsLink;
import cn.chendd.blog.admin.blog.friendslink.model.FriendsLinkCount;
import cn.chendd.blog.admin.blog.friendslink.po.FriendsLinkParam;
import cn.chendd.blog.admin.blog.friendslink.service.FriendsLinkService;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.client.friendslink.FriendsLinkDto;
import cn.chendd.blog.client.friendslink.FriendsLinkNewestDto;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友情链接Service接口实现
 *
 * @author chendd
 * @date 2020/6/27 21:46
 */
@Service
@CacheConfig(cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
public class FriendsLinkServiceImpl extends ServiceImpl<FriendsLinkMapper, FriendsLink> implements FriendsLinkService {

    @Resource
    private FriendsLinkMapper friendsLinkMapper;

    @Resource
    private FriendsLinkCountMapper friendsLinkCountMapper;

    /**
     * 查询友链列表
     * @param title 标题
     * @return 列表数据
     */
    @Override
    @Log(description = "查询友链列表" , scope = LogScopeEnum.METHOD_PARAMETER)
    public List<FriendsLinkDto> queryFriendsLink(String title) {
        return friendsLinkMapper.queryFriendsLink(title);
    }

    @Override
    @Log(description = "'保存友情链接：' + (#param.id != null ? '修改' : '新增')" , scope = LogScopeEnum.ALL)
    @Caching(
         evict = {
               @CacheEvict(key = "'index_queryAllFriendsLink'" , allEntries = false , beforeInvocation = true),
               @CacheEvict(key = "'queryFriendsLinkNewest_15'" , allEntries = false , beforeInvocation = true)
         }
    )
    public FriendsLink saveFriendsLink(FriendsLinkParam param) {

        FriendsLink friendsLink = new FriendsLink();
        BeanUtils.copyProperties(param , friendsLink);
        super.saveOrUpdate(friendsLink);
        return friendsLink;
    }

    @Override
    @Log(description = "'删除友链，ID = ' + #id" , scope = LogScopeEnum.METHOD_PARAMETER)
    public void removeFriendsLink(Long id) {
        super.removeById(id);
    }

    @Override
    @Cacheable(key = "'queryFriendsLinkNewest_15'")
    public List<FriendsLinkNewestDto> queryFriendsLinkNewest() {
        return friendsLinkMapper.queryFriendsLinkNewest(15);
    }

    @Override
    @Cacheable(key = "'index_queryAllFriendsLink'")
    public List<FriendsLinkDto> queryFriendsLink() {
        return friendsLinkMapper.queryFriendsLink(null);
    }

    @Override
    public void saveFriendsLinkAccess(Long id, int value) {
        FriendsLinkCount result = friendsLinkCountMapper.selectById(id);
        if (result == null) {
            result = new FriendsLinkCount();
            result.setFlId(id);
            result.setCount(value);
            this.friendsLinkCountMapper.insert(result);
        } else {
            result.setCount(result.getCount() + value);
            this.friendsLinkCountMapper.updateById(result);
        }

    }
}
