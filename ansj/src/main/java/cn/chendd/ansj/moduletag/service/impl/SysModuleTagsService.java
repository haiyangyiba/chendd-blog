package cn.chendd.ansj.moduletag.service.impl;

import cn.chendd.ansj.moduletag.mapper.SysModuleTagsMapper;
import cn.chendd.ansj.moduletag.model.SysModuleTags;
import cn.chendd.ansj.moduletag.service.ISysModuleTagsService;
import cn.chendd.ansj.moduletag.vo.ModuleResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模块标签表Service接口实现
 * @author chendd
 * @date 2020/08/08 21:06
 */
@Service
public class SysModuleTagsService extends ServiceImpl<SysModuleTagsMapper , SysModuleTags> implements ISysModuleTagsService {

    @Resource
    private SysModuleTagsMapper sysModuleTagsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveModuleTags(Long targetId, String moduleName, List<String> tags) {
        //删除指定模块数据
        QueryWrapper wrapper = new QueryWrapper<SysModuleTags>().eq("moduleName" , moduleName).eq("targetId" , targetId);
        sysModuleTagsMapper.delete(wrapper);
        List<SysModuleTags> dataList = Lists.newArrayList();
        for (String tag : tags) {
            if (StringUtils.length(tag) > 64) {
                continue;
            }
            dataList.add(new SysModuleTags(targetId , moduleName , tag));
        }
        super.saveBatch(dataList);
    }

    @Override
    public List<ModuleResult> getModuleCountFor10(String moduleName, Long targetId) {
        return sysModuleTagsMapper.getModuleCountFor10(moduleName , targetId);
    }
}
