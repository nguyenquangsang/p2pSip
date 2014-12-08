package me.p2p.spec;

import org.json.JSONObject;

public interface IPeer extends IP2PProtocol {
	/**
	 * - Gửi thông điệp yêu cầu tham gia mạng đến Bootstrap
	 */
	public void joinRequest();
	/**
	 * - Gửi thông điệp yêu cầu rời mạng
	 */
	public void leaveRequest();
	/**
	 * - Gửi thông điệp yêu cầu cập nhật thông tin
	 */
	public void updateRequest();
	/**
	 * - Xử lý yêu cầu chuyển dữ liệu list peer từ bootsrtap
	 * @param bstrListPeerInfo danh sách peer ở dạng json
	 */
	public void handleTransferRequest(JSONObject bstrListPeerInfo);
}