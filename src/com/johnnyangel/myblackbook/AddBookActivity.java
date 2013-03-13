package com.johnnyangel.myblackbook;

import java.io.File;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
//import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.johnnyangel.myblackbook.ui.AppAlertDialog;
import com.johnnyangel.myblackbook.ui.AppAlertDialog.OnDialogDoneListener;
import com.johnnyangel.myblackbook.ui.CustomCheckBox;
import com.johnnyangel.myblackbook.util.AppServiceManager;
import com.johnnyangel.myblackbook.util.FileIO;
import com.johnnyangel.myblackbook.util.MyApplicationProperties;
import com.johnnyangel.myblackbook.xml.Book;
import com.johnnyangel.myblackbook.xml.ImageIdType;
import com.johnnyangel.myblackbook.xml.PasswordRecoveryType;
import com.johnnyangel.myblackbook.xml.PasswordType;

public class AddBookActivity extends Activity implements OnDialogDoneListener {
    
	private static final Logger logger = LoggerFactory.getLogger();
	
    private static final String activityName = "AddBookActivity";

	private static final int SELECT_PICTURE = 1;
	
	private static BigInteger BOOK_ID;
	
    private Uri selectedImagePath;
    private String filemanagerstring;
	
	private String temp_photo_file; 
	private String bookDirectory;
	private String bookFileName;
	private String currentView;
	
	private EditText bookName;
	private EditText password;
	private EditText confirm_password;
	private EditText question1;
	private EditText answer1;
	private EditText question2;
	private EditText answer2;
	
	private CustomCheckBox 	mCheckbox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 BOOK_ID = new BigInteger(""+System.currentTimeMillis());
		
    	setTempPhotoFile(BOOK_ID + ".png");
    	
		setContentView(R.layout.activity_add_book);
		this.setCurrentView(activityName);

		AppServiceManager.setActivityStack(this);
		
		ImageButton addButton = (ImageButton)findViewById(R.id.add_book_button);
		addButton.setOnClickListener( new View.OnClickListener() {
		    public void onClick(View v) {
		    	boolean valid = validate();

		        if(valid){
		        	if(FileIO.createDirectory(getBookDirectory())){
			        	Book book = instantiate();
			        	
						boolean wroteFile = FileIO.unMarshalXml(book, getBookDirectory(),getBookFileName());
			    		
						if(!wroteFile)
							Toast.makeText(getApplicationContext(), "Error writing data file to SD card for "+ MyApplicationProperties.getApplicationName() +" application", Toast.LENGTH_LONG).show();
				        	
						Intent intent = new Intent(getApplicationContext(),MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        startActivity(intent);
		        	} else {
						Toast.makeText(getApplicationContext(), "Error creating book directory on SD card for "+ MyApplicationProperties.getApplicationName() +" application", Toast.LENGTH_LONG).show();
		        	}
		        }

		    }
		});
		
		ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel_book_button);
		cancelButton.setOnClickListener( new View.OnClickListener() {
		    public void onClick(View v) {
	        	Intent intent = new Intent(getApplicationContext(),MainActivity.class);
	        	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
		    }
		});
		
