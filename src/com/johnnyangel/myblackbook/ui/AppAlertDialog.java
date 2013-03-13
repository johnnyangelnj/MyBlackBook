package com.johnnyangel.myblackbook.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AppAlertDialog extends DialogFragment implements DialogInterface.OnClickListener {
	
	public interface OnDialogDoneListener {
		public void onDialogDone(String tag, CharSequence message, boolean cancelled);
	}
	
	
	public static AppAlertDialog newInstance(String title, String message) {
		AppAlertDialog dialog = new AppAlertDialog();	  
		Bundle bundle = new Bundle(); 
		bundle.putString("title", title);
		bundle.putString("message", message); 
		dialog.setArguments(bundle); 
		return dialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setCancelable(true);
		int style = DialogFragment.STYLE_NORMAL, theme = 0;
		setStyle(style, theme);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(this.getArguments().getString("title"));
		builder.setPositiveButton("Ok", this);
		builder.setNegativeButton("Cancel", this);
		builder.setMessage(this.getArguments().getString("message"));
		return builder.create();
	}

	public void onClick(DialogInterface dialog, int returnCode) {
		OnDialogDoneListener act = (OnDialogDoneListener) getActivity();
		boolean result = false;
		if (returnCode == AlertDialog.BUTTON_POSITIVE) {
			result = true;
		}
		act.onDialogDone(getTag(), " Dialog returned with ", result);
	}
	
}


