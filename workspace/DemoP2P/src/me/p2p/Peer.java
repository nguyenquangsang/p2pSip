package me.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import me.p2p.constant.PeerPort;
import me.p2p.spec.IPeer;
import me.p2p.spec.MessageCallback;

public class Peer extends Thread implements IPeer, MessageCallback {
	final int MAX_CONNECTION = 20;
	// msg handler to handle message;
	ServerSocket serverSocket;
	/*
	 * Chỉ có một kết nối để update dữ liệu tại một thời điểm nên chỉ cần một
	 * MessageHandler;
	 */
	MessageHandler msgHandler;

	// socket to request to server;
	Socket socket;
	Request request;

	InetAddress localAddress;
	InetAddress bstrAddress;

	/**
	 * Chứa thông tin của một peer. Bao gồm:<br>
	 * - Thông tin về địa chỉ ip của peer.<br>
	 * - Thông tin về username đăng ký.
	 */
	PeerInfo peerInfo;

	/**
	 * Biến dùng để set tình trạng cho Peer: có tiếp tục lắng nghe request<br>
	 * từ những peer khác hay không?
	 */
	boolean shutdown = false;

	public Peer(String userName, InetAddress localAdress, InetAddress bootstrapAddress) {
		// khởi tạo inetadress;
		if (localAdress != null) {
			/**
			 * Khởi tạo lại local Address bằng cách truyền lại giá trị
			 * localAdress. Trên máy java thông thường thì sử dụng được nhưng
			 * trên thiết bị Android thì ko sử dụng được, nên phải lấy ip theo
			 * cách khác và khởi tạo theo cách này;
			 */
			this.localAddress = localAdress;
		} else {
			try {
				this.localAddress = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// khởi tạo thông tin về peerInfo;
		peerInfo = new PeerInfo(localAdress.getHostAddress(), userName);
		
		// khởi tạo serverSocket cho việc lắng nghe update thông tin từ những nút khác
		try {
			serverSocket = new ServerSocket(PeerPort.PORT_PEER, MAX_CONNECTION,
					localAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// khởi tạo socket bootstrap và request cho để message cho nó;
		try {
			this.bstrAddress = bootstrapAddress;
			socket = new Socket(this.bstrAddress, PeerPort.PORT_BOOTSTRAP);
			request = new Request(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Peer(String userName, InetAddress bootstrapAddress) {
		// TODO Auto-generated constructor stub
		this(userName, null, bootstrapAddress);
	}

	public InetAddress getAddress() {
		return localAddress;
	}

	@Override
	public void joinRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveRequest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateRequest() {
		// TODO Auto-generated method stub

	}

	/**
	 * Implement as Server, always listen change from other node;
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!shutdown) {
			// listen;
			try {
				Socket localSocket = serverSocket.accept();
				synchronized (msgHandler) {
					msgHandler = new MessageHandler(localSocket, this);
					msgHandler.handleMessage();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		msgHandler.stopHandle();
	}

	public void shutdown() {
		shutdown = true;
	}

	@Override
	public void onSessionStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessage(Socket peerSocket, JSONObject data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionEnd() {
		// TODO Auto-generated method stub

	}
}
