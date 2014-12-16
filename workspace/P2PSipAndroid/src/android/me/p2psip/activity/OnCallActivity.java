package android.me.p2psip.activity;

import org.zoolu.sip.address.NameAddress;

import local.ua.UserAgent;
import me.sip.ua.specify.UACListener;
import android.me.p2psip.R;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class OnCallActivity extends SipActivity implements UACListener {
	SurfaceView mSurfaceView;
	ImageButton mBtEndCall;
	
	@Override
	public void onSipServiceConnected(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/*
		 * Cài đặt layout cho Activity;
		 */
		setContentView(R.layout.activity_on_call);
		
		initComponents();
		initViews();
	}
	
	private void initViews() {
		mSurfaceView = (SurfaceView) findViewById(R.id.onCall_SurfaceView);
		mBtEndCall = (ImageButton) findViewById(R.id.onCall_btEnCall);
		mBtEndCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*
				 * Kết thúc cuộc gọi
				 */
				mSipService.hangup();
				
				/*
				 * Tiếp tục lắng nghe cuộc gọi
				 */
				mSipService.listen();
				
				/*
				 * Kết thúc Activity
				 */
				finish();
			}
		});
	}
	
	private void initComponents() {
		mSipService.setUACListener(this);
	}

	@Override
	public void onUACCallIncoming(UserAgent ua, NameAddress caller,
			NameAddress callee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCallUASCancelled(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUACCallClosed(UserAgent ua) {
		// TODO Auto-generated method stub
		/*
		 * Hàm này được gọi khi kết thúc cuộc gọi;
		 */
		finish();
	}
}
