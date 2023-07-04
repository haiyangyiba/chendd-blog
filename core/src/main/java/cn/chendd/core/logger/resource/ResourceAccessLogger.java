package cn.chendd.core.logger.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源文件输出类
 *
 * @author chendd
 * @date 2020/7/19 13:57
 */
public class ResourceAccessLogger {

    private static final Logger log = LoggerFactory.getLogger(ResourceAccessLogger.class);

    public static Logger getLog() {
        return log;
    }

}
