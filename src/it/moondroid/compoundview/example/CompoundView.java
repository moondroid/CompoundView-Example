package it.moondroid.compoundview.example;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompoundView extends LinearLayout {

	private static final String DEFAULT_LABEL = "Label";
	private static final String DEFAULT_HINT = "enter text here";

	private String label;
	private String hint;

	private TextView mTextView;
	private EditText mEditText;
	private Button mButton;
	private OnCompoundViewClickListener listener;
	
	// interface which shall be implemented by the possible listeners
	public interface OnCompoundViewClickListener {
		public void onValueConfirmed(View src, String value);
	}
	
	//registration method for the listener
	public void setOnCompoundViewClickListener(OnCompoundViewClickListener listener){
		this.listener = listener;
	}
	
	private void dispatchOnCompoundViewClickListener(){
		
		String value = mEditText.getText().toString();
		
		//dispatch the event only if the listener is defined and the EditText contains something
		if (listener!=null && !value.equalsIgnoreCase("")) {
			listener.onValueConfirmed(this, value);
		}
	}
	
	// Constructor required for inflation from resource file
	public CompoundView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
		readAttributes(context, attrs);
		
	}

	// Constructor required for in-code creation
	public CompoundView(Context context) {
		super(context);

		init(context);
	}

	private void init(Context c) {

		// Inflate the view from the layout resource.
		final LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.compound_view, this, true);

		// Get references to the child controls.
		// You have to get the view by tag, because if you have multiple controls in your xml with same "id",
		// then when it saves state, and then restores state, all controls with the same id get the same value
		mTextView = (TextView) findViewWithTag("textLabel");
		mEditText = (EditText) findViewWithTag("textEdit");
		mButton = (Button)findViewWithTag("buttonOK");
		
		//hook up the event handler for the Button
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dispatchOnCompoundViewClickListener();
			}
		});
	}

	// Read Attributes from the xml
	// custom attributes are defined in attrs.xml as declare-styleable resources
	private void readAttributes(Context context, AttributeSet attrs) {

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.CompoundView);

		label = ta.getString(R.styleable.CompoundView_label);
		if (label == null) {
			label = DEFAULT_LABEL;
		}
		mTextView.setText(label);
		
		hint = ta.getString(R.styleable.CompoundView_hint);
		if (hint == null) {
			hint = DEFAULT_HINT;
		}
		mEditText.setHint(hint);
		
		// Don't forget this
		ta.recycle();
	}

	//Saves CompoundView state
	@Override
	protected Parcelable onSaveInstanceState() {

		Bundle bundle = new Bundle();
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		bundle.putString("currentEdit", mEditText.getText().toString());
		bundle.putBoolean("isFocused", mEditText.hasFocus());
		return bundle;

	}

	//Restores CompoundView state
	@Override
	protected void onRestoreInstanceState(Parcelable state) {

		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mEditText.setText(bundle.getString("currentEdit"));
			if (bundle.getBoolean("isFocused")) {
				mEditText.requestFocus();
			}
			super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
			return;
		}

		super.onRestoreInstanceState(state);
	}
	
	// Implement this to change child views reposition logic
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
	
	
	public String getText(){
		
		return mEditText.getText().toString();
	}
	
	public void setLabel(String label){
		
		mTextView.setText(label);
	}
	
	public String getLabel(){
		
		return mTextView.getText().toString();
	}
}
