package me.p2p;

import org.json.JSONObject;

public class MessageBuilder {
	public static final String build(String message) {
		return message + "\n";
	}
	
	public static final JSONObject buildJoinMessage(Peer peer) {
		JSONObject data = new JSONObject();
		return data;
	}
}