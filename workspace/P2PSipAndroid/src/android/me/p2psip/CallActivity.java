package android.me.p2psip;

import org.zoolu.sip.address.NameAddress;

import local.ua.UserAgent;
import local.ua.UserAgentListener;
import me.p2p.Peer;
import me.p2p.PeerInfo;
import me.sip.SipNode;
import android.support.v7.app.ActionBarActivity;
import android.me.p2psip.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CallActivity extends ActionBarActivity implements UserAgentListener {
	MeApplication mApplication;
	
	TextView textView;
	SipNode mSipNode;
	
	Peer mPeer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		
		textView = (TextView) findViewById(R.id.txtSipNodeAddress);
		
		PeerInfo peerInfo = (PeerInfo) getIntent().getSerializableExtra("peer_info");
		textView.setText(peerInfo.toJSONObject().toString());
		
		mApplication = (MeApplication) getApplication();
		mPeer = mApplication.getPeer();
		
		mSipNode = new SipNode(mPeer, this);
		mSipNode.listen();
		
		mSipNode.callTo(peerInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call, menu);
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
	public void onUaCallIncoming(UserAgent ua, NameAddress caller,
			NameAddress callee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallCancelled(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallRinging(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallAccepted(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallTrasferred(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallFailed(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUaCallClosed(UserAgent ua) {
		// TODO Auto-generated method stub
		
	}
}
