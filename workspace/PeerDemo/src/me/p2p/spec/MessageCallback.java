package me.p2p.spec;


import java.net.Socket;

import org.json.JSONObject;

/**
 * Callback Interface when message has transfered successful from
 * client to server.
 * @author Sang
 *
 */
public interface MessageCallback {
	/**
	 * Hàm được gọi khi phiên giữa peer và bootstrap diễn ra
	 */
	public void onSessionStart();
	
	/**
	 * Hàm này sẽ được gọi khi peer thực hiện gửi xong một thông điệp theo phương
	 * thức TexProtocol.
	 * @param peerSocket được sử dụng để truyền dữ liệu lại peer node
	 * @param data nội dung của thông điệp
	 */
	public void onMessage(Socket peerSocket, JSONObject data);
	
	/**
	 * Hàm được gọi khi phiên giữa peer và bootstrap kết thúc
	 */
	public void onSessionEnd();
}