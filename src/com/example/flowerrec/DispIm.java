package com.example.flowerrec;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


public class DispIm extends ListActivity {

	String TAG = "TEST";
	public static ArrayList<Model> listItems;
	public static MySimpleArrayAdapter adapter;
	int counter = 0;
	ListView lstView;
	public static int mSelectedItem = -1;
	public static TextView statusV;



	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.setContentView(R.layout.display_image);
		
		///////////////////////////////////////////////////
		
		Bundle d = getIntent().getExtras();
		d.setClassLoader(ModelParcelable.class.getClassLoader());
		Parcelable[] ps = d.getParcelableArray("cont");
		ModelParcelable[] m = new ModelParcelable[ps.length];
		System.arraycopy(ps, 0,m, 0, ps.length);
		ps = null;
		
		statusV = (TextView) findViewById(R.id.plant_sel);
		listItems =new ArrayList<Model>();
		lstView = this.getListView();
		lstView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


		for(int i=0;i<FlowerUtils.retNumIm+1;i++){	
			listItems.add(FlowerUtils.values[i]);
		}
		adapter = new MySimpleArrayAdapter(this, listItems);
		this.setListAdapter(adapter);
		System.out.println("The method onCreate has been called");


	}  

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		if(listItems.get(position).getBool()==0){

			startActivity(new Intent(this,DetailActivity.class).putExtra("pos",position));

		}


		if(listItems.get(position).getBool()==1){

			// here was disp im plant amount
		}
	}


	public void onCheckboxClicked(View view) {
		boolean textF = false;

		for(int i=0;i<listItems.size();i++){

			System.out.println(i);
			System.out.println(listItems.size());


			if(listItems.get(i).getName().equals(((CheckBox) view).getTag()) && ((CheckBox) view).isChecked()){
				listItems.get(i).setBool(1);
				statusV.setText("Bitte auswahl bestätigen..");
				textF = true;
			}

			if(listItems.get(i).getName().equals(((CheckBox) view).getTag()) && !((CheckBox) view).isChecked()){
				listItems.get(i).setBool(0);
			}

		}
		if(!textF){
			statusV.setText("Welche der Planzen kommt deiner am nächsten?");

		}

		adapter.notifyDataSetChanged();

	}




} 