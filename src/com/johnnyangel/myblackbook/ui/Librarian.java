package com.johnnyangel.myblackbook.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.OpenBookActivity;
import com.johnnyangel.myblackbook.R;
import com.johnnyangel.myblackbook.util.FileIO;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;
import com.johnnyangel.myblackbook.xml.Book;

public class Librarian extends android.app.Application  {
	
	private static final Logger logger = LoggerFactory.getLogger();
	
	private static final String activityName = "Librarian";

	
	private static LinearLayout tableLayout;
	private static TableLayout table;
	
	private static Intent intent;
	private static Context context;
	
	private static TextView bookDirectory;
	private static TextView bookFileName;
	

	public static LinearLayout getBookCatalog(final Intent intent, final Context context, ViewGroup view){
		
		logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" getBookCatalog() "+context.getPackageName());
	    View tableView =   LayoutInflater.from(context).inflate(R.layout.activity_book_catalog,view);
	    
	    setIntent(intent);
	    setContext(context);
	    
	    table = ((TableLayout) tableView.findViewById(R.id.myTableLayout));
	    
		try {
			table = loadTableRow(table);
		} catch (FileNotFoundException e) {
			logger.error(MyApplicationProperties.getApplicationName()+" - "+activityName+" Error getBookCatalog() File not found "+e.getMessage());
			e.printStackTrace();
		}

