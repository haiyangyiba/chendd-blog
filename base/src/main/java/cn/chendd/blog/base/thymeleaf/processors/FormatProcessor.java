package cn.chendd.blog.base.thymeleaf.processors;

import cn.chendd.blog.base.thymeleaf.enums.EnumFormatType;
import cn.chendd.blog.base.thymeleaf.format.IFormatType;
import cn.chendd.blog.base.thymeleaf.format.impl.UndefinedFormat;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * 格式化处理类
 *
 * @author chendd
 * @date 2020/5/31 21:56
 */
public class FormatProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "format";//标签名
    private static final int PRECEDENCE = 100000;//优先级

    public FormatProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // 此处理器将仅应用于HTML模式
                dialectPrefix,     // 要应用于名称的匹配前缀
                TAG_NAME,          // 标签名称：匹配此名称的特定标签
                true,              // 将标签前缀应用于标签名称
                null,              // 无属性名称：将通过标签名称匹配
                false,             // 没有要应用于属性名称的前缀
                PRECEDENCE);       // 优先(内部方言自己的优先)
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structure) {
        String value = tag.getAttributeValue("value");
        String type = tag.getAttributeValue("type");
        EnumFormatType formatType = EnumFormatType.getInstance(type);
        String result = null;
        if (formatType == null || StringUtils.isEmpty(value)) {
            result = new UndefinedFormat().format(value);
        } else {
            Class<? extends IFormatType> formatTypeClazz = formatType.getClazz();
            try {
                result = ((IFormatType) formatTypeClazz.newInstance()).format(value);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new UnsupportedOperationException(e);
            }
        }
        //替换原有标签的内容体
        //structure.setBody(result , false);
        //删除原有标签
        //structure.removeTags();
        structure.replaceWith(result , false);
    }
}
