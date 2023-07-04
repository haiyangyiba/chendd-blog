package cn.chendd.blog.base.filters.wrapper;

import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 参数处理vo对象
 *
 * @author chendd
 * @date 2020/8/7 22:40
 */
@Data
public class ParamRepairResult {

    /**
     * 特殊字符
     */
    private List<SysDbValue> specialList;

    /**
     * 排除特殊字符过滤的属性名称
     */
    private List<String> excludeSpecialList = Lists.newArrayList("editorContent");

    /**
     * 特殊字符
     */
    private List<SysDbValue> sensitiveList;
    /**
     * 排除敏感字符过滤的属性名称
     */
    private List<SysDbValue> excludeSensitiveList = Lists.newArrayList();

}
