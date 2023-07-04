package cn.chendd.toolkit.dbproperty.commonents;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 参数表数据映射对象
 *
 * @author chendd
 * @date 2019/9/13 8:05
 */
@Data
@DbValueConfiguration
@Component
public class SysDbValueMapping extends AbstractSysDbValueMapping {

    @DbValue
    private String httpServerIp;

    @DbValue("http.server.ip")
    private String httpServerIpBak;

    @DbValue("http.server.name")
    private String httpServerName;

    @DbValue("http.server.port")
    private Integer httpServerPort;

    @DbValue(suffix = "key.pi")
    private Double piSuffix;

    @DbValue(prefix = "test.key")
    private Double piPrefix;

    @DbValue("test.status")
    private Boolean status;

    @DbValue(prefix = "http.server")
    private Map<String , String> servers;

    @DbValue(group = "test.database")
    private Map<String , String> databases;

    @DbValue(prefix = "http", suffix = "name")
    private Map<String , String> connects;

    @DbValue("test.database.host1")
    private String mysqlPort;

    @DbValue("test.database.host2")
    private String oraclePort;

}
