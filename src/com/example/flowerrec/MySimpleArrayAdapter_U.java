package com.example.flowerrec;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;


public class MySimpleArrayAdapter_U extends ArrayAdapter<Model> {
	Context context;
	ArrayList<Model> values;
	View rowView;
	TextView dateView;
	TextView gpsView_1;
	TextView gpsView_2;
	ImageView imView;
	
	String TAG = "TEST";


	public MySimpleArrayAdapter_U(Context context, ArrayList<Model> values) {
		super(context, R.layout.rawlayout_u, values);
		this.context = context;
		this.values = values;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.d(TAG,"The on view of the adapter has en called");
		
	

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.rawlayout_u, parent, false);
		dateView = (TextView) rowView.findViewById(R.id.label_u);
		gpsView_1 = (TextView) rowView.findViewById(R.id.labelsmall_u);
		gpsView_2 = (TextView) rowView.findViewById(R.id.labelsmall_u_2);
		imView = (ImageView) rowView.findViewById(R.id.icon_u);

		
		dateView.setText(values.get(position).getDate());
		gpsView_1.setText(values.get(position).getGPS().split(",")[0]);
		gpsView_2.setText(values.get(position).getGPS().split(",")[1]);
		rowView.setTag(values.get(position).getName());
		imView.setImageBitmap(values.get(position).getBitmap());
		
		return rowView;

	}
} 