		return tableLayout;
		
	}
	
	private static TableLayout loadTableRow(TableLayout tableView) throws FileNotFoundException{
		
		Map<String, String>  fileNameList = FileIO.getDirectoryFileList(MyApplicationProperties.getApplicationDataDirectory());
		for (Map.Entry<String, String> entry : fileNameList.entrySet()) {
			
			if(entry.getKey().contains(".xml")){
				TableRow tr = createTableRow(context);
				logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" loadTableRow() processing file "+entry.getKey());
				Book book = new Book();
	    		book = (Book) FileIO.marshalXml(book, entry.getValue(),entry.getKey());
	    		logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" loadTableRow() Book ID "+book.getId());
	    		logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" loadTableRow() Book Name "+book.getTitle());
	    		logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" loadTableRow() Book Image Uri "+book.getImageId().getUri());
	    		
	    		boolean bookLocked = false;
	    		if(book.getPassword()!= null)
	    			bookLocked = (!book.getPassword().getPasscode().isEmpty()?true:false);
	    			
	    		tr.addView(createImageDisplayView(context, entry.getValue(), entry.getKey(), book.getTitle(),new FileInputStream( new File(book.getImageId().getUri()))));
	    		tr.addView(createImageStatusView(context, entry.getValue(), entry.getKey(), book.getTitle(),bookLocked));
	
	    		tableView.addView(tr);
			}
		}
		
		
		return tableView;
	}
	
	@SuppressWarnings("deprecation")
	private static TableRow createTableRow(final Context context){
		TableRow tr = new TableRow(context);
        tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        tr.setPadding(10, 10, 10, 10);
        tr.setBackgroundResource(R.drawable.row_border);
		return tr;
	}
	
    private static View createImageDisplayView(final Context context, String directory, String fileName, String bookName, InputStream imageUri) {
		View view = LayoutInflater.from(context).inflate(R.layout.book_display_layout, null);

		TextView tv = (TextView) view.findViewById(R.id.imageDisplayName);
		
		if(bookName.length() > 20){
			String displayName = bookName.substring(0, 15);
			tv.setText(displayName+"... Book");
		} else 
			tv.setText(bookName+" Book");

		Bitmap selectedImage =  BitmapFactory.decodeStream(imageUri);
		ImageView icon = (ImageView) view.findViewById(R.id.imageIcon);
        icon.setImageBitmap(selectedImage);
        
		TextView myBookName = (TextView) view.findViewById(R.id.BookName);
		myBookName.setText(bookName);
		
		TextView bookDirectory = (TextView) view.findViewById(R.id.BookDirectory);
		bookDirectory.setText(directory);
		
		TextView bookFileName = (TextView) view.findViewById(R.id.BookFileName);
		bookFileName.setText(fileName);
		
        view.setOnClickListener(getOnClickListener());
        view.setOnLongClickListener(getOnLongClickListener());

		return view;
	}
    
    private static View createImageStatusView(final Context context, String directory, String fileName, String bookName, boolean passWordProtected) {
		View view = LayoutInflater.from(context).inflate(R.layout.book_status_layout, null);

		TextView tv = (TextView) view.findViewById(R.id.imageStatusName);
		tv.setTypeface(Typeface.DEFAULT_BOLD);

		ImageView icon = (ImageView) view.findViewById(R.id.imageStatusIcon);
		if(passWordProtected)
			icon.setImageDrawable(context.getResources().getDrawable(R.drawable.book_locked));
		else
			icon.setImageDrawable(context.getResources().getDrawable(R.drawable.book_unlocked));
 
		TextView myBookName = (TextView) view.findViewById(R.id.BookName);
		myBookName.setText(bookName);
		
		TextView bookDirectory = (TextView) view.findViewById(R.id.BookDirectory);
		bookDirectory.setText(directory);
		
		TextView bookFileName = (TextView) view.findViewById(R.id.BookFileName);
		bookFileName.setText(fileName);
		
        view.setOnClickListener(getOnClickListener());
        view.setOnLongClickListener(getOnLongClickListener());

		return view;
	}
    
    private static OnClickListener getOnClickListener(){
    	
         OnClickListener onclicklistener = new OnClickListener() {

            @Override
            public void onClick(View v) {
        		bookDirectory = (TextView) v.findViewById(R.id.BookDirectory);
        		bookFileName = (TextView) v.findViewById(R.id.BookFileName);
            	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" onclicklistener() Book Location "+bookDirectory.getText()+File.separator+bookFileName.getText());
            	Intent intent = new Intent(context, OpenBookActivity.class);
            	context.startActivity(intent);
            }
        };
        
        return onclicklistener;
    }
    
    private static OnLongClickListener getOnLongClickListener(){
    
    	OnLongClickListener onlongclicklistener = new OnLongClickListener() {
    		
	        @Override
	        public boolean onLongClick(View v) {
        		bookDirectory = (TextView) v.findViewById(R.id.BookDirectory);
        		bookFileName = (TextView) v.findViewById(R.id.BookFileName);
            	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" onlongclicklistener() Book Location "+bookDirectory.getText()+File.separator+bookFileName.getText());	        	
            	showMenuOptions(v);

            	return true;
	        }
    	};
    	
    	return onlongclicklistener;
    }
    
    public static boolean deleteBook(String directory, String fileName){
    	boolean successful = false;
    	
    	if(FileIO.doesFileExist(directory, fileName))
    		successful = FileIO.deleteFile(directory, fileName);
    	
    	return successful;

    }
    
    @SuppressLint("NewApi")
	public static void  showMenuOptions(View v){    	
    	PopupMenu popupMenu = new  PopupMenu(context,v); 
    	popupMenu.setOnMenuItemClickListener(getOnMenuItemClickListener ());
    	popupMenu.setOnDismissListener(getOnDismissListener()); 
    	popupMenu.inflate(R.menu.activity_menu);
    	popupMenu.show();
    	
    }

    @SuppressLint("NewApi")
	public static OnDismissListener getOnDismissListener (){
		OnDismissListener ondismisslistener = new OnDismissListener() {
			public void  onDismiss(PopupMenu menu) {
            	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" ondismisslistener()");	        	
			}
	    };
	    return ondismisslistener;
	}
	
	public static OnMenuItemClickListener getOnMenuItemClickListener (){
		OnMenuItemClickListener onmenuitemclicklistener = new OnMenuItemClickListener() {
	    	public boolean onMenuItemClick(MenuItem item) {
	    		
	    		switch (item.getItemId()){
	        		case  R.id.menu_delete :
	                	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" onmenuitemclicklistener() Clicked Delete option");	        	
	                	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" onmenuitemclicklistener() Book Location "+bookDirectory.getText()+File.separator+bookFileName.getText());	        	
	                	FileIO.deleteDirectory(bookDirectory.getText().toString());
	                	context.startActivity(intent);
	                	return true;		
	        		default:			
	        			return false;			
	    		}		
	    		
	    	}
	    };
	    return onmenuitemclicklistener;
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		Librarian.context = context;
	}

	public static Intent getIntent() {
		return intent;
	}

	public static void setIntent(Intent intent) {
		Librarian.intent = intent;
	}
}