package cn.chendd.core.common.convert;

import cn.chendd.core.common.constant.Constant;

/**
 * 名称分割转换
 *
 * @author chendd
 * @date 2019/9/13 21:47
 */
public class NamePartitionConvert {

    private NamePartitionConvert(){}

    /**
     * 按大写字符将字段命名转换为以特定分隔符，
     *     如将 serverPort转换为 server.port，未考虑收尾字母的大小写，需要遵循规范
     * @param fieldName 字段名称
     * @param partition 分割符号
     * @return 转换后的字符串
     */
    public static String getConvertNameByField(String fieldName , String partition){
        int lens = fieldName.length();
        StringBuilder builder = new StringBuilder();
        for (int i=0 ; i < lens ; i++){
            Character c = fieldName.charAt(i);
            boolean upperCase = Character.isUpperCase(c);
            if(upperCase){
                builder.append(partition).append(c.toString().toLowerCase());
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 按大写字符将字段命名转换为以特定分隔符，
     *     如将 serverPort转换为 server.port，未考虑收尾字母的大小写，需要遵循规范
     * @param fieldName 字段名称
     * @return 转换后的字符串
     */
    public static String getConvertNameByField(String fieldName){
        return getConvertNameByField(fieldName , Constant.FIELD_PARTITION_CONVERT);
    }

}
