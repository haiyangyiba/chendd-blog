package cn.chendd.blog.admin.blog.tag.mapper;

import cn.chendd.blog.admin.blog.tag.model.Tag;
import cn.chendd.blog.admin.blog.tag.po.TagManageParam;
import cn.chendd.blog.admin.blog.tag.vo.TagManageResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 标签管理Mapper接口定义
 *
 * @author chendd
 * @date 2020/7/15 10:55
 */
public interface TagManageMapper extends BaseMapper<Tag> {


    /**
     * 查询分页数据
     * @param page 分页参数
     * @param param 查询条件
     * @return 分页数据
     */
    Page<TagManageResult> queryTagManagePage(Page page, @Param("param") TagManageParam param);

}
