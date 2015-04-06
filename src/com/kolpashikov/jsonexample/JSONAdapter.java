package com.kolpashikov.jsonexample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;

public class JSONAdapter {
	final String LOG = "mLogs";
	
	final String JSON_FNAME = "f_name";
	final String JSON_LNAME = "l_name";
	final String JSON_BIRTHDAY = "birthday";
	final String JSON_AVATAR = "avatr_url";
	final String JSON_SPECIALTY = "specialty";
	final String JSON_SPECIALTY_ID = "specialty_id";
	final String JSON_NAME = "name";
	
	AssetManager aMngr;
	StringBuilder sb;
	Context context;
	
	ArrayList<Map<String, String>> groupSpecialty;
	ArrayList<Map<String, String>> childEmployee, shadowChildEmployee;
	ArrayList<ArrayList<Map<String, String>>> group, shadowGroup;
	HashMap<String, String> hMap;
	
	SimpleExpandableListAdapter adapter;
	
	public JSONAdapter(Context _context){
		context = _context;
	}
	
	SimpleExpandableListAdapter getAdapter(){
		JSONObject jsonObj;
		JSONArray jsonArr;
		
		try{
			InputStreamReader istream = new InputStreamReader(aMngr.open("testTask.json"));
			BufferedReader br = new BufferedReader(istream);
			String line;
			while((line=br.readLine()) != null){
				sb.append(line);
			}
			Log.d(LOG, "After read file");
			br.close();	
			try{
				group = new ArrayList<ArrayList<Map<String, String>>>();
				groupSpecialty = new ArrayList<Map<String, String>>(); // Этот список попадет в 
																	   // ExpandableListView
				jsonObj = new JSONObject(sb.toString());
				jsonArr = jsonObj.getJSONArray("response");
				for(int i = 0; i<jsonArr.length(); i++){
					JSONArray job = jsonArr.getJSONObject(i).getJSONArray("specialty");
					for(int j = 0; j < job.length(); j++){	
						String s = (String)job.getJSONObject(j).get("name");
						if(!groupSpecialty.contains(s)){ // Данного названия должности еще нет в списке
							hMap = new HashMap<String, String>(); 
							hMap.put(JSON_NAME, s); 
							groupSpecialty.add(hMap); // Создали запись о должности
							
							// Теперь заполняем данные о фамилиях которые работают в данной должности
							childEmployee = new ArrayList<Map<String, String>>();
							hMap = new HashMap<String, String>();
							s = jsonArr.getJSONObject(i).getString(JSON_FNAME) + 
									" "+jsonArr.getJSONObject(i).getString(JSON_LNAME);
							hMap.put(JSON_FNAME, s);
							childEmployee.add(hMap);
							group.add(childEmployee);
							
						} else { // Данная должность уже присутствует в списке, тогда....
							int index = groupSpecialty.indexOf(s);
							
						}
					} // for(int j = 0;.....
				}
				Log.d(LOG, "Count of elements: "+ jsonArr.length());
			}catch(JSONException e){
				String s = "JSON Exception " + e.getMessage();
				Log.d(LOG, s);
			}
		}catch(FileNotFoundException e){ 
			String s = "FileNotFoundException: " + e.getMessage();
			Log.d(LOG, s);
		}catch(IOException e){
			String s = "IOException: " + e.getMessage();
			Log.d(LOG, s);
		}		
		return adapter;
	}
	
	String getGroupText(int groupPos){
		
		return null;
	}
	
	ArrayList<Map<String, String>> getChild(int groupPos, int childPos){
		
		return null;
	}
}
