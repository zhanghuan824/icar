package com.auto.client.module.maintenance;

import java.util.ArrayList;
import java.util.HashMap;

import com.actionbarsherlock.app.SherlockActivity;
import com.auto.client.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChooseLocationActivity extends SherlockActivity {

	private String[] _provinces = {"����1", "����", "����", "�㶫"};
	private HashMap<String, ArrayList<String>> _cities = new HashMap<String, ArrayList<String>>();
	
	public ChooseLocationActivity() {
		ArrayList<String> a = new ArrayList<String>();
		a.add("������");
		a.add("��������");
		
		ArrayList<String> b = new ArrayList<String>();
		b.add("��ɳ");
		b.add("��̶");
		
		ArrayList<String> c = new ArrayList<String>();
		c.add("�人");
		c.add("��ʯ");
		
		ArrayList<String> d = new ArrayList<String>();
		d.add("����");
		d.add("����");
		
		//_cities.put("ѡ��ʡ��", new ArrayList<String>());
		_cities.put(_provinces[0], a);
		_cities.put(_provinces[1], b);
		_cities.put(_provinces[2], c);
		_cities.put(_provinces[3], d);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_location);
		
		Spinner provinceSpinner = (Spinner)findViewById(R.id.province_spinner);
		final Spinner citySpinner = (Spinner)findViewById(R.id.city_spinner);
		
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, R.layout.sherlock_spinner_item, new ArrayList<String>());
		cityAdapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		citySpinner.setAdapter(cityAdapter);
		
		final ArrayAdapter<CharSequence> provinceAdapter = new ArrayAdapter<CharSequence>(this, R.layout.sherlock_spinner_item, _provinces);
		provinceAdapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int itemIndex, long arg3) {
				if(itemIndex >= 0) {
					String name = _provinces[itemIndex];
					ArrayList<String> cities = _cities.get(name);
					@SuppressWarnings("unchecked")
					ArrayAdapter<String> adapter = (ArrayAdapter<String>)citySpinner.getAdapter();
					adapter.clear();
					adapter.addAll(cities);
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
		provinceSpinner.setAdapter(provinceAdapter);
		
		provinceSpinner.setSelection(0);
	}

}
