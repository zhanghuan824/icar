package com.auto.client.module.maintenance;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class ListedSpinnerAdapter<T> extends ArrayAdapter<T> implements SpinnerAdapter {

	private int _resourceId;
	private int _dropDownViewResource;
	
	@Override
	public void setDropDownViewResource(int resource) {
		// TODO Auto-generated method stub
		super.setDropDownViewResource(resource);
		_dropDownViewResource = resource;
	}
	
	public ListedSpinnerAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		_resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = ((SherlockActivity)getContext()).getLayoutInflater().inflate(_resourceId, null);
		}
		
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = ((SherlockActivity)getContext()).getLayoutInflater().inflate(_dropDownViewResource, parent);
		}
		
		return convertView;
	}

}
