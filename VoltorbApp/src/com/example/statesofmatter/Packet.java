package com.example.statesofmatter;

public abstract class Packet {

	public static enum PacketTypes {
		INVALID(-1), GAME_OVER(99), SWITCH(01);
		
		private int packetID;
		
		PacketTypes(int PacketID) {
			this.packetID = packetID;
		}
		
		public int getID() {
			return packetID;
		}
		
		public String getName() {
			return name();
		}
	}
	
	public int packetID;
	
	public Packet(int packetID) {
		this.packetID = packetID;
	}
	
	public static PacketTypes lookupPacket(int packetID) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getID() == packetID)
				return p;
		}
		return PacketTypes.INVALID;
	}
	
	public String getName() {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getID() == packetID)
				return p.getName();
		}
		return PacketTypes.INVALID.name();
	}
}