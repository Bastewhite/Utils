package aparcandgo.fontables;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.holoeverywhere.R;

public class FontableTextView extends TextView {

	public FontableTextView(Context context) {
		super(context);

		Typeface tf = null;
		try {
			
			tf = TypefaceHelper.getFont(R.raw.vag_rounded_bt, context);
			
			if (this instanceof TextView) {
				((TextView) ((View) this)).setTypeface(tf);
			} else {
				((Button) ((View) this)).setTypeface(tf);
			}
		} catch (Exception ignore) {
		}

	}

	public FontableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

        if (isInEditMode()) return;
		
		Typeface tf = null;
		int fontID = 0;
		try {

			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.FontableTextView);

			final int N = a.getIndexCount();
			for (int i = 0; i < N; ++i) {
				int attr = a.getIndex(i);
				switch (attr) {
				case R.styleable.FontableTextView_FontID:
					fontID = a.getResourceId(attr, -1);
					// ...do something with myText...
					break;
				}
			}
			a.recycle();

			if (fontID == 0) {

				tf = TypefaceHelper.getFont(R.raw.vag_rounded_bt, context);
			}
			else 
			{
				tf = TypefaceHelper.getFont(fontID, context);
			}

			if (this instanceof TextView) {
				((TextView) ((View) this)).setTypeface(tf);
				//((TextView) ((View) this)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 200);
			} else {
				((Button) ((View) this)).setTypeface(tf);
				//((Button) ((View) this)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 200);
			}
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
	}

	public FontableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		Typeface tf = null;
		int fontID = 0;
		try {
			
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.FontableTextView);

			final int N = a.getIndexCount();
			for (int i = 0; i < N; ++i) {
				int attr = a.getIndex(i);
				switch (attr) {
				case R.styleable.FontableTextView_FontID:
					fontID = a.getResourceId(attr, 0);
					// ...do something with myText...
					break;
				}
			}
			a.recycle();

			if (fontID == 0) {

				tf = TypefaceHelper.getFont(R.raw.vag_rounded_bt, context);
				
			}
			else 
			{
				tf = TypefaceHelper.getFont(fontID, context);
			}
			
			if (this instanceof TextView) {
				((TextView) ((View) this)).setTypeface(tf);
			} else {
				((Button) ((View) this)).setTypeface(tf);
			}
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
	}
}