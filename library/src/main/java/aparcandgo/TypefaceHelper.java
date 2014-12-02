package aparcandgo;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class TypefaceHelper {
	
	private static SparseArray<Typeface> hashMap = new SparseArray<Typeface>();

	public static Typeface getFont(int fontID, Context context) {
		if (hashMap.get(fontID, null) != null)
			return hashMap.get(fontID);
		else {
			Typeface tf = Typefaces.getFontFromRes(fontID, context);
			hashMap.put(fontID, tf);
			return tf;
		}
	}
}
