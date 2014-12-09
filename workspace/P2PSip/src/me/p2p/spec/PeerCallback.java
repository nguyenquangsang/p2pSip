package me.p2p.spec;

import me.p2p.Peer;

public interface PeerCallback {
	public void onJoined(Peer peer);
	public void onAddNode(Peer peer);
	public void onUpdate(Peer peer);
}
