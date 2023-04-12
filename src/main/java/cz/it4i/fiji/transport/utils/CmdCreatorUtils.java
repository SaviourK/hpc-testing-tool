package cz.it4i.fiji.transport.utils;

import cz.it4i.fiji.transport.config.CmdConfig;

public class CmdCreatorUtils {

	private CmdCreatorUtils() {
		// Static Utils class
	}

	public static ProcessBuilder createSCPUploadCmd(String remotePath, String localPath, int id) {
		return new ProcessBuilder("scp", "-r", "-i", CmdConfig.PRIVATE_KEY_PATH, localPath, String.format(remotePath, id));
	}

	public static ProcessBuilder createSCPDownloadCmd(String remotePath, String localPath) {
		return new ProcessBuilder("scp", "-r", "-i", CmdConfig.PRIVATE_KEY_PATH, remotePath, localPath);
	}

	public static ProcessBuilder createSFTPPutCmd() {
		return new ProcessBuilder("sftp", "-i", CmdConfig.PRIVATE_KEY_PATH, "-b", CmdConfig.SCP_PUT_CMD_PATH, CmdConfig.USERNAME_AND_HOST_IP + ":" + CmdConfig.REMOTE_DIR);
	}

	public static ProcessBuilder createSFTPGetCmd() {
		return new ProcessBuilder("sftp", "-i", CmdConfig.PRIVATE_KEY_PATH, "-b", CmdConfig.SCP_GET_CMD_PATH, CmdConfig.USERNAME_AND_HOST_IP + ":" + CmdConfig.REMOTE_DIR);
	}
	public static String createSFTPUploadCmd(String localFilePath) {
		return "put -r " + localFilePath + "\n";
	}

	public static String createSFTPDownloadCmd(String remoteFilePath) {
		return "get -r " + remoteFilePath + "\n";
	}

	public static String createSFTPExitCmd() {
		return "exit\n";
	}

	public static String createPrefix(String tool, String size, String operation) {
		return tool + "-" + size + "-" + operation + "-";
	}

	public static String createIdString(int id) {
		return String.format("%03d", id);
	}
}
