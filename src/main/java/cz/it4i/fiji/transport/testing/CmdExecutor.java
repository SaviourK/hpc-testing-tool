package cz.it4i.fiji.transport.testing;

import cz.it4i.fiji.transport.pcap.PcapRunnable;
import org.apache.commons.lang3.time.StopWatch;
import org.pcap4j.core.NotOpenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cz.it4i.fiji.transport.utils.ExecutorUtils.startPacketCapturing;
import static cz.it4i.fiji.transport.utils.ExecutorUtils.stopPacketCapturing;

import java.io.IOException;

public class CmdExecutor {

	private final StopWatch stopWatch;

	public CmdExecutor() {
		this.stopWatch = new StopWatch();
	}

	private static final Logger logger = LoggerFactory.getLogger(CmdExecutor.class);

	public void executeCmd(ProcessBuilder pb, PcapRunnable pcapRunnable) throws IOException, NotOpenException {
		Thread thread = startPacketCapturing(pcapRunnable, stopWatch);

		if (logger.isInfoEnabled()) {
			logger.info("Executing cmd: {}", String.join(" ", pb.command().toArray(new String[0])));
		}
		Process process = pb.start();
		try {
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				logger.info("The command was successful");
			} else {
				logger.error("The command ended with an error. Exit code: {}", exitCode);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stopPacketCapturing(pcapRunnable, thread, stopWatch);
	}

	// Multiple cmds
	/*public void executeCmd(ProcessBuilder pb, PcapRunnable pcapRunnable, String... additionalCommands) throws IOException, NotOpenException {
		Thread thread = startPacketCapturing(pcapRunnable);

		logger.info("Executing cmd: {}", String.join(" ", pb.command().toArray(new String[0])));
		Process process = pb.start();
		try {
			InputStream inputStream = process.getInputStream();

			// Wait for prompt

			for (String additionalCommand : additionalCommands) {
				//readUntilPrompt(inputStream);
				logger.info("Executing additional cmd: {}", additionalCommand);
				process.getOutputStream().write(additionalCommand.getBytes());
				process.getOutputStream().flush();
			}
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				logger.info("The command was successful");
			} else {
				logger.error("The command ended with an error. Exit code: {}", exitCode);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stopPacketCapturing(pcapRunnable, thread);
	}*/
}
