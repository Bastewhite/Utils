package commons;

import android.content.Context;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import es.utils.R;

public class UtilsEditText {
	
	/**
	 * Funcion que devuelve cierto si un editText esta vacio y muestra un
	 * mensaje de error
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isError(TextView editText) {
		return isError(editText, true);
	}
	
	public static boolean isError(TextView editText, boolean requestFocus) {
		if (editText.getText().toString().isEmpty()) {
			editText.setError(editText.getContext().getString(R.string.campo_obligatorio));
			if (requestFocus)
				editText.requestFocus();
			return true;
		} else {
			editText.setError(null);
			return false;
		}
	}

    /**
     * Devuelve true si el checkbox está en estado 'checked'. En caso contrario pone un error
     * propiado en el TextView pasado como target
     */
    public static boolean isCVError(CheckBox checkBox, TextView target) {
        if (checkBox.isChecked()) {
            target.setError(null);
            return false;
        }
        else {
            target.setError(checkBox.getContext().getString(R.string.campo_obligatorio));
            return true;
        }
    }
	
	public static boolean isErrorEmail(EditText editText) {
		return isErrorPattern(editText, Patterns.EMAIL_ADDRESS, R.string.error_formato_email);
	}

    public static boolean isErrorPostalCode(EditText editText){
        return isErrorPattern(editText, Pattern.compile("\\d{5}"), R.string.error_formato_cp);
    }

    public static boolean isErrorPhone(EditText editText){
        return isErrorPattern(editText, Pattern.compile("^[0-9]{9,}$"), R.string.error_formato_phone);
    }

	private static boolean isErrorPattern(EditText editText, Pattern mPattern, int errorMsg) {
		Context context = editText.getContext();
		String text = editText.getText().toString();
		if (text.isEmpty()) {
			editText.setError(context.getString(R.string.campo_obligatorio));
			editText.requestFocus();
			return true;
		} else {
			if (mPattern.matcher(text).matches()) {
				editText.setError(null);
				return false;
			}
			else {
				editText.setError(context.getString(errorMsg));
				editText.requestFocus();
				return true;
			}
		}
	}

    public static boolean isPassError(EditText mPass, EditText mPassRepeat) {
        Context context = mPass.getContext();
        String pass = mPass.getText().toString();
        String repeatPass = mPassRepeat.getText().toString();
        if (!pass.isEmpty() && pass.length() < 6) {
            mPass.setError(context.getString(R.string.pass_error_lenght));
            return true;
        }
        else {
            mPass.setError(null);
        }

        if (pass.equals(repeatPass)) {
            mPassRepeat.setError(null);
            return false;
        }
        else {
            mPassRepeat.setError(context.getString(R.string.pass_error));
            return true;
        }
    }

    public static void dismissKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
