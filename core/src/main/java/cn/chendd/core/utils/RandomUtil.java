package cn.chendd.core.utils;

import java.util.Random;

/**
 * 随机函数工具类
 *
 * @author chendd
 * @date 2020/10/28 21:51
 */
public class RandomUtil {

    /**
     * 随机获取颜色
     * @return
     */
    public static String getRandomColor() {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        Random random = new Random();
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();
        red = red.length() == 1 ? "0" + red : red;
        green = green.length() == 1 ? "0" + green : green;
        blue = blue.length() == 1 ? "0" + blue : blue;
        return "#" + red + green + blue;
    }

    /**
     * 随即返回一个字体大小
     * @param min 最小号
     * @param step + min = 最大号
     * @return
     */
    public static String getRandomRange(int min , int step , String prefix) {
        int result = min + new Random().nextInt(step + 1);
        return result + prefix;
    }

    /**
     * @return 提供默认范围内的随机数字大小
     */
    public static String getRandomFontSize() {
        return getRandomRange(14 , 8 , "px");
    }

}
