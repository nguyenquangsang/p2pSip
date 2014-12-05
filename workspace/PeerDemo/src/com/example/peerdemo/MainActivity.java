package com.example.peerdemo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import me.p2p.MessageHandler;
import me.p2p.MsgProtocol;
import me.p2p.Request;
import me.p2p.constant.PeerPort;
import me.p2p.spec.MessageCallback;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements MessageCallback {
	final String TAG = "MainActivity";
	final String data = "{\"msg_type\":\"join\", \"msg_data\":{\"address\":\"0.0.0.0\", \"username\":\"QuangSang\"}}";


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
		setContentView(R.layout.activity_main);

		// WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
		// WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		// int ip = wifiInfo.getIpAddress();
		// String ipAddress = Formatter.formatIpAddress(ip);
		// Log.e(TAG, "Ip Address: " + ipAddress);

		try {
			Socket socket = new Socket("192.168.3.120", PeerPort.PORT_BOOTSTRAP);

			// send request;
			Request request = new Request(socket);
			request.send(MsgProtocol.START_SESSION_MSG);

			for (int i = 0; i < 3; i++) {
				System.out.println("Send Message...");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Send: " + MsgProtocol.START_MSG + "\n");
				// send start_msg;
				request.send(MsgProtocol.START_MSG);

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// send data;
				System.out.println("Send: " + data + "\n");
				request.send(data);

				// send end_msg;
				System.out.println("Send " + MsgProtocol.END_MSG + "\n");
				request.send(MsgProtocol.END_MSG);

				// sleep 500;
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// end session;
			request.send(MsgProtocol.END_SESSION_MSG);
			
			MessageHandler msgHandler = new MessageHandler(socket, this);
			msgHandler.handleMessage();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	public void onSessionStart() {
		// TODO Auto-generated method stub
		Log.e(TAG, "onSessionStart()");
	}

	@Override
	public void onMessage(Socket peerSocket, JSONObject data) {
		// TODO Auto-generated method stub
		Log.e(TAG, data.toString());
	}

	@Override
	public void onSessionEnd() {
		// TODO Auto-generated method stub
		Log.e(TAG, "onSessionEnd()");
	}
}
