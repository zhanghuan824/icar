package com.auto.client.common.control.moduleslider;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModuleAdapter extends ArrayAdapter<ModuleItem> {

	private final List<ModuleItem> _data;
	private int _resource;
	
	public ModuleAdapter(Context context, int resource, List<ModuleItem> data) {
		super(context, resource, data);
		_resource = resource;
		_data = new ArrayList<ModuleItem>(data);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			SherlockFragmentActivity context = (SherlockFragmentActivity)getContext();
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(_resource, parent, false);
			
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					SherlockFragmentActivity context = (SherlockFragmentActivity)getContext();
					String clazzName = _data.get(position).getModuleActivityClassName();
					try {
						Intent intent = new Intent(context, Class.forName(clazzName));						
						intent.putExtra("name", clazzName);
						context.startActivity(intent);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		ImageView image = (ImageView)convertView.findViewById(R.id.module_image);
		TextView text = (TextView)convertView.findViewById(R.id.module_name);
		text.setText(_data.get(position).getName());
		//Bitmap bitmap = BitmapFactory.decodeFile(_data.get(position).getImage());
		//image.setImageBitmap(bitmap);
		return convertView;
	}
	
}
