package me.p2p.spec;

import me.p2p.Peer;
import me.p2p.PeerInfo;


public interface PeerCallback {
	/**
	 * Hàm này được gọi khi Peer đã nhận data list từ server<br>
	 * và đã trở thành một peer node trong mạng lưới
	 */
	public void onJoined(Peer peer);
	
	/**
	 * Hàm này được gọi khi peer node nhận được yêu cầu add node<br>
	 * từ một peer node khác
	 */
	public void onAddedNode(PeerInfo peerInfo);
	
	/**
	 * Hàm này được gọi khi peer node nhận được yêu cầu update từ<br>
	 * một node khác
	 */
	public void onUpdated(PeerInfo peerInfo);
	
	/**
	 * Hàm này được gọi khi peer node nhận được yêu cầu leave từ<br>
	 * một node khác
	 */
	public void onLeaved(PeerInfo peerInfo);
}
