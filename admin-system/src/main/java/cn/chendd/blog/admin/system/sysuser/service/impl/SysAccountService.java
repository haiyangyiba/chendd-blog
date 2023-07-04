package cn.chendd.blog.admin.system.sysuser.service.impl;

import cn.chendd.blog.admin.system.sysuser.mapper.SysAccountMapper;
import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.admin.system.sysuser.service.ISysAccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户账号管理Service接口实现
 *
 * @author chendd
 * @date 2019/11/24 16:07
 */
@Service
public class SysAccountService extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {

    @Resource
    private SysAccountMapper sysAccountMapper;

    @Override
    public SysAccount querySysAccountByUsername(String username) {
        return sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().eq("username", username));
    }
}
