package me.p2p.request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import me.p2p.message.Message;


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
	DataOutputStream dtos;
	Socket socket;

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
		try {
			this.dtos.writeBytes(RequestBuilder.build(message.toJsonObject().toString()));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startSession() {
		try {
			this.dtos.writeBytes(RequestBuilder.build(RequestType.START_SESSION_MSG));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startMsg() {
		try {
			this.dtos.writeBytes(RequestBuilder.build(RequestType.START_MSG));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endMsg() {
		try {
			this.dtos.writeBytes(RequestBuilder.build(RequestType.END_MSG));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endSession() {
		try {
			this.dtos.writeBytes(RequestBuilder.build(RequestType.END_SESSION_MSG));
			this.dtos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}