package me.sip;

import org.zoolu.sip.address.SipURL;

import me.p2p.PeerInfo;

public class SipUrlBuilder {
	PeerInfo peerInfo;
	String stringSipUrl;
	
	public SipUrlBuilder(PeerInfo peerInfo) {
		// TODO Auto-generated constructor stub
		this.peerInfo = peerInfo;
		action();
	}
	
	private void action() {
		SipURL sipURL = new SipURL(peerInfo.userName, peerInfo.address);
		stringSipUrl =sipURL.toString();
	}
	
	public String build() {
		return stringSipUrl;
	}
}
