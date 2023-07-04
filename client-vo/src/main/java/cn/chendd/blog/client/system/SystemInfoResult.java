package cn.chendd.blog.client.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 系统信息参数对象
 *
 * @author chendd
 * @date 2020/8/17 18:39
 */
@Data
public class SystemInfoResult {

    @ApiModelProperty("jvm厂商")
    private String jvmVendor;
    @ApiModelProperty("jvm名称")
    private String jvmName;
    @ApiModelProperty("jvm版本")
    private String jvmVersion;
    @ApiModelProperty("操作系统名称")
    private String osName;
    @ApiModelProperty("操作系统位数")
    private String osArch;
    @ApiModelProperty("操作系统用户")
    private String userName;
    @ApiModelProperty("操作系统语言")
    private String userLanguage;
    @ApiModelProperty("操作系统城市")
    private String userCountry;
    @ApiModelProperty("操作系统时区")
    private String userTimezone;
    @ApiModelProperty("进程ID")
    private String pid;
    @ApiModelProperty("系统编码")
    private String fileEncoding;
    @ApiModelProperty("user dir")
    private String userDir;
    @ApiModelProperty("java home")
    private String javaHome;
    @ApiModelProperty("catalina home")
    private String catalinaHome;
    @ApiModelProperty("catalina base")
    private String catalinaBase;

    @ApiModelProperty("计算机名")
    private String hostName;
    @ApiModelProperty("制造商")
    private String manufacturer;
    @ApiModelProperty("操作系统")
    private String family;
    @ApiModelProperty("版本信息")
    private String versionInfoVersion;
    @ApiModelProperty("版本代码")
    private String versionInfoCodeName;
    @ApiModelProperty("位数")
    private Integer bitness;
    @ApiModelProperty("处理器名称")
    private String processorName;
    @ApiModelProperty("总内存")
    private Long memoryTotal;
    @ApiModelProperty("可用内存")
    private Long memoryAvailable;
    @ApiModelProperty("开机时间")
    private String systemBootTime;

    @ApiModelProperty("固件发布日期")
    private String firmwareReleaseDate;
    @ApiModelProperty("管理员权限")
    private Boolean elevated;
    @ApiModelProperty("处理器个数")
    private Integer logicalProcessorCount;
    @ApiModelProperty("磁盘个数")
    private Integer fileStoresCount;
    @ApiModelProperty("总进程数")
    private Integer processCount;
    @ApiModelProperty("进程ID")
    private Integer processId;
    @ApiModelProperty("线程总数量")
    private Integer threadCount;
    @ApiModelProperty("网卡数量")
    private Integer networkIFs;
    @ApiModelProperty("处理器标识符")
    private String processorIdentifier;
    @ApiModelProperty("显示器个数")
    private Integer displayCount;
    @ApiModelProperty("声卡数量")
    private Integer soundCardsCount;
    @ApiModelProperty("服务个数")
    private Integer servicesCount;

    @ApiModelProperty("磁盘信息")
    private List<FileStore> fileStores;

    public SystemInfoResult() {

    }

    /**
     * 磁盘参数对象
     */
    public static class FileStore {

        private String name;

        private String volume;

        private String label;

        private String logicalVolume;

        private String mount;

        private String description;

        private String type;

        private String options;

        private long freeSpace;

        private long usableSpace;

        private long totalSpace;

        private long freeInodes;

        private long totalInodes;

        private boolean updateAttributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLogicalVolume() {
            return logicalVolume;
        }

        public void setLogicalVolume(String logicalVolume) {
            this.logicalVolume = logicalVolume;
        }

        public String getMount() {
            return mount;
        }

        public void setMount(String mount) {
            this.mount = mount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }

        public long getFreeSpace() {
            return freeSpace;
        }

        public void setFreeSpace(long freeSpace) {
            this.freeSpace = freeSpace;
        }

        public long getUsableSpace() {
            return usableSpace;
        }

        public void setUsableSpace(long usableSpace) {
            this.usableSpace = usableSpace;
        }

        public long getTotalSpace() {
            return totalSpace;
        }

        public void setTotalSpace(long totalSpace) {
            this.totalSpace = totalSpace;
        }

        public long getFreeInodes() {
            return freeInodes;
        }

        public void setFreeInodes(long freeInodes) {
            this.freeInodes = freeInodes;
        }

        public long getTotalInodes() {
            return totalInodes;
        }

        public void setTotalInodes(long totalInodes) {
            this.totalInodes = totalInodes;
        }

        public boolean isUpdateAttributes() {
            return updateAttributes;
        }

        public void setUpdateAttributes(boolean updateAttributes) {
            this.updateAttributes = updateAttributes;
        }
    }

}
