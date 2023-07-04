package cn.chendd.blog.base.filters.wrapper;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class BodyHttpParamRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 参数处理类
     */
    private ParamRepairResult paramRepair;
    /**
     * 内容体
     */
    private final String bodyContent;

    public BodyHttpParamRequestWrapper(HttpServletRequest request , ParamRepairResult paramRepair) throws IOException {
        super(request);
        this.paramRepair = paramRepair;
        this.bodyContent = this.resetContent(request);
    }

    private String resetContent(HttpServletRequest request) throws IOException {
        String content = IOUtils.toString(request.getInputStream() , Charsets.UTF_8);
        //当前传递过来的请求类型包含：application/json即认为为json格式请求，比如：applicaiton/json;charset=UTF-8等
        if(StringUtils.isNotEmpty(content) && StringUtils.containsIgnoreCase(this.getContentType() , MediaType.APPLICATION_JSON_VALUE)) {
            //如果传递过来的是"hello"的字符串，即 String hello = "\"hello\"";时的特殊处理逻辑
            //可以修改为使用JSONValidator.Type.Value.equals(type)
            if (StringUtils.startsWith(content , "\"") && StringUtils.endsWith(content , "\"")) {
                log.debug("***json文本格式化---");
                JSONObject json = new JSONObject();
                json.put("data" , StringUtils.substring(content , 1 , content.length() - 1));
                String jsonContent = JSONObject.toJSONString(json, new ParamValueFilter(paramRepair));
                JSONObject result = JSON.parseObject(jsonContent);
                String value = "\"" + result.getString("data") + "\"";
                return value;
            }
            log.debug("***json格式化前---{}" , content);
            JSONValidator.Type type = JSONValidator.from(content).getType();
            if (JSONValidator.Type.Object.equals(type)) {
                content = JSON.toJSONString(JSON.parseObject(content) , new ParamValueFilter(paramRepair));
            } else if (JSONValidator.Type.Array.equals(type)) {
                content = JSON.toJSONString(JSON.parseArray(content) , new ParamValueFilter(paramRepair));
            }
            log.debug("***json格式化后---{}" , content);
            return content;
        }
        return content;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if(StringUtils.isNotEmpty(value)) {
            value = this.resetDataList(name , value , paramRepair);
        }
        log.debug("getParameter-->参数名 name = {} , 替换后的参数值 value = {}" , name , JSONObject.toJSONString(value));
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
        log.debug("getParameterValues-->参数名 name = {} , 替换后的参数值 value = {}" , name , JSONObject.toJSONString(values));
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
        log.debug("getParameterMap 参数值被重载");
        return map;
    }

    @Override
    public ServletInputStream getInputStream() {
        log.debug("getInputStream 参数值被重载");
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyContent.getBytes(Charsets.UTF_8));
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * @return bodyContent
     */
    public String getBodyContent() {
        return bodyContent;
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
