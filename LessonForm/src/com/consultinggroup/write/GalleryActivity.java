package com.consultinggroup.write;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.photo.util.Res;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GalleryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("activity_gallery"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

}
