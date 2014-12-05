package me.p2p.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import me.p2p.spec.MessageCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler extends Thread {
	static final String TAG = "MessageHandler";
	Socket socket;
	boolean inMessage = false;
	StringBuilder msgData = null;
	MessageCallback msgListener;
	String clientRequest = null;
	BufferedReader bufferedReader;
	boolean stopHandle = false;

	public RequestHandler(Socket socket, MessageCallback msgListener)
			throws IOException {
		// TODO Auto-generated constructor stub
		// client;
		this.socket = socket;
		this.msgListener = msgListener;

		this.clientRequest = null;
		this.bufferedReader = new BufferedReader(new InputStreamReader(
				this.socket.getInputStream()));
	}

	public void run() {
		// TODO Auto-generated method stub
		while (!stopHandle) {
			// handle request, read data;
			try {
				clientRequest = bufferedReader.readLine();
				System.out.println("Client Command: " + clientRequest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (clientRequest != null) {
				if (clientRequest.equals(RequestType.START_SESSION_MSG)) {
					System.out.println(TAG + ": Start Session");
					if (msgListener != null) {
						msgListener.onSessionStart();
					}
				} else {
					if (clientRequest.equals(RequestType.END_SESSION_MSG)) {
						System.out.println(TAG + ": Exit Session");
						if (msgListener != null) {
							msgListener.onSessionEnd();
						}

						break;
					} else {
						if (clientRequest.equals(RequestType.START_MSG)) {
							// bat dau doc data, chuan bi doi de chua;
							System.out.println(TAG + ": Start Command");

							inMessage = true;
							msgData = new StringBuilder();
						} else {
							if (clientRequest.equals(RequestType.END_MSG)) {
								// ket thuc doc data;
								System.out.println(TAG + ": End Command");
								// change state of message handler;
								inMessage = false;
								// log to console;
								System.out.println(msgData.toString());
								// notify msg listener;
								if (msgListener != null) {
									JSONObject data = null;
									try {
										// nếu là object;
										data = new JSONObject(msgData.toString());
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									msgListener.onMessage(socket, data);
								}
							} else {
								if (inMessage) {
									// nếu không phải hai trường hợp kia thì
									// append
									// nó
									// vào
									// data;
									System.out.println(TAG
											+ ": In Message: Append Data");
									msgData.append(clientRequest);
								} else {
									System.out.println(TAG
											+ ": Not In Message: Skip");
								}
							}
						}
					}
				}
			} else {
				System.out.println(TAG + ": client command is null");
			}
		}
	}

	public void handleRequest() {
		System.out.println(TAG + " handle message...");
		start();
	}
	
	public void stopHandle() {
		stopHandle = true;
	}
}