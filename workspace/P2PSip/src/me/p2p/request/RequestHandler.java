package me.p2p.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import me.p2p.log.Log;
import me.p2p.respone.Respone;
import me.p2p.specify.MessageCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler extends Thread {
	static final String TAG = "RequestHandler";
	Socket socket;
	boolean inMessage = false;
	StringBuilder msgData = null;
	MessageCallback msgListener;
	String clientRequest = null;
	BufferedReader requestBufferedReader;
	boolean stopHandle = false;

	ArrayList<String> requestQueue;

	public RequestHandler(Socket socket, MessageCallback msgListener) {
		// TODO Auto-generated constructor stub
		// client;
		this.socket = socket;
		this.msgListener = msgListener;

		this.requestQueue = new ArrayList<String>();
		this.clientRequest = null;
	}

	public void run() {
		// TODO Auto-generated method stub
		while (!stopHandle) {

			try {
				this.requestBufferedReader = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				clientRequest = requestBufferedReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (clientRequest != null) {
				if (clientRequest.equals(RequestType.END_MSG)) {
					// add message cuối cùng;
					requestQueue.add(clientRequest);

					// xử lý tất cả request;
					for (String s : requestQueue) {
						if (s.equals(RequestType.START_MSG)) {
							// create msg data;
							msgData = new StringBuilder();
							// log
							Log.logToConsole(TAG, "Peer: "
									+ socket.getInetAddress().toString()
									+ " send start_msg request");
							// call listener;
							if (msgListener != null) {
								msgListener.onMessageStart();
							}
						} else {
							if (s.equals(RequestType.END_MSG)) {
								// end msg;
								if (msgListener != null) {
									JSONObject data = null;
									try {
										data = new JSONObject(
												msgData.toString());
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									// Gọi xử lý message;
									msgListener.onMessage(data);

									/*
									 * Sau khi xử lý xong thì gọi hàm onEndMsg
									 * để báo hiệu là xử lý thông điệp đã xong.
									 */
									msgListener.onMessageEnd();
								}
								// stop handle msg
								stopHandle();
							} else {
								msgData.append(s);
							}
						}
					}
				} else {
					// add tất cả những mesage còn lại;
					requestQueue.add(clientRequest);
				}

				// đọc xong một message, gửi request ok lại cho client;
				Respone respone = new Respone(socket);
				respone.sendServerOk();
			} else {
				stopHandle();
			}
		}

		Log.logToConsole(TAG, "Stop handle request");
		Log.logToConsole(TAG,
				"//////////////////////////////////////////////////");
	}

	public void handleRequest() {
		Log.logToConsole("", "");
		Log.logToConsole(TAG,
				"//////////////////////////////////////////////////");
		Log.logToConsole(TAG, "Handle message...");
		setName(TAG);
		start();
	}

	public void stopHandle() {
		stopHandle = true;
	}
}