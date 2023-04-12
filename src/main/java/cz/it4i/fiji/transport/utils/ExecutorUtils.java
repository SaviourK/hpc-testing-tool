package cz.it4i.fiji.transport.utils;

import cz.it4i.fiji.transport.pcap.PcapRunnable;
import cz.it4i.fiji.transport.testing.TrafficCapture;
import org.apache.commons.lang3.time.StopWatch;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorUtils.class);

    private ExecutorUtils() {
        // Utils class
    }

    public static PcapRunnable initPcapRunnable(TrafficCapture trafficCapture, String pcapFilePrefix, String idString) throws PcapNativeException, NotOpenException {
        trafficCapture.initHandler();
        return new PcapRunnable(trafficCapture.getPcapHandle(), pcapFilePrefix, idString);
    }

    public static Thread startPacketCapturing(PcapRunnable pcapRunnable, StopWatch stopWatch) {
        // Start packet capture
        Thread thread = new Thread(pcapRunnable);
        thread.start();
        stopWatch.start();
        return thread;
    }

    public static void stopPacketCapturing(PcapRunnable pcapRunnable, Thread thread, StopWatch stopWatch) throws NotOpenException {
        // Stop packet capture
        stopWatch.stop();
        pcapRunnable.stop();
        thread.interrupt();

        logger.info("Total time taken {}ms", stopWatch.getTime());
        stopWatch.reset();
    }
}
