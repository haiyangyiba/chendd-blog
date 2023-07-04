package cn.chendd.ueditor;

import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ueditor代码插件转换，替换默认的代码语法着色实现
 *
 * @author chendd
 * @date 2022/3/12 9:40
 */
public class UeditorCodeConvert {

    /**
     * 匹配代码块正则
     */
    private static final Pattern pattern = Pattern.compile("<pre class=\"brush:(\\w+);.*?>([\\s+\\S]*?)</pre>", Pattern.CASE_INSENSITIVE);
    /**
     * Prism插件代码块语法规则
     */
    private static final String CODE_TEMPLATE = "<pre class=\"language-%1$s line-numbers\"><code>%2$s</code></pre>";

    /**
     * 代码块转换为Prism插件格式
     * @param content 字符串含代码块
     * @return 替换后的文本
     */
    public static String code2Prims(String content) {
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String code = String.format(CODE_TEMPLATE , matcher.group(1) , matcher.group(2));
            content = content.replace(matcher.group() , code);
        }
        return content;
    }

}
