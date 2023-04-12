package cz.it4i.fiji.transport.scp;

import cz.it4i.fiji.transport.config.CmdConfig;
import cz.it4i.fiji.transport.config.ExecConfig;
import cz.it4i.fiji.transport.pcap.PcapRunnable;
import cz.it4i.fiji.transport.testing.CmdExecutor;
import cz.it4i.fiji.transport.testing.TrafficCapture;
import cz.it4i.fiji.transport.utils.CmdCreatorUtils;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SCPTestingMain {

	private static final Logger logger = LoggerFactory.getLogger(SCPTestingMain.class);

	public static void main(String[] args) throws IOException, NotOpenException, PcapNativeException {
		logger.info("Start testing transport");
		CmdExecutor executor = new CmdExecutor();
		TrafficCapture trafficCapture = new TrafficCapture("3.125.116.50", "18:31:bf:30:4e:9a");

		String fileNamePrefix;
		ExecConfig execConfig;

		// Example of SCP upload
		//fileNamePrefix = CmdCreatorUtils.createPrefix(CommonCmdConfig.SCP, CommonCmdConfig.SIZE_SMALL, CommonCmdConfig.OPERATION_TYPE_TO);
		//execConfig = new ExecConfig(true, CommonCmdConfig.LOCAL_PATH_SMALL_FILE, fileNamePrefix);
		//executeScpCmd(executor, trafficCapture, execConfig);

		// Example of SFTP upload small
		//fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SFTP, CmdConfig.SIZE_SMALL, CmdConfig.OPERATION_TYPE_TO);
		//execConfig = new ExecConfig(true, CmdConfig.LOCAL_PATH_SMALL_FILE, fileNamePrefix);
		//executeSftpCmd(executor, trafficCapture, execConfig);

		// Example of SFTP upload large
		//fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SFTP, CmdConfig.SIZE_LARGE, CmdConfig.OPERATION_TYPE_TO);
		//execConfig = new ExecConfig(true, CmdConfig.LOCAL_PATH_LARGE_FILE, fileNamePrefix);
		//executeSftpCmd(executor, trafficCapture, execConfig);

		// Example of SFTP upload
		fileNamePrefix = CmdCreatorUtils.createPrefix(CmdConfig.SFTP, CmdConfig.SIZE_LARGE, CmdConfig.OPERATION_TYPE_FROM);
		execConfig = new ExecConfig(false, CmdConfig.LOCAL_PATH_LARGE_FILE, fileNamePrefix);
		executeSftpCmd(executor, trafficCapture, execConfig);

		logger.info("End testing transport");

	}

	private static void executeSftpCmd(CmdExecutor executor, TrafficCapture trafficCapture, ExecConfig execConfig) throws NotOpenException, PcapNativeException, IOException {
		logger.info("Start testing SFTP");

		for (int i = 1; i <= CmdConfig.NUMBER_OF_TESTS; i++) {
			String idString = CmdCreatorUtils.createIdString(i);
			logger.info("Start id {}", idString);

			ProcessBuilder sftpCmd;
			if (execConfig.isUpload()) {
				sftpCmd = CmdCreatorUtils.createSFTPPutCmd();
			} else {
				sftpCmd = CmdCreatorUtils.createSFTPGetCmd();
			}

			sftpCmd.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			PcapRunnable pcapRunnable = initPcapRunnable(trafficCapture, execConfig.getPcapFilePrefix(), idString);

			executor.executeCmd(sftpCmd, pcapRunnable);
		}

		logger.info("End testing SFTP");
	}

	private static void executeScpCmd(CmdExecutor executor, TrafficCapture trafficCapture, ExecConfig execConfig) throws PcapNativeException, NotOpenException, IOException {
		logger.info("Start testing SCP");

		for (int i = 1; i <= CmdConfig.NUMBER_OF_TESTS; i++) {
			String idString = CmdCreatorUtils.createIdString(i);
			logger.info("Start id {}", idString);

			PcapRunnable pcapRunnable = initPcapRunnable(trafficCapture, execConfig.getPcapFilePrefix(), idString);

			ProcessBuilder scpCmd;
			if (execConfig.isUpload()) {
				// Upload
				scpCmd = CmdCreatorUtils.createSCPUploadCmd(CmdConfig.REMOTE_PATH_TEMPLATE, execConfig.getFilePath(), i);
			} else {
				// Download
				scpCmd = CmdCreatorUtils.createSCPDownloadCmd(CmdConfig.REMOTE_PATH_TEMPLATE, execConfig.getFilePath());
			}
			executor.executeCmd(scpCmd, pcapRunnable);

			logger.info("End id {}", idString);
		}
		logger.info("End testing SCP");
	}

	private static PcapRunnable initPcapRunnable(TrafficCapture trafficCapture, String pcapFilePrefix, String idString) throws PcapNativeException, NotOpenException {
		trafficCapture.initHandler();
		return new PcapRunnable(trafficCapture.getPcapHandle(), pcapFilePrefix, idString);
	}
}
