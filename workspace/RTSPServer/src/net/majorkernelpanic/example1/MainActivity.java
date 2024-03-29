package net.majorkernelpanic.example1;

import java.io.IOException;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * A straightforward example of how to use the RTSP server included in
 * libstreaming.
 */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	Session mSession;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mSurfaceView = (SurfaceView) findViewById(R.id.surface);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// Configures the SessionBuilder
		try {
			mSession = SessionBuilder.getInstance()
					.setSurfaceHolder(mSurfaceHolder)
					.setContext(getApplicationContext())
					.setCamera(CameraInfo.CAMERA_FACING_FRONT)
					.setAudioEncoder(SessionBuilder.AUDIO_NONE)
					.setVideoEncoder(SessionBuilder.VIDEO_H264).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Starts the RTSP server
		this.startService(new Intent(this, RtspServer.class));
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		mSession.stop();
		mSession.flush();
	}
}
