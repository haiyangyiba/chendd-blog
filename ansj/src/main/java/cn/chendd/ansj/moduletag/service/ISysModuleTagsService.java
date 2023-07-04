package cn.chendd.ansj.moduletag.service;

import cn.chendd.ansj.moduletag.model.SysModuleTags;
import cn.chendd.ansj.moduletag.vo.ModuleResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 模块标签表Service接口定义
 * @auth chendd
 * @date 2020/08/08 21:06
 */
public interface ISysModuleTagsService extends IService<SysModuleTags> {

    /**
     * 保存模块关键字
     * @param targetId 文章ID
     * @param moduleName 模块名称
     * @param tags 关键字
     */
    void saveModuleTags(Long targetId , String moduleName , List<String> tags);

    /**
     * 根据模块名称和目标ID查询前10个标签关键字
     * @param moduleName 模块名称
     * @param targetId 目标ID
     * @return 标签关键字列表
     */
    List<ModuleResult> getModuleCountFor10(String moduleName , Long targetId);
    
}
