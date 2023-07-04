package cn.chendd.toolkit;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import oshi.SystemInfo;
import oshi.util.FormatUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

/**
 * OShi测试类
 *
 * @author chendd
 * @date 2019/9/25 23:27
 */
public class OShiTest {

    @Test
    public void testSystem(){

        System.out.println(SystemInfo.getCurrentPlatformEnum());

        SystemInfo systemInfo = new SystemInfo();
        System.out.println(JSONObject.toJSONString(systemInfo , true));
    }

    /**
     * 设置Java虚拟机
     */
    @Test
    public void setJvmInfo() throws UnknownHostException {
        Properties props = System.getProperties();
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(props.getProperty("java.version"));
        System.out.println(props.getProperty("java.home"));
    }

    @Test
    public void setSysInfo() {
        Properties props = System.getProperties();
        System.out.println(getHostName());
        System.out.println(getHostIp());
        System.out.println(props.getProperty("os.name"));
        System.out.println(props.getProperty("os.arch"));
        System.out.println(props.getProperty("user.dir"));
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }

    @Test
    public void testOther(){
        System.out.println(FormatUtil.formatElapsedSecs(0));
        System.out.println(FormatUtil.formatElapsedSecs(1));
        System.out.println(FormatUtil.formatElapsedSecs(20));
        System.out.println(FormatUtil.formatElapsedSecs(60));
        System.out.println(FormatUtil.formatElapsedSecs(434118));
        System.out.println(FormatUtil.formatElapsedSecs(1569459418));
        System.out.println(FormatUtil.formatElapsedSecs(129417));
        System.out.println(new Date().getTime());
        System.out.println(FormatUtil.formatBytes(16711680));//格式化文件大小
        System.out.format(" File Descriptors: %d/%d%n", 82193, 16711680);
        System.out.println(Instant.ofEpochSecond(1569459418));
        System.out.println(FormatUtil.formatElapsedSecs(129417));
        System.out.println("Running with" + (false ? "" : "out") + " elevated permissions.");//在没有提升权限的情况下运行。
    }
}
