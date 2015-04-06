package com.kolpashikov.jsonexample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
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

public class MainActivity extends Activity {
	final static String LOG = "mLogs";
	
	ExpandableListView elView;
	AdapterHelper ah;
	SimpleExpandableListAdapter adapter;
	TextView tvInfo;
	
	StringBuffer sb = new StringBuffer();
	JSONObject jsonObj;
	JSONArray  jsonArr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		elView = (ExpandableListView)findViewById(R.id.jsonListView);
		
		ah = new AdapterHelper(this);
		adapter = ah.getAdapter();
		elView.setAdapter(adapter);
		
		elView.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Log.d(LOG, "onChildClick: groupPosition-"+groupPosition+", childPosition-"
						+childPosition+", id-"+id );
				tvInfo.setText(ah.getGroupChildText(groupPosition, childPosition));
				
				return false;
			}			
		});
		
		elView.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				Log.d(LOG, "onGroupClick groupPosition = " + groupPosition
						+ " id = " + id);
				// блокируем дальнейшую обработку события для группы с позицией
				// 1
				if (groupPosition == 1)
					return true;

				return false;
			}
		});

		elView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			public void onGroupCollapse(int groupPosition) {
				Log.d(LOG, "onGroupCollapse groupPosition = " + groupPosition);
				tvInfo.setText("Свернули " + ah.getGroupText(groupPosition));
			}
		});
		
		elView.setOnGroupExpandListener(new OnGroupExpandListener() {
			public void onGroupExpand(int groupPosition) {
				Log.d(LOG, "onGroupExpand groupPosition = " + groupPosition);
				tvInfo.setText("Равзвернули " + ah.getGroupText(groupPosition));
			}
		});

		// разворачиваем группу с позицией 2
		elView.expandGroup(2);

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
		AssetManager aMngr = getApplicationContext().getAssets();
		int id = item.getItemId();
		if (id == R.id.action_settings) {	
			
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}











/* */
