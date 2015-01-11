package android.me.p2psip.activity;

import me.p2p.Peer;
import me.p2p.PeerInfo;
import me.p2p.log.Log;
import me.p2p.specify.PeerCallback;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.me.p2psip.R;
import android.me.p2psip.adapter.AListPeer;
import android.me.p2psip.application.MeApplication;
import android.me.p2psip.service.SipService;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements PeerCallback {
	final String TAG = "MainActivity";
	final String bootstrapIpAddress = "192.168.0.105";
	final String userName = "SangNguyen";

	MeApplication mApplication;
	ListView listPeerView;
	AListPeer aListPeer;

	Peer mPeer;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get ip address;
		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
		@SuppressWarnings("deprecation")
		final String ipAddress = Formatter.formatIpAddress(wm
				.getConnectionInfo().getIpAddress());
		final String fileListPeerPath = getFilesDir().getAbsolutePath();
		
		Log.filePathLog = fileListPeerPath;
		mApplication = (MeApplication) getApplication();

		new Thread("PeerStartUp") {
			public void run() {
				mPeer = new Peer(fileListPeerPath, userName, ipAddress,
						bootstrapIpAddress);
				mPeer.setPeerCallback(MainActivity.this);

				mPeer.listenRequest();
				mPeer.joinRequest();
			};
		}.start();

		listPeerView = (ListView) findViewById(R.id.listPeer);
		listPeerView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				PeerInfo peerInfo = (PeerInfo) arg0.getAdapter().getItem(arg2);
				Intent intent = new Intent(MainActivity.this,
						CallActivity.class);
				intent.putExtra("peer_info", peerInfo);

				startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mPeer.shutdown();
		
		super.onDestroy();
	}

	@Override
	public void onJoined(final Peer peer) {
		// TODO Auto-generated method stub
		mApplication.setPeer(mPeer);
		
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				aListPeer = new AListPeer(MainActivity.this, R.layout.listpeer_item, mPeer
						.getDataManager().getListPeerInfo());
				listPeerView.setAdapter(aListPeer);
			}
		});
		
		/*
		 * Khởi tạo SipService cho những thành phần khác sử dụng;
		 */
		Intent intent = new Intent(this, SipService.class);
		startService(intent);
	}

	@Override
	public void onAddedNode(final PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.logToConsole(TAG, "onAddedNode(): " + peerInfo.toJSONObject().toString());
				aListPeer.notifyDataSetChanged();
			}
		});
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		/*
		 * Dừng SipService;
		 */
		Intent intent = new Intent(this, SipService.class);
		stopService(intent);
	}
}
