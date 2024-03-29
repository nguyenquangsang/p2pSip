package me.p2p.log;

import java.io.FileWriter;
import java.io.IOException;

public class Log {
	/**
	 * Tên file log;
	 */
	public static final String FILE_NAME_LOG = "log.log";

	/**
	 * Cài đặt đường dẫn file log cho việc log những hành động của hệ thống <br>
	 * Nếu không cài đặt đường dẫn thì log chỉ hiển thị ra Console chứ không in<br>
	 * ra file.
	 */
	public static String filePathLog = null;

	public static void logToConsole(String tag, String message) {
		String aLogLine = tag + ": " + message;
		System.out.println(aLogLine);

		/*
		 * Log ra file nếu được chỉ định đường dẫn;
		 */
		if (filePathLog != null) {
			try {
				FileWriter fileWriter = new FileWriter(filePathLog + "/"
						+ FILE_NAME_LOG, true);
				fileWriter.write(aLogLine + "\n");
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void logStartPartLog() {
		logToConsole("", "");
	}
}
