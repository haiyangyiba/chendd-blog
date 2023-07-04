package cn.chendd.toolkit.dbproperty.service.impl;

import cn.chendd.toolkit.dbproperty.commonents.SysDbValueMapping;
import cn.chendd.toolkit.dbproperty.mapper.SysDbValueMapper;
import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import cn.chendd.toolkit.dbproperty.po.SysDbValueParam;
import cn.chendd.toolkit.dbproperty.service.SysDbValueService;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统参数映射Service
 *
 * @author chendd
 * @date 2019/9/13 18:14
 */
@Service
public class SysDbValueServiceImpl extends ServiceImpl<SysDbValueMapper , SysDbValue> implements SysDbValueService {

    @Resource
    private SysDbValueMapper sysDbValueMapper;

    @Resource
    private SysDbValueMapping sysDbValueMapping;

    @Override
    public List<SysDbValue> queryAll() {
        return sysDbValueMapping.getAllList();
    }

    @Override
    public List<SysDbValue> queryListByGroup(String group) {
        if(StringUtils.isEmpty(group)) {
            throw new NullPointerException("group must not null");
        }
        return this.queryAll().stream().filter(item -> StringUtils.equals(item.getGroup(), group))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysDbValue> queryAllAndRefresh() {
        synchronized (sysDbValueMapping) {
            List<SysDbValue> dataList = this.selectAll(null);
            sysDbValueMapping.setAllList(dataList);
            return dataList;
        }
    }

    @Override
    @Log(description = "参数管理-查询参数列表")
    public List<SysDbValue> selectAll(SysDbValueParam param) {
        return sysDbValueMapper.selectList(new QueryWrapper<>());
    }

    @Override
    @Log(description = "'参数管理-' + (#dbValue.id == null ? '新增' : '修改')")
    public void saveSysDbValue(SysDbValue dbValue) {
        SysDbValue entity = new SysDbValue();
        BeanUtils.copyProperties(dbValue , entity);
        super.saveOrUpdate(entity);
    }

    @Override
    @Log(description = "参数管理-删除")
    public void removeSysDbValue(Long id) {
        super.removeById(id);
    }

    @Override
    @Log(description = "参数管理-同步更新")
    public void asyncSysDbValue() {
        this.queryAllAndRefresh();
    }

    @Override
    public String getDbValueByKey(String group , String key) {
        SysDbValue dbValue = sysDbValueMapper.selectOne(new QueryWrapper<SysDbValue>().eq("`group`", group).eq("`key`", key));
        if (dbValue == null) {
            return null;
        }
        return dbValue.getValue();
    }
}
