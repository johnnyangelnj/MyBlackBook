package com.johnnyangel.myblackbook;

import android.app.Activity;
import android.os.Bundle;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;

public class BookNotesActivity extends Activity {

	private static final Logger logger = LoggerFactory.getLogger();

	private static final String activityName = "BookNotesActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_notes);
		
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
    public void onBackPressed() {
        super.onBackPressed();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onBackPressed()");
    }

}
