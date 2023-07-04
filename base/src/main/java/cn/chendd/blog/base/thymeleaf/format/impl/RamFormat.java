package cn.chendd.blog.base.thymeleaf.format.impl;

import cn.chendd.blog.base.thymeleaf.format.IFormatType;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DecimalFormat;

/**
 * 内存容量大小格式化
 *
 * @author chendd
 * @date 2020/5/31 23:11
 */
public class RamFormat implements IFormatType {

    public interface IRam {

        default String execute(Double value , Double max , String formatName){
            DecimalFormat decimalFormat = new DecimalFormat(formatName);
            return decimalFormat.format(value / max * Ram.K.getMax());
        }

    }

    @Getter
    public enum Ram implements IRam {

        K(1024 * 1.0 , 1 , "0字节"),
        KB(K.getMax() * 1024 , 2 , "0.00KB"),
        MB(KB.getMax() * 1024 , 3 , "0.00MB"),
        GB(MB.getMax() * 1024 , 4 , "0.00GB"),
        ;

        private Double max;
        private Integer sort;
        private String formatName;

        Ram(Double max , Integer sort , String formatName) {
            this.max = max;
            this.sort = sort;
            this.formatName = formatName;
        }

        /**
         * 按从小到大的范围格式化
         * @param value 值大小
         * @return 格式化后的容量大小
         */
        public static String getInstance(Double value) {
            Ram rams[] = Ram.values();
            for (int i=1 ; i <= rams.length ; i++){
                Ram ram = rams[i - 1];
                if (ram.getSort() == i){
                    if (value < ram.getMax()) {
                        return ram.execute(value , ram.getMax() , ram.getFormatName());
                    }
                }
            }
            return Ram.GB.execute(value , Ram.GB.getMax() , Ram.GB.getFormatName());
        }

    }

    @Override
    public String format(String value) {
        Double doubleValue = NumberUtils.createDouble(value);
        return Ram.getInstance(doubleValue);
    }
}
