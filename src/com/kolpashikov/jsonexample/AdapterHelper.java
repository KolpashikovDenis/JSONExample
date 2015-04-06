package com.kolpashikov.jsonexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

public class AdapterHelper {
	final static String ATTR_GROUP_NAME = "groupName";
	final static String ATTR_PHONE_NAME = "phoneName";
	
	// ������������� ���������
	String[] groups = new String[]{ "HTC", "Samsung", "LG", "Sony" };
	
	// ������ ��������� ��� ������� �� ��������������
	String[] phoneHTC = new String[]{ "Sensation", "Desire", "Wildfire", "Hero" };
	String[] phoneSams = new String[]{ "Galaxy SII", "Galaxy Nexus", "Wave" };
	String[] phoneLG = new String[]{ "Optimus", "Optimus Link", "Optimus Black", "Optimus One" };
	String[] phoneSony = new String[]{ "XPeria Z1", "XPeria Z3", "XPeria Z mini" };

	// ��������� ��� �����
	ArrayList<Map<String, String>> groupData;
	
	// ��������� ��� �������� ����� ������
	ArrayList<Map<String, String>> childItemData;
	
	// ����� ��������� ��� ��������� ���������
	// �.�. ArrayList<childDataItem> childData
	ArrayList<ArrayList<Map<String, String>>> childData;
	
	// �� � ���, ��������......
	Map<String, String> m;
	
	// ��������� ��� �������
	SimpleExpandableListAdapter seAdapter;
	
	Context context;
	
	public AdapterHelper(Context _context){
		context = _context;
	}
	
	SimpleExpandableListAdapter getAdapter(){
		//��������� ��� �����
		groupData = new ArrayList<Map<String, String>>();   
		for(String group: groups){
			m = new HashMap<String, String>();
			m.put(ATTR_GROUP_NAME, group);
			groupData.add(m);
		}
		
		String[] groupFrom = new String[]{ "groupName" };
		String[] childFrom = new String[]{ "phoneName" };
		int[] groupTo = new int[]{ android.R.id.text1 };
		int[] childTo = new int[]{ android.R.id.text1 };
		
		// ����� ��������� ��� ��������� ���������
		childData = new ArrayList<ArrayList<Map<String, String>>>();
		
		// ��������� ��� ��������� ����� ������
		childItemData = new ArrayList<Map<String, String>>();
		for(String phone: phoneHTC){
			m = new HashMap<String, String>();
			m.put(ATTR_PHONE_NAME, phone);
			childItemData.add(m);
		}		
		childData.add(childItemData);
		
		childItemData = new ArrayList<Map<String, String>>();
		for(String phone: phoneSams){
			m = new HashMap<String, String>();
			m.put(ATTR_PHONE_NAME, phone);
			childItemData.add(m);
		}
		childData.add(childItemData);
		
		childItemData = new ArrayList<Map<String, String>>();
		for(String phone: phoneLG){
			m = new HashMap<String, String>();
			m.put(ATTR_PHONE_NAME, phone);
			childItemData.add(m);
		}
		childData.add(childItemData);
		
		childItemData = new ArrayList<Map<String, String>>();
		for(String phone: phoneSony){
			m = new HashMap<String, String>();
			m.put(ATTR_PHONE_NAME, phone);
			childItemData.add(m);
		}
		childData.add(childItemData);
		
		seAdapter = new SimpleExpandableListAdapter(context,
				groupData, android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
				childData, android.R.layout.simple_list_item_1, childFrom, childTo);	
		
		return seAdapter;
	}
	
	String getGroupText( int groupPos ){
		return ((Map<String, String>)(seAdapter.getGroup(groupPos))).get(ATTR_GROUP_NAME);
	}
	
	String getChildText( int groupPos, int childPos ){		
		return((Map<String, String>)seAdapter.getChild(groupPos, childPos)).get(ATTR_PHONE_NAME);
	}
	
	String getGroupChildText( int groupPos, int childPos ){
		return getGroupText( groupPos ) + "." + getChildText( groupPos, childPos );
	}
	
}







