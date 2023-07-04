package cn.chendd.blog.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * base模块log输出
 *
 * @author chendd
 * @date 2023/2/26 14:28
 */
public class BaseLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseLog.class.getSimpleName());

    public static Logger getLogger() {
        return LOGGER;
    }

}
