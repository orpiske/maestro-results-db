package org.maestro.results.loader;

import org.maestro.common.HostTypes;
import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dao.EnvResultsDao;
import org.maestro.results.dto.EnvResource;
import org.maestro.results.dto.EnvResults;
import org.maestro.results.dto.Test;
import org.maestro.results.exceptions.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EnvResourceLoader {
    private static final Logger logger = LoggerFactory.getLogger(EnvResourceLoader.class);

    private Test test;
    private String envName;
    private EnvResourceDao envResourceDao;
    private EnvResultsDao envResultsDao;

    public EnvResourceLoader(Test test, final String envName) {
        this.test = test;
        this.envName = envName;

        this.envResourceDao = new EnvResourceDao();
        this.envResultsDao = new EnvResultsDao();
    }

    private EnvResource insert(final String hostName, final Map<String, Object> properties) {
        EnvResource envResource = new EnvResource();

        envResource.setEnvResourceName(hostName);

        String hwCpu = (String) properties.get("workerOperatingSystemCpuCount");
        envResource.setEnvResourceHwCpu(hwCpu);

        String osArch = (String) properties.get("workerOperatingSystemArch");
        envResource.setEnvResourceOsArch(osArch);

        String osName = (String) properties.get("workerOperatingSystemName");
        envResource.setEnvResourceOsName(osName);

        String osVersion = (String) properties.get("workerOperatingSystemVersion");
        envResource.setEnvResourceOsVersion(osVersion);

        try {
            String memory = (String) properties.get("workerOperatingSystemMemory");
            envResource.setEnvResourceHwRam(Integer.parseInt(memory));
        }
        catch (Throwable t) {
            logger.warn("Unable to determine the system memory");
        }


        String jvmName = (String) properties.get("workerJvmName");
        String jvmVersion = (String) properties.get("workerJvmJavaVersion");

        envResource.setEnvResourceOsOther("jvm=" + jvmName + ",version=" + jvmVersion);

        int id = envResourceDao.insert(envResource);
        logger.warn("Added a new env resource with ID {}", id);

        envResource.setEnvResourceId(id);
        return envResource;
    }

    public EnvResource load(final String hostName, final Map<String, Object> properties) {
        EnvResource envResource;

        try {
            envResource = envResourceDao.fetchByName(hostName);
        } catch (DataNotFoundException e) {
            envResource = insert(hostName, properties);
        }

        return envResource;

    }


}
