package com.example.flowerrec;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<Model> {
	private final Context context;
	private final ArrayList<Model> values;
	View rowView;
	TextView classView;
	TextView probView;
	ImageView imView;
	CheckBox cB;


	public MySimpleArrayAdapter(Context context, ArrayList<Model> values){
		super(context, R.layout.rawlayout, values);
		System.out.println("The MySimpleArrayAdapter has been called");
		this.context = context;
		this.values = values;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.rawlayout, parent, false);
		classView = (TextView) rowView.findViewById(R.id.label);
		probView = (TextView) rowView.findViewById(R.id.labelsmall);
		imView = (ImageView) rowView.findViewById(R.id.icon);
		cB = (CheckBox) rowView.findViewById(R.id.checkbox);
		classView.setText(values.get(position).getName());
		probView.setText(""+(position+1) +". " +"Prob: " +String.format("%.2f",values.get(position).getProb()) +"%");
		cB.setTag(values.get(position).getName());
		rowView.setTag(values.get(position).getName());
		imView.setImageBitmap(Bitmap.createScaledBitmap(values.get(position).getBitmap(), 100, 100, true));
		System.out.println("cB.toString() is :" +cB.toString());

		if(DispIm.listItems.get(position).getBool()==1){
			cB.setChecked(true);
			rowView.setBackgroundColor(0xFFAEC6CF);
			DispIm.statusV.setText("Bitte Auswal bestätigen..");

		}
		return rowView;

	}
} 