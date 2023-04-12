package cz.it4i.fiji.transport.testing;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.util.LinkLayerAddress;

public class TrafficCapture {
	private static final int SNAPSHOT_LENGTH = 5000; // in bytes
	private static final int READ_TIMEOUT = 10; // in milliseconds
	private final PcapNetworkInterface device;
	private final String hostIpAddress;
	private PcapHandle pcapHandle;

	public TrafficCapture(String hostIpAddress, String devicePhysicalAddr) throws PcapNativeException {
		this.hostIpAddress = hostIpAddress;
		this.device = Pcaps.getDevByName(getDeviceNameByPhysicalAddr(devicePhysicalAddr));
	}

	public void initHandler() throws PcapNativeException, NotOpenException {
		// Open the device and get a handle
		this.pcapHandle = new PcapHandle.Builder(device.getName())
				.snaplen(SNAPSHOT_LENGTH)
				.promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS)
				.timeoutMillis(READ_TIMEOUT)
				.bufferSize(700_000_000)
				.build();

		//this.pcapHandle = device.openLive(SNAPSHOT_LENGTH, PcapNetworkInterface.PromiscuousMode.NONPROMISCUOUS, READ_TIMEOUT);

		// Set a filter to only listen for host ip address provided
		String filter = "host " + hostIpAddress;
		pcapHandle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
	}

	public PcapHandle getPcapHandle() {
		return pcapHandle;
	}

	private static String getDeviceNameByPhysicalAddr(String devicePhysicalAddr) throws PcapNativeException {
		System.out.println("Pcap Interfaces: ");
		for (PcapNetworkInterface dev : Pcaps.findAllDevs()) {
			for (LinkLayerAddress linkLayerAddress : dev.getLinkLayerAddresses()) {
				if (linkLayerAddress.toString().equals(devicePhysicalAddr)) {
					return dev.getName();
				}
			}
		}
		throw new RuntimeException("Interface with physical address " + devicePhysicalAddr + " not found");
	}
}
