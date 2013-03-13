package com.johnnyangel.myblackbook;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;


public class SettingsActivity extends Activity {
	
	private static final Logger logger = LoggerFactory.getLogger();
	
    private static final String activityName = "SettingsActivity";

	private String currentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		setContentView(R.layout.activity_settings);
		this.setCurrentView(activityName);
		
		AppServiceManager.setActivityStack(this);

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
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onDestroy()");
    }
    
	
    @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Intent intent = new Intent(this, OverlayActivity.class);
			intent.putExtra("currentView", this.getCurrentView());
			startActivity(intent);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);

    }



	public String getCurrentView() {
		return currentView;
	}


	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}
	
}
