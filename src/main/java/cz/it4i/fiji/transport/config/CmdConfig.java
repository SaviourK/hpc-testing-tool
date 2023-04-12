package cz.it4i.fiji.transport.config;

public class CmdConfig {

	// Global settings
	public static final int NUMBER_OF_TESTS = 30;

	// Security settings
	public static final String PRIVATE_KEY_PATH = "c:\\Users\\VítězslavKaňok\\.ssh/kanok-ec2-user.pem";
	public static final String USERNAME = "ec2-user";
	public static final String HOST_IP = "3.125.116.50";
	public static final String USERNAME_AND_HOST_IP = CmdConfig.USERNAME + "@" + CmdConfig.HOST_IP;

	// Remote data location
	public static final String REMOTE_DIR = "/home/ec2-user/test";
	public static final String REMOTE_PATH_TEMPLATE = CmdConfig.USERNAME + "@" + CmdConfig.HOST_IP+ ":"+ REMOTE_DIR + "/%d/";

	public static final String SCP_PUT_CMD_PATH = "C:\\Projects\\hpc-testing-tool\\src\\main\\resources\\sftp-put.txt";
	public static final String SCP_GET_CMD_PATH = "C:\\Projects\\hpc-testing-tool\\src\\main\\resources\\sftp-get.txt";
	// Local data location
	public static final String LOCAL_PATH_SMALL_FILE = "C:\\All\\Fiji\\data\\mouse-n5-216MB\\";
	public static final String LOCAL_PATH_LARGE_FILE = "C:\\All\\Fiji\\data\\mouse-n5-542MB\\";

	// pcap file name commons
	public static final String SCP = "scp";
	public static final String SFTP = "sftp";
	public static final String SIZE_SMALL = "216";
	public static final String SIZE_LARGE = "542";
	public static final String OPERATION_TYPE_TO = "cts";
	public static final String OPERATION_TYPE_FROM = "cfs";

}
