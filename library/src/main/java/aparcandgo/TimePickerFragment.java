package aparcandgo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {
	
	private static String ENTERTYPE = "enter_port_type";

	public static TimePickerFragment newInstance(boolean isEnter) {
		TimePickerFragment newFragment = new TimePickerFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean(ENTERTYPE, isEnter);
		newFragment.setArguments(bundle);
		return newFragment;
	}

	public interface OnTimeSetListener {
		public void onTimeSet(String time);
	}

	private OnTimeSetListener onTimeSetListener;

	public void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
		this.onTimeSetListener = onTimeSetListener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) 
		{
			Bundle bundle = getArguments();
			if (bundle != null && bundle.containsKey(ENTERTYPE))
				return new PortTimePickDialog(getActivity(), this, bundle.getBoolean(ENTERTYPE));
			else
				return new DurationTimePickDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()), 15);
		}
		else
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		String time_selected = String.format("%02d", hourOfDay) + ":"
				+ String.format("%02d", minute);
		onTimeSetListener.onTimeSet(time_selected);
	}

}
