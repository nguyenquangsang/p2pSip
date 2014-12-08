package com.example.peerdemo;

import me.p2p.Peer;
import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	final String TAG = "MainActivity";
	final String bootstrapIpAddress = "192.168.3.120";
	final String userName = "tobeNguyen";
	Peer peer;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get ip address;
		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
		final String ipAddress = Formatter.formatIpAddress(wm
				.getConnectionInfo().getIpAddress());
		final String fileListPeerPath = getFilesDir().getAbsolutePath();
		
		new Thread("PeerStartUp") {
			public void run() {
				peer = new Peer(fileListPeerPath, userName, ipAddress,
						bootstrapIpAddress);
				peer.listenRequest();
				peer.joinRequest();
			};
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		peer.shutdown();
	}
}
