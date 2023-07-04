package cn.chendd.core.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import static cn.chendd.core.common.constant.Constant.NUMBER_CHAR;
import static cn.chendd.core.common.constant.Constant.SPECIAL_CHAR;

/**
 * 加密/编码工具类
 *
 * @author chendd
 * @date 2019/11/24 15:36
 */
public abstract class Codec {


    /**
     * 根据文本获取其加密后的编码
     *
     * @param text
     *            待加密的文本
     * @return 加密后的字符串
     */
    public static String getSecurityCodeByText(String text) {
        byte data[] = DigestUtils.md5Hex(text).getBytes();
        return new String(Hex.encodeHex(data));
    }

    /**
     * 比较加密后的文本是否匹配
     *
     * @param text
     *            待加密的文本
     * @param compare
     *            与加密后文本相比较的值
     * @return 是否相等
     */
    public static boolean hasRightSecurityCode(String text, String compare) {

        return getSecurityCodeByText(text).equals(compare);
    }

    /**
     * MD5加密，转换为大写
     */
    public static String md5(String text) {
        String newText = NUMBER_CHAR + text + SPECIAL_CHAR;
        return DigestUtils.md5Hex(newText).toUpperCase();
    }

}
