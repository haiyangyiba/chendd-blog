package cn.chendd.toolkit.quartz.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 定时任务状态类型
 *
 * @author chendd
 * @date 2020/7/4 23:37
 */
@Getter
public enum EnumTriggerState implements IEnum<String> {

    NORMAL("正常"),
    WAITING("等待执行"),
    ACQUIRED("正常执行"),
    COMPLETE("完成"),
    PAUSED("已暂停"),
    BLOCKED("阻塞"),
    PAUSED_BLOCKED("暂停阻塞"),
    ERROR("错误")
    ;

    private String text;

    EnumTriggerState(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
