package me.p2p.spec;

import org.json.JSONObject;

public interface IP2PProtocol {
	/**
	 * Thời gian hết hiệu lực kết nối tới một cổng là 30 giây 
	 */
	public static final int SC_TIME_OUT = 30000;
	
	/**
	 * Số lượng kết nối tối đa, ở môi trường thử nghiệm chỉ là 10;
	 */
	public static final int BACK_LOG = 10;
	
	/**
	 * - Khởi động lắng nghe request từ những client khác.<br>
	 * Lúc này peer đóng vai trò là server để xử lý các yêu<br>
	 * như chuyển dữ liệu từ bootstrap (transfer peer list),<br>
	 * yêu cầu leave, update từ những peer khác. 
	 */
	public void listenRequest();
	
	/**
	 * Bất kỳ node nào đều cũng cần phải shutdown khi kết thúc hoạt động
	 */
	public void shutdown();

	/**
	 * - Xử lý yêu cầu rời mạng từ nút khác gửi đến.
	 * @param requestPeerInfo
	 */
	public void handleLeaveRequest(JSONObject requestPeerInfo);

	/**
	 * - Xử lý yêu cầu cập nhật nút từ nút khác gửi đến.
	 */
	public void handleUpdateRequest(JSONObject requestPeerInfo);
}
