package me.p2p.peermain;

import org.zoolu.sip.address.NameAddress;

import local.ua.UserAgent;
import local.ua.UserAgentListener;
import me.p2p.Peer;
import me.p2p.PeerInfo;
import me.p2p.log.Log;
import me.p2p.specify.PeerCallback;
import me.sip.SipNode;

public class PeerNode implements PeerCallback, UserAgentListener {
	final String TAG = "PeerNode";
	String bootstrapAddress;
	String userName;
	String filePath;
	Peer peer;
	SipNode sipNode;
	
	public PeerNode(String filePath, String userName, String bootstrapAddress) {
		// TODO Auto-generated constructor stub
		this.filePath = filePath;
		this.userName = userName;
		this.bootstrapAddress = bootstrapAddress;
		
		this.peer = new Peer(this.filePath, this.userName, this.bootstrapAddress);
		this.peer.setPeerCallback(this);
	}
	
	public void run() {
		// peer node listen;
		peer.listenRequest();
		
		// peer node call join;
		peer.joinRequest();
	}
	
	@Override
	public void onJoined(Peer peer) {
		Log.logToConsole(TAG, "onJoined(): Peer has joined network");
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "Init SipNode");
		sipNode = new SipNode(peer, this);
		// listen for incomming call;
		Log.logToConsole(TAG, "Listen for incomming call");
		sipNode.listen();
	}

	@Override
	public void onAddedNode(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdated(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaved(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallIncoming(UserAgent ua, NameAddress caller,
			NameAddress callee) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallIncoming(): " + caller.getAddress() + " calling...");
		ua.accept();
	}

	@Override
	public void onUaCallCancelled(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallCancelled()");
	}

	@Override
	public void onUaCallRinging(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallRinging()");
	}

	@Override
	public void onUaCallAccepted(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallAccepted()");
		
	}

	@Override
	public void onUaCallTrasferred(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallFailed()");
	}

	@Override
	public void onUaCallFailed(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallFailed()");
	}

	@Override
	public void onUaCallClosed(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "onUaCallClosed()");
	}
}
