package cn.chendd.third.login.custom.github;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * Github获取Token结果集
 *
 * @author chendd
 * @date 2020/1/5 0:06
 */
@Getter
@Setter
@Slf4j
public class AccessTokenResult {

    private String accessToken;

    private String tokenType;

    public AccessTokenResult(String result) {
        String groups[] = result.split("&");
        for (String group : groups) {
            String props[] = group.split("=");
            if(props.length == 1){
                props = new String[]{props[0] , ""};
            }
            String key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL , props[0]);
            String value = props[1];
            try {
                Field field = FieldUtils.getField(this.getClass() , key , true);
                if(field != null){
                    field.set(this , value);
                }
            } catch (IllegalAccessException e) {
                log.error("设置字段 {} 出现错误：" , key , e);
            }
        }
    }

}
