package cz.it4i.fiji.transport.config;

public class ExecConfig {

	private final boolean upload;

	/**
	 * Can be local for upload or remote for download
	 */
	private final String filePath;
	private final String pcapFilePrefix;

	public ExecConfig(boolean upload, String filePath, String pcapFilePrefix) {
		this.upload = upload;
		this.filePath = filePath;
		this.pcapFilePrefix = pcapFilePrefix;
	}

	public boolean isUpload() {
		return upload;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getPcapFilePrefix() {
		return pcapFilePrefix;
	}
}
