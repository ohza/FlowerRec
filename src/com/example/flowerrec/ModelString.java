package com.example.flowerrec;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;

public class ModelString{



	private int id;
	private String author;
	private String date;
	private String gps;
	private String bitmap_base64;
	private String bitmap_base64_seg;
	private String classname;
	private String classprob;
	private String amount;
	private String place;




	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return this.id;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return this.author;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return this.date;
	}

	public void setGPS(String gps){
		this.gps = gps;
	}

	public String getGPS(){
		return this.gps;
	}


	public void setBitmap(Bitmap mybitmap){

		Bitmap immagex=mybitmap;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
		this.bitmap_base64 = imageEncoded;
	}
	
	public void setBitmap_Seg(Bitmap mybitmap){

		Bitmap immagex=mybitmap;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
		this.bitmap_base64_seg = imageEncoded;
	}

	public void setBitmap(String mybitmap){
		this.bitmap_base64 = mybitmap;
	}
	
	public void setBitmap_Seg(String mybitmap){
		this.bitmap_base64_seg = mybitmap;
	}

	public String getBitmap(){
		return this.bitmap_base64;
	}
	
	public String getBitmap_Seg(){
		return this.bitmap_base64_seg;
	}

	public void setClassName(String classname){
		this.classname = classname;
	}

	public String getClassName(){
		return this.classname;
	}

	public void setClassProb(String classprob){
		this.classprob = classprob;
	}

	public String getClassProb(){
		return this.classprob;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return this.amount;
	}

	public void setPlace(String place){
		this.place = place;
	}

	public String getPlace(){
		return this.place;
	}


}