package com.example.flowerrec;


import java.io.FileOutputStream;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
public class DrawCrop extends Activity {

	DrawingView dv ;

	private Paint       mPaint;
	private Bitmap  mutableBitmap;
	String TAG = "TEST";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dv = new DrawingView(this);
		RelativeLayout relativeLayout = new RelativeLayout(this);



		RelativeLayout.LayoutParams relativeParams2 = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeParams2.addRule(RelativeLayout.BELOW,100);


		relativeLayout.addView(dv,relativeParams2);
		setContentView(relativeLayout);






		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(7);



	}

	public void executeStuff(){

		try { 


			FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+ "/Pictures/" + MainActivity.imageTime+"_test_seg.jpg");
			//mutableBitmap = Bitmap.createScaledBitmap( mutableBitmap,480,640,true);
			mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.close();
			Intent returnIntent = new Intent();
			setResult(RESULT_OK, returnIntent);        
			finish();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public class DrawingView extends View {

		public int width;
		public  int height;
		private Bitmap  mBitmap;
		private Canvas  mCanvas;
		private Path    mPath;
		private Paint   mBitmapPaint;
		Context context;
		private Paint circlePaint;
		private Path circlePath;

		public DrawingView(Context c) {
			super(c);
			context=c;
			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);  
			circlePaint = new Paint();
			circlePath = new Path();
			circlePaint.setAntiAlias(true);
			circlePaint.setColor(Color.BLUE);
			circlePaint.setStyle(Paint.Style.STROKE);
			circlePaint.setStrokeJoin(Paint.Join.MITER);
			circlePaint.setStrokeWidth(4f); 

		}


		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);

			mBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+ "/Pictures/" + MainActivity.imageTime+"_test.jpg"),w,h,true);
			//Log.d(TAG,"The width is: "+mBitmap.getWidth());
			//Log.d(TAG,"The heigth is: "+mBitmap.getHeight());
			mutableBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
			mCanvas = new Canvas(mutableBitmap);

		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			canvas.drawBitmap( mutableBitmap, 0, 0, mBitmapPaint);

			canvas.drawPath( mPath,  mPaint);

			canvas.drawPath( circlePath,  circlePaint);
		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}
		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
				mX = x;
				mY = y;

				circlePath.reset();
				circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
			}
		}
		private void touch_up() {
			mPath.lineTo(mX, mY);
			circlePath.reset();
			mCanvas.drawPath(mPath,  mPaint);
			mPath.reset();
		}



		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			}
			return true;
		}  
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.draw_conf, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);

		switch(item.getItemId()){
		case(R.id.action_settings):{
			executeStuff();
			return true;
		}

		default: return false;
		}

	}


}