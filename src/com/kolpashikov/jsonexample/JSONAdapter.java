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
	
	AssetManager aMngr;
	StringBuilder sb;
	Context context;
	
	ArrayList<Map<String, String>> groupSpecialty;
	ArrayList<Map<String, String>> childEmployee, shadowChildEmployee;
	ArrayList<ArrayList<Map<String, String>>> group, mainData = null;
	HashMap<String, String> hMap;
	JSONObject jsonObj;
	JSONArray jsonArr;
	
	SimpleExpandableListAdapter adapter;
	
	public JSONAdapter(Context _context){
		context = _context;
	}
	
	SimpleExpandableListAdapter getAdapter(){
		
		aMngr = context.getAssets();
		sb = new StringBuilder();
		try{
			Log.d(LOG, "opening testTask.json");
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
				mainData = new ArrayList<ArrayList<Map<String, String>>>();
				
				groupSpecialty = new ArrayList<Map<String, String>>(); // Этот список попадет в 
																	   // ExpandableListView				
				// Получили сам объект JSON
				jsonObj = new JSONObject(sb.toString());
				// Далее, в нем есть массив с именем "response"
				jsonArr = jsonObj.getJSONArray(Constants.JSON_SPECIALTY);
				// Затем перебираем каждый элемент массива "response"
				int len = jsonArr.length();
				for(int i = 0; i<len; i++){
					// два вспомогательный JSON-объекта
					JSONObject tmpSubObj = jsonArr.getJSONObject(i); // Субобъект с именем, фамилией и т.д.
					JSONArray tmpSubArr = tmpSubObj.getJSONArray(Constants.JSON_SPECIALTY);// Здесь получаю субмассив
					// Получаем имя (s1) и фамилию (s2) сотрудников
					String s1 = jsonArr.getJSONObject(i).getString(Constants.JSON_FNAME);
					String s2 = jsonArr.getJSONObject(i).getString(Constants.JSON_LNAME);
					// Перебираем json-субмассив "specialty"
					for(int j = 0; j < tmpSubArr.length(); j++){	
						String s = (String)tmpSubArr.getJSONObject(j).get(Constants.JSON_SPECIALTY_NAME);
						hMap = new HashMap<String, String>();
						hMap.put(Constants.JSON_SPECIALTY_NAME, s);
						if( !groupSpecialty.contains(hMap) ){ // Данного названия должности еще нет в списке
							
  							groupSpecialty.add(hMap); // Создали запись о должности
							
							// Теперь заполняем данные о фамилиях которые работают в данной должности
							childEmployee = new ArrayList<Map<String, String>>();
							shadowChildEmployee = new ArrayList<Map<String, String>>();
							// здесь заполняем Arraylist'ы для ExpandableListView
							s = upFirstChars(s1, s2);	
							hMap = new HashMap<String, String>();
							hMap.put(Constants.JSON_FNAME, s);
							childEmployee.add(hMap);
							group.add(childEmployee);
							// Тут заполняем ArrayList'ы для mainData, который содержит полные данные о 
							// сотрудниках
							hMap = new HashMap<String, String>();
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_FNAME);							
							hMap.put(Constants.JSON_FNAME, s);
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_LNAME);
							hMap.put(Constants.JSON_LNAME, s);
							s = (String)jsonArr.getJSONObject(i).getString(Constants.JSON_BIRTHDAY);
							hMap.put(Constants.JSON_BIRTHDAY, s);
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_AVATAR);
							hMap.put(Constants.JSON_AVATAR, s);
							int n = tmpSubArr.getJSONObject(j).getInt(Constants.JSON_SPECIALTY_ID);
							s = String.valueOf(n);
							hMap.put(Constants.JSON_SPECIALTY_ID, s);
							s = tmpSubArr.getJSONObject(j).getString(Constants.JSON_SPECIALTY_NAME);
							hMap.put(Constants.JSON_SPECIALTY_NAME, s);
							shadowChildEmployee.add(hMap);
							mainData.add(shadowChildEmployee);
							
						} else { // Данная должность уже присутствует в списке, тогда....
							// ищем в специальностях индекс должности
							int index = groupSpecialty.indexOf(hMap);
							childEmployee = group.get(index); // получаем его из group
							s = upFirstChars(s1, s2);
							hMap = new HashMap<String, String>();
							hMap.put(Constants.JSON_FNAME, s);
							childEmployee.add(hMap);
							
							shadowChildEmployee = mainData.get(index);
							hMap = new HashMap<String, String>();
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_FNAME);							
							hMap.put(Constants.JSON_FNAME, s);
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_LNAME);
							hMap.put(Constants.JSON_LNAME, s);
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_BIRTHDAY);
							hMap.put(Constants.JSON_BIRTHDAY, s);
							s = jsonArr.getJSONObject(i).getString(Constants.JSON_AVATAR);
							hMap.put(Constants.JSON_AVATAR, s);
							int n = tmpSubArr.getJSONObject(j).getInt(Constants.JSON_SPECIALTY_ID);
							s = String.valueOf(n);
							hMap.put(Constants.JSON_SPECIALTY_ID, s);
							s = tmpSubArr.getJSONObject(j).getString(Constants.JSON_SPECIALTY_NAME);
							hMap.put(Constants.JSON_SPECIALTY_NAME, s);
							shadowChildEmployee.add(hMap);
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
		String[] groupFrom = new String[]{ Constants.JSON_SPECIALTY_NAME };
		int[] groupTo = new int[]{ android.R.id.text1 };
		String[] childFrom = new String[]{ Constants.JSON_FNAME };
		int[] childTo = new int[]{ android.R.id.text1 }; 

		adapter = new SimpleExpandableListAdapter(context, 
				groupSpecialty, android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
				group, android.R.layout.simple_list_item_1, childFrom, childTo);
		
		return adapter;
	}
	
	ArrayList<ArrayList<Map<String, String>>> getMainData(){		
		return mainData;
	}
	
	String upFirstChars(String f_name, String l_name){	
		// Сначала строку имени преобразуем в нижний регистр, затем первый символ в верхний регистр....
		String s1 = f_name.toLowerCase();
		char c1[] = s1.toCharArray();
		c1[0] = Character.toUpperCase(c1[0]);	
		// ....то же самое проделываем для фамилии
		String s2 = l_name.toLowerCase();
		char c2[] = s2.toCharArray();
		c2[0] = Character.toUpperCase(c2[0]);
		
		return new String( c1 ) + " " + new String( c2 );
	}
	
	HashMap<String, String> getChild(int groupPos, int childPos){
		HashMap<String, String> t = (HashMap<String, String>)(mainData.get(groupPos)).get(childPos);
		return t;	
	}
	
}
