package com.johnnyangel.myblackbook;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.google.code.microlog4android.config.PropertyConfigurator;
import com.johnnyangel.myblackbook.ui.Librarian;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.FileIO;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;



public class MainActivity extends Activity  {
	
	private static final Logger logger = LoggerFactory.getLogger();
	
	private static final String overLayService = "com.johnnyangel.myblackbook.ui.OverlayService";
	
	
	//protected Dialog mSplashDialog;
	
	private static final String activityName = "MainActivity";
	private static final String CreateBookNavigationActivityName  = "CreateBookNavigationActivity";
	
    boolean isBound = false;
    
	private String currentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		/*
	    @SuppressWarnings("deprecation")
		MyStateSaver data = (MyStateSaver) getLastNonConfigurationInstance();
	    if (data != null) {
	        // Show splash screen if still loading
	        if (data.showSplashScreen) {
	            showSplashScreen();
	        }
	        
	        setContentView(R.layout.activity_main);       
	 
	        // Rebuild your UI with your saved state here
	    } else {
	        showSplashScreen();
		*/
			PropertyConfigurator.getConfigurator(this).configure();

			AppServiceManager.setActivityStack(this);
			
		    try {
		    	FileIO.loadProperties(this, "myblackbook.properties");
		    } catch (IOException e) {
		    	 Toast.makeText(this, "Can not load "+ MyApplicationProperties.getApplicationName() +" properties file", Toast.LENGTH_LONG).show();
		    }


			boolean doesExternalStorageExist = FileIO.isExternalStoragePresent();
			
			if(doesExternalStorageExist){
				boolean doesMyAppStorageExist = FileIO.createDirectory(MyApplicationProperties.getApplicationDirectoryList());
				
				if(doesMyAppStorageExist){
					
					boolean isBookImagesCreated = FileIO.createResourceImageFile(this, MyApplicationProperties.getApplicationImageDirectory(), MyApplicationProperties.getApplicationDefaultBookImageNames(), ".png");
					logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" isBookImagesCreated "+isBookImagesCreated);
					
					if(isBookImagesCreated){
						sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
					}

					boolean isUserDataDirectoryEmpty = FileIO.isDirectoryEmpty(MyApplicationProperties.getApplicationDataDirectory());
					
					logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  isUserDataDirectoryEmpty "+isUserDataDirectoryEmpty);
					
					if(!isUserDataDirectoryEmpty){
						this.setCurrentView(activityName);
						setContentView(R.layout.activity_main);
						Librarian.getBookCatalog(this.getIntent(), this, ((LinearLayout)findViewById(R.id.myMainLayout)));
					} else {
						setContentView(R.layout.activity_create_book_navigation);
						this.setCurrentView(CreateBookNavigationActivityName);
					}			

				} else {
					Toast.makeText(this, "Can not create "+ MyApplicationProperties.getApplicationName() +" Storage on SD Card", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "SD card required to use "+ MyApplicationProperties.getApplicationName() +" application", Toast.LENGTH_LONG).show();
			}	        
	        
	    //}
	    

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
		Intent intent = new Intent(MainActivity.this, ExitActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putInt("displayView", R.layout.activity_main);
        mBundle.putString("callingClass", "com.johnnyangel.myblackbook.MainActivity");
        intent.putExtras(mBundle);
		startActivity(intent);
		AppServiceManager.stopMyService(this, overLayService);
    }
    
    
	public void addBook(View view) {
		Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
		startActivity(intent);
	}
	
	/*
	@Override
	public Object onRetainNonConfigurationInstance() {
	    MyStateSaver data = new MyStateSaver();
	    // Save your important data here
	 
	    if (mSplashDialog != null) {
	        data.showSplashScreen = true;
	        removeSplashScreen();
	    }
	    return data;
	}
	 

	protected void removeSplashScreen() {
	    if (mSplashDialog != null) {
	        mSplashDialog.dismiss();
	        mSplashDialog = null;
	    }
	}
	 

	protected void showSplashScreen() {
	    mSplashDialog = new Dialog(this, R.style.SplashScreen);
	    mSplashDialog.setContentView(R.layout.activity_splash_screen);
	    mSplashDialog.setCancelable(false);
	    mSplashDialog.show();
	     
	    // Set Runnable to remove splash screen just in case
	    final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	      @Override
	      public void run() {
	        removeSplashScreen();
	      }
	    }, 3000);
	}
	 

	private class MyStateSaver {
	    public boolean showSplashScreen = false;
	}
	*/
	
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
