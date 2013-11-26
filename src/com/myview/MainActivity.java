package com.myview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myview.R;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends Activity {

	
	private VideoView videoView;
	private LinearLayout linear;
	private ProgressBar bar;
	private MediaController mediaController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		super.onCreate(savedInstanceState);
		
		
//		Intent intent= new Intent();
//		intent.setAction("action");
//		sendBroadcast(intent, "android.mypermission");
		setContentView(R.layout.activity_main);
//		myView view= (myView) findViewById(R.id.myView);
//		view.setImage(R.drawable.ic_launcher);
//		Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//		view.setImage(bitmap);
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		        @Override
		        public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
		            switch (updateStatus) {
		            case 0: // has update
		                UmengUpdateAgent.showUpdateDialog(MainActivity.this, updateInfo);
		                break;
		            case 1: // has no update
		                Toast.makeText(MainActivity.this, "没有更新", Toast.LENGTH_SHORT)
		                        .show();
		                UmengUpdateAgent.showUpdateDialog(MainActivity.this, updateInfo);
		                break;
		            case 2: // none wifi
		                Toast.makeText(MainActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            case 3: // time out
		                Toast.makeText(MainActivity.this, "超时", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            }
		        }
		});
//		init();
	}
	
	public void init() {
		
		videoView= (VideoView) findViewById(R.id.videoView);
		linear= (LinearLayout) findViewById(R.id.prepared);
		bar= (ProgressBar) findViewById(R.id.my_bar);
		bar.setMax(100);
	}
	
	@Override
	protected void onResume() {

		super.onResume();
//		preparedProgressBar();
	}
	
	private void preparedProgressBar() {
		
		new progressThread(new progressHandler()).start();
	}
	
	class progressHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			bar.setProgress(msg.arg1);
			if(msg.arg1== 50) {
				
				linear.setVisibility(View.GONE);
				videoView.setVisibility(View.VISIBLE);
				mediaPlay();
			}
		}
	}
	
	private void mediaPlay() {
		
		String root= Environment.getExternalStorageDirectory().getAbsolutePath();
		
		videoView.setVideoPath(root+"/DCIM/camera/20130915_170852.mp4");
		mediaController= new MediaController(this);
		videoView.setMediaController(mediaController);
		mediaController.setMediaPlayer(videoView);
		videoView.setMinimumHeight(getWindowManager().getDefaultDisplay().getHeight());
		videoView.setMinimumWidth(getWindowManager().getDefaultDisplay().getWidth());
		if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
		
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
//		videoView.start();
//		videoView.requestFocus();
	}
	
	class progressThread extends Thread {
		
		
		private Handler handler;
		
		public progressThread(Handler handler) {
			
			this.handler= handler;
		}
		@Override
		public void run() {

			boolean flag= true;
			int pro= 0;
			while(flag) {
				
				try {
					
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				Message msg= new Message();
				msg.arg1= pro+= 10;
				handler.sendMessage(msg);
				if(pro== 50) {
					
					flag= false;
				}
			}
		}
	}
	
	
	public static Bitmap toGrayImage(Bitmap bitmap){
	    int width=bitmap.getWidth();
	    int height=bitmap.getHeight();
    	Bitmap grayImg = null;
	    try{
	    	grayImg = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
		    Canvas canvas = new Canvas(grayImg);
		    Paint paint = new Paint();
		    ColorMatrix colorMatrix = new ColorMatrix();
		    colorMatrix.setSaturation(0);
		    ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix); 
		    paint.setColorFilter(colorMatrixFilter);
		    canvas.drawBitmap(bitmap,0,0, paint);
	
		    bitmap.recycle();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return grayImg;
	}
	
	
	
	
}
