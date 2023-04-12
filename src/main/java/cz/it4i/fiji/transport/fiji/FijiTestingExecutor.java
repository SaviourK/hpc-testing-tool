package cz.it4i.fiji.transport.fiji;

import cz.it4i.fiji.transport.config.HpcTestingConfig;
import cz.it4i.fiji.transport.pcap.PcapRunnable;
import cz.it4i.fiji.transport.testing.CmdExecutor;
import cz.it4i.fiji.transport.testing.TrafficCapture;
import cz.it4i.fiji.transport.utils.CmdCreatorUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static cz.it4i.fiji.transport.utils.ExecutorUtils.*;

public class FijiTestingExecutor<T> {

    private static final StopWatch stopWatch = new StopWatch();

    private static final HpcTestingConfig CONFIG = HpcTestingConfig.INSTANCE;

    public void testFijiCommand(Future<T> command, String pcapFilePrefix) throws ExecutionException, InterruptedException, NotOpenException, PcapNativeException {
        TrafficCapture trafficCapture;
        try {
            trafficCapture = new TrafficCapture(CONFIG.getHostIp(), CONFIG.getDevicePhysicalAddress());
        } catch (PcapNativeException e) {
            throw new RuntimeException(e);
        }

        for (int i = 1; i < CONFIG.getNumberOfTests(); i++) {
            String idString = CmdCreatorUtils.createIdString(i);
            PcapRunnable pcapRunnable = initPcapRunnable(trafficCapture, pcapFilePrefix, idString);
            final Thread thread = startPacketCapturing(pcapRunnable, stopWatch);

            // Execute fiji cmd
            command.get();

            stopPacketCapturing(pcapRunnable, thread, stopWatch);
        }

    }
}
