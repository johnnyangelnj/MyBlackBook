package com.johnnyangel.myblackbook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.ui.OverlayService;
import com.johnnyangel.myblackbook.ui.OverlayService.MyLocalBinder;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;
 

public class OverlayActivity extends Activity {
	
	private static final Logger logger = LoggerFactory.getLogger();
	
	private static final String activityName = "OverlayActivity";
	
	OverlayService myService;
    boolean isBound = false;
	private String currentView;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);    
        Intent myIntent = getIntent();
    	this.setCurrentView(myIntent.getStringExtra("currentView"));
    	
		AppServiceManager.setActivityStack(this);
		
        toggleService();
        finish(); // Just close the Activity after the toggle
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isBound)
        	unbindService(myConnection);
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onDestroy()");
    }
    
    @Override
    public void onBackPressed() {
            super.onBackPressed();
            logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onBackPressed()");
            this.finish();
    }
     
    private void toggleService(){
        Intent intent = new Intent(this, OverlayService.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("currentView", this.getCurrentView());
        intent.putExtras(mBundle);
        
        if(!stopService(intent)){
            startService(intent);
            bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
        } 
        
    }
 
    private ServiceConnection myConnection = new ServiceConnection() {

    public void onServiceConnected(ComponentName className,
            IBinder service) {
        MyLocalBinder binder = (MyLocalBinder) service;
        myService = binder.getService();
        isBound = true;
    }
    
    public void onServiceDisconnected(ComponentName arg0) {
        isBound = false;
    }
    
   };

	public String getCurrentView() {
		return currentView;
	}

	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}
}