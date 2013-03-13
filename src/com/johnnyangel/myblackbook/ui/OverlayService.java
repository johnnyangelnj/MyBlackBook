package com.johnnyangel.myblackbook.ui;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.R;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;

public class OverlayService extends Service {
	
 
	private final IBinder myBinder = new MyLocalBinder();
	
    RelativeLayout oView;
    
    private String activityName = "OverlayService";
    private String currentView;
    
    private static final Logger logger = LoggerFactory.getLogger();
 
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onStartCommand()");

        if (intent !=null && intent.getExtras()!=null)
        	this.setCurrentView( intent.getExtras().getString("currentView"));
        
        this.createCustomMenu();
           
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onCreate()");
        
    }
    

    public void createCustomMenu(){
    	
	    oView = new RelativeLayout(this);  
	    oView.setBackgroundColor(0x88000000); // The translucent black color
	    
	    //0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
	    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
	            WindowManager.LayoutParams.MATCH_PARENT,
	            WindowManager.LayoutParams.MATCH_PARENT,
	            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY|WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
	            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
	            PixelFormat.TRANSLUCENT); 
	    
	    RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
	    mParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
	
	    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
	    View menuLayout =  layoutInflater.inflate(R.layout.activity_custom_menu, oView);
		CustomMenuBar cmb = new CustomMenuBar(menuLayout);
		cmb.setMenuService(this);
		cmb.setCurrentView(this.getCurrentView());
		cmb.setupViews();
	    
	    WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
	    wm.addView(oView, params);  
	    
    }
    
    @Override
    public void onDestroy() {           
        super.onDestroy();
        logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onDestroy()");
        if(oView!=null){
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(oView);
        }

    }

	public String getCurrentView() {
		return currentView;
	}

	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}
	
	public class MyLocalBinder extends Binder {
		public OverlayService getService() {           
            return OverlayService.this;
        }
	}
 
}