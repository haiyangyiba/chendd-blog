package cn.chendd.blog.admin.system.info.model;

import cn.chendd.core.utils.Html;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 系统内容信息表
 * @author chendd
 * @date 2020/08/29 11:46
 */
@Data
@ApiModel
@TableName("sys_info_content")
public class SysInfoContent implements Serializable {

    @TableId(value = "id" , type = IdType.ASSIGN_ID)
    @TableField("id")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @TableField("title")
    @ApiModelProperty(value = "标题")
    private String title;

    @TableField("`key`")
    @ApiModelProperty(value = "唯一标识")
    private String key;

    @TableField("sortOrder")
    @ApiModelProperty(value = "排序")
    private String sortOrder;

    @TableField("editorContent")
    @ApiModelProperty(value = "内容信息")
    private String editorContent;

    @TableField("createUser")
    @ApiModelProperty(value = "创建者")
    private String createUser;

    @TableField("createTime")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @TableField("updateUser")
    @ApiModelProperty(value = "（最后一次）修改人")
    private String updateUser;

    @TableField("updateTime")
    @ApiModelProperty(value = "（最后一次）修改时间")
    private String updateTime;

    @JsonIgnore
    @JSONField(serialize = false)
    private transient String editorContentPureText;

    /**
     * 获取html内容的纯文本
     * @return 获取html内容的纯文本
     */
    @ApiModelProperty(value = "获取html内容的纯文本")
    public String getEditorContentPureText() {
        if (this.editorContentPureText == null) {
            this.editorContentPureText = Html.getPureText(this.getEditorContent() , false);
        }
        return editorContentPureText;
    }

    /**
     * @return 根据维护内容获取html转码后的纯文本
     */
    @ApiModelProperty(value = "内容转换为JSONObject")
    public JSONObject getEditorContentJSONObject() {
        boolean json = this.isJsonObject();
        if (json) {
            return JSONObject.parseObject(this.getEditorContentPureText());
        }
        return null;
    }

    /**
     * @return 获取指定内容的jsonArray类型数据
     */
    @ApiModelProperty(value = "内容转换为JSONArray")
    public JSONArray getEditorContentJSONArray() {
        boolean json = this.isJsonArray();
        if (json) {
            return JSONArray.parseArray(this.getEditorContentPureText());
        }
        return null;
    }

    /**
     * 获取key标识中的最后一个下划线分割后的内容（约定为自定义页面的标识）
     * @return  关键字
     */
    public String getCustomPageName() {
        return StringUtils.substringAfterLast(this.key , "_");
    }

    /**
     * 是否为JJsonObject格式
     * @return true ? 是 : 否
     */
    private boolean isJsonObject() {
        return this.isJson("{" , "}");
    }

    /**
     * 是否为JsonArray格式
     * @return true ? 是 : 否
     */
    private boolean isJsonArray() {
        return this.isJson("[" , "]");
    }

    /**
     * 根据第一个字符和最后一个字符判定是否为json格式数据
     * @param firstChar 第一个字符
     * @param endChar 最后一个字符
     * @return true ? json : !json
     */
    private boolean isJson(String firstChar , String endChar) {
        String html = this.getEditorContentPureText();
        int length = StringUtils.length(html);
        if (length < 2) {
            return false;
        }
        String first = StringUtils.substring(html , 0 , 1);
        String end = StringUtils.substring(html , html.length() - 1 , html.length());
        boolean isJson = StringUtils.equals(first , firstChar) && StringUtils.equals(end , endChar);
        return isJson;
    }

}
