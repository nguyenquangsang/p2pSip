package me.p2p.request;


public class RequestType {
	/**
	 * Bắt đầu một thông điệp. Một phiên giao dịch có thể gửi rất nhiều thông điệp,<br>
	 * mỗi thông điệp sẽ được mở đầu bằng message này.
	 */
	public static final String START_MSG = "start_msg";
	
	/**
	 * Kết thúc một thông điệp.
	 */
	public static final String END_MSG = "end_msg";
}
