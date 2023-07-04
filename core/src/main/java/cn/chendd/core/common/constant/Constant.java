package cn.chendd.core.common.constant;

/**
 * 常量类属性定义
 *
 * @author chendd
 * @date 2019/9/13 22:06
 */
public interface Constant {

    /**
     * 字段分隔符转换
     */
    String FIELD_PARTITION_CONVERT = ".";

    /**
     * 日期格式 yyyyMMdd
     */
    String DATE_PATTERN = "yyyyMMdd";

    /**
     * 日期时间格式 yyyyMMdd HH:mm:ss
     */
    String DATE_TIME_PATTERN = "yyyyMMdd HH:mm:ss";

    /**
     * 年月日时分，yyyy-MM-dd HH:mm
     */
    String DATE_HH_MM_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式化 yyyy-MM-dd HH:mm:ss
     */
    String DATE_TIME_FORMAT_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式化 HH:mm:ss
     */
    String TIME_PATTERN = "HH:mm:ss";

    /**
     * 开始时间
     */
    String TIME_BEGIN = "00:00:00";

    /**
     * 结束时间
     */
    String TIME_END = "23:59:59";

    /**
     * 系统中存储在session中的验证码
     */
    String LOGIN_VALIDATE_KEY = "systemValidateCode";


    /**
     * MD5 盐值加密前置
     */
    String NUMBER_CHAR = "01234567890";

    /**
     * MD5 盐值加密后置
     */
    String SPECIAL_CHAR = "~!@#$%^&*()";

    /**
     * 系统Session中当前用户
     */
    String SYSTEM_CURRENT_USER = "systemCurrentUser";

    /**
     * 系统默认角色key
     */
    String SYSTEM_DEFAULT_ROLE_KEY = "user";

    /**
     * 菜单管理根节点ID
     */
    String MENU_ROOT_ID = "-1";


    String JAVA_ENTER_CHAR = "\r\n";

    String HTML_ENTER_CHAR = "<br/>";

    /**
     * 用户默认头像地址
     */
    String DEFAULT_USER_IAMGE = "/images/user.png";

    /**
     * 默认每页显示大小
     */
    Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认最大允许每页显示大小
     */
    Integer DEFAULT_MAX_PAGE_SIZE = DEFAULT_PAGE_SIZE * 100;

    /**
     * 正序排序
     */
    String ORDER_ASC = "asc";

    /**
     * 倒序排序
     */
    String ORDER_DESC = "desc";
}
