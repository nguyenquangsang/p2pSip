package me.sip;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.sip.provider.SipStack;

import local.ua.UserAgent;
import local.ua.UserAgentListener;
import local.ua.UserAgentProfile;
import me.p2p.Peer;
import me.p2p.PeerInfo;
import me.p2p.log.Log;

public class SipNode implements UserAgentListener {
	final String TAG = "SipNode";
	
	final int DEBUG_LEVEL = -1;
	final int SIP_PORT = 5060;
	
	UserAgent userAgent;
	UserAgentProfile userAgentProfile;
	UserAgentListener userAgentListener;
	SipProvider sipProvider;
	
	Peer peer;
	
	public SipNode(Peer peer, UserAgentListener userAgentListener) {
		this.peer = peer;
		this.userAgentListener = userAgentListener;
		
		// init sip stack;
		if (!SipStack.isInit()) {
			SipStack.init();
			SipStack.debug_level = DEBUG_LEVEL;
		}
	
		// create user agent profile
		createUserAgentProfile();
		
		// create SipProvider;
		createSipProvider();
		
		// create useragent;
		this.userAgent = new UserAgent(sipProvider, userAgentProfile, this);
	}
	
	private void createUserAgentProfile() {
		userAgentProfile = new UserAgentProfile();
		
		userAgentProfile.audio = true;
		userAgentProfile.video = true;
	}
	
	private void createSipProvider() {
		sipProvider = new SipProvider(peer.getAddress().toString(), SIP_PORT);
	}

	@Override
	public void onUaCallIncoming(UserAgent ua, NameAddress caller,
			NameAddress callee) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Comming Call");
		
		// remote ua;
		
		// set up remote ua;
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallIncoming(ua, caller, callee);
	}

	@Override
	public void onUaCallCancelled(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Call Canceled");
		/**
		 * Sau khi hủy bỏ một cuộc gọi thì tiếp tục cài đặt cho UserAgent tiếp
		 * tục lắng nghe các cuộc gọi đến khác.
		 */
		ua.listen();
		
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallCancelled(ua);
	}

	@Override
	public void onUaCallRinging(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Call Ring");
		
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallRinging(ua);
	}

	@Override
	public void onUaCallAccepted(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Call Accepted");
		
		// listenet rtp;
		// rtp clinet -> decode by g722 codec;
		
		// tao ra local rtp;
		// streamming send -> encode by g722 codec;
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallAccepted(ua);
	}

	@Override
	public void onUaCallTrasferred(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Call Trasferred");
		/**
		 * Sau khi hủy bỏ một cuộc gọi thì tiếp tục cài đặt cho UserAgent tiếp
		 * tục lắng nghe các cuộc gọi đến khác.
		 */
		ua.listen();
		
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallTrasferred(ua);
	}

	@Override
	public void onUaCallFailed(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Call Failed");
		/**
		 * Sau khi hủy bỏ một cuộc gọi thì tiếp tục cài đặt cho UserAgent tiếp
		 * tục lắng nghe các cuộc gọi đến khác.
		 */
		ua.listen();
		
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallFailed(ua);
	}

	@Override
	public void onUaCallClosed(UserAgent ua) {
		// TODO Auto-generated method stub
		Log.logToConsole(TAG, "UA Call Closed");
		/**
		 * Sau khi hủy bỏ một cuộc gọi thì tiếp tục cài đặt cho UserAgent tiếp
		 * tục lắng nghe các cuộc gọi đến khác.
		 */
		ua.listen();
		
		// remove all rtp service;
		/**
		 * Gọi listener bên ngoài;
		 */
		userAgentListener.onUaCallClosed(ua);
	}
	
	public void callTo(PeerInfo peerInfo) {
		SipURL sipURL = new SipURL(peerInfo.userName, peerInfo.address);
		Log.logToConsole(TAG, "Call to: " + sipURL.toString());
		userAgent.call(sipURL.toString());	
	}
	
	public void callTo(String sipUrl) {
		Log.logToConsole(TAG, "Call to: " + sipUrl);
		userAgent.call(sipUrl);
	}
	
	public void listen() {
		Log.logToConsole(TAG, "This node listen for incoming call...");
		userAgent.listen();
	}
}