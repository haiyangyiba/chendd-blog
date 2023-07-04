package cn.chendd.core.spring;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * Spel简单封装
 *
 * @author chendd
 * @date 2019/9/20 23:14
 */
public final class SpelStandUtil {

    /**
     * 表达式上下文
     */
    private StandardEvaluationContext context;
    /**
     * 表达式解析器
     */
    private ExpressionParser parser;

    /**
     * 构造方法私有
     */
    private SpelStandUtil() {
        context = new StandardEvaluationContext();
        parser = new SpelExpressionParser();
    }

    /**
     * 静态方法构造实例
     * @return 构造函数
     */
    public static SpelStandUtil newInstance() {
        return new SpelStandUtil();
    }

    public SpelStandUtil set(String name , Object value){
        context.setVariable(name, value);
        return this;
    }

    public SpelStandUtil sets(Map<String , Object> varMap){
        context.setVariables(varMap);
        return this;
    }

    public Object get(String eval){
        return parser.parseExpression(eval).getValue(context);
    }

    public <T> T get(String eval , Class<T> clazz){
        return parser.parseExpression(eval).getValue(context , clazz);
    }

}
