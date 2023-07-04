package cn.chendd.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 图片类型枚举后缀
 *
 * @author chendd
 * @date 2020/6/20 19:41
 */
public enum EnumImageType {

    jpg(".jpg"),
    png(".png"),
    gif(".gif"),
    bmp(".bmp"),

    ;

    private String suffix;

    EnumImageType(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 根据文件名称判定是否为图片
     * @param fileName 文件名称
     * @return true ？ 是支持的图片 ： 不支持的图片
     */
    public static boolean isImage(String fileName) {
        EnumImageType imageTypes[] = EnumImageType.values();
        for (EnumImageType imageType : imageTypes) {
            if(StringUtils.endsWithIgnoreCase(fileName , imageType.getSuffix())) {
                return true;
            }
        }
        return false;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
