package com.johnnyangel.myblackbook;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;

public class OpenBookActivity extends Activity   {
	
	private static final Logger logger = LoggerFactory.getLogger();

	private static final String activityName = "OpenBookActivity";
	
	private RelativeLayout mMenuPanel;
	private TabHost mTabHost;
	
	private static class TabItem {
		public final static int BOOK_CONTACTS = 0;
		public final static int BOOK_IMAGES = 1;
		public final static int BOOK_NOTES = 2;	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_book);
		
		AppServiceManager.setActivityStack(this);
		
		setUpTabMenu(savedInstanceState);
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
	    
		@SuppressWarnings("deprecation")
		private void setUpTabMenu(Bundle savedInstanceState) {
			
	    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - setUpTabMenu()");

			setMenuPanel(((RelativeLayout) findViewById(R.id.openBookPopup)));
			
			LocalActivityManager localActivityManager = new LocalActivityManager(this, false);
			localActivityManager.dispatchCreate(savedInstanceState);
			localActivityManager.dispatchResume(); 
			localActivityManager.dispatchPause(isFinishing());
			
			
	    	mTabHost = (TabHost) getMenuPanel().findViewById(android.R.id.tabhost);
			mTabHost.setup(localActivityManager);
			
	    	mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);	
	    	
	    	Intent bookContactsIntent = new Intent(this, BookContactsActivity.class);
	    	bookContactsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    	Intent bookImagesIntent = new Intent(this, BookImagesActivity.class);
	    	bookImagesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    	Intent bookNotesIntent = new Intent(this, BookNotesActivity.class);
	    	bookNotesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    	
	    	addActivityTab(new TextView(this), "Contacts", R.drawable.contacts, bookContactsIntent);
	    	addActivityTab(new TextView(this), "Images", R.drawable.image_gallery, bookImagesIntent);
	    	addActivityTab(new TextView(this), "Notes", R.drawable.notes, bookNotesIntent);

			mTabHost.setOnTabChangedListener(getOnTabChangedListener());
			
		}
		
		private TabHost.OnTabChangeListener getOnTabChangedListener() {
			
			TabHost.OnTabChangeListener ontabchangedlistener = new TabHost.OnTabChangeListener() {
					@Override
					public void onTabChanged(String arg0) {
						
						if (getMenuPanel() != null) {
	
							logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" Tab "+mTabHost.getCurrentTab()+ " selected");
							switch (mTabHost.getCurrentTab()) {
								case TabItem.BOOK_CONTACTS:
									break;
								case TabItem.BOOK_IMAGES:
									break;
								case TabItem.BOOK_NOTES: 
									break;
								default:
									break;
							}
							
							mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {

								}
							});
						}
					}
				};
			
			return ontabchangedlistener;
		}
		
	    private void addActivityTab(final View view, final String tag, int iconResource, Intent intent) {
			View tabview = createTabView(mTabHost.getContext(), tag, iconResource);
			 
			TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
					.setContent(intent);
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
	    
		public RelativeLayout getMenuPanel() {
			return mMenuPanel;
		}

		public void setMenuPanel(RelativeLayout mMenuPanel) {
			this.mMenuPanel = mMenuPanel;
		}

}