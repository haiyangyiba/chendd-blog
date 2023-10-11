package cn.chendd.blog.admin.blog.article.vo;

import cn.chendd.blog.base.enums.EnumUserSource;
import lombok.Data;

/**
 * 邮件提醒至被回复的用户
 *
 * @author chendd
 * @date 2022/4/21 22:15
 */
@Data
public class RemindUserCommentResult {

    private String targetId;
    private String title;
    private String url;
    private String ascription;
    private Long replyUserId;
    private String userSource;
    private String realName;
    private String email;

    public String getUserSource() {
        if (this.userSource != null) {
            return EnumUserSource.valueOf(this.userSource).getText();
        }
        return null;
    }
}
