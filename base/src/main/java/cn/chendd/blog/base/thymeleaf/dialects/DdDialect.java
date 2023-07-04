package cn.chendd.blog.base.thymeleaf.dialects;

import cn.chendd.blog.base.thymeleaf.processors.FormatAttributeTagProcessor;
import cn.chendd.blog.base.thymeleaf.processors.FormatProcessor;
import cn.chendd.blog.base.thymeleaf.processors.ThemeAttributeTagProcessor;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Set;

/**
 * 自定义标签方言类
 *
 * @author chendd
 * @date 2020/5/31 21:51
 */
@Component
public class DdDialect extends AbstractProcessorDialect {

    /**
     * 定义方言名称
     */
    private static final String DIALECT_NAME = "Dd Dialect";
    private static final String PREFIX = "dd";

    protected DdDialect() {
        super(DIALECT_NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = Sets.newHashSet();
        processors.add(new FormatProcessor(dialectPrefix));
        processors.add(new FormatAttributeTagProcessor(TemplateMode.HTML , dialectPrefix));
        processors.add(new ThemeAttributeTagProcessor(TemplateMode.HTML , dialectPrefix));
        return processors;
    }
}
