package cn.chendd.blog.admin.blog.article.vo;

import cn.chendd.blog.base.enums.EnumUserSource;
import cn.chendd.core.utils.EnumUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 邮件提示结果集
 * @author chendd
 * @date 2022/4/11 22:06
 */
@Data
public class RemindPrausesAndCommentResult {

    /**
     * 文章Id
     */
    private Long articleId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 用户Id
     */
    private Long createUserId;
    /**
     * 用户名称
     */
    private String createUserName;
    /**
     * 用户来源
     */
    private String userSource;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 获取用户来源
     * @return 用户来源
     */
    public String getUserSource() {
        EnumUserSource userSource = EnumUtil.getInstanceByName(this.userSource, EnumUserSource.class);
        if (userSource == null) {
            return StringUtils.EMPTY;
        }
        return userSource.getText();
    }

}
