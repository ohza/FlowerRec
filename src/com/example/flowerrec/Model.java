package com.example.flowerrec;

import android.graphics.Bitmap;

public class Model{

	private String name;
	private float prob;
	private int isChecked;
	private Bitmap bitmap;
	
	private String date;
	private String gps;

	public void setBitmap(Bitmap mybitmap){
		this.bitmap = mybitmap;
	}

	public void setName(String myname){
		this.name = myname;
	}

	public void setProb(float myprob){
		this.prob = myprob;
	}

	public void setBool(int mybool){
		this.isChecked = mybool;
	}

	public String getName(){
		return this.name;
	}

	public Bitmap getBitmap(){
		return this.bitmap;
	}

	public float getProb(){
		return this.prob;
	}

	public int getBool(){
		return this.isChecked;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getGPS(){
		return this.gps;
	}
	
	public void setDate(String mydate){
		this.date = mydate;
	}
	
	public void setGPS(String mygps){
		this.gps = mygps;
	}
}