package promofarma;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import commons.NothingSelectedSpinnerAdapter;
import es.utils.R;


/**
 * Created by Fran on 24/07/2014.
 */
public class NothingSelectedSpinner extends Spinner {

    public NothingSelectedSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(SpinnerAdapter adapter, int stringId) {
        NothingSelectedSpinnerAdapter spinnerAdapter = new NothingSelectedSpinnerAdapter(adapter, getContext(), stringId);
        super.setAdapter(spinnerAdapter);
    }

    public void setSelectionString(int arrayID, String s) {
        String[] provincesArray = getContext().getResources().getStringArray(arrayID);
        for (int i = 0; i < provincesArray.length; i++) {
            if (provincesArray[i].equals(s)) {
                setSelection(i + 1);
            }
        }
    }

    public int getCustomSelectedItemPosition() {
        return getSelectedItemPosition()-1;
    }

    public String getSelectedString(int arrayID) {
        if (getCustomSelectedItemPosition() == -1) return "";

        String[] provincesArray = getContext().getResources().getStringArray(arrayID);
        return provincesArray[getCustomSelectedItemPosition()];
    }

    public boolean isError() {
        if (getCustomSelectedItemPosition() == -1) {
            TextView textView = (TextView) getSelectedView();
            textView.setError(getContext().getString(R.string.campo_obligatorio));
            return true;
        }
        else {
            return false;
        }
    }

}
