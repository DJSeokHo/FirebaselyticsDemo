package com.swein.firebaselyticsdemo.framework.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by seokho on 17/11/2016.
 */

public class DialogUtil {

    public static void createNormalDialogWithOneButton(Context context, String title, String message, boolean cancelAble, String positiveButtonText,
                                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);


        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, onClickListener);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public static void createNormalDialogWithTwoButton(Context context, String title, String message, boolean cancelAble, String positiveButtonText, String negativeButtonText,
                                                       DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, positiveButton)
                .setNegativeButton(negativeButtonText, negativeButton);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static void createNormalDialogWithThreeButton(Context context, String title, String message, boolean cancelAble, String positiveButtonText, String negativeButtonText, String otherButtonText,
                                                         DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton, DialogInterface.OnClickListener neutralButton) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelAble)
                .setPositiveButton(positiveButtonText, positiveButton)
                .setNegativeButton(negativeButtonText, negativeButton)
                .setNeutralButton(otherButtonText, neutralButton);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public static void createListItemDialogWithTwoButton(final Context context, String title, ListAdapter listAdapter, DialogInterface.OnClickListener adapter,
                                                         boolean cancelAble, String positiveButtonText, String negativeButtonText,
                                                         DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);

        alertDialogBuilder
                .setCancelable(cancelAble)
                .setAdapter(listAdapter, adapter)
                .setPositiveButton(positiveButtonText, positiveButton)
                .setNegativeButton(negativeButtonText, negativeButton);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public static ProgressDialog createProgressDialog(Context context, String title, String msg, boolean cancelAble, boolean canceledOnTouchOutside){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelAble);
        progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }

    public static void createCustomListViewDialog(Context context , String title , String positiveBtnTitle , String negativeBtnTitle , ListAdapter listAdapter ,
                                                  DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton , int textColor, int padding) {

        if (textColor == -1) {
            textColor = Color.WHITE;
        }

        AlertDialog.Builder builderSingle = new AlertDialog.Builder( context );
        builderSingle.setIcon( null );
        builderSingle.setCancelable(false);

        TextView titleTv = new TextView( context );
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT );
        titleTv.setPadding( padding , padding , padding , padding );
        titleTv.setLayoutParams(lp);
        titleTv.setText(title);
        titleTv.setTextColor(textColor);
        titleTv.setTypeface(null, Typeface.BOLD);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        builderSingle.setCustomTitle(titleTv);

        builderSingle.setNegativeButton(negativeBtnTitle, negativeButton);

        builderSingle.setAdapter(listAdapter, null);
        builderSingle.setPositiveButton(positiveBtnTitle, positiveButton);

        builderSingle.show();
    }

}
