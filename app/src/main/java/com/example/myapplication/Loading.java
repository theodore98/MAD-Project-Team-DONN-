package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class Loading {

        private Activity activity;
        private AlertDialog dialog;

        Loading(Activity myActivity)
        {
            activity = myActivity;
        }

        void startLoadingDialog()
        {
            AlertDialog.Builder builder =  new AlertDialog.Builder(activity);

            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.custom_dialog, null));
            builder.setCancelable(false);

            dialog = builder.create();
            dialog.show();

        }

}
