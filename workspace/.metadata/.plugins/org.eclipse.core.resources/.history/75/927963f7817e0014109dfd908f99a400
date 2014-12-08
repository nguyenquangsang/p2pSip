package me.p2p.spec;

import java.net.Socket;

import org.json.JSONObject;

public interface IBootstrap {
	/**
	 * Khi nhận msg join thì việc boostrap cần làm là:<br>
	 * - Lấy thông tin về peer được gửi từ client.<br>
	 * - Lưu thông tin vào danh sách peer.<br>
	 * - Truyền thông tin danh sách peer đến peer vừa đưa vào theo phương thức giao<br>
	 * tiếp TextProtocol.<br>
	 * - Thông báo đến tất cả những node còn lại để cập nhật danh sách peer.<br>
	 * => Các tham số cần là:<br>
	 * - Message Data.<br>
	 * - Socket của client đang đợi để lấy dữ liệu.<br>
	 */
	public void handleJoinMsg(JSONObject data, Socket peerSocket);
	
	/**
	 * Khi nhận được msg leave thì việc bootstrap cần làm là:<br>
	 * - Lấy thông tin về peer được gửi từ client.<br>
	 * - Xóa thông tin của node trong danh sách peer.<br>
	 * - (?) Truyền msg ok đã xóa ok đến peer.<br>
	 * => Các tham số cần là:<br>
	 * - Message Data.<br>
	 * - Socket của client đang đợi để lấy dữ liệu.<br>
	 */
	public void handleLeaveMsg(JSONObject data, Socket peerSocket);
	
	/**
	 * Khi nhận được msg update thì việc bootstrap cần làm là:<br>
	 * - Lấy thông tin về peer được gửi từ client.<br>
	 * - Cập nhật thông tin của node trong danh sách.<br>
	 * - Tung broadcast đến tất cả những peer trong list peer.
	 * => Các tham số cần là:<br>
	 * - Message Data.<br>
	 * - Trong trường hợp này bootstrap node đóng vai trò là client gửi<br>
	 * yêu cầu đến tất cả server là tất cả các peer node trong list peer.
	 */
	public void handleUpdateMsg(JSONObject data);
	
	/**
	 * Gửi thông điệp đến một socket peer.
	 * @param peerSocket
	 * @param data
	 */
	public void sendBroadCast(Socket peerSocket, JSONObject data);
}
