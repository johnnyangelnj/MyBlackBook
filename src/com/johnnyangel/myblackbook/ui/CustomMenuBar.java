package com.johnnyangel.myblackbook.ui;


import android.app.Service;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.AddBookActivity;
import com.johnnyangel.myblackbook.R;
import com.johnnyangel.myblackbook.SettingsActivity;
import com.johnnyangel.myblackbook.util.AppServiceManager;
//import android.os.Bundle;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;


@SuppressWarnings("deprecation")
public class CustomMenuBar extends TabActivity {
	
	private TabHost mTabHost;
	private static RelativeLayout mMenuPanel;
	private View menuLayout;
	private Service menuService;
	private String currentView;
	
	private static final String activityName = "CustomMenuBar";
	private static final String MainActivityName = "MainActivity";
	//private static final String CreateBookNavigationActivityName = "CreateBookNavigationActivity";
	//private static final String AddBookActivityName = "AddBookActivity";
	//private static final String SettingsActivityName = "SettingsActivity";
	private static final String EXIT = "EXIT";
	
	private static final Logger logger = LoggerFactory.getLogger();
	

	private final static String WEBSITE = "http://www.google.com";
	
	// This isn't necessary but it makes it nice to determine which tab I am on in the switch statement below
	private static class TabItem {
		public final static int WEBSITE = 0;
		public final static int ADD_BOOK = 1;
		public final static int SETTINGS = 2;
		public final static int QUIT = 3;
		
	}
	
	public CustomMenuBar(View _menuLayout){
		menuLayout = _menuLayout;
	}

    
    public RelativeLayout setupViews() {
    	
    	if(getCurrentView().equalsIgnoreCase(EXIT)){
			new Thread(new Runnable() {
				public void run() {	
					AppServiceManager.clearActivityStackChildrenOnly();
					getMenuService().stopSelf();
				}
			}).start();
    	}
    	
    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - setupViews()");
    	
    	mMenuPanel = ((RelativeLayout) menuLayout.findViewById(R.id.menuPopup));
    	//mMenuPanel.setVisibility(View.GONE);
		
    	mTabHost = (TabHost) menuLayout.findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
    	mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);	
    	
    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Current View [" + this.getCurrentView() + "]");
    	
		addMethodTab(new TextView(menuLayout.getContext()), "Website", R.drawable.globe, View.GONE);
		
		if(this.getCurrentView().equalsIgnoreCase(MainActivityName)){
			addMethodTab(new TextView(menuLayout.getContext()), "Add Book", R.drawable.add, View.VISIBLE);
			addMethodTab(new TextView(menuLayout.getContext()), "Settings", R.drawable.tools, View.VISIBLE);
		} else {
			addMethodTab(new TextView(menuLayout.getContext()), "Add Book", R.drawable.add, View.GONE);
			addMethodTab(new TextView(menuLayout.getContext()), "Settings", R.drawable.tools, View.GONE);
		}
		
		addMethodTab(new TextView(menuLayout.getContext()), "Quit", R.drawable.power, View.VISIBLE);

		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String arg0) {
				
				if (mMenuPanel != null) {
					if (mMenuPanel.getVisibility() == View.VISIBLE) {					
						toggleMenu();
					}
					logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Tab "+mTabHost.getCurrentTab()+ " selected");
					switch (mTabHost.getCurrentTab()) {
						case TabItem.WEBSITE:
							//content.setText(getString(R.string.website));
							final Intent visit = new Intent(Intent.ACTION_VIEW);
							visit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							visit.setData(android.net.Uri.parse(WEBSITE));
							
							// Use a thread so that the menu is responsive when clicked
							new Thread(new Runnable() {
								public void run() {
									startActivity(visit);
								}
							}).start();					
							break;
						case TabItem.SETTINGS:
							new Thread(new Runnable() {
								public void run() {	
									Intent dialogIntent = new Intent(menuLayout.getContext(), SettingsActivity.class);
									dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									menuLayout.getContext().startActivity(dialogIntent);
									getMenuService().stopSelf();
								}
							}).start();
							break;
						case TabItem.ADD_BOOK:
							new Thread(new Runnable() {
								public void run() {	
									Intent dialogIntent = new Intent(menuLayout.getContext(), AddBookActivity.class);
									dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									menuLayout.getContext().startActivity(dialogIntent);
									getMenuService().stopSelf();
								}
							}).start();
							break;
						case TabItem.QUIT:
							//content.setText(getString(R.string.quit));
							new Thread(new Runnable() {
								public void run() {	
									AppServiceManager.clearActivityStack();
									getMenuService().stopSelf();
								}
							}).start();
							break;
						default:
							break;
					}
					
					// Handle click on currently selected tab - hide menu bar
					// IMPORTANT: This listener has to appear AFTER the tabs are added
					// Unfortunately, This doesn't work when the current tab contains an activity (except for tab 0)
					// If you only have method tabs then it works perfect
					mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							toggleMenu();
						}
					});
					
					//if you want to reset the current tab
					//mTabHost.setCurrentTab(0);
				}
			}
		});
		
		return mMenuPanel;
    }
    
    // Use this method to add an activity or intent to the tab bar
    /*
    private void addActivityTab(final View view, final String tag, int iconResource, Intent intent, int visible) {
		View tabview = createTabView(mTabHost.getContext(), tag, iconResource);
		tabview.setVisibility(visible);
		
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		mTabHost.addTab(setContent);

	}
    */
    // Use this method if you only want the tab to execute a method
    private void addMethodTab(final View view, final String tag, int iconResource, int visible) {
		View tabview = createTabView(mTabHost.getContext(), tag, iconResource);
		tabview.setVisibility(visible);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		mTabHost.addTab(setContent);

	}
    
    private static View createTabView(final Context context, final String text,
			int iconResource) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_layout, null);

		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

		ImageView icon = (ImageView) view.findViewById(R.id.tabsIcon);
		icon.setImageResource(iconResource);

		return view;
	}
    
    @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			toggleMenu();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
		
    }
    
    public static void toggleMenu() {
		if (mMenuPanel.getVisibility() == View.GONE) {
			mMenuPanel.setVisibility(View.VISIBLE);
		} else {
			mMenuPanel.setVisibility(View.GONE);
		}		
	}


	public Service getMenuService() {
		return menuService;
	}


	public void setMenuService(Service menuService) {
		this.menuService = menuService;
	}


	public String getCurrentView() {
		if(currentView == null)
			return EXIT;
		return currentView;
	}


	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}
}
