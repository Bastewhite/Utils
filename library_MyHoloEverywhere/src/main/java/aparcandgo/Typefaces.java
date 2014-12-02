package aparcandgo;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import aparcandgo.debug.Debugger;

public class Typefaces {
	private static final String TAG = "Typefaces";
	
	public static Typeface getFontFromRes(int resource, Context context)
	{ 
	    Typeface tf = null;
	    InputStream is = null;
	    try {
	        is = context.getResources().openRawResource(resource);
	    }
	    catch(NotFoundException e) {
	    	Debugger.error(TAG, "Could not find font in resources!");
	    }

	    String outPath = context.getCacheDir() + "/tmp" + System.currentTimeMillis() +".raw";

	    try
	    {
	        byte[] buffer = new byte[is.available()];
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outPath));

	        int l = 0;
	        while((l = is.read(buffer)) > 0)
	            bos.write(buffer, 0, l);

	        bos.close();

	        tf = Typeface.createFromFile(outPath);

	        // clean up
	        new File(outPath).delete();
	    }
	    catch (IOException e)
	    {
	    	Debugger.error(TAG, "Error reading in font!");
	        return null;
	    }

	    return tf;      
	}
	
	
}