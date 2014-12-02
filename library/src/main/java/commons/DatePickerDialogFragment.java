/*
 * Copyright 2012 David Cesarino de Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>This class provides a usable {@link android.app.DatePickerDialog} wrapped as a {@link android.support.v4.app.DialogFragment},
 * using the compatibility package v4. Its main advantage is handling Issue 34833 
 * automatically for you.</p>
 *
 * <p>Current implementation (because I wanted that way =) ):</p>
 *
 * <ul>
 * <li>Only two buttons, a {@code BUTTON_POSITIVE} and a {@code BUTTON_NEGATIVE}.
 * <li>Buttons labeled from {@code android.R.string.ok} and {@code android.R.string.cancel}.
 * </ul>
 *
 * <p><strong>Usage sample:</strong></p>
 *
 * <pre>class YourActivity extends Activity implements OnDateSetListener
 *
 * // ...
 *
 * Bundle b = new Bundle();
 * b.putInt(DatePickerDialogFragment.YEAR, 2012);
 * b.putInt(DatePickerDialogFragment.MONTH, 6);
 * b.putInt(DatePickerDialogFragment.DATE, 17);
 * DialogFragment picker = new DatePickerDialogFragment();
 * picker.setArguments(b);
 * picker.show(getActivity().getSupportFragmentManager(), "fragment_date_picker");</pre>
 *
 * @author davidcesarino@gmail.com
 * @version 2012.0828
 * @see <a href="http://code.google.com/p/android/issues/detail?id=34833">Android Issue 34833</a>
 * @see <a href="http://stackoverflow.com/q/11444238/489607"
 * >Jelly Bean DatePickerDialog — is there a way to cancel?</a>
 *
 */
public class DatePickerDialogFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    public interface OnDateSetListener {
        public void onDateSet(String date);
    }

    private static String MIN_DATE = "min_date";
    private static String CURRENT_DATE = "current_date";

    private OnDateSetListener mListener;

    public static DatePickerDialogFragment newInstance(Date minDate) {
        DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
        Bundle bundle = new Bundle();
        if (minDate != null)
            bundle.putLong(MIN_DATE, minDate.getTime());
        newFragment.setArguments(bundle);
        return newFragment;
    }

    public static DatePickerDialogFragment newInstance(Date currentDate, OnDateSetListener mListener) {
        DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.setListener(mListener);
        Bundle bundle = new Bundle();
        if (currentDate != null)
            bundle.putLong(CURRENT_DATE, currentDate.getTime());
        newFragment.setArguments(bundle);
        return newFragment;
    }

    public void setListener(OnDateSetListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @TargetApi(11)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();

		Date minDate = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(CURRENT_DATE)) {
                Date date = new Date(bundle.getLong(CURRENT_DATE));
                c.setTime(date);
            }
            else if (bundle.containsKey(MIN_DATE)){
                minDate = new Date(bundle.getLong(MIN_DATE));
                c.setTime(minDate);
            }
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog pickerDialog;
        if (isHoneycombAndAbove())
        {
            pickerDialog = new DatePickerDialog(getActivity(), getConstructorListener(), year, month, day);
			if (minDate != null)
                pickerDialog.getDatePicker().setMinDate(minDate.getTime());
            else
                pickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        }
        else {
            pickerDialog = new MyDatePickerDialog(getActivity(), getConstructorListener(), year, month, day);
            if (minDate != null)
                ((MyDatePickerDialog)pickerDialog).setMinDate(minDate);
            else
                ((MyDatePickerDialog)pickerDialog).setMaxDate(new Date());

        }
        // Jelly Bean introduced a bug in DatePickerDialog (and possibly 
        // TimePickerDialog as well), and one of the possible solutions is 
        // to postpone the creation of both the listener and the BUTTON_* .
        // 
        // Passing a null here won't harm because DatePickerDialog checks for a null
        // whenever it reads the listener that was passed here. >>> This seems to be 
        // true down to 1.5 / API 3, up to 4.1.1 / API 16. <<< No worries. For now.
        //
        // See my own question and answer, and details I included for the issue:
        //
        // http://stackoverflow.com/a/11493752/489607
        // http://code.google.com/p/android/issues/detail?id=34833
        //
        // Of course, suggestions welcome.

        final DatePickerDialog picker = pickerDialog;

        if (hasJellyBeanAndAbove()) {
            picker.setButton(DialogInterface.BUTTON_POSITIVE,
                    getActivity().getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatePicker dp = picker.getDatePicker();
                            onDateSet(dp,
                                    dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                        }
                    });
            picker.setButton(DialogInterface.BUTTON_NEGATIVE,
                    getActivity().getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
        }
        return picker;
    }

    private static boolean hasJellyBeanAndAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    private static boolean isHoneycombAndAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private DatePickerDialog.OnDateSetListener getConstructorListener() {
        return hasJellyBeanAndAbove() ? null : this;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String date_selected = String.format("%02d", day) + "/"
                + String.format("%02d", month+1) + "/"
                + String.valueOf(year);
        mListener.onDateSet(date_selected);
    }

    /**
     * @see <a href="https://code.google.com/p/android/issues/detail?id=23761"
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }
}
