package cn.chendd.blog.base.filters.wrapper;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 系统参数request包装
 *
 * @author chendd
 * @date 2020/7/16 22:16
 */
@Slf4j
@SuppressWarnings("all")
public class ParameterHttpParamRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 参数处理类
     */
    private ParamRepairResult paramRepair;

    public ParameterHttpParamRequestWrapper(HttpServletRequest request , ParamRepairResult paramRepair) throws IOException {
        super(request);
        this.paramRepair = paramRepair;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if(StringUtils.isNotEmpty(value)) {
            value = this.resetDataList(name , value , paramRepair);
        }
        log.debug("param getParameter-->参数名 name = {} , 替换后的参数值 value = {}" , name , JSONObject.toJSONString(value));
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String values[] = super.getParameterValues(name);
        if(values != null) {
            for (int i = 0; i < values.length; i++) {
                if(StringUtils.isNotBlank(values[i])) {
                    values[i] = this.resetDataList(name , values[i] , paramRepair);
                }
            }
        }
        log.debug("param getParameterValues-->参数名 name = {} , 替换后的参数值 value = {}" , name , JSONObject.toJSONString(values));
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String , String[]> map = super.getParameterMap();
        Set<Map.Entry<String, String[]>> entrySet = map.entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String values[] = entry.getValue();
            if(ArrayUtils.isEmpty(values)) {
                continue;
            }
            String name = entry.getKey();
            for (int i = 0; i < values.length; i++) {
                values[i] = this.resetDataList(name , values[i] , paramRepair);
            }
        }
        log.debug("param getParameterMap 参数值被重载");
        return map;
    }

    /**
     * 重置参数，若参数名在范围内则不进行重置
     * @param name request获取参数名
     * @param value request获取参数值
     * @param paramRepair 参数替换对象
     * @return 替换后的新参数
     */
    private String resetDataList(String name , String value, ParamRepairResult paramRepair) {

        boolean release = paramRepair.getExcludeSpecialList().contains(name);
        if (release) {
            return value;
        }

        //替换特殊字符
        for (SysDbValue dbValue : paramRepair.getSpecialList()) {
            //该参数不在排除替换的范围内
            if (!paramRepair.getExcludeSpecialList().contains(dbValue.getKey())) {
                value = StringUtils.replace(value , dbValue.getKey() , dbValue.getValue());
            }
        }
        //替换敏感字符
        for (SysDbValue dbValue : paramRepair.getSensitiveList()) {
            //该参数不在排除替换的范围内
            if (!paramRepair.getExcludeSensitiveList().contains(dbValue.getKey())) {
                value = StringUtils.replace(value , dbValue.getKey() , dbValue.getValue());
            }
        }
        return value;
    }

}
