package me.p2p.log;

import me.p2p.data.DataManager;


public class Log {
	public static void logToConsole(String tag, String message) {
		String aLogLine = tag + ": " + message;
		
		DataManager.getInstance().writeToLogFile(aLogLine);
		System.out.println(aLogLine);
	}
}
