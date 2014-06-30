package com.example.statesofmatter;

public class PacketHandler {
	
	public static void handlePacket(byte[] data, String host) {
		int separator = -1;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i] == '~') {
				separator = data[i];
				break;
			}
		}
		
		
		int packetID = Integer.parseInt(data.toString().substring(0, separator));
		System.out.println("Packet ID: " + packetID);
		System.out.println(data.toString().substring((separator + 1)));
	}
}