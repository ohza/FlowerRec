package com.example.flowerrec;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	String TAG = "TEST";
	String CREATE_UNLABELLED_PLANT_TABLE = "CREATE TABLE meineunlabelledschaetze ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			"author TEXT, "+
			"date TEXT, "+
			"gps TEXT, "+
			"bitmap_base64 TEXT, "+
			"bitmap_base64_seg TEXT )";

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "SCHAETZEDB";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		//SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL("DROP TABLE IF EXISTS meineschaetze");
		//db.execSQL(CREATE_UNLABELLED_PLANT_TABLE);
		
		
		//context.deleteDatabase(DATABASE_NAME);
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.d(TAG,"No error until here OnCreate");

		String CREATE_PLANT_TABLE = "CREATE TABLE meineschaetze ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				"author TEXT, "+
				"date TEXT, "+
				"gps TEXT, "+
				"bitmap_base64 TEXT, "+
				"classname TEXT, "+
				"amount TEXT, "+
				"place TEXT, "+
				"classprob TEXT )";
		
	
		
		
		String CREATE_UNLABELLED_PLANT_TABLE = "CREATE TABLE meineunlabelledschaetze ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				"author TEXT, "+
				"date TEXT, "+
				"gps TEXT, "+
				"bitmap_base64 TEXT, "+
				"bitmap_base64_seg TEXT )";


		db.execSQL(CREATE_PLANT_TABLE);
		db.execSQL(CREATE_UNLABELLED_PLANT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older plants table if existed
		db.execSQL("DROP TABLE IF EXISTS meineschaetze");
		db.execSQL("DROP TABLE IF EXISTS meineunlabelledschaetze");

		// create fresh plants table
		this.onCreate(db);
	}

	/**
	 * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
	 */

	// Books table name
	private static final String TABLE_SCHAETZE = "meineschaetze";
	private static final String TABLE_SCHAETZE_U = "meineunlabelledschaetze";

	// Books Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_AUTHOR = "author";
	private static final String KEY_DATE = "date";
	private static final String KEY_GPS = "gps";
	private static final String KEY_BITMAPBASE64 = "bitmap_base64";
	private static final String KEY_CLASSNAME = "classname";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_PLACE = "place";
	private static final String KEY_CLASSPROB = "classprob";
	
	private static final String KEY_BITMAPBASE64_SEG = "bitmap_base64_seg";
	


	private static final String[] COLUMNS = {KEY_ID,KEY_AUTHOR,KEY_DATE,KEY_GPS,KEY_BITMAPBASE64,KEY_CLASSNAME,KEY_AMOUNT,KEY_PLACE,KEY_CLASSPROB};
	private static final String[] COLUMNS_U = {KEY_ID,KEY_AUTHOR,KEY_DATE,KEY_GPS,KEY_BITMAPBASE64,KEY_BITMAPBASE64_SEG};

	public void addModel(ModelString model){
		Log.d(TAG, model.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_AUTHOR, model.getAuthor()); // get author
		values.put(KEY_DATE, model.getDate()); // get author
		values.put(KEY_GPS, model.getGPS()); // get author
		values.put(KEY_BITMAPBASE64, model.getBitmap()); // get author
		values.put(KEY_CLASSNAME, model.getClassName()); // get author
		values.put(KEY_AMOUNT, model.getAmount()); // get author
		values.put(KEY_PLACE, model.getPlace()); // get author
		values.put(KEY_CLASSPROB, model.getClassProb()); // get author

		// 3. insert
		db.insert(TABLE_SCHAETZE, // table
				null, //nullColumnHack
				values); // key/value -> keys = column names/ values = column values

		// 4. close
		db.close(); 
	}
	
	public void addModel_U(ModelString model){
	
		
		
		
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_AUTHOR, model.getAuthor()); // get author
		values.put(KEY_DATE, model.getDate()); // get author
		values.put(KEY_GPS, model.getGPS()); // get author
		values.put(KEY_BITMAPBASE64, model.getBitmap()); // get author
		values.put(KEY_BITMAPBASE64_SEG, model.getBitmap_Seg()); // get author
		
		// 3. insert
		db.insert(TABLE_SCHAETZE_U, // table
				null, //nullColumnHack
				values); // key/value -> keys = column names/ values = column values

		// 4. close
		db.close(); 
		Log.d(TAG,"No error until here 6");
	}
	
	
	
	

	public ModelString getModel(int id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
		Cursor cursor = 
				db.query(TABLE_SCHAETZE, // a. table
						COLUMNS, // b. column names
						" id = ?", // c. selections 
						new String[] { String.valueOf(id) }, // d. selections args
						null, // e. group by
						null, // f. having
						null, // g. order by
						null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build book object
		ModelString model = new ModelString();
		model.setId(Integer.parseInt(cursor.getString(0)));
		model.setAuthor(cursor.getString(1));
		model.setDate(cursor.getString(2));
		model.setGPS(cursor.getString(3));
		model.setBitmap(cursor.getString(4));
		model.setClassName(cursor.getString(5));
		model.setAmount(cursor.getString(6));
		model.setPlace(cursor.getString(7));
		model.setClassProb(cursor.getString(8));


		return model;
	}
	
	
	
	
	public ModelString getModel_U(int id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
		Cursor cursor = 
				db.query(TABLE_SCHAETZE_U, // a. table
						COLUMNS_U, // b. column names
						" id = ?", // c. selections 
						new String[] { String.valueOf(id) }, // d. selections args
						null, // e. group by
						null, // f. having
						null, // g. order by
						null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build book object
		ModelString model = new ModelString();
		model.setId(Integer.parseInt(cursor.getString(0)));
		model.setAuthor(cursor.getString(1));
		model.setDate(cursor.getString(2));
		model.setGPS(cursor.getString(3));
		model.setBitmap(cursor.getString(4));
		model.setBitmap_Seg(cursor.getString(5));


		return model;
	}
	
	
	
	
	
	
	
	
	
	
	

	// Get All Books
	public List<ModelString> getAllModels() {
		List<ModelString> models = new LinkedList<ModelString>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_SCHAETZE;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		ModelString model = null;
		if (cursor.moveToFirst()) {
			do {
				model = new ModelString();
				model.setId(Integer.parseInt(cursor.getString(0)));
				model.setAuthor(cursor.getString(1));
				model.setDate(cursor.getString(2));
				model.setGPS(cursor.getString(3));
				model.setBitmap(cursor.getString(4));
				model.setClassName(cursor.getString(5));
				model.setAmount(cursor.getString(6));
				model.setPlace(cursor.getString(7));
				model.setClassProb(cursor.getString(8));

				models.add(model);
			} while (cursor.moveToNext());
		}


		return models;
	}
	
	
	
	
	
	
	public List<ModelString> getAllModels_U() {
		List<ModelString> models = new LinkedList<ModelString>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_SCHAETZE_U;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		ModelString model = null;
		if (cursor.moveToFirst()) {
			do {
				model = new ModelString();
				model.setId(Integer.parseInt(cursor.getString(0)));
				model.setAuthor(cursor.getString(1));
				model.setDate(cursor.getString(2));
				model.setGPS(cursor.getString(3));
				model.setBitmap(cursor.getString(4));
				model.setBitmap_Seg(cursor.getString(5));
				
				

				models.add(model);
			} while (cursor.moveToNext());
		}


		return models;
	}
	
	
	
	
	
	

	// Updating single book
	public int updateModel(ModelString model) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues(); 
		values.put(KEY_AUTHOR, model.getAuthor()); // get author
		values.put(KEY_DATE, model.getDate()); // get author
		values.put(KEY_GPS, model.getGPS()); // get author
		values.put(KEY_BITMAPBASE64, model.getBitmap()); // get author
		values.put(KEY_CLASSNAME, model.getClassName()); // get author
		values.put(KEY_AMOUNT, model.getAmount()); // get author
		values.put(KEY_PLACE, model.getPlace());
		values.put(KEY_CLASSPROB, model.getClassProb()); // get author

		// 3. updating row
		int i = db.update(TABLE_SCHAETZE, //table
				values, // column/value
				KEY_ID+" = ?", // selections
				new String[] { String.valueOf(model.getId()) }); //selection args

		// 4. close
		db.close();

		return i;

	}
	
	
	
	
	public int updateModel_U(ModelString model) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues(); 
		values.put(KEY_AUTHOR, model.getAuthor()); // get author
		values.put(KEY_DATE, model.getDate()); // get author
		values.put(KEY_GPS, model.getGPS()); // get author
		values.put(KEY_BITMAPBASE64, model.getBitmap()); // get author
		values.put(KEY_BITMAPBASE64_SEG, model.getBitmap_Seg()); // get author
		

		// 3. updating row
		int i = db.update(TABLE_SCHAETZE_U, //table
				values, // column/value
				KEY_ID+" = ?", // selections
				new String[] { String.valueOf(model.getId()) }); //selection args

		// 4. close
		db.close();

		return i;

	}
	
	
	
	
	
	
	
	
	


	public void deleteModel(ModelString model) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. delete
		db.delete(TABLE_SCHAETZE,
				KEY_ID+" = ?",
				new String[] { String.valueOf(model.getId()) });

		// 3. close
		db.close();



	}
	
	
	
	public void deleteModel_U(ModelString model) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. delete
		db.delete(TABLE_SCHAETZE_U,
				KEY_ID+" = ?",
				new String[] { String.valueOf(model.getId()) });

		// 3. close
		db.close();



	}
}