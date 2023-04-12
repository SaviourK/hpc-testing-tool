package cz.it4i.fiji.transport.utils;

import cz.it4i.fiji.transport.config.HpcTestingConfig;

public class CmdCreatorUtils {

	private static final HpcTestingConfig APP_CONFIG = HpcTestingConfig.INSTANCE;

	private CmdCreatorUtils() {
		// Static Utils class
	}

	public static ProcessBuilder createSCPUploadCmd(String remotePath, String localPath, int id) {
		return new ProcessBuilder("scp", "-r", "-i", APP_CONFIG.getPrivateKeyPath(), localPath, String.format(remotePath, id));
	}

	public static ProcessBuilder createSCPDownloadCmd(String remotePath, String localPath) {
		return new ProcessBuilder("scp", "-r", "-i", APP_CONFIG.getPrivateKeyPath(), remotePath, localPath);
	}

	public static ProcessBuilder createSFTPPutCmd() {
		return new ProcessBuilder("sftp", "-i", APP_CONFIG.getPrivateKeyPath(), "-b", APP_CONFIG.getScpPutCmdPath(), APP_CONFIG.getRemotePath());
	}

	public static ProcessBuilder createSFTPGetCmd() {
		return new ProcessBuilder("sftp", "-i", APP_CONFIG.getPrivateKeyPath(), "-b", APP_CONFIG.getScpGetCmdPath(), APP_CONFIG.getRemotePath());
	}

	public static String createPrefix(String tool, String size, String operation) {
		return tool + "-" + size + "-" + operation + "-";
	}

	public static String createHpcPrefix(String tool, String type, String size, String operation) {
		return tool + "-" + type + "-" + size + "-" + operation + "-";
	}

	public static String createIdString(int id) {
		return String.format("%03d", id);
	}
}
