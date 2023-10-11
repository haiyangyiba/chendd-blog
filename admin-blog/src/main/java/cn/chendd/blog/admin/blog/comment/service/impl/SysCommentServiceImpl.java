package cn.chendd.blog.admin.blog.comment.service.impl;

import cn.chendd.blog.admin.blog.article.enums.ArticleAscriptionEnum;
import cn.chendd.blog.admin.blog.article.vo.RemindPrausesAndCommentResult;
import cn.chendd.blog.admin.blog.article.vo.RemindUserCommentResult;
import cn.chendd.blog.admin.blog.comment.mapper.SysCommentMapper;
import cn.chendd.blog.admin.blog.comment.model.SysComment;
import cn.chendd.blog.admin.blog.comment.po.SysCommentManageParam;
import cn.chendd.blog.admin.blog.comment.po.SysCommentParam;
import cn.chendd.blog.admin.blog.comment.service.SysCommentService;
import cn.chendd.blog.admin.blog.comment.vo.SysCommentManageResult;
import cn.chendd.blog.admin.system.constants.CacheNameConstant;
import cn.chendd.blog.admin.system.info.model.SysInfoContent;
import cn.chendd.blog.admin.system.info.service.SysInfoContentService;
import cn.chendd.blog.admin.system.shortcut.config.ShortcutMenuParamComponent;
import cn.chendd.blog.base.enums.EnumDataStatus;
import cn.chendd.blog.client.comment.vo.CommentNewestDto;
import cn.chendd.blog.client.comment.vo.CommentQueryResult;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.utils.DateFormat;
import cn.chendd.toolkit.operationlog.annotions.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 留言评论表Service接口实现
 * @author chendd
 * @date 2021/02/19 20:46
 */
@Service
public class SysCommentServiceImpl extends ServiceImpl<SysCommentMapper , SysComment> implements SysCommentService {

    private static final Integer REPLY_MAX_NUMBER = 10;

    @Resource
    private SysCommentMapper sysCommentMapper;
    @Resource
    private SysInfoContentService sysInfoContentService;
    @Resource
    private ShortcutMenuParamComponent shortcutMenuParam;

    @Override
    @Caching(
        evict = {
            @CacheEvict(key = "'index_queryCommentNewestList_6'" , cacheNames = CacheNameConstant.NOT_EXPIRED)
        }
    )
    public Long saveSysComment(SysCommentParam param) {
        SysComment comment = new SysComment();
        BeanUtils.copyProperties(param , comment);
        comment.setCreateTime(DateFormat.formatDatetime());
        super.save(comment);
        return comment.getId();
    }

    @Override
    @Caching(
        evict = {
            @CacheEvict(key = "'querySysCommentPage_' + #param.targetId + '_' + #param.pageNumber" , cacheNames = CacheNameConstant.NOT_EXPIRED),
            @CacheEvict(key = "'index_queryCommentNewestList_6'" , cacheNames = CacheNameConstant.NOT_EXPIRED)
        }
    )
    public Long saveSysCommentForChild(SysCommentParam param) {
        Long childId = param.getChildId();
        if (childId != null) {
            int replyCount = this.getSysCommentForReplyCount(childId);
            if (replyCount > REPLY_MAX_NUMBER) {
                throw new ValidateDataException(String.format("系统限制最多允许回复 %d 条" , REPLY_MAX_NUMBER));
            }
        }
        SysComment comment = new SysComment();
        BeanUtils.copyProperties(param , comment);
        comment.setCreateTime(DateFormat.formatDatetime());
        super.save(comment);
        return comment.getId();
    }

