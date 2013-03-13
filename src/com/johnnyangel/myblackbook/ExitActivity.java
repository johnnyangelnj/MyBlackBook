package com.johnnyangel.myblackbook;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.ui.AppAlertDialog;
import com.johnnyangel.myblackbook.ui.AppAlertDialog.OnDialogDoneListener;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;

public class ExitActivity extends Activity implements OnDialogDoneListener {

	private static final Logger logger = LoggerFactory.getLogger();
	
	//protected Dialog mSplashDialog;
	
	private static final String activityName = "ExitActivity";
	
	private String currentView;
	private Class<?> callingClass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AppServiceManager.setActivityStack(this);
		
        Intent myIntent = getIntent();
		setContentView(myIntent.getIntExtra("displayView", R.layout.activity_main));
		try {
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onCreate() - getting Class for string "+myIntent.getStringExtra("callingClass"));
			this.callingClass = Class.forName(myIntent.getStringExtra("callingClass"));
		} catch (ClassNotFoundException e) {
			logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onCreate() - Class Not found Exception "+e.getMessage());
			e.printStackTrace();
		}

        FragmentManager.enableDebugLogging(true);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AppAlertDialog a = AppAlertDialog.newInstance("Exit My Black Book","Would you like to exit application!");
        a.show(ft, "Exit Application");
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
    public void onBackPressed() {
        super.onBackPressed();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onBackPressed()");
    }
    
	public String getCurrentView() {
		return currentView;
	}

	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}
	
	public void onDialogDone(String tag, CharSequence message, boolean returnCode) {
		logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onDialogDone() message " + tag + message + returnCode);
		
		if(returnCode){
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Leave app ");
			AppServiceManager.clearActivityStack();
            this.finish();
		} else {
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Stay in app ");
			Intent intent = new Intent(getApplicationContext(),this.callingClass);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			AppServiceManager.dumpActivityStack();
	        startActivity(intent); 

		}
	}
}
