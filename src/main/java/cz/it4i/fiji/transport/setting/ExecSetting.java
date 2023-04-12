package cz.it4i.fiji.transport.setting;

public class ExecSetting {

	private final boolean upload;

	/**
	 * Can be local for upload or remote for download
	 */
	private final String filePath;
	private final String pcapFilePrefix;

	public ExecSetting(boolean upload, String filePath, String pcapFilePrefix) {
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
