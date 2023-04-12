package cz.it4i.fiji.transport.pcap;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PcapRunnable implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(PcapRunnable.class);

	private final PcapHandle pcapHandle;
	private final String fileNamePrefix;
	private final String id;

	private PcapDumper pcapDumper;

	private static final String PCAP_FILE_EXTENSION = ".pcapng";

	public PcapRunnable(PcapHandle pcapHandle, String fileNamePrefix, String id) {
		this.pcapHandle = pcapHandle;
		this.fileNamePrefix = fileNamePrefix;
		this.id = id;
	}

	@Override
	public void run() {
		try {

			this.pcapDumper = pcapHandle.dumpOpen(fileNamePrefix + id + PCAP_FILE_EXTENSION);

			// Create a listener that defines what to do with the received packets
			PacketListener listener = packet -> {
				// Print packet information to screen
				logger.trace("Timestamp {} packet {}", pcapHandle.getTimestamp(), packet);

				// Dump packets to file
				try {
					pcapDumper.dump(packet, pcapHandle.getTimestamp());
				} catch (NotOpenException e) {
					e.printStackTrace();
				}
			};
			// Tell the handle to loop using the listener we created
			try {
				// -1 equals infinity
				int infinity = -1;
				pcapHandle.loop(infinity, listener);
			} catch (PcapNativeException | NotOpenException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				logger.info("Pcap Thread stopped after stop capturing");
			}
		} catch (NotOpenException | PcapNativeException e) {
			throw new RuntimeException(e);
		}

	}

	public void stop() throws NotOpenException {
		pcapHandle.breakLoop();
		// Cleanup when complete
		pcapDumper.close();
		pcapHandle.close();
	}

}
