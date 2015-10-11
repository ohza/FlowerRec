
package com.example.flowerrec;

import android.os.AsyncTask;

public class NonfreeJNILib extends AsyncTask<String, Integer, Void> {
	
	public static native void doFlowerRec(String a);
	
	@Override
	protected void onPreExecute() {
	    {
	    	try
	    	{ 
	    		System.loadLibrary("opencv_java");
	    		System.loadLibrary("nonfree");
	    		System.loadLibrary("flowerrec");
	    	}
	    	catch( UnsatisfiedLinkError e )
			{
	           System.err.println("Native code library failed to load.\n" + e);		
			}
	    }	
	}
	
	@Override
    protected Void doInBackground(String... a) {
        doFlowerRec(a[0]);
        return null;
    }
}