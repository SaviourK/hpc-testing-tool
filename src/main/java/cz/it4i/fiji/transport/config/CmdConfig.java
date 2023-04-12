package cz.it4i.fiji.transport.config;

public class CmdConfig {

	private CmdConfig() {
		// config class
	}

	// pcap file name commons
	public static final String HPC = "hpc";
	public static final String SCP = "scp";
	public static final String SFTP = "sftp";
	public static final String SIZE_SMALL = "216";
	public static final String SIZE_LARGE = "542";
	public static final String OPERATION_TYPE_TO = "cts";
	public static final String OPERATION_TYPE_FROM = "cfs";

	// hpc testing types
	public static final String HPC_DEFAULT_H2_HTTP_1_1 = "dh1";
	public static final String HPC_DEFAULT_H2_HTTP_2 = "dh2";
	public static final String HPC_DEFAULT_REACTIVE_POSTGRES_HTTP_1_1 = "drp1";
	public static final String HPC_REACTIVE_POSTGRES_HTTP_2 = "rp2";

}
