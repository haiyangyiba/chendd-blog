package cn.chendd.core.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Core模块日志输出器
 *
 * @author chendd
 * @date 2022/4/18 22:51
 */
public final class CoreLogger {

    /**
     * 定义日志输出器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreLogger.class.getSimpleName());

    /**
     * 获取日志输出器对象
     * @return 日志输出器
     */
    public static Logger getLogger() {
        return LOGGER;
    }

}
