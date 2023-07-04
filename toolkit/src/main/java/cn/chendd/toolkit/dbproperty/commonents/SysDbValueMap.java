package cn.chendd.toolkit.dbproperty.commonents;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 这是另外的一个属性映射，看看是否支持
 *
 * @author chendd
 * @date 2019/9/15 0:32
 */
@Data
@DbValueConfiguration
@Component
public class SysDbValueMap {

    @DbValue("http.server.ip")
    private String testServerIp;

    @DbValue("test.database.host1")
    private String mysqlPort;

    @DbValue("test.database.host2")
    private String oraclePort;


}
