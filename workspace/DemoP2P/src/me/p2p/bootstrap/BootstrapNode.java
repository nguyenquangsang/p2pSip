package me.p2p.bootstrap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import me.p2p.Log;
import me.p2p.PeerInfoParser;
import me.p2p.constant.PeerPort;
import me.p2p.message.EMsgType;
import me.p2p.message.Message;
import me.p2p.message.MessageParser;
import me.p2p.request.Request;
import me.p2p.request.RequestHandler;
import me.p2p.resource.DataManager;
import me.p2p.spec.IBootstrap;
import me.p2p.spec.MessageCallback;

import org.json.JSONObject;

public class BootstrapNode extends Thread implements MessageCallback,
		IBootstrap {
	final int MAX_CON = 5;
	final String TAG = "BootstrapNode";
	final String filePath = "E:/";

	ServerSocket serverSocket;
	InetAddress inetAddress;
	MessageParser msgParser;
	/**
	 * Request dùng để gửi dữ liệu trở lại cho peer node;
	 */
	Request request;

	/**
	 * Đối tượng quản lý danh sách peer;
	 */
	DataManager dataManager;

	// create bootstrap node with default port
	public BootstrapNode() {
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			serverSocket = new ServerSocket(PeerPort.PORT_BOOTSTRAP, MAX_CON,
					inetAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DataManager.prepare(filePath);
		dataManager = DataManager.getInstance();
	}

	// run it
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			try {
				Socket socket = serverSocket.accept();
				RequestHandler requestHandler = new RequestHandler(socket, this);
				requestHandler.handleRequest();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void listen() {
		Log.logToConsole(TAG, "IP Adress: " + inetAddress.getHostAddress());
		Log.logToConsole(TAG, "Bootstrap Listening...");
		start();
	}

	public synchronized void onMessage(Socket peerSocket, JSONObject data) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onMessage(): " + data.toString());

		msgParser = new MessageParser(data);
		EMsgType msgType = msgParser.getMessageType();
		JSONObject msgData = msgParser.getMessageData();

		switch (msgType) {
		case JOIN: {
			handleJoinMsg(msgData, peerSocket);
		}
			break;

		case LEAVE: {
			handleLeaveMsg(msgData, peerSocket);
		}
			break;
		case UPDATE: {
			handleUpdateMsg(msgData);
		}
			break;
			
		case TRANSFER_LIST: {
			// bootstrap do nothing with this request;
		}
			break;
		}
	}

	@Override
	public synchronized void onSessionStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void onSessionEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void handleJoinMsg(
			JSONObject data /* peer info data */, Socket peerSocket) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "handleJoinMsg()");

		/* Lưu thông tin của peer vừa giao tiếp vào file list peer */
		PeerInfoParser piParser = new PeerInfoParser(data);
		dataManager.add(piParser.getPeerInfo());

		/* Gửi danh sách peer đến nút đang giao tiếp */
		Request request = new Request(peerSocket);
		request.startSession();
		request.startMsg();

		// build message;
		Message message = new Message(EMsgType.TRANSFER_LIST,
				dataManager.getJsonListPeer());

		// send message;
		request.sendMessage(message);
		request.endMsg();
		request.endSession();
	}

	@Override
	public synchronized void handleLeaveMsg(JSONObject data, Socket peerSocket) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "handleLeaveMsg()");
	}

	@Override
	public synchronized void handleUpdateMsg(JSONObject data) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "handleUpdateMsg()");
	}

	@Override
	public synchronized void sendBroadCast(Socket peerSocket, JSONObject data) {
		// TODO Auto-generated method stub

	}
}
