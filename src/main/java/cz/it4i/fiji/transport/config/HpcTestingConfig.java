package cz.it4i.fiji.transport.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum HpcTestingConfig {
    INSTANCE;

    private final int numberOfTests;
    private final String privateKeyPath;
    private final String username;
    private final String hostIp;
    private final String remoteDir;
    private final String scpPutCmdPath;
    private final String scpGetCmdPath;
    private final String localPathSmallFile;
    private final String localPathLargeFile;
    private final String devicePhysicalAddress;
    private final String usernameAndHost;
    private final String remotePath;
    private final String remotePathTemplate;

    HpcTestingConfig() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream("/hpc-testing.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.numberOfTests = Integer.parseInt(properties.getProperty("number_of_tests"));
        this.privateKeyPath = properties.getProperty("private_key_path");
        this.username = properties.getProperty("username");
        this.hostIp = properties.getProperty("host_ip");
        this.remoteDir = properties.getProperty("remote_dir");
        this.scpPutCmdPath = properties.getProperty("scp_put_cmd_path");
        this.scpGetCmdPath = properties.getProperty("scp_get_cmd_path");
        this.localPathSmallFile = properties.getProperty("local_path_small_file");
        this.localPathLargeFile = properties.getProperty("local_path_large_file");
        this.devicePhysicalAddress = properties.getProperty("device_physical_address");
        this.usernameAndHost = this.username + "@" + this.hostIp;
        this.remotePath = this.usernameAndHost + ":" + this.remoteDir;
        this.remotePathTemplate = remotePath + "/%d/";
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public String getUsername() {
        return username;
    }

    public String getHostIp() {
        return hostIp;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public String getScpPutCmdPath() {
        return scpPutCmdPath;
    }

    public String getScpGetCmdPath() {
        return scpGetCmdPath;
    }

    public String getLocalPathSmallFile() {
        return localPathSmallFile;
    }

    public String getLocalPathLargeFile() {
        return localPathLargeFile;
    }

    public String getDevicePhysicalAddress() {
        return devicePhysicalAddress;
    }

    public String getUsernameAndHost() {
        return usernameAndHost;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public String getRemotePathTemplate() {
        return remotePathTemplate;
    }
}
