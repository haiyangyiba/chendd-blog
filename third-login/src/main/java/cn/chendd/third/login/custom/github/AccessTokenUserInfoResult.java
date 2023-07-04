package cn.chendd.third.login.custom.github;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据授权码获取授权用户信息
 *
 * @author chendd
 * @date 2020/1/1 20:08
 */
@Data
@NoArgsConstructor
public class AccessTokenUserInfoResult {

    private Long id;//用户id
    private String login;//登录名称
    private String name;//昵称
    private String avatarUrl;//头像地址
    private String url;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starred_url;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String repos_url;
    private String events_url;
    private String receivedEventsUrl;
    private String type;
    private String siteAdmin;
    private String blog;
    private String weibo;
    private String bio;
    private Integer publicRepos;
    private Integer publicGists;
    private Integer followers;
    private Integer following;
    private Integer stared;
    private Integer watched;
    private String createdAt;
    private String updatedAt;
    private String email;

    public AccessTokenUserInfoResult(Long id , String name , String avatarUrl){
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

}
