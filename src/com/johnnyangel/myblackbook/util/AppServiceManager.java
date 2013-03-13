package com.johnnyangel.myblackbook.util;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;

public class AppServiceManager extends android.app.Application  {
	
	
	private static final Logger logger = LoggerFactory.getLogger();
	
	private static final String activityName = "AppServiceManager";
	
	private static RunningServiceInfo myService;
	
	private static ArrayList<Activity> activityStack = new ArrayList<Activity>();
	
	private static boolean clearChildrenOnly = false;

	
	public AppServiceManager(){
		activityStack = new ArrayList<Activity>();
	}
			
	public static boolean isMyServiceRunning(Context context, String serviceName) {
	    ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceName.equals(service.service.getClassName())) {
	        	setMyService(service);
	            return true;
	        }
	    }
	    return false;
	}
	
	public static boolean stopMyService (Context context, String serviceName) {
		if(isMyServiceRunning(context, serviceName)){
			android.os.Process.killProcess(getMyService().pid);
			System.exit(0);
			return true;
		}
		
		return false;
	}
	
	
	public static boolean clearActivityStackChildrenOnly(){
		boolean successful = false;
		setClearChildrenOnly(true);
		successful = clearActivityStack();
		setClearChildrenOnly(false);
		return successful;
		
	}
	
	public static boolean clearActivityStack(){
		boolean successful = false;
		
		try {
			if(!getActivityStack().isEmpty()){
				for (Activity a : getActivityStack()){
					if(getClearChildrenOnly()){
						if(!a.getLocalClassName().equalsIgnoreCase("MainActivity")){
							logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  clearActivityStack stopping Activity " +a.getLocalClassName());
							a.finish();
							removeActivityFromStack(a);
						}
					} else {
						logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  clearActivityStack stopping Activity " +a.getLocalClassName());
						a.finish();
						removeActivityFromStack(a);
					}
					
				}
			} else {
				logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  clearActivityStack  activityStack is Empty ");

			}
			successful = true;
		} catch(ArrayIndexOutOfBoundsException e){
			logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error clearActivityStack iterating through ActivityStack " +e.getMessage());
		} catch(Exception e){
			logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error clearActivityStack processing ActivityStack " +e.getMessage());
		}
		
		return successful;
	}
	
	
	public static boolean removeActivityFromStack(Activity _activity){
		boolean removed = false;
		
		ArrayList<Activity> tempActivityStack = new ArrayList<Activity>();
		
		if(!getActivityStack().isEmpty()){
			for (Activity a : getActivityStack()){
				if(!_activity.getLocalClassName().equals(a.getLocalClassName())){
					tempActivityStack.add(a);
				} else {
					logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  removeActivityFromActivityStack removing Activity " +_activity.getLocalClassName());
				}
			}
		}
		
		return removed;
	}
	
	public static void setActivityStack(Activity _activity){
		if(isExistInActivityStack(_activity)){
			removeActivityFromStack(_activity);
		}
		logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  setActivityStack adding Activity " +_activity.getLocalClassName());
		activityStack.add(_activity);
		
	}
	
	public static void setActivityStack(ArrayList<Activity> activityList){
		activityStack = activityList;
	}
	
	public static ArrayList<Activity> getActivityStack(){
		return activityStack;
	}
	
	public static void dumpActivityStack(){
		if(!getActivityStack().isEmpty()){
			for (Activity a : getActivityStack()){
				logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+"  dumpActivityStack  Activity " +a.getLocalClassName());
			}
		}
		
	}
	
	public static boolean isExistInActivityStack(Activity _activity){
		boolean doesExist = false;
		if(!getActivityStack().isEmpty()){
			for (Activity a : getActivityStack()){
				if(_activity.getLocalClassName().equals(a.getLocalClassName())){
					doesExist = true;
					break;
				} 
			}
		}
		return doesExist;
	}
	
	private static RunningServiceInfo getMyService(){
		return myService;
	}
	
	private static void setMyService(RunningServiceInfo _myService){
		AppServiceManager.myService = _myService;
	}

	public static boolean getClearChildrenOnly() {
		return clearChildrenOnly;
	}

	public static void setClearChildrenOnly(boolean clearChildrenOnly) {
		AppServiceManager.clearChildrenOnly = clearChildrenOnly;
	}

}
