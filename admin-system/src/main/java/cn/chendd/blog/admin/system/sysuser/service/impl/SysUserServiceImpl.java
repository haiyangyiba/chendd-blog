package cn.chendd.blog.admin.system.sysuser.service.impl;

import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.admin.system.sysrolemenu.service.SysRoleMenuService;
import cn.chendd.blog.admin.system.sysuser.mapper.SysAccountMapper;
import cn.chendd.blog.admin.system.sysuser.mapper.SysUserMapper;
import cn.chendd.blog.admin.system.sysuser.model.SysAccount;
import cn.chendd.blog.admin.system.sysuser.model.SysUser;
import cn.chendd.blog.admin.system.sysuser.po.SysUserManageParam;
import cn.chendd.blog.admin.system.sysuser.po.SysUserSaveParam;
import cn.chendd.blog.admin.system.sysuser.service.SysUserService;
import cn.chendd.blog.admin.system.sysuser.vo.SysUserManageResult;
import cn.chendd.blog.admin.system.sysuser.vo.UserInfoViewResult;
import cn.chendd.blog.admin.system.sysuserrole.service.SysUserRoleService;
import cn.chendd.blog.base.enums.EnumStatus;
import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.blog.base.model.ThirdUserResult;
import cn.chendd.blog.base.spring.component.Base64AndImageComponent;
import cn.chendd.blog.client.user.vo.SysUserResult;
import cn.chendd.blog.client.user.vo.UserInfoResult;
import cn.chendd.blog.client.user.vo.UserNewestDto;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.utils.Codec;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.annotions.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户Service实现
 *
 * @author chendd
 * @date 2019/11/24 16:39
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAccountMapper sysAccountMapper;
    @Resource
    private Base64AndImageComponent base64Component;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser querySysUserByAccountId(Long accountId) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("accountId" , accountId));
    }

    @Override
    @Log(description = "查询用户列表")
    public Page<SysUserManageResult> querySysUserPage(SysUserManageParam userParam) {

        Page page = new Page(userParam.getPageNumber() , userParam.getPageSize());
        return sysUserMapper.querySysUserPage(page, userParam);
    }

    @Override
    public SysAccount querySysUserByUsername(String username) {
        return sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().eq("username" , username));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(description = "用户管理-新增")
    public SuccessResult<String> saveUser(SysUserSaveParam param) {
        String username = param.getUsername();
        SysAccount dbAccount = this.querySysUserByUsername(username);
        if(dbAccount != null) {
            throw new ValidateDataException(String.format("当前用户账户 [%s] 已经存在！" , username));
        }
        //存储用户账户信息
        SysAccount account = new SysAccount();
        String datetime = DateFormat.formatDatetime();
        account.setCreateDate(datetime);
        account.setUsername(username);
        String newPassword = Codec.getSecurityCodeByText(param.getPassword());
        account.setPassword(newPassword);
        //默认用户禁用
        account.setStatus(EnumStatus.DISABLE);
        sysAccountMapper.insert(account);
        //存储用户上传头像信息
        String userImage = this.reloadUserImage(param.getUserImage() , account.getUsername());
        //存储用户主体信息
        SysUser sysUser = new SysUser();
        sysUser.setUserSource(EnumUserSource.System);
        sysUser.setAccountId(account.getAccountId());
        sysUser.setCreateTime(datetime);
        sysUser.setEmail(param.getEmail());
        sysUser.setRealName(param.getRealName());
        sysUser.setPortrait(userImage);
        sysUser.setUserNumber(null);
        sysUserMapper.insert(sysUser);
        String result = String.format("用户账号 [%s] 保存成功！", account.getUsername());
        return new SuccessResult<String>(result);
    }

    @Override
    @Log(description = "'查看用户：' + #userId")
    public UserInfoViewResult getUserInfoViewById(Long userId) {
        return sysUserMapper.getUserInfoViewById(userId);
    }

    @Override
    @Log(description = "用户管理-修改")
    public BaseResult editUser(SysUserSaveParam param) {
        //运行修改的用户数据范围：昵称（真实姓名）、邮箱、头像
        String username = param.getUsername();
        String userImage = param.getUserImage();
        param.setUserImage(this.reloadUserImage(userImage , username));
        sysUserMapper.editUser(param);
        String result = String.format("用户账号 [%s] 修改成功！", username);
        return new SuccessResult<String>(result);
    }

    @Override
    @Log(description = "'用户状态修改：状态=' + #status + '，用户=' + #userId")
    public Integer editUserStatus(String status, Long userId) {
        return sysUserMapper.editUserStatus(status , userId);
    }

    @Override
    @Log(description = "'用户密码重置：accountId=' + #accountId")
    public void resetPassword(Long accountId, String password) {
        SysAccount account = new SysAccount();
        account.setPassword(Codec.getSecurityCodeByText(password));
        sysAccountMapper.update(account , new QueryWrapper<SysAccount>().eq("accountId" , accountId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserResult thirdUserStore(ThirdUserResult userResult) {

        String userNumber = userResult.getUserNumber();
        EnumUserSource userSource = userResult.getUserSource();
        if (userSource == null || EnumUserSource.System.equals(userSource)) {
            throw new ValidateDataException("不支持的用户来源");
        }
        SysAccount dbAccount = null;
        SysUser dbUser = this.getOne(new QueryWrapper<SysUser>().eq("userNumber" , userNumber).eq("userSource" , userSource.getValue()));
        String datetime = DateFormat.formatDatetime();
        if (dbUser == null) {
            //存储用户账号对象、用户信息对象、用户角色
            dbAccount = new SysAccount();
            dbAccount.setUsername(userSource.getValue() + ":" + userNumber);
            dbAccount.setPassword(Codec.getSecurityCodeByText(dbAccount.getUsername()));
            dbAccount.setCreateDate(datetime);
            dbAccount.setStatus(EnumStatus.ENABLE);
            this.sysAccountMapper.insert(dbAccount);
            //构造用户信息对象
            SysUser user = new SysUser();
            user.setUserNumber(userNumber);
            user.setPortrait(userResult.getPortrait());
            user.setRealName(userResult.getNickName());
            user.setCreateTime(datetime);
            user.setAccountId(dbAccount.getAccountId());
            user.setUserSource(userSource);
            user.setLastLoginTime(datetime);
            this.sysUserMapper.insert(user);
            //存储默认角色
            sysUserRoleService.saveDefaultUserRole(user.getUserId());
        } else {
            //更新用户最新的信息，如昵称、头像、最后一次登录时间
            dbUser.setLastLoginTime(datetime);
            dbUser.setRealName(userResult.getNickName());
            dbUser.setPortrait(userResult.getPortrait());
            this.sysUserMapper.update(dbUser , new QueryWrapper<SysUser>().eq("userNumber" , userNumber).eq("userSource" , userSource.getValue()));
            dbAccount = this.sysAccountMapper.selectById(dbUser.getAccountId());
        }
        //查询用户全部主体信息
        return sysRoleMenuService.putUserResult(dbAccount);
    }

    @Override
    @Cacheable(key = "'queryUserNewestList_' + #limit" , cacheNames = CacheNameConstant.AUTO_EXPIRED_1_DAY)
    public List<UserNewestDto> queryUserNewestList(int limit) {
        return this.sysUserMapper.queryUserNewestList(limit);
    }

    @Override
    public UserInfoResult queryUserInfo(Long userId) {
        return this.sysUserMapper.queryUserInfo(userId);
    }

    @Override
    public void updateEmailByUserId(String userId, String email) {
        this.sysUserMapper.updateEmailByUserId(userId , email);
    }

    /**
     * 重构用户头像
     */
    private String reloadUserImage(String userImage , String username) {
        String start = StringUtils.substring(userImage , 0 , 4);
        if(StringUtils.startsWithIgnoreCase(userImage , "../.")) {
            //替换掉页面上的../路径
            return Constant.DEFAULT_USER_IAMGE;
        } else if(StringUtils.startsWithIgnoreCase(userImage , "/images/")) {
            //替换掉页面上的../路径
            return Constant.DEFAULT_USER_IAMGE;
        }  else if(StringUtils.startsWithIgnoreCase(userImage , "/file/")) {
            //替换掉页面上的../路径
            return userImage;
        } else if(StringUtils.startsWithIgnoreCase(userImage , "data")) {
            //将图片进行保存，并且
            String prefix = "data:image/png;base64,";
            String image = userImage.substring(prefix.length() , userImage.length() - 1);
            return "/file" + base64Component.convert2File(image , "/system/userImage/" + username + ".png");
        } else {
            throw new ValidateDataException("不能识别的用户头像！");
        }
    }
}
