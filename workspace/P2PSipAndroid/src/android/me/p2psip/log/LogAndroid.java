package android.me.p2psip.log;

import me.p2p.log.Log;

public class LogAndroid {
	public static void log(String tag, String message) {
		android.util.Log.e(tag, message);
		Log.logToConsole(tag, message);
	}
}
