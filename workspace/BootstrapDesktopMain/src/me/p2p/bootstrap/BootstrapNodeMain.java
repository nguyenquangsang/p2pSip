package me.p2p.bootstrap;

import me.p2p.log.Log;



public class BootstrapNodeMain {
	static final String filePath = "E:/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Log.filePathLog = filePath;
		
		BootstrapNode bootstrapNode = new BootstrapNode(filePath);
		// listen for request
		bootstrapNode.listenRequest();
	}
}
