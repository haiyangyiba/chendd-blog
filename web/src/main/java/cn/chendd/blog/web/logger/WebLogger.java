package cn.chendd.blog.web.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模块日志输出器
 *
 * @author chendd
 * @date 2024/3/23 18:37
 */
public final class WebLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogger.class.getSimpleName());

    /**
     * 返回日志输出器
     * @return 日志对象
     */
    public static Logger getLogger() {
        return LOGGER;
    }

}
