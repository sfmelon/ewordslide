package com.example.ewordslide;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ScreenService extends Service {

	private ScreenReceiver mReceiver = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		KeyguardManager.KeyguardLock k1;

		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		KeyguardManager km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		k1= km.newKeyguardLock("IN");
		k1.disableKeyguard();


		mReceiver = new ScreenReceiver();		
	     IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
	     filter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);

		if(intent != null){
			if(intent.getAction()==null){
				if(mReceiver==null){
					mReceiver = new ScreenReceiver();					
				     IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
				     filter.addAction(Intent.ACTION_SCREEN_OFF);
					registerReceiver(mReceiver, filter);
				}
			}
		}
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy(){		 	
		super.onDestroy();

		if(mReceiver != null){
			unregisterReceiver(mReceiver);
		}
	}
}
