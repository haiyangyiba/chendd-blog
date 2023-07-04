package cn.chendd.ansj.moduletag.mapper;

import cn.chendd.ansj.moduletag.vo.ModuleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.chendd.ansj.moduletag.model.SysModuleTags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模块标签表Mapper接口定义
 * @author chendd
 * @date 2020/08/08 21:06
 */
public interface SysModuleTagsMapper extends BaseMapper<SysModuleTags> {

    /**
     * 根据模块名称和目标ID查询前10个标签关键字
     * @param moduleName 模块名称
     * @param targetId 目标ID
     * @return 标签关键字列表
     */
    List<ModuleResult> getModuleCountFor10(@Param("moduleName") String moduleName, @Param("targetId") Long targetId);
}
