package android.me.p2psip.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.me.p2psip.service.SipService;
import android.me.p2psip.service.SipService.SipServiceBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;

@SuppressLint("InlinedApi")
public abstract class SipActivity extends ActionBarActivity {
	final String TAG = "SipActivity";
	private Bundle mSavedInstanceState;
	private boolean mBound = false;

	ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mBound = false;
			mSipService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mBound = true;
			mSipService = ((SipServiceBinder) service).getService();

			onSipServiceConnected(mSavedInstanceState);
		}
	};

	protected SipService mSipService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mSavedInstanceState = savedInstanceState;

		Intent intent = new Intent(this, SipService.class);
		bindService(intent, mServiceConnection, Context.BIND_IMPORTANT);
	}

	/**
	 * - Hàm này được gọi sau khi Service được kết nối<br>
	 * - Những việc cần phải làm trong hàm này:<br>
	 * + Thiết đặt những thành phần tiếp theo sau khi Service được kết nối<br>
	 * + Không được gọi super()<br>
	 * 
	 * @param savedInstanceState
	 */
	public abstract void onSipServiceConnected(Bundle savedInstanceState);

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		/*
		 * Chấm dứt kết nối đến SipService;
		 */
		if (mBound) {
			unbindService(mServiceConnection);
		}
	}
}
