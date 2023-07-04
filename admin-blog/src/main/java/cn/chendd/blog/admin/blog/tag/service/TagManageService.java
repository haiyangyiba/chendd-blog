package cn.chendd.blog.admin.blog.tag.service;

import cn.chendd.blog.admin.blog.tag.model.Tag;
import cn.chendd.blog.admin.blog.tag.po.TagManageParam;
import cn.chendd.blog.admin.blog.tag.po.TagParam;
import cn.chendd.blog.admin.blog.tag.vo.TagManageResult;
import cn.chendd.blog.client.article.tag.TagArticleDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 标签管理Service接口定义
 * @author chendd
 * @date 2020/7/15 10:46
 */
public interface TagManageService extends IService<Tag> {

    /**
     * 查询全部数据分页
     * @param param 查询条件
     * @return 分页数据
     */
    Page<TagManageResult> queryTagManagePage(TagManageParam param);

    /**
     * 保存或修改标签
     * @param param 数据对象
     * @return 主键ID
     */
    Long saveTag(TagParam param);

    /**
     * 删除标签
     * @param id 主键ID
     */
    void removeTag(Long id);

    /**
     * @return 查询系统推荐标签列表
     */
    List<TagManageResult> queryStrongTagsList();

    /**
     * 根据标签名称查询标签关联的文章数据
     * @param tag 标签名称
     * @return 文章列表数据
     */
    List<TagArticleDto> queryStrongTagsListByName(String tag);
}
