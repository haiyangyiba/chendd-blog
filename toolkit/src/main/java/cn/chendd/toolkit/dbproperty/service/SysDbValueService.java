package cn.chendd.toolkit.dbproperty.service;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import cn.chendd.toolkit.dbproperty.po.SysDbValueParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统参数映射Service接口定义
 *
 * @author chendd
 * @date 2019/9/13 19:30
 */
public interface SysDbValueService extends IService<SysDbValue> {

    /**
     * 从数据库查询所有数据
     * @param param 查询参数
     * @return 全部数据
     */
    List<SysDbValue> selectAll(SysDbValueParam param);

    /**
     * 根据组查询数据
     * @param group 组名
     * @return 数据
     */
    List<SysDbValue> queryListByGroup(String group);

    /**
     * 获取已经查询后的数据
     * @return 全部数据
     */
    List<SysDbValue> queryAll();

    /**
     * 查询所有数据并刷新
     */
    List<SysDbValue> queryAllAndRefresh();

    /**
     * 保存系统参数
     * @param dbValue 参数对象
     */
    void saveSysDbValue(SysDbValue dbValue);

    /**
     * 删除系统参数
     * @param id 参数id
     */
    void removeSysDbValue(Long id);

    /**
     * 同步更新参数管理
     */
    void asyncSysDbValue();

    /**
     * 根据key获取value值
     * @param group group
     * @param key key
     * @return value值
     */
    String getDbValueByKey(String group , String key);
}
