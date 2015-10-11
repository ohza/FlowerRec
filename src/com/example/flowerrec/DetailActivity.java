package com.example.flowerrec;



import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class DetailActivity extends Activity {

	Cursor mCursor;
	ListView lstView;
	ImageView selectedImage;
	ImageView madeImage;
	String TAG = "TEST";
	TextView plantInfo;
	ImageButton yBut;
	ImageButton nBut;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.display_image_detail);
		selectedImage = (ImageView) findViewById(R.id.imagDisp);
		madeImage = (ImageView) findViewById(R.id.madeDisp);
		plantInfo = (TextView) findViewById(R.id.plantInfo);

		yBut = (ImageButton) findViewById(R.id.yesButton1);
		nBut = (ImageButton) findViewById(R.id.noButton1);

		plantInfo.setText(Html.fromHtml("<b>" + FlowerUtils.values[getIntent().getIntExtra("pos",0)].getName() + "</b>" +  "<br />" + 
				"<small>" +FlowerUtils.pDes.get(FlowerUtils.values[getIntent().getIntExtra("pos",0)].getName()) + "</small>"));

		plantInfo.setMovementMethod(new ScrollingMovementMethod());

		final LinearLayout linLay = (LinearLayout) findViewById(R.id.linlay);


		final ViewTreeObserver vto = linLay.getViewTreeObserver();


		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				drawStuff(linLay.getMeasuredHeight(),linLay.getMeasuredWidth());

			}

		});

		yBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				retWithSel();
			}
		});

		nBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				retToSel();
			}
		});
	}


	public void drawStuff(int height,int width) {

		byte[] data = Base64.decode(FlowerUtils.i1.getImBin(0), Base64.DEFAULT);
		Bitmap madeImg = BitmapFactory.decodeByteArray(data,0,data.length);

		double scale_2 = (double)(height/2) / (double)madeImg.getHeight() ;
		double width_2 = scale_2 * madeImg.getWidth();

		Log.d(TAG,"height: "+scale_2);
		Log.d(TAG,"scale_2: "+scale_2);
		Log.d(TAG,"width_2: "+width_2);

		Bitmap selB = addWhiteBorder(Bitmap.createScaledBitmap(FlowerUtils.values[getIntent().getIntExtra("pos",0)].getBitmap(),width/2-4,width/2-4,true),2);
		Bitmap madB = addWhiteBorder(Bitmap.createScaledBitmap(madeImg,width/2-4,width/2-4,true),2);



		selectedImage.setImageBitmap(selB);
		madeImage.setImageBitmap(madB);

	}

	private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
		Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
		Canvas canvas = new Canvas(bmpWithBorder);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(bmp, borderSize, borderSize, null);
		return bmpWithBorder;
	}

	public void retToSel(){
		this.onBackPressed();
	}

	public void retWithSel(){

		DispIm.listItems.get(getIntent().getIntExtra("pos",0)).setBool(1);
		DispIm.adapter.notifyDataSetChanged();
		this.onBackPressed();

	}








}
