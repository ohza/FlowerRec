package com.example.flowerrec;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;

public class MainActivity extends Activity {

	String TAG = "TEST";
	Activity act;
	DatabaseHandler dB;

	String fromServer;
	int c;

	BufferedReader br;
	ObjectInputStream ois;
	String line;
	public static TextView tf;
	public static String str;
	public static int v;
	Button b1;
	Button b2;
	Button b3;
	ImageView im1;
	boolean i;
	public static long imageTime;
	private static int TAKE_PICTURE = 1;
	private static int RETURN_SEGMENTATION = 2;
	private Uri outputFileUri;
	FlowerUtils bU;
	Context context;
	Location lo;
	File file;
	static boolean anim = false;
	public void startFadeOut(){

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"THE ONCREATE METHOD HAS BEEN CALLED");
		context = getBaseContext();
		
		act = this;
		
		bU = new FlowerUtils(act);
		
		setContentView(R.layout.activity_main);
		tf = (TextView) findViewById(R.id.TextField01);
		b1 = (Button) findViewById(R.id.btnThree);
		b2 = (Button) findViewById(R.id.btnTwo);
		b3 = (Button) findViewById(R.id.btnOne);
		im1 = (ImageView) findViewById(R.id.imageView1);

		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
			onDestroy();
		}

		if(getIntent().getIntExtra("Foto",0)==11){
			raisePhotoIntent();
		}

		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopupMenu2(v);
		}
		
		});

		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {}		
		});

		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {}
		});
	}
	
	public void doFeat()
	{	
		double [][] mtx=null;
		double [][] codebook=null;
		
		try
		{
			mtx = Matrix.loadFromFileXML(Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/descriptors.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			codebook = Matrix.loadFromFile(Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/mycodebook.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double[] featV = new double[150];
		
		for(int x = 0;x < Matrix.theRows;x++)
		{
			double[] closest = Matrix.multiply(codebook,mtx[x]);	
			int idx = Matrix.getMaxValue(closest);
			//Log.d(TAG,"The max index is: "+idx);
			featV[idx] += 1.0 / Matrix.theRows;
		}
		
		StringBuffer output = new StringBuffer("0");
		
		for(int h=0;h<150;h++)
		{
			output.append(" ");
			output.append(h);
			output.append(":");
			output.append(featV[h]);
		}
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/testdata.txt"));
			pw.write(output.toString());
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}
	
	public void doSVM(){
	
		try {
			
			String[] initV ={"-r",Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/Range.txt", Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/testdata.txt"};
			
			svm_scale.runA(initV);
			
			float[][] myret = svm_predict.run();
			
			java.util.Arrays.sort(myret, new java.util.Comparator<float[]>() {
			    public int compare(float[] a, float[] b) {
			        return Float.compare(a[0], b[0]);
			    }
			});
			
			float[][] myret_c = new float[FlowerUtils.retNumIm][2];
		
			for(int r = 0;r<FlowerUtils.retNumIm;r++){
				myret_c[r][0] = myret[FlowerUtils.retNumIm-1-r][0];
				myret_c[r][1] = myret[FlowerUtils.retNumIm-1-r][1];	
			}
			
			myret = myret_c;
			FlowerUtils.values = new Model[FlowerUtils.retNumIm+1];

			for (int n=0;n<(FlowerUtils.retNumIm+1);n++) {
				if (n<FlowerUtils.retNumIm) {
					FlowerUtils.values[n] = new Model();
					FlowerUtils.values[n].setProb(myret[n][0]*100);
					FlowerUtils.values[n].setName(FlowerUtils.plantNameID.get(""+((int)myret[n][1])));
					FlowerUtils.values[n].setBitmap(BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier("image_class_"+((int)myret[n][1]), "drawable",  context.getPackageName())));		
				} else {
					FlowerUtils.values[n] = new Model();
					FlowerUtils.values[n].setProb(0.5f);
					FlowerUtils.values[n].setName(getString(R.string.unknown));
					FlowerUtils.values[n].setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.beeq));
				}
			}
			
			Intent intent = new Intent(context,DispIm.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			ModelParcelable M [] = new ModelParcelable[2];
			
			M[0] = new ModelParcelable();
			
			M[0].setName(FlowerUtils.values[0].getName());
			M[0].setDate(FlowerUtils.values[0].getDate());
			M[0].setGPS(FlowerUtils.values[0].getGPS());
			M[0].setBitmap(FlowerUtils.values[0].getBitmap());
			
	        M[1] = new ModelParcelable();
			
			M[1].setName(FlowerUtils.values[1].getName());
			M[1].setDate(FlowerUtils.values[1].getDate());
			M[1].setGPS(FlowerUtils.values[1].getGPS());
			M[1].setBitmap(FlowerUtils.values[1].getBitmap());
			
			intent.putExtra("cont", (ModelParcelable[])M);
			
			startActivity(intent);
			
		} catch (IOException e) {
			Log.d(TAG, "Could not read the model file");
			e.printStackTrace();
		}	
	}
	
	public void raisePhotoIntent(){
		
		java.util.Date date = new java.util.Date();
		imageTime = date.getTime();
		file = new File(Environment.getExternalStorageDirectory().getPath(), "Pictures/" + imageTime+"_test.jpg");
		outputFileUri = Uri.fromFile(file);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, TAKE_PICTURE);
	}

	private void showPopupMenu2(View v){
		PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
		popupMenu.getMenuInflater().inflate(R.menu.foto_popup, popupMenu.getMenu());

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				if(item.toString().equals(getString(R.string.new_photo))){
						 raisePhotoIntent();
				}

				if(item.toString().equals(getString(R.string.to_list))){
					
				}

				return true;
			}
		});

		popupMenu.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == TAKE_PICTURE)
			if (resultCode == RESULT_OK) {
				
				Bitmap bitmap_scaled = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/Pictures/" + imageTime+"_test.jpg"),240,320,true);
				try{
					
				    ByteArrayOutputStream bos = new ByteArrayOutputStream();
				    bitmap_scaled.compress(CompressFormat.JPEG,100, bos);
				    byte[] bitmapdata = bos.toByteArray();

				    FileOutputStream fos = new FileOutputStream(file);
				    fos.write(bitmapdata);
				    fos.flush();
				    fos.close();
				    }
			    catch(Exception e){
			    	
			    }
			
			Intent intent = new Intent(MainActivity.this,DrawCrop.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, RETURN_SEGMENTATION);
		}

		if (requestCode == RETURN_SEGMENTATION) {
			if (resultCode == RESULT_OK) {
				
				FlowerUtils.i1.setImBin(""+imageTime+"_test.jpg",0);
				FlowerUtils.i1.setImBin(""+imageTime+"_test_seg.jpg",1);
				
				Calendar c = Calendar.getInstance();
				
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = df.format(c.getTime());
				
				FlowerUtils.i1.setDate(formattedDate);
				
				try {
					new NonfreeJNILib().execute(Environment.getExternalStorageDirectory().getPath()+"/Pictures/" + imageTime+"_test.jpg").get();
					doFeat();
					doSVM();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void storeAndDisplayImage() {
		try {

			File file = new File(Environment.getExternalStorageDirectory()
					.getPath(), "FromMac/" + ""+imageTime+"_Rot_test.jpg");
			Log.d(TAG, "File status " + file.delete());

			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			Log.d(TAG, "OK i am here");

			while (true) {
				if (file.exists()) {
					intent.setDataAndType(Uri.fromFile(file), "image/*");						
					startActivity(intent);

					break;
				} else {
					if (i) {
						MainActivity.tf
						.setText("Could not get respone..");
						break;
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "An error occured catch");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch(item.getItemId()){
		case(R.id.action_settings):{
			showDialog(DIALOG_ALERT);

			return true;
		}

		default: return false;
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ALERT:
			// create out AlterDialog
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.quit_ask);
			builder.setCancelable(true);
			builder.setPositiveButton(R.string.yes, new OkOnClickListener());
			builder.setNegativeButton(R.string.no, new CancelOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		return super.onCreateDialog(id);
	}
	
	private static final int DIALOG_ALERT = 10;

	private final class CancelOnClickListener implements
	DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
	}

	private final class OkOnClickListener implements
	DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
			onDestroy();
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.d(TAG, "ON PAUSE ON MAIN ACTIVITY HAS BEEN CALLED");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d(TAG, "ON RESUME ON MAIN ACTIVITY HAS BEEN CALLED");
	}
		
}
