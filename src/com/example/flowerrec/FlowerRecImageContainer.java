package com.example.flowerrec;

import java.io.File;
import java.io.FileInputStream;

import android.os.Environment;
import android.util.Base64;

public class FlowerRecImageContainer {
	
	BeeUtils bU = new BeeUtils();
	String TAG = "TEST";

	private String Date;
	private String Attr;

	private String [] Image_base64 = new String[BeeUtils.retNumIm];
	private String[] ClassName = new String[BeeUtils.retNumIm];
	private float [] ClassProb = new float[BeeUtils.retNumIm];

	FlowerRecImageContainer(FlowerRecImageContainer InitCont){

		this.Date = InitCont.Date;
		this.Attr = "";
	}

	FlowerRecImageContainer(){
		
		this.Date="";
		this.ClassName[0]="";
		this.Image_base64[0]="";
		this.Attr ="";
	}

	FlowerRecImageContainer(String InitString){

		for(int i = 0;i<BeeUtils.retNumIm;i++){

			String imbin = "ImBin0"+ Integer.toString(i+1);
			String classN = "ClassName0"+ Integer.toString(i+1);
			String classP = "ClassProb0"+ Integer.toString(i+1);

			this.Image_base64[i] = bU.getWithinTags(InitString, imbin);
			this.ClassName[i] = bU.getWithinTags(InitString, classN);
			this.ClassProb[i] = Float.parseFloat(bU.getWithinTags(InitString, classP));
		}
	}

	public void setAttr(String attr){
		this.Attr =  attr;
	}

	public void setDate(long unix_time){
		this.Date =  Long.toString(unix_time);
	}

	public void setDate(String unix_time){
		this.Date = unix_time;
	}

	public void setClassName(String className,int indx){
		this.ClassName[indx] = className;
	}

	public void setImBin(String fName,int index){
		FileInputStream fileInputStream=null;

		File file = new File(Environment
				.getExternalStorageDirectory().getPath(),
				"Pictures/" + fName);

		byte[] bFile = new byte[(int) file.length()];

		try {
			//convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		this.Image_base64[index] = Base64.encodeToString(bFile, Base64.DEFAULT);
	}
	
	public void setImBin(String a,String b,int index){
		this.Image_base64[index] = a;
	}
	
	public String getDate(){
		return this.Date;
	}

	public String getClassName(int index){
		return this.ClassName[index];
	}

	public float getClassProb(int index){
		return this.ClassProb[index];
	}

	public String getImBin(int index){
		return this.Image_base64[index];
	}

	@Override
	public String toString() {
		return bU.defAttr(this.Attr)+"<BeesmartImage>"+bU.setWithinTags(this.Date, "Date") +bU.setWithinTags(this.ClassName[0], "ClassName") +bU.setWithinTags(this.Image_base64[0], "ImBin0") +bU.setWithinTags(this.Image_base64[1], "ImBin1") +"</BeesmartImage>"+"</BeesmartFile>";
	}
}
