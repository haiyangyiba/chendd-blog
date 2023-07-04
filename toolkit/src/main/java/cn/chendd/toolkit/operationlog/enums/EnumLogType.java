package cn.chendd.toolkit.operationlog.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 定义日志记录分类枚举，大功能模块
 *
 * @author chendd
 * @date 2019/9/20 16:29
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumLogType implements IEnum<String> {

    DEFAULT("默认"),//默认，没有什么意义
    LOGIN("登录"),
    SYSTEM("系统管理"),
    BLOG("博客管理"),
    ;

    private String text;

    EnumLogType(String text){
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
