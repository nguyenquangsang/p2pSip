package me.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import me.p2p.constant.PeerPort;
import me.p2p.spec.IPeer;

public class Peer implements IPeer {
	// msg handler to handle message;
	ServerSocket serverSocket;
	// socket to request to server;
	Socket socket;
	MessageHandler msgHandler;
	Request request;
	InetAddress inetAddress;
	
	public Peer(InetAddress bootstrapAddress) {
		// TODO Auto-generated constructor stub
		try {
			serverSocket = new ServerSocket(PeerPort.PORT_PEER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			socket = new Socket(bootstrapAddress, PeerPort.PORT_BOOTSTRAP);
			request = new Request(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public InetAddress getAddress() {
		return inetAddress;
	}

	@Override
	public void join() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
