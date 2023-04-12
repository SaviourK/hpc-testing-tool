package cz.it4i.fiji.transport;

import cz.it4i.fiji.transport.config.HpcTestingConfig;
import cz.it4i.fiji.transport.config.CmdConfig;
import cz.it4i.fiji.transport.setting.ExecSetting;
import cz.it4i.fiji.transport.pcap.PcapRunnable;
import cz.it4i.fiji.transport.testing.CmdExecutor;
import cz.it4i.fiji.transport.testing.TrafficCapture;
import cz.it4i.fiji.transport.utils.CmdCreatorUtils;
import static cz.it4i.fiji.transport.utils.ExecutorUtils.initPcapRunnable;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HpcTestingMain {

	private static final HpcTestingConfig APP_CONFIG = HpcTestingConfig.INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(HpcTestingMain.class);

	public static void main(String[] args) throws IOException, NotOpenException, PcapNativeException {
		logger.info("Start testing transport");
		CmdExecutor executor = new CmdExecutor();
		TrafficCapture trafficCapture = new TrafficCapture(APP_CONFIG.getHostIp(), APP_CONFIG.getDevicePhysicalAddress());

		String fileNamePrefix;
		ExecSetting execSetting;

		// Example of SCP upload
		//fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SCP, CmdConfig.SIZE_SMALL, CmdConfig.OPERATION_TYPE_TO);
		//execConfig = new ExecConfig(true, APP_CONFIG.getLocalPathSmallFile(), fileNamePrefix);
		//executeScpCmd(executor, trafficCapture, execConfig);

		// Example of SFTP upload small
		//fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SFTP, CmdConfig.SIZE_SMALL, CmdConfig.OPERATION_TYPE_TO);
		//execConfig = new ExecConfig(true, APP_CONFIG.getLocalPathSmallFile(), fileNamePrefix);
		//executeSftpCmd(executor, trafficCapture, execConfig);

		// Example of SFTP upload large
		//fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SFTP, CmdConfig.SIZE_LARGE, CmdConfig.OPERATION_TYPE_TO);
		//execConfig = new ExecConfig(true, APP_CONFIG.getLocalPathLargeFile(), fileNamePrefix);
		//executeSftpCmd(executor, trafficCapture, execConfig);

		// Example of SFTP download large
		fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SFTP, CmdConfig.SIZE_LARGE, CmdConfig.OPERATION_TYPE_FROM);
		execSetting = new ExecSetting(false, APP_CONFIG.getLocalPathLargeFile(), fileNamePrefix);
		executeSftpCmd(executor, trafficCapture, execSetting);

		logger.info("End testing transport");

	}

	private static void executeSftpCmd(CmdExecutor executor, TrafficCapture trafficCapture, ExecSetting execSetting) throws NotOpenException, PcapNativeException, IOException {
		logger.info("Start testing SFTP");

		for (int i = 1; i <= APP_CONFIG.getNumberOfTests(); i++) {
			String idString = CmdCreatorUtils.createIdString(i);
			logger.info("Start id {}", idString);

			ProcessBuilder sftpCmd;
			if (execSetting.isUpload()) {
				sftpCmd = CmdCreatorUtils.createSFTPPutCmd();
			} else {
				sftpCmd = CmdCreatorUtils.createSFTPGetCmd();
			}

			sftpCmd.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			PcapRunnable pcapRunnable = initPcapRunnable(trafficCapture, execSetting.getPcapFilePrefix(), idString);

			executor.executeCmd(sftpCmd, pcapRunnable);
		}

		logger.info("End testing SFTP");
	}

	private static void executeScpCmd(CmdExecutor executor, TrafficCapture trafficCapture, ExecSetting execSetting) throws PcapNativeException, NotOpenException, IOException {
		logger.info("Start testing SCP");

		for (int i = 1; i <= APP_CONFIG.getNumberOfTests(); i++) {
			String idString = CmdCreatorUtils.createIdString(i);
			logger.info("Start id {}", idString);

			PcapRunnable pcapRunnable = initPcapRunnable(trafficCapture, execSetting.getPcapFilePrefix(), idString);

			ProcessBuilder scpCmd;
			if (execSetting.isUpload()) {
				// Upload
				scpCmd = CmdCreatorUtils.createSCPUploadCmd(APP_CONFIG.getRemotePathTemplate(), execSetting.getFilePath(), i);
			} else {
				// Download
				scpCmd = CmdCreatorUtils.createSCPDownloadCmd(APP_CONFIG.getRemotePathTemplate(), execSetting.getFilePath());
			}
			executor.executeCmd(scpCmd, pcapRunnable);

			logger.info("End id {}", idString);
		}
		logger.info("End testing SCP");
	}

}