    @Override
    @Cacheable(key = "'querySysCommentPage_' + #targetId + '_' + #pageNumber" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public Page<CommentQueryResult> querySysComment(Long targetId , Long pageNumber) {
        Page page = new Page(pageNumber , Constant.DEFAULT_PAGE_SIZE);
        Page<CommentQueryResult> pageFinder = sysCommentMapper.queryPageSysComment(page , targetId);
        long total = pageFinder.getTotal();
        if (total == 0) {
            return pageFinder;
        }
        List<CommentQueryResult> commentList = pageFinder.getRecords();
        for (CommentQueryResult result : commentList) {
            result.setCreateTime(DateFormat.getTimeInterval(result.getCreateTime()));
        }
        List<CommentQueryResult> dataList = sysCommentMapper.querySysComment(targetId , commentList);
        if (dataList.isEmpty()) {
            return pageFinder;
        }
        //将两个集合的结果集汇总
        for (CommentQueryResult item : commentList) {
            List<CommentQueryResult> detailList = Lists.newArrayList();
            Long itemId = item.getId();
            for (CommentQueryResult detail : dataList) {
                Long childId = detail.getChildId();
                if (itemId.equals(childId)) {
                    detail.setCreateTime(DateFormat.getTimeInterval(detail.getCreateTime()));
                    detailList.add(detail);
                }
            }
            item.setChildList(detailList);
        }
        return pageFinder;
    }

    @Override
    @Log(description = "分页查询")
    public Page<SysCommentManageResult> querySysCommentManagePage(SysCommentManageParam param) {
        Page page = new Page(param.getPageNumber() , param.getPageSize());
        return sysCommentMapper.querySysCommentManagePage(page , param);
    }

    /**
     * 根据主键ID查询子评论的回复个数
     * @param id 评论主键ID
     * @return 回复个数
     */
    private int getSysCommentForReplyCount(Long id) {
        return super.count(new QueryWrapper<SysComment>().eq("childId" , id));
    }

    @Override
    @Log(description = "查看留言内容")
    public SysComment getCommentById(Long id) {
        return this.sysCommentMapper.querySysCommentById(id);
    }

    @Override
    public Boolean removeCommentById(Long id) {
        SysComment sysComment = super.getById(id);
        if (sysComment == null) {
            //数据已被删除
            return this.sysCommentMapper.updateDataStatusById(id , EnumDataStatus.USABLE.getValue()) > 0;
        } else {
            return super.removeById(id);
        }
    }

    @Override
    public Integer querySysCountById(Long id) {
        return this.sysCommentMapper.querySysCountById(id);
    }

    @Override
    @Cacheable(key = "'index_queryCommentNewestList_6'" , cacheNames = CacheNameConstant.NOT_EXPIRED)
    public List<CommentNewestDto> queryCommentNewestList(int limit) {
        return this.sysCommentMapper.queryCommentNewestList(limit);
    }

    @Override
    public List<RemindPrausesAndCommentResult> queryRemindComment(String begin, String end) {
        return this.sysCommentMapper.queryRemindComment(begin , end);
    }

    @Override
    public List<RemindUserCommentResult> queryRemindUserComment(String begin, String end) {
        List<RemindUserCommentResult> dataList = this.sysCommentMapper.queryRemindUserComment(begin, end);
        List<SysInfoContent> sysInfoList = this.sysInfoContentService.list();
        for (RemindUserCommentResult result : dataList) {
            if (ArticleAscriptionEnum.Custom.getValue().equals(result.getAscription())) {
                SysInfoContent sysInfoContent = sysInfoList.stream().filter(item -> StringUtils.contains(item.getEditorContent(), result.getTargetId())).findFirst().orElse(null);
                if (sysInfoContent == null) {
                    continue;
                }
                String key = sysInfoContent.getKey();
                if (StringUtils.startsWith(key , "systemProject_")) {
                    String url = String.format("%sblog/project/%s.html" , shortcutMenuParam.getShortcutMenus().get("web首页") , StringUtils.substringAfter(key , "systemProject_"));
                    result.setUrl(url);
                } else if (StringUtils.startsWith(key , "customPage_")) {
                    String url = String.format("%sblog/page/%s.html" , shortcutMenuParam.getShortcutMenus().get("web首页") , StringUtils.substringAfter(key , "customPage_"));
                    result.setUrl(url);
                }
            } else {
                String url = String.format("%sblog/article/%s.html" , shortcutMenuParam.getShortcutMenus().get("web首页") , result.getTargetId());
                result.setUrl(url);
            }
        }
        return dataList;
    }
}