		CustomCheckBox checkBox = (CustomCheckBox)findViewById(R.id.security_checkbox);
		checkBox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		    	logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onCheckedChanged() isChecked " + isChecked);
		    	LinearLayout security_layout = ((LinearLayout) findViewById(R.id.security_layout));
		        if ( isChecked )
		        {
		        	security_layout.setVisibility(View.VISIBLE);
		        } else {
		        	security_layout.setVisibility(View.GONE);
		        }
		    }

		});
		
		ImageButton bookImageButton = (ImageButton)findViewById(R.id.add_image_button);
		bookImageButton.setOnClickListener( new View.OnClickListener() {
			public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, getTempUri());
                intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
                startActivityForResult(intent, 1);
		    }
		});
	
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
  	    logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onActivityResult() value of requestCode " + requestCode);
	    switch (requestCode) {
		    case SELECT_PICTURE:
		        if (resultCode == RESULT_OK) {  
		          if (data!=null){
	
		        	  logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - onActivityResult() value of tempUri " + getTempUri());
		               //String filePath= getAppImageDirectory() + "/" + TEMP_PHOTO_FILE;
		               
		               this.selectedImagePath = getTempUri();
		               this.filemanagerstring = this.selectedImagePath.getPath();
		                		
		               Bitmap selectedImage =  BitmapFactory.decodeFile(this.filemanagerstring);
		               ImageButton bookImageButton = (ImageButton)findViewById(R.id.add_image_button);
		               bookImageButton.setImageBitmap(selectedImage);

	
		          }
		        }
	    }
		
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
    
	public String getBookDirectory() {
		return bookDirectory;
	}

	public void setBookDirectory(String bookDirectory) {
        String tempDirectory = bookDirectory.replaceAll("\\s+", "_");
        tempDirectory = tempDirectory.replaceAll("-", "_");
        
		this.bookDirectory = MyApplicationProperties.getApplicationDataDirectory()+File.separator+tempDirectory;
	}
	
    private void setBookFileName(String f) {
        String tempFileName = f.replaceAll("\\s+", "_");
        tempFileName = tempFileName.replaceAll("-", "_");
        setBookDirectory(tempFileName);
    	this.bookFileName = tempFileName+".xml";
    }
    
    private String getBookFileName() {
    	return this.bookFileName;
    }
	
    private void setTempPhotoFile(String f) {
    	this.temp_photo_file = f;
    }
    
    private String getTempPhotoFile() {
    	if(this.temp_photo_file == null)
    		this.temp_photo_file = BOOK_ID + ".png";
    	return this.temp_photo_file;
    }
	
    private Uri getTempUri() {
    	return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
    	
    	return FileIO.createFile(MyApplicationProperties.getApplicationImageDirectory(),getTempPhotoFile());

    }
    
    /*
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
    */
    
    public boolean validate(){
    	boolean valid = true;
    	boolean isFocusSet = false;
        bookName = (EditText) findViewById(R.id.add_book_name);

        
        if( bookName.getText().toString().length() == 0 ){
        	bookName.setHint(getString(R.string.add_book_name));
            bookName.setError( "Book name is required and can not contain special characters!" );
            if(!isFocusSet){
	            bookName.setFocusableInTouchMode(true);
	            bookName.requestFocus();
	            isFocusSet = true;
            }
            valid = false;
            
        }
        
        Pattern p = Pattern.compile("^.*[^a-zA-Z0-9 ].*$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(bookName.getText().toString());
        boolean hasSpecialCharacters = m.find();

        if (hasSpecialCharacters){
        	bookName.setHint(getString(R.string.add_book_name));
            bookName.setError( "Book name is required and can not contain special characters!" );
            if(!isFocusSet){
	            bookName.setFocusableInTouchMode(true);
	            bookName.requestFocus();
	            isFocusSet = true;
            }
            valid = false;		       
        }
        
        if(valid) {
        	setBookFileName(bookName.getText().toString());
        
	        if(FileIO.doesFileExist(getBookDirectory(), getBookFileName())){
	            FragmentManager.enableDebugLogging(true);
	            FragmentTransaction ft = getFragmentManager().beginTransaction();
	            AppAlertDialog a = AppAlertDialog.newInstance("Book Already Exists!","Would you like to overwrite the existing book!");
	            a.show(ft, "Overwrite File");  	
	            valid = false;
	        }
        }
        
        password = (EditText) findViewById(R.id.add_password);
        
        if( password.getText().toString().length() != 0 ){
        	if (password.getText().toString().length() < 8){
            	password.setHint(getString(R.string.add_password));
                password.setError( "Password must be greater than 8 characters!" );
                if(!isFocusSet){
    	            password.setFocusableInTouchMode(true);
    	            password.requestFocus();
    	            isFocusSet = true;
                }
                valid = false;
            	
            }
        } 
        
        confirm_password = (EditText) findViewById(R.id.add_confirm_password);
        
        if( password.getText().toString().length() != 0 && confirm_password.getText().toString().length() == 0 ){
        	confirm_password.setHint(getString(R.string.add_confirm_password));
        	confirm_password.setError( "Confirm Password is required!" );
            if(!isFocusSet){
            	confirm_password.setFocusableInTouchMode(true);
            	confirm_password.requestFocus();
	            isFocusSet = true;
            }
            valid = false;
        } else if(password.getText().toString().length() != 0 && !password.getText().toString().equals(confirm_password.getText().toString())){
        	confirm_password.setHint(getString(R.string.add_confirm_password));
        	confirm_password.setError( "Password and Confirm Password must match!" );
            if(!isFocusSet){
            	confirm_password.setFocusableInTouchMode(true);
            	confirm_password.requestFocus();
	            isFocusSet = true;
            }
            valid = false;		        	
        }
        
    	mCheckbox = (CustomCheckBox)findViewById(R.id.security_checkbox);
    	
    	question1 = (EditText) findViewById(R.id.security_question_1);
    	answer1 = (EditText) findViewById(R.id.security_answer_1);
    	question2 = (EditText) findViewById(R.id.security_question_2);
    	answer2 = (EditText) findViewById(R.id.security_answer_2);
    	
    	if(mCheckbox.isChecked()){

	        if( question1.getText().toString().length() == 0 ){
	        	question1.setHint(getString(R.string.security_question_1));
	        	question1.setError( "First Question is required!" );
	            if(!isFocusSet){
	            	question1.setFocusableInTouchMode(true);
	            	question1.requestFocus();
		            isFocusSet = true;
	            }
	            valid = false;
	            
	        }

	        if(question1.getText().toString().length() > 1 && answer1.getText().toString().length() == 0 ){
	        	answer1.setHint(getString(R.string.security_answer_1));
	        	answer1.setError( "First answer to question one is required!" );
	            if(!isFocusSet){
	            	answer1.setFocusableInTouchMode(true);
	            	answer1.requestFocus();
		            isFocusSet = true;
	            }
	            valid = false;
	        } 

	        if( question2.getText().toString().length() == 0 ){
	        	question2.setHint(getString(R.string.security_question_2));
	        	question2.setError( "Second Question is required!" );
	            if(!isFocusSet){
	            	question2.setFocusableInTouchMode(true);
	            	question2.requestFocus();
		            isFocusSet = true;
	            }
	            valid = false;
	            
	        }
	        
	        if(question2.getText().toString().length() > 1 && answer2.getText().toString().length() == 0 ){
	        	answer2.setHint(getString(R.string.security_answer_2));
	        	answer2.setError( "Second answer to question two is required!" );
	            if(!isFocusSet){
	            	answer2.setFocusableInTouchMode(true);
	            	answer2.requestFocus();
		            isFocusSet = true;
	            }
	            valid = false;
	        } 
    	}
    	
    	return valid;
    }
    
    public Book instantiate(){
    	
    	Book book = new Book();
    	
        if(mCheckbox.isChecked()){
			int increment = 1;
			PasswordRecoveryType prt = new PasswordRecoveryType();
			PasswordRecoveryType.Security sec1 = new PasswordRecoveryType.Security();
			BigInteger sequence = new BigInteger("1");
			sec1.setId(sequence);
			sec1.setQuestion(question1.getText().toString());
			sec1.setAnswer(answer1.getText().toString());
			prt.getSecurity().add(sec1);
			PasswordRecoveryType.Security sec2 = new PasswordRecoveryType.Security();
			sequence = sequence.add(BigInteger.valueOf(increment));
			sec2.setId(sequence);
			sec2.setQuestion(question2.getText().toString());
			sec2.setAnswer(answer2.getText().toString());
			prt.getSecurity().add(sec2);
			prt.setId(BOOK_ID);
			book.getPasswordRecovery().add(prt);
        }
    	
    	//Create book dates
		Date calendar = new Date();
		book.setCreateDate(calendar.toString());
		book.setModifyDate(calendar.toString());

    	
		if(!password.getText().toString().isEmpty()){
	    	//Create password
	    	PasswordType pwd = new PasswordType();
	    	pwd.setId(BOOK_ID);
	    	pwd.setPasscode(password.getText().toString());
	    	
	    	Calendar expireDate = Calendar.getInstance();  
	    	expireDate.setTime(new Date());
	    	expireDate.add(Calendar.YEAR,1);
	    	pwd.setExpireDate(expireDate.toString());
	    	book.setPassword(pwd);
		}
    	
    	ImageIdType im = new ImageIdType();
    	im.setId(BOOK_ID);
    	
    	
    	String tempBookFileNameUri = null;
    	if(filemanagerstring == null)
    		tempBookFileNameUri = FileIO.getDefaultBookImageUri();
    	else
    		tempBookFileNameUri = filemanagerstring;
    	
    	FileIO.moveFile(MyApplicationProperties.getApplicationImageDirectory(), FileIO.getFileNameFromUri(tempBookFileNameUri), getBookDirectory());
    	
    	im.setUri(getBookDirectory()+File.separator+FileIO.getFileNameFromUri(tempBookFileNameUri));

    	book.setImageId(im);
    	
    	book.setId(BOOK_ID);
    	book.setTitle(bookName.getText().toString());
    	        
        return book;
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
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Confirm Delete ");
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Delete book and validate write new content ");
			FileIO.deleteDirectory(getBookDirectory());
	    	boolean valid = validate();

	        if(valid){
	        	if(FileIO.createDirectory(getBookDirectory())){
		        	Book book = instantiate();
		        	
					boolean wroteFile = FileIO.unMarshalXml(book, getBookDirectory(),getBookFileName());
		    		
					if(!wroteFile)
						Toast.makeText(getApplicationContext(), "Error writing data file to SD card for "+ MyApplicationProperties.getApplicationName() +" application", Toast.LENGTH_LONG).show();
			        	
					Intent intent = new Intent(getApplicationContext(),MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        startActivity(intent);
	        	} else {
					Toast.makeText(getApplicationContext(), "Error creating book directory on SD card for "+ MyApplicationProperties.getApplicationName() +" application", Toast.LENGTH_LONG).show();
	        	}
	        }
		} else {
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Cancel Delete ");
			logger.debug(MyApplicationProperties.getApplicationName()+" - "+activityName+" - Change validate to enter new book name ");	
	      	bookName.setHint(getString(R.string.add_book_name));
            bookName.setError( "Change book name, book with this name already exists!" );
	        bookName.setFocusableInTouchMode(true);
	        bookName.requestFocus();
		}
	}
}
