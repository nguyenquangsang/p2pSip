package com.example.peerdemo;

import me.p2p.Peer;
import android.app.Application;

public class MeApplication extends Application {
	private Peer peer;
	
	public Peer getPeer() {
		return this.peer;
	}
	
	public void setPeer(Peer peer) {
		this.peer = peer;
	}
}
