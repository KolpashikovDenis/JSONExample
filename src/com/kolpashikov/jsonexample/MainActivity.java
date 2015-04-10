package com.kolpashikov.jsonexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	final static String LOG = "mLogs";
		
	TextView tvInfo;
	ExpandableListView elView;
	
	JSONAdapter ja;
	ArrayList<ArrayList<Map<String, String>>> mainData;
	
	SimpleExpandableListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		elView = (ExpandableListView)findViewById(R.id.jsonListView);
		
		ja = new JSONAdapter(this);
		adapter = ja.getAdapter();
		
		mainData = ja.getMainData();
		elView.setAdapter(adapter);
		
		elView.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
//				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), DetailActivity.class);				
				HashMap<String, String> t = ja.getChild(groupPosition, childPosition);
				intent.putExtra(Constants.JSON_FNAME, t.get(Constants.JSON_FNAME));
				intent.putExtra(Constants.JSON_LNAME, t.get(Constants.JSON_LNAME));
				intent.putExtra(Constants.JSON_BIRTHDAY, t.get(Constants.JSON_BIRTHDAY));
				intent.putExtra(Constants.JSON_AVATAR, t.get(Constants.JSON_AVATAR));
				intent.putExtra(Constants.JSON_SPECIALTY_ID, t.get(Constants.JSON_SPECIALTY_ID));
				intent.putExtra(Constants.JSON_SPECIALTY_NAME, t.get(Constants.JSON_SPECIALTY_NAME));
				startActivity(intent);
				/*
				StringBuilder sb = new StringBuilder();
				sb.append(t.get(Constants.JSON_FNAME) + "\n");
				String s = t.get(Constants.JSON_BIRTHDAY);
				if( (s.length() == 0)||(s.equals("null")) ) s = "нет ДР";
				sb.append(s + "\n");
				sb.append(t.get(Constants.JSON_SPECIALTY_ID) + "\n");
				sb.append(t.get(Constants.JSON_SPECIALTY_NAME)+ "\n");
				
				Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show(); */
				return false;				
			}			
		});
		
		elView.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				Log.d(LOG, "onGroupClick groupPosition = " + groupPosition
						+ " id = " + id);

				return false;
			}
		});

		elView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			public void onGroupCollapse(int groupPosition) {
				Log.d(LOG, "onGroupCollapse groupPosition = " + groupPosition);
//				tvInfo.setText("Свернули " + ah.getGroupText(groupPosition));
			}
		});
		
		elView.setOnGroupExpandListener(new OnGroupExpandListener() {
			public void onGroupExpand(int groupPosition) {
				Log.d(LOG, "onGroupExpand groupPosition = " + groupPosition);
//				tvInfo.setText("Равзвернули " + ah.getGroupText(groupPosition));
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(LOG, "Вызвано меню");
		int id = item.getItemId();
		if (id == R.id.action_settings) {	
			
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}











/* */
