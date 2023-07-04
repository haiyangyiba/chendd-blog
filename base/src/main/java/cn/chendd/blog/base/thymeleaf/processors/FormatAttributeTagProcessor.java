package cn.chendd.blog.base.thymeleaf.processors;

import cn.chendd.blog.base.thymeleaf.format.impl.RamFormat;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.processor.AbstractStandardExpressionAttributeTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author chendd
 * @date 2020/6/1 20:09
 */
public class FormatAttributeTagProcessor extends AbstractStandardExpressionAttributeTagProcessor {

    public static final int PRECEDENCE = 1300;
    public static final String ATTR_NAME = "raw-format";

    public FormatAttributeTagProcessor(final TemplateMode templateMode, final String dialectPrefix) {
        super(templateMode, dialectPrefix, ATTR_NAME, PRECEDENCE, true, (templateMode == TemplateMode.TEXT));
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, Object expressionResult, IElementTagStructureHandler structureHandler) {
        System.out.println("----" + attributeName + "---" + attributeValue + "---" + expressionResult);
        if(expressionResult == null) {
            structureHandler.removeTags();
            return;
        }
        String result = RamFormat.Ram.getInstance(Double.valueOf(expressionResult.toString()));
        structureHandler.replaceWith(result , false);
    }
}
