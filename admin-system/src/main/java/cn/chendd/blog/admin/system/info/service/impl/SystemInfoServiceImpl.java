package cn.chendd.blog.admin.system.info.service.impl;

import cn.chendd.blog.admin.system.info.service.SystemInfoService;
import cn.chendd.blog.client.system.SystemInfoResult;
import cn.chendd.core.utils.DateFormat;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 系统信息 Service 实现
 *
 * @author chendd
 * @date 2020/5/30 22:04
 */
@Service
@Slf4j
public class SystemInfoServiceImpl implements SystemInfoService {

    private Properties systemProps = System.getProperties();
    private SystemInfoResult systemInfoResult;
    private Lock lock = new ReentrantLock();

    /**
     * 系统启动时初始化一次
     */
    public SystemInfoServiceImpl() {
        this.setSystemInfo();
    }

    @Override
    public SystemInfoResult getSystemInfoResult() {
        return systemInfoResult;
    }

    @Override
    public void setSystemInfoResult() {
        boolean flag = lock.tryLock();
        if (! flag) {
            return;
        }
        try {
            setSystemInfo();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 设置系统参数信息
     */
    private void setSystemInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        GlobalMemory memory = hardware.getMemory();
        CentralProcessor.ProcessorIdentifier processor = hardware.getProcessor().getProcessorIdentifier();
        OperatingSystem os = systemInfo.getOperatingSystem();
        OperatingSystem.OSVersionInfo versionInfo = os.getVersionInfo();
        List<OSFileStore> osFileStores = os.getFileSystem().getFileStores();
        this.systemInfoResult =  new SystemInfoResult();
        systemInfoResult.setJvmVendor(systemProps.getProperty("java.vm.vendor"));
        systemInfoResult.setJvmName(systemProps.getProperty("java.vm.name"));
        systemInfoResult.setJvmVersion(systemProps.getProperty("java.version"));
        systemInfoResult.setOsName(systemProps.getProperty("os.name"));
        systemInfoResult.setOsArch(systemProps.getProperty("os.arch"));
        systemInfoResult.setUserName(systemProps.getProperty("user.name"));
        systemInfoResult.setUserLanguage(systemProps.getProperty("user.language"));
        systemInfoResult.setUserCountry(systemProps.getProperty("user.country"));
        systemInfoResult.setUserTimezone(systemProps.getProperty("user.timezone"));
        systemInfoResult.setPid(systemProps.getProperty("PID"));
        systemInfoResult.setFileEncoding(systemProps.getProperty("file.encoding"));
        systemInfoResult.setUserDir(systemProps.getProperty("user.dir"));
        systemInfoResult.setJavaHome(systemProps.getProperty("java.home"));
        systemInfoResult.setCatalinaHome(systemProps.getProperty("catalina.home"));
        systemInfoResult.setCatalinaBase(systemProps.getProperty("catalina.base"));
        systemInfoResult.setHostName(os.getNetworkParams().getHostName());
        systemInfoResult.setManufacturer(os.getManufacturer());
        systemInfoResult.setFamily(os.getFamily());
        systemInfoResult.setVersionInfoVersion(versionInfo.getVersion());
        systemInfoResult.setVersionInfoCodeName(versionInfo.getCodeName());
        systemInfoResult.setBitness(os.getBitness());
        systemInfoResult.setProcessorName(processor.getName());
        systemInfoResult.setMemoryTotal(memory.getTotal());
        systemInfoResult.setMemoryAvailable(memory.getAvailable());
        systemInfoResult.setSystemBootTime(DateFormat.format(os.getSystemBootTime() * 1000 , DateFormat.ISO_EXTENDED_DATETIME_FORMAT.getPattern()));
        systemInfoResult.setFirmwareReleaseDate(hardware.getComputerSystem().getFirmware().getReleaseDate());
        systemInfoResult.setElevated(os.isElevated());
        systemInfoResult.setLogicalProcessorCount(hardware.getProcessor().getLogicalProcessorCount());
        systemInfoResult.setProcessCount(os.getProcessCount());
        systemInfoResult.setProcessId(os.getProcessId());
        systemInfoResult.setThreadCount(os.getThreadCount());
        systemInfoResult.setNetworkIFs(hardware.getNetworkIFs().size());
        systemInfoResult.setProcessorIdentifier(processor.getIdentifier());
        systemInfoResult.setDisplayCount(hardware.getDisplays().size());
        systemInfoResult.setSoundCardsCount(hardware.getSoundCards().size());
        systemInfoResult.setServicesCount(os.getServices().length);
        systemInfoResult.setFileStoresCount(osFileStores.size());
        List<SystemInfoResult.FileStore> fileStores = Lists.newArrayList();
        osFileStores.forEach(item -> {
            SystemInfoResult.FileStore fileStore = new SystemInfoResult.FileStore();
            BeanUtils.copyProperties(item , fileStore);
            fileStores.add(fileStore);
        });
        systemInfoResult.setFileStores(fileStores);
    }

}
