package cn.chendd.core.enums;

import cn.chendd.core.utils.EnumUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.baomidou.mybatisplus.core.enums.IEnum;

import java.lang.reflect.Type;

/**
 * fastjson的枚举反序列化，json的value值为枚举类中的实例
 * <pre>
 * 1.枚举类必须实现IEnum接口；
 * 2.枚举类的序列化方式使用fastjson默认方式：以枚举实例名称为值；
 * 3.枚举类的序列化方式使用Jackson注解@JsonFormat(shape = JsonFormat.Shape.OBJECT)的方式；
 * 4.配合@JSONField(deserializeUsing = EnumFastjsonDeserializer.class)使用；
 * </pre>
 *
 * @author chendd
 * @date 2021/2/13 0:29
 */
@SuppressWarnings("ALL")
public class EnumFastjsonDeserializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object o) {
        if (IEnum.class.isAssignableFrom((Class) type)) {
            Object object = parser.parse();
            if (object instanceof String) {
                Enum instance = EnumUtil.getInstanceByName(object.toString(), (Class) type);
                return (T) instance;
            } else if (object instanceof JSONObject) {
                JSONObject json = (JSONObject) object;
                if (json.containsKey("value")) {
                    Enum instance = EnumUtil.getInstanceByName(json.getString("value"), (Class) type);
                    return (T) instance;
                }
            }

        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.UNDEFINED;
    }
}
