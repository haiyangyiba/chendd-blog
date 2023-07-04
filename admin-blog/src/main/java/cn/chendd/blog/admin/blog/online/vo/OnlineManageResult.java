package cn.chendd.blog.admin.blog.online.vo;

import cn.chendd.blog.base.enums.EnumSessionAttribute;
import cn.chendd.core.utils.DateFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.SerializationUtils;

/**
 * @author chendd
 * @date 2020/7/15 21:44
 */
@ApiModel
@Data
public class OnlineManageResult {

    @ApiModelProperty(value = "主键ID")
    private String primaryId;

    @ApiModelProperty(value = "sessionId")
    private String sessionId;

    @ApiModelProperty(value = "创建时间")
    private Long creationTime;

    @ApiModelProperty(value = "最后访问时间")
    private Long lastAccessTime;

    @ApiModelProperty(value = "有效期")
    private Integer maxInactiveInterval;

    @ApiModelProperty(value = "到期时间")
    private Long expiryTime;

    @ApiModelProperty(value = "主要名称")
    private String principalName;

    @ApiModelProperty("当前时间")
    private Long currentTime;

    @ApiModelProperty("session数据")
    private byte[] attributeBytes;

    @ApiModelProperty("属性名称")
    private EnumSessionAttribute attributeName;

    public String getCreationTime() {
        return DateFormat.formatDatetime(creationTime , DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    public String getLastAccessTime() {
        return DateFormat.formatDatetime(lastAccessTime , DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    public String getCurrentTime() {
        return DateFormat.formatDatetime(System.currentTimeMillis() , DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    public String getExpiryTime() {
        return DateFormat.formatDatetime(expiryTime , DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern());
    }

    public EnumSessionAttribute getAttributeName() {
        return this.attributeName == null ? EnumSessionAttribute.none : this.attributeName;
    }

    public String getAttributeValue() {
        byte datas[] = this.getAttributeBytes();
        if(datas == null) {
            return EnumSessionAttribute.none.getStringValue(null);
        }
        Object value = SerializationUtils.deserialize(datas);
        return attributeName.getStringValue(value);
    }

}
