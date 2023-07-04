package cn.chendd.blog.base.thymeleaf.processors;

import cn.chendd.blog.base.enums.EnumAdminTheme;
import cn.chendd.core.utils.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.processor.AbstractStandardExpressionAttributeTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Objects;

/**
 * 系统主题自定义属性处理类
 * <strong>
 * 约定需要替换的主题规则为：查找以最后出现的 “.min.”文本，在此文本前追加 “-theme”
 * </strong>
 * @author chendd
 * @date 2020/8/22 22:12
 */
public class ThemeAttributeTagProcessor extends AbstractStandardExpressionAttributeTagProcessor {

    public static final int PRECEDENCE = 1300;
    public static final String ATTR_NAME = "theme";

    public ThemeAttributeTagProcessor(final TemplateMode templateMode, final String dialectPrefix) {
        super(templateMode, dialectPrefix, ATTR_NAME, PRECEDENCE, true, (templateMode == TemplateMode.TEXT));
    }
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName
            attributeName, String attributeValue, Object expressionResult, IElementTagStructureHandler structureHandler) {
        if(expressionResult == null) {
            //直接return将忽略当前属性
            return;
        }
        if( StringUtils.isEmpty(attributeValue) || Objects.isNull(expressionResult)) {
            return;
        }
        EnumAdminTheme adminTheme = EnumUtil.getInstanceByCode(expressionResult.toString(), EnumAdminTheme.class);
        //判断是否取得主题
        if (adminTheme == null || EnumAdminTheme.light.equals(adminTheme)) {
            return;
        }
        String href = tag.getAttributeValue("href");
        StringBuilder builder = new StringBuilder(href);
        int index = StringUtils.lastIndexOf(href , ".min.");
        if(index != -1) {
            builder.insert(index , "-" + expressionResult);
        }
        structureHandler.replaceAttribute(attributeName , "href" , builder.toString());
    }

}
