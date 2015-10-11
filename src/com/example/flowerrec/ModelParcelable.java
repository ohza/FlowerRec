package com.example.flowerrec;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ModelParcelable implements Parcelable{


	private String name;
	private float prob;
	private int isChecked;
	private Bitmap bitmap;
	private String date;
	private String gps;
	
	ModelParcelable(){
		
	}


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
	
	public ModelParcelable(Parcel in){
		
		Bitmap bit = (Bitmap)in.readParcelable(getClass().getClassLoader());
		
		this.bitmap = bit;
		
        String[] data = new String[3];

        in.readStringArray(data);
        this.name = data[0];
        this.date = data[1];
        this.gps = data[2];
        
    }
    

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	
    	dest.writeParcelable(this.bitmap,PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeStringArray(new String[] {this.name,this.date,this.gps});
    }
    

    public static final Parcelable.Creator<ModelParcelable> CREATOR = new Parcelable.Creator<ModelParcelable>() {
        @Override
        public ModelParcelable createFromParcel(Parcel in) {
            return new ModelParcelable(in);
        }

        @Override
        public ModelParcelable[] newArray(int size) {
            return new ModelParcelable[size];
        }
    };
}