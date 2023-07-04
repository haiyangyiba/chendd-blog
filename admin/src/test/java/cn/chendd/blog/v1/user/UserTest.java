package cn.chendd.blog.v1.user;

import cn.chendd.BaseBootstrapTest;
import cn.chendd.core.spring.SpringBeanFactory;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 博客V1版本的用户Test
 *
 * @author chendd
 * @date 2022/2/10 21:52
 */
public class UserTest extends BaseBootstrapTest {

    String userTemplate = "" +
            "                                                        <div class=\"col-lg-3\">\n" +
            "                                                            <div class=\"pt-2 pb-2 text-center\">\n" +
            "                                                                <img src=\"${portrait}\" class=\"rounded-circle img-thumbnail avatar-xl\" alt=\"profile-image\">\n" +
            "                                                                <h4 class=\"mt-3\"><a class=\"text-dark\">${username}</a></h4>\n" +
            "                                                                <div class=\"text-left mt-3\">\n" +
            "                                                                    <p class=\"text-muted mb-2 font-13\">\n" +
            "                                                                        <strong>Email：</strong><span class=\"ml-2\">${email}</span>\n" +
            "                                                                    </p>\n" +
            "                                                                    <p class=\"text-muted mb-2 font-13\"><strong>创建时间：</strong><span class=\"ml-2\">${createTime}</span></p>\n" +
            "                                                                    <p class=\"text-muted mb-2 font-13\"><strong>最后登录时间：</strong><span class=\"ml-2 \">${lastLoginTime}</span></p>\n" +
            "                                                                </div>\n" +
            "                                                            </div>\n" +
            "                                                        </div>";

    @Test
    public void queryThirdUserList() {
        String sql =
            "select " +
                " a.username, a.portrait, a.email , a.createTime , a.lastLoginTime , a.source " +
                " from struts.sysuser_third a " +
            "order by a.source , a.createTime";
        int number = 1;
        DruidDataSource dataSource = SpringBeanFactory.getBean(DruidDataSource.class);
        try (DruidPooledConnection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String username = rs.getString("username");
                String portrait = rs.getString("portrait");
                String email = rs.getString("email");
                if (StringUtils.isEmpty(email)) {
                    email = StringUtils.EMPTY;
                } else {
                    int index = email.indexOf("@");
                    if (index != -1) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(StringUtils.substring(email , 0 , index - 3));
                        builder.append("***@").append(StringUtils.substringAfter(email , "@"));
                        email = builder.toString();
                    }

                }
                String createTime = rs.getString("createTime");
                String lastLoginTime = rs.getString("lastLoginTime");
                if (StringUtils.isEmpty(lastLoginTime)) {
                    lastLoginTime = StringUtils.EMPTY;
                }
                String source = rs.getString("source");
                switch (source) {
                    case "2" :
                        source = "百度";
                        break;
                    case "3" :
                        source = "腾讯QQ";
                        break;
                    case "4" :
                        source = "支付宝";
                        break;
                    case "5" :
                        source = "新浪微博";
                        number++;
                        break;
                }
                if ("新浪微博".equals(source)) {
                    String data = userTemplate
                            .replace("${portrait}" , portrait)
                            .replace("${username}" , username)
                            .replace("${email}" , email)
                            .replace("${createTime}" , createTime)
                            .replace("${lastLoginTime}" , lastLoginTime);
                    System.out.println(data);
                }
            }
            System.out.println("总个数：" + number);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
