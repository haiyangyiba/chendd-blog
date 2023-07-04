package cn.chendd.blog.base.thymeleaf.format;

/**
 * format类型定义
 *
 * @author chendd
 * @date 2020/5/31 22:46
 */
public interface IFormatType {

    /**
     * 格式化文本
     * @param value 文本
     * @return 格式化后的文本
     */
    String format(String value);

}
