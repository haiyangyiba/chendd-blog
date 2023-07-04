package cn.chendd.core.enums;

import cn.chendd.core.utils.EnumUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Jackson反序列化实现
 *
 * @author chendd
 * @date 2021/2/20 10:39
 */
/**
 * Jackson的枚举反序列化，json的value值为枚举类中的实例名称
 * <pre>
 * 1.枚举类必须实现IEnum接口；
 * 2.枚举类的序列化方式使用Jackson默认方式：以枚举实例名称为值；
 * 3.枚举类的序列化方式使用Jackson注解@JsonFormat(shape = JsonFormat.Shape.OBJECT)的方式；
 * 4.配合@JsonDeserialize(using = EnumJacksonDeserializer.class)使用（配合JsonFormat是为Swagger指定json格式的枚举参数只为String类型@JsonFormat(shape = JsonFormat.Shape.STRING)）；
 * </pre>
 *
 * @author chendd
 * @date 2021/2/13 0:29
 */
public class EnumJacksonDeserializer extends JsonDeserializer<Enum> {

    @SuppressWarnings("unchecked")
    @Override
    public Enum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object value = jsonParser.getCurrentValue();
        if (value != null) {
            String name = jsonParser.getText();
            String fieldName = jsonParser.getCurrentName();
            Field field = FieldUtils.getField(value.getClass(), fieldName, true);
            Class<? extends Enum> enumClass = (Class<? extends Enum>) field.getType();
            return EnumUtil.getInstanceByName(name, enumClass);
        }
        return null;
    }
}
