package com.kolpashikov.jsonexample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	final String LOG = "mLogs";
	
	ImageView ivPhoto;
	TextView tvFName, tvLName, tvBirthday, tvSpecialtyID, tvSpecialtyName;
	Bitmap bmp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		setContentView(R.layout.detail);
		
		
	}
}
