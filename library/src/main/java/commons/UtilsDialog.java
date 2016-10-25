package commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListAdapter;

import es.utils.R;

/**
 * Created by Fran on 28/05/2014.
 */
public final class UtilsDialog {

    /**
     * The constant loading_progress.
     */
    private static ProgressDialog loading_progress = null;
    /**
     * The constant alertDialog.
     */
    private static AlertDialog alertDialog = null;

    /**
     * Instantiates a new Utils dialog.
     */
    private UtilsDialog() {
        //Not Used
    }

    /**
     * Show progress dialog.
     *
     * @param context the context
     */
    public static void showProgressDialog(final Context context) {
        showProgressDialog(context, null);
    }

    /**
     * Show progress dialog.
     *
     * @param context  the context
     * @param stringID the string id
     */
    public static void showProgressDialog(final Context context, final DialogInterface.OnCancelListener listener) {
        if (loading_progress == null && context != null) {
            loading_progress = new ProgressDialog(context);
            loading_progress.setMessage(context.getString(R.string.loading));
            loading_progress.setCancelable(listener != null);
            loading_progress.setCanceledOnTouchOutside(false);
            loading_progress.setOnCancelListener(listener);
            loading_progress.show();
        }
    }

    /**
     * Dismiss progress dialog.
     */
    public static void dismissProgressDialog() {
        if (loading_progress != null && loading_progress.isShowing()) {
            try {
                loading_progress.dismiss();
            } catch (IllegalArgumentException e) {
                Log.e(UtilsDialog.class.getCanonicalName(), "dismissProgressDialog: ", e);
            }
        }
        loading_progress = null;
    }

    /**
     * Show alert dialog.
     *
     * @param context    the context
     * @param titleRes   the title res
     * @param messageRes the message res
     */
    public static void showAlertDialog(final Context context, final int titleRes, final int messageRes) {
        showAlertDialog(context, context.getString(titleRes), context.getString(messageRes), android.R.string.cancel,
                null);
    }

    /**
     * Show alert dialog.
     *
     * @param context the context
     * @param title   the title text
     * @param message the message text
     */
    public static void showAlertDialog(final Context context, final String title, final String message) {
        showAlertDialog(context, title, message, android.R.string.cancel, null);
    }

    /**
     * Show alert dialog.
     *
     * @param context the context
     * @param message the message
     */
    public static void showAlertDialog(final Context context, final String message) {
        showAlertDialog(context, null, message, android.R.string.cancel, null);
    }

    /**
     * Show alert dialog.
     *
     * @param context  the context
     * @param stringId the string id
     */
    public static void showAlertDialog(final Context context, final int stringId) {
        showAlertDialog(context, null, context.getString(stringId), android.R.string.cancel, null);
    }

    /**
     * Show alert dialog.
     *
     * @param context  the context
     * @param stringId the string id
     * @param listener the listener
     */
    public static void showAlertDialog(final Context context, final int stringId,
                                       final DialogInterface.OnClickListener listener) {
        showAlertDialog(context, null, context.getString(stringId), android.R.string.cancel, listener);
    }

    /**
     * Show alert dialog.
     *
     * @param context  the context
     * @param string   the string
     * @param listener the listener
     */
    public static void showAlertDialog(final Context context, final String string,
                                       final DialogInterface.OnClickListener listener) {
        showAlertDialog(context, null, string, android.R.string.cancel, listener);
    }

    /**
     * Show alert dialog.
     *
     * @param context          the context
     * @param stringId         the string id
     * @param stringPositiveId the string positive id
     * @param listener         the listener
     */
    public static void showAlertDialog(final Context context, final int stringId, final int stringPositiveId,
                                       final DialogInterface.OnClickListener listener) {
        showAlertDialog(context, null, context.getString(stringId), stringPositiveId, listener);
    }

    /**
     * Show alert dialog.
     *
     * @param context       the context
     * @param stringTitle   the string title
     * @param stringMessage the string message
     * @param listener      the listener
     */
    public static void showAlertDialog(final Context context, final String stringTitle, final String stringMessage,
                                       final DialogInterface.OnClickListener listener) {
        showAlertDialog(context, stringTitle, stringMessage, android.R.string.ok, listener);
    }

    /**
     * Show alert dialog.
     *
     * @param context          the context
     * @param titleRes         the title res
     * @param messageRes       the message res
     * @param stringPositiveId the string positive id
     * @param listener         the listener
     */
    public static void showAlertDialog(final Context context, final int titleRes,
                                       final int messageRes, final int stringPositiveId,
                                       final DialogInterface.OnClickListener listener) {
        showAlertDialog(context, context.getString(titleRes), context.getString(messageRes),
                stringPositiveId, listener);
    }

