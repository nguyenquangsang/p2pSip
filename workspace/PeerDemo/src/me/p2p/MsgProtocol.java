package me.p2p;


public class MsgProtocol {
	/**
	 * Khi một client nối đến một server thì một socket liên kết giữa server và<br>
	 * client sẽ được mở ra và thông điệp này sẽ thể hiện phiên giao dịch được<br>
	 * diễn ra.<br>
	 * Khi gọi session end thì liên lạc giữa client và server sẽ chấm dứt.
	 */
	public static final String START_SESSION_MSG = "session_start";
	
	/**
	 * Khi gọi session end thì liên lạc giữa client và server sẽ chấm dứt.
	 */
	public static final String END_SESSION_MSG = "session_end";
	
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