package com.example.semiprojectsample.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

    public static void showDialog(Context context, String title, String msg, String ok_msg, DialogInterface.OnClickListener okListener , String cancel_msg,
                                  DialogInterface.OnClickListener cancelListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);

        if( okListener != null){
            builder.setPositiveButton(ok_msg,okListener);
        }

        if(cancelListener != null){
            builder.setNegativeButton(cancel_msg,cancelListener);
        }

        builder.show();
    }
}
