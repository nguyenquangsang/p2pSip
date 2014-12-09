package me.p2p.request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import me.p2p.log.Log;
import me.p2p.message.Message;
import me.p2p.respone.ResponeType;


/**
 * Request là những mẩu thông điệp nhỏ được dùng để gửi giữa<br>
 * các node dùng để thông báo các tác vụ được chỉ định. Ví dụ:<br>
 * - Mở phiên giao dịch: start_session<br>
 * - Kết thúc phiên giao dịch: end_session<br>
 * - Bắt đầu gửi thông điệp: start_msg<br>
 * - Kết thúc gửi thông điệp: end_msg
 * @author Sang
 *
 */
public class Request {
	final String TAG = "Request";
	DataOutputStream dtos;
	
	Socket socket;
	boolean block = false;

	public Request(Socket socket) {
		this.socket = socket;
		try {
			this.dtos = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message message) {
		Log.logToConsole(TAG, "Request message: " + message.toJsonObject().toString());
		/*
		 * Gửi một tin nhắn và chờ cho đến khi server phát sinh tín hiệu đã
		 * xử lý xong tin nhắn.
		 */
		try {
			this.dtos.writeBytes(RequestBuilder.build(message.toJsonObject().toString()));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Khóa cho đến khi nhận được respone accept;
		 */
		block();
	}
	
	public void startMsg() {
		Log.logToConsole(TAG, "Request startMsg()");
		try {
			this.dtos.writeBytes(RequestBuilder.build(RequestType.START_MSG));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		block();
	}
	
	public void endMsg() {
		Log.logToConsole(TAG, "Request endMsg()");
		try {
			this.dtos.writeBytes(RequestBuilder.build(RequestType.END_MSG));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		block();
	}
	
	public void block() {
		/**
		 * Khóa request cho đến khi server gửi đến một respone chấp nhận
		 */
		block = true;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(block) {
			Log.logToConsole(TAG, "In function block() wait Server respone OK");
			// block
			try {
				String respone = br.readLine();
				if (respone.equals(ResponeType.SERVER_OK)) {
					block = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
