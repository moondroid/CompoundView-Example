package it.moondroid.compoundview.example;

import it.moondroid.compoundview.example.CompoundView.OnCompoundViewClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements
		OnCompoundViewClickListener {

	CompoundView v1;
	CompoundView v2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		v1 = (CompoundView) findViewById(R.id.compoundview1);
		v2 = (CompoundView) findViewById(R.id.compoundview2);

		v1.setOnCompoundViewClickListener(this);
		v2.setOnCompoundViewClickListener(this);

		v1.requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;

	}

	@Override
	public void onValueConfirmed(View src, String value) {

		
		switch (src.getId()) {
		case R.id.compoundview1:
			//do something
			break;

		case R.id.compoundview2:
			//do something else
			break;
		}

		CompoundView cv = (CompoundView)src;
		Toast.makeText(this, cv.getLabel() + ": " + value, Toast.LENGTH_SHORT)
				.show();

	}

}
