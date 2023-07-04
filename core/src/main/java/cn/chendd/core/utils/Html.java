package cn.chendd.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * html工具类
 *
 * @author chendd
 * @date 2020/10/31 11:39
 */
public class Html {

    /**
     * 根据html内容获取纯文本
     *
     * @param html html内容
     * @return 纯文本
     */
    public static String getPureText(String html) {
        return getPureText(html , false);
    }

    /**
     * 根据html内容获取纯文本
     * @param html html内容
     * @param unescape 是否将html转义
     * @return 纯文本
     */
    public static String getPureText(String html , boolean unescape) {
        if (unescape) {
            html = StringEscapeUtils.unescapeHtml4(html);
        }
        if (StringUtils.isEmpty(html)) {
            return html;
        }
        Document document = Jsoup.parseBodyFragment(html);
        return document.text();
    }

    /**
     * 获取纯文本，且支持放行一些简单的html标签
     *
     * @param html html内容
     * @return 含简单html标签的文本
     */
    public static String getSimpleHtml(String html) {
        if (StringUtils.isEmpty(html)) {
            return html;
        }
        return Jsoup.clean(html, Whitelist.basic());
    }

    /**
     * 获取过滤html文本，指定运行放行某些标签
     *
     * @param html html内容
     * @param tags 标签名称数组
     * @return 重新设置后的文本
     */
    public static String getIncludeHtml(String html, String... tags) {
        return getFilterHtml(html, true, tags);
    }

    /**
     * 获取过滤html文本，指定运行放行某些标签
     *
     * @param html html内容
     * @param tags 标签名称数组
     * @return 重新设置后的文本
     */
    public static String getExcludeHtml(String html, String... tags) {
        return getFilterHtml(html, false, tags);
    }

    /**
     * 过滤html文本内容
     *
     * @param html html文本
     * @param flag true ? 包含 : 排除
     * @param tags 标签范围数组
     * @return 重新设置后的文本
     */
    private static String getFilterHtml(String html, boolean flag, String... tags) {
        if (StringUtils.isEmpty(html)) {
            return html;
        }
        if (tags == null || tags.length == 0) {
            return html;
        }
        if (flag) {
            return Jsoup.clean(html, new Whitelist().addTags(tags));
        } else {
            return Jsoup.clean(html, new Whitelist().removeTags(tags));
        }
    }

}
