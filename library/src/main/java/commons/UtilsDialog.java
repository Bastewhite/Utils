package commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import es.utils.R;

/**
 * Created by Fran on 28/05/2014.
 */
public class UtilsDialog {

    private static ProgressDialog loading_progress = null;
    private static AlertDialog alertDialog = null;

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, R.string.loading);
    }

    public static void showProgressDialog(Context context, int stringID) {
        if (loading_progress == null && context != null) {
            loading_progress = new ProgressDialog(context);
            loading_progress.setMessage(context.getString(stringID));
            loading_progress.setCancelable(false);
            loading_progress.show();
        }
    }

//    public static void showProgressDialog(Context context, final BaseWrapper wrapper) {
//        loading_progress = new ProgressDialog(context);
//        loading_progress.setMessage(context.getString(R.string.loading));
//        loading_progress.setCancelable(true);
//        loading_progress.show();
//
//        loading_progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                wrapper.cancelPendingRequests();
//            }
//        });
//    }

    public static void updateProgressDialog(Context mContext, final String message) {
        Activity activity = null;
        if (mContext instanceof Activity)
            activity = ((Activity) mContext);
        else if (mContext instanceof FragmentActivity)
            activity = ((FragmentActivity) mContext);

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (loading_progress != null) {
                        loading_progress.setMessage(message);
                        loading_progress.setCancelable(false);
                    }
                }
            });
        }
    }

    public static void dismissProgressDialog() {
        if(loading_progress != null && loading_progress.isShowing()) {
            loading_progress.dismiss();
        }
        loading_progress = null;
    }

    public static void showAlertDialog(Context context, String string) {
        showAlertDialog(context, string, null);
    }

    public static void showAlertDialog(Context context, int stringId) {
        showAlertDialog(context, context.getString(stringId), null);
    }

    public static void showAlertDialog(Context context, int stringId, DialogInterface.OnClickListener listener) {
        showAlertDialog(context, context.getString(stringId), listener);
    }

    public static void showAlertDialog(Context context, String string, DialogInterface.OnClickListener listener) {
        if (context == null)
            return;
        if (context instanceof FragmentActivity && ((FragmentActivity) context).isFinishing())
            return;
        if (context instanceof Activity && ((Activity) context).isFinishing())
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(listener == null);
        builder.setMessage(string);
        builder.setPositiveButton(android.R.string.ok, listener);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showAlertDialogTwoButtons(Context context, int stringId, DialogInterface.OnClickListener listener) {
        if (context instanceof FragmentActivity && ((FragmentActivity) context).isFinishing())
            return;
        if (context instanceof Activity && ((Activity) context).isFinishing())
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(stringId));
        builder.setPositiveButton(android.R.string.ok, listener);
        builder.setNegativeButton(android.R.string.cancel, null);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void dismissAlertDialog() {
        if(alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public static void showToast(Context context, int stringID) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing()))
            return;

        Toast.makeText(context, stringID, Toast.LENGTH_SHORT).show();
    }

}
