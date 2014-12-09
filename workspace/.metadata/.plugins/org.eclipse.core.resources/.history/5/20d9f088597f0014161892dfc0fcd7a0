package me.p2p.spec;


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
	public void onMessageStart();
	
	/**
	 * Hàm này sẽ được gọi khi peer thực hiện gửi xong một thông điệp theo phương<br>
	 * thức TexProtocol. Vì đối số socket được truyền từ request handler nên không<br>
	 * đảm bảo là socket sẽ visible, phải kiểm tra socket close trước khi gọi.
	 * @param peerSocket được sử dụng để truyền dữ liệu lại peer node
	 * @param message nội dung của thông điệp
	 */
	public void onMessage(JSONObject message);
	
	/**
	 * Hàm được gọi khi phiên giữa peer và bootstrap kết thúc
	 */
	public void onMessageEnd();
}
