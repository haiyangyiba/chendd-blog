package cn.chendd.blog.admin.system.pool.service.impl;

import cn.chendd.blog.admin.system.pool.bo.DataSourceBo;
import cn.chendd.blog.admin.system.pool.service.DataSourceService;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.utils.DateFormat;
import com.alibaba.druid.pool.DruidConnectionHolder;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库管理Service接口实现
 *
 * @author chendd
 * @date 2021/11/20 21:01
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Override
    public DataSourceBo getDataSourceConfig() {
        DataSourceBo bo = new DataSourceBo();
        DruidDataSource dataSource = SpringBeanFactory.getBean(DruidDataSource.class);
        //基本参数
        bo.setName(dataSource.getName());
        bo.setDriverClassName(dataSource.getDriverClassName());
        bo.setUrl(dataSource.getUrl());
        bo.setUsername(dataSource.getUsername());
        bo.setPassword("******");
        bo.setMaxActive(dataSource.getMaxActive());
        bo.setMinIdle(dataSource.getMinIdle());
        bo.setMaxWait(dataSource.getMaxWait());
        bo.setInitialSize(dataSource.getInitialSize());
        //toString参数
        bo.setCreateTime(DateFormat.formatDatetime(dataSource.getCreatedTime()));
        bo.setActiveCount(dataSource.getActiveCount());
        bo.setCreateCount(dataSource.getCreateCount());
        bo.setDestroyCount(dataSource.getDestroyCount());
        bo.setCloseCount(dataSource.getCloseCount());
        bo.setConnectCount(dataSource.getConnectCount());
        int poolingCount = dataSource.getPoolingCount();
        bo.setPoolingCount(poolingCount);
        //Connections
        bo.setExecuteQueryCount(dataSource.getExecuteQueryCount());
        bo.setExecuteUpdateCount(dataSource.getExecuteUpdateCount());
        bo.setExecuteCount(dataSource.getExecuteCount());
        bo.setExecuteCount2(dataSource.getExecuteCount2());
        //连接详细
        List<DataSourceBo.DataSourceConnectionBo> connectionBoList = Lists.newArrayList();
        bo.setDataSourceConnections(connectionBoList);
        List<Map<String, Object>> connections = dataSource.getPoolingConnectionInfo();
        for (Map<String, Object> connMap : connections) {
            DataSourceBo.DataSourceConnectionBo connBo = new DataSourceBo.DataSourceConnectionBo();
            connBo.setConnectionId((Long) connMap.get("connectionId"));
            connBo.setUseCount((Long) connMap.get("useCount"));
            Date lastActiveTime = (Date) connMap.get("lastActiveTime");
            if (lastActiveTime != null) {
                connBo.setLastActiveTime(DateFormat.formatDatetime(lastActiveTime));
            }
            connBo.setConnectTime(DateFormat.formatDatetime((Date) connMap.get("connectTime")));
            connectionBoList.add(connBo);
        }
        return bo;
    }
}
