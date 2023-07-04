package cn.chendd.blog.base.filters.wrapper;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * fastjson系统参数值过滤
 *
 * @author chendd
 * @date 2020/7/17 20:24
 */
public class ParamValueFilter implements ValueFilter {

    private ParamRepairResult paramRepair;

    public ParamValueFilter(ParamRepairResult paramRepair) {
        this.paramRepair = paramRepair;
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if(value instanceof String) {
            String val = (String) value;
            //替换特殊字符
            for (SysDbValue dbValue : paramRepair.getSpecialList()) {
                //该参数不在排除替换的范围内
                if (!paramRepair.getExcludeSpecialList().contains(name)) {
                    //匹配特殊转义字符被替换后的报错，比如：\"被替换后成了\＂，而\＂又无法被正常转义导致的报错
                    val = StringUtils.replace(val , "\\" + dbValue.getKey() , dbValue.getValue());
                    //val = StringUtils.replace(val , dbValue.getKey() , dbValue.getValue());
                }
            }
            //替换敏感字符
            for (SysDbValue dbValue : paramRepair.getSensitiveList()) {
                //该参数不在排除替换的范围内
                if (!paramRepair.getExcludeSensitiveList().contains(name)) {
                    val = StringUtils.replace(val , dbValue.getKey() , dbValue.getValue());
                }
            }
            return val;
        }
        return value;
    }
}