    /**
     * Show alert dialog.
     *
     * @param context          the context
     * @param title            the title
     * @param message          the message
     * @param stringPositiveId the string positive id
     * @param listener         the listener
     */
    public static void showAlertDialog(final Context context, final String title, final String message,
                                       final int stringPositiveId, final DialogInterface.OnClickListener listener) {
        if (context == null) {
            return;
        }
        if (context instanceof FragmentActivity && ((FragmentActivity) context).isFinishing()) {
            return;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        if (alertDialog == null || !alertDialog.isShowing()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(listener == null);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(stringPositiveId, listener);
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context   the context
     * @param messageId the message id
     * @param listener  the listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final int messageId,
                                                 final DialogInterface.OnClickListener listener) {
        showAlertDialogTwoButtons(context, context.getString(messageId), listener);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context  the context
     * @param message  the message
     * @param listener the listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final String message,
                                                 final DialogInterface.OnClickListener listener) {
        showAlertDialogTwoButtons(context, null, message, android.R.string.ok, listener, null);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context          the context
     * @param messageId        the message id
     * @param stringPositiveId the string positive id
     * @param listener         the listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final int messageId,
                                                 final int stringPositiveId,
                                                 final DialogInterface.OnClickListener listener) {
        showAlertDialogTwoButtons(context, null, context.getString(messageId), stringPositiveId,
                listener, null);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context          the context
     * @param titlePopUp       the title pop up
     * @param messageId        the message id
     * @param stringPositiveId the string positive id
     * @param listener         the listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final int titlePopUp,
                                                 final int messageId, final int stringPositiveId,
                                                 final DialogInterface.OnClickListener listener) {
        showAlertDialogTwoButtons(context, context.getString(titlePopUp),
                context.getString(messageId), stringPositiveId, listener, null);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context          the context
     * @param message          the message
     * @param stringPositiveId the string positive id
     * @param positiveListener the positive listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final String message,
                                                 final int stringPositiveId,
                                                 final DialogInterface.OnClickListener positiveListener) {
        showAlertDialogTwoButtons(context, null, message, stringPositiveId, positiveListener, null);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context          the context
     * @param titlePopUp       the title pop up
     * @param message          the message
     * @param stringPositiveId the string positive id
     * @param positiveListener the positive listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final int titlePopUp,
                                                 final String message, final int stringPositiveId,
                                                 final DialogInterface.OnClickListener positiveListener) {
        showAlertDialogTwoButtons(context, context.getString(titlePopUp), message,
                stringPositiveId, positiveListener, null);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context          the context
     * @param titlePopUp       the title pop up
     * @param message          the message
     * @param stringPositiveId the string positive id
     * @param positiveListener the positive listener
     * @param negativeListener the negative listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final String titlePopUp,
                                                 final String message, final int stringPositiveId,
                                                 final DialogInterface.OnClickListener positiveListener,
                                                 final DialogInterface.OnClickListener negativeListener) {
        showAlertDialogTwoButtons(context, titlePopUp, message, stringPositiveId,
                android.R.string.cancel, positiveListener, negativeListener);
    }

    /**
     * Show alert dialog two buttons.
     *
     * @param context          the context
     * @param titlePopUp       the title pop up
     * @param message          the message
     * @param stringPositiveId the string positive id
     * @param stringNegativeId the string negative id
     * @param positiveListener the positive listener
     * @param negativeListener the negative listener
     */
    public static void showAlertDialogTwoButtons(final Context context, final String titlePopUp,
                                                 final String message, final int stringPositiveId,
                                                 final int stringNegativeId,
                                                 final DialogInterface.OnClickListener positiveListener,
                                                 final DialogInterface.OnClickListener negativeListener) {
        if (context instanceof FragmentActivity && ((FragmentActivity) context).isFinishing())
            return;
        if (context instanceof Activity && ((Activity) context).isFinishing())
            return;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, getDialogTheme());
        builder.setMessage(message);
        if (!TextUtils.isEmpty(titlePopUp)) {
            builder.setTitle(titlePopUp);
        }

        builder.setPositiveButton(stringPositiveId, positiveListener);
        builder.setNegativeButton(stringNegativeId, negativeListener);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showListDialog(Context context, String title, String[] items,
                                      DialogInterface.OnClickListener onClickListener) {
        showListDialog(context, title, items, null, onClickListener);
    }

    public static void showListDialog(Context context, String title, ListAdapter listAdapter,
                                      DialogInterface.OnClickListener onClickListener) {
        showListDialog(context, title, null, listAdapter, onClickListener);
    }

    private static void showListDialog(Context context, String title, String[] items, ListAdapter listAdapter,
                                       DialogInterface.OnClickListener onClickListener) {

        if (context == null) {
            return;
        }
        if (context instanceof FragmentActivity && ((FragmentActivity) context).isFinishing()) {
            return;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }

        if (alertDialog == null || !alertDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            if (listAdapter != null) {
                builder.setAdapter(listAdapter, onClickListener);
            } else if (items != null) {
                builder.setItems(items, onClickListener);
            }

            builder.setCancelable(true);
            builder.setTitle(title);
            alertDialog = builder.create();
            alertDialog.show();
        }

    }

    /**
     * To fix:
     * <ref>
     * http://stackoverflow.com/questions/27187353/dialog-buttons-with-long-text-not-wrapping-squeezed-out-
     * material-theme-on-an
     * </ref>
     *
     * @return The Theme
     */
    private static int getDialogTheme() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 ? R.style.CustomLollipopDialogStyle : 0;
    }

}
