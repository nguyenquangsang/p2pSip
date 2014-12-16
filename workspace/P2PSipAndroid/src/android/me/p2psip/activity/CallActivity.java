package android.me.p2psip.activity;

import local.ua.UserAgent;
import me.p2p.PeerInfo;
import me.sip.ua.specify.UASListener;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.me.p2psip.R;
import android.me.p2psip.service.SipService;
import android.me.p2psip.service.SipService.SipServiceBinder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CallActivity extends ActionBarActivity implements UASListener {
	public static final String ACTION_HAS_CALL = "android.me.p2psip.action.HAS_CALL";
	public static final String ACTION_MAKE_CALL = "android.me.p2psip.action.MAKE_CALL";

	final String TAG = "CallActivity";
	SipService mSipService;
	boolean mBound = false;
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

			/*
			 * Gọi hàm callback khi kết nối service thành công;
			 */
			onSipServiceConnected();
		}
	};

	/*
	 * Những view cần thiết để user control activity này;
	 */
	private TextView mTxtCallTo;
	private ImageButton mBtEndCall;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);

		/*
		 * Nối vào SipService đã được cài đặt từ trước;
		 */
		Intent intent = new Intent(this, SipService.class);
		bindService(intent, mServiceConnection, Context.BIND_IMPORTANT);

		/*
		 * Cài đặt những thành phần View của Activity này;
		 */
		initView();
	}

	private void onSipServiceConnected() {
		/*
		 * Cài đặt các thông số cho Service;
		 */
		mSipService.setUASListener(this);

		/*
		 * Lấy thông tin của peer được chỉ định gọi;
		 */
		PeerInfo peerInfo = (PeerInfo) getIntent().getSerializableExtra(
				"peer_info");
		/*
		 * Cài đặt địa chỉ người được gọi;
		 */
		mSipService.setSipUrlCall(peerInfo);

		/*
		 * Cài đặt địa chỉ;
		 */
		mTxtCallTo.setText(mSipService.getCallSipUrl().getUserName());

		/*
		 * Thực hiện cuộc gọi đến peer vừa chọn;
		 */
		mSipService.call();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		/*
		 * Chấm dứt kết nối đến SipService;
		 */
		mSipService.hangup();
		unbindService(mServiceConnection);
	}

	private void initView() {
		mTxtCallTo = (TextView) findViewById(R.id.call_txtCallTo);

		mBtEndCall = (ImageButton) findViewById(R.id.call_btEnCall);
		mBtEndCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Chấm dứt cuộc gọi
				 */
				mSipService.hangup();

				/*
				 * Kết thúc activity;
				 */
				finish();
			}
		});
	}

	@Override
	public void onUASCallRinging(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCallUACAccepted(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCallUACFailed(UserAgent ua) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onUASCallClosed(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}
}