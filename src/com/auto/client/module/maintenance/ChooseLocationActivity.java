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

	private String[] _provinces = {"北京1", "湖南", "湖北", "广东"};
	private HashMap<String, ArrayList<String>> _cities = new HashMap<String, ArrayList<String>>();
	
	public ChooseLocationActivity() {
		ArrayList<String> a = new ArrayList<String>();
		a.add("北京市");
		a.add("北京市区");
		
		ArrayList<String> b = new ArrayList<String>();
		b.add("长沙");
		b.add("湘潭");
		
		ArrayList<String> c = new ArrayList<String>();
		c.add("武汉");
		c.add("黄石");
		
		ArrayList<String> d = new ArrayList<String>();
		d.add("广州");
		d.add("深圳");
		
		//_cities.put("选择省份", new ArrayList<String>());
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
