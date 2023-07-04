package cn.chendd.blog.admin.system.shortcut.config;

import cn.chendd.toolkit.dbproperty.annotions.DbValue;
import cn.chendd.toolkit.dbproperty.annotions.DbValueConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 快捷菜单参数管理
 *
 * @author chendd
 * @date 2020/6/20 19:09
 */
@DbValueConfiguration
@Component
@Data
public class ShortcutMenuParamComponent {

    /**
     * 快捷菜单列表数据
     */
    @DbValue(group = "shortcut.menu")
    private Map<String , String> shortcutMenus;

}
