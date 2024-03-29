package me.p2p.peermain;

import me.p2p.log.Log;


public class PeerDesktopMain {
	static final String TAG = "PeerMain";
	static final String USER_NAME = "tobeNguyen";
	static final String filePath = "E:/PeerData";
	

	public static void main(String[] args) {
		/**
		 * Chỉ định đường dẫn log;
		 */
		Log.filePathLog = filePath;
		
		String bootstrapAdress = "192.168.0.106";
		
		Log.logToConsole(TAG, "Create PeerNode");
		PeerNode peerNode = new PeerNode(filePath, USER_NAME, bootstrapAdress);
		Log.logToConsole(TAG, "Run PeerNode");
		peerNode.run();
	}
}