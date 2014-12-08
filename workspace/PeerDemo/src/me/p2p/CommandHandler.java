package me.p2p;

import java.io.BufferedReader;
import java.io.IOException;

public class CommandHandler {
	static final String TAG = "CommandHandler";
	BufferedReader bufferedReader;
	String command = null;
	boolean inMessage = false;
	
	public interface ICommand {
		public void onCommand(String command);
	}
	
	ICommand startSession;
	ICommand endSession;
	ICommand startMsg;
	ICommand endMsg;
	ICommand inMsg;
	
	public CommandHandler(BufferedReader br) {
		this.bufferedReader = br;
		this.command = null;
	}

	public void setStartSession(ICommand listener) {
		this.startSession = listener;
	}

	public void setEndSession(ICommand listener) {
		this.endSession = listener;
	}

	public void setStartMsg(ICommand listener) {
		this.startMsg = listener;
	}

	public void setEndMsg(ICommand listener) {
		this.endMsg = listener;
	}

	public void setInMsg(ICommand listener) {
		this.inMsg = listener;
	}

	public void processCommand() {
		while (true) {
			// handle request, read data;
			try {
				command = bufferedReader.readLine();
				System.out.println("Client Command: " + command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (command != null) {
				if (command.equals(MsgProtocol.START_SESSION_MSG)) {
					System.out.println(TAG + ": Start Session");
					startMsg.onCommand(command);
				} else {
					if (command.equals(MsgProtocol.END_SESSION_MSG)) {
						System.out.println(TAG + ": Exit Session");
						endSession.onCommand(command);

						break;
					} else {
						if (command.equals(MsgProtocol.START_MSG)) {
							// bat dau doc data, chuan bi doi de chua;
							System.out.println(TAG + ": Start Message");

							inMessage = true;
							inMsg.onCommand(command);
						} else {
							if (command.equals(MsgProtocol.END_MSG)) {
								// ket thuc doc data;
								System.out.println(TAG + ": End Message");
								// change state of message handler;
								inMessage = false;
								
								// end msg;
								endMsg.onCommand(command);
							} else {
								if (inMessage) {
									// gọi trường hợp in message;
									System.out.println(TAG + ": In Message");
									inMsg.onCommand(command);
								} else {
									System.out.println(TAG + ": Not In Message");
								}
							}
						}
					}
				}
			} else {
				System.out.println(TAG + ": command is null");
			}
		}
	}
}