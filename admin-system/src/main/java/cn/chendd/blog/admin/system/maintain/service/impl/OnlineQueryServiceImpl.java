package cn.chendd.blog.admin.system.maintain.service.impl;

import cn.chendd.blog.admin.system.maintain.mapper.OnlineQueryMapper;
import cn.chendd.blog.admin.system.maintain.service.OnlineQueryService;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.toolkit.operationlog.annotions.Log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL在线查询接口实现
 *
 * @author chendd
 * @date 2022/2/24 10:10
 */
@Service
@Slf4j
public class OnlineQueryServiceImpl implements OnlineQueryService {

    /**
     * 不允许出现的关键字
     */
    private static final String[] EXCLUDES = {
        "\\s*(delete)\\s+" , "\\s*(update)\\s+" , "\\s*(insert)\\s+" , "\\s*(create)\\s+" , "\\*(drop)\\s+" , "\\s*(truncate)\\s+"
    };
    /**
     * 必须出现的关键字
     */
    private static final String[] INCLUDES = {
        "\\s*(select)\\s+" , "\\s*(from)\\s+"
    };

    @Resource
    private OnlineQueryMapper onlineQueryMapper;

    @Override
    @Transactional(readOnly = true)
    ///@Log(description = "sql在线查询") 由于开启了事务只读，故无法记录操作日志，使用Log记录
    public Page<Map<String, Object>> executeQuery(String sql) {
        log.info("execute sql: {}" , sql);
        this.validatorSql(sql);
        Page<Map<String, Object>> page = new Page<>(1 , 50);
        return onlineQueryMapper.queryForList(sql , page);
    }

    /**
     * 验证SQL语句的安全性
     * @param sql sql语句
     */
    private void validatorSql(String sql) {
        for (String exclude : EXCLUDES) {
            Matcher match = Pattern.compile(exclude , Pattern.CASE_INSENSITIVE).matcher(sql);
            if (match.find()) {
                throw new ValidateDataException(String.format("不允许出现 %s 关键字" , match.group(1)));
            }
        }
        for (String include : INCLUDES) {
            Matcher match = Pattern.compile(include , Pattern.CASE_INSENSITIVE).matcher(sql);
            if (! match.find()) {
                throw new ValidateDataException(String.format("必须出现 %s 关键字" , StringUtils.substringBetween(include , "(" , ")")));
            }
        }
    }

}
