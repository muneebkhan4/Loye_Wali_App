package com.example.project;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.project.Model.Iron_Info;

import java.util.Arrays;
import java.util.HashMap;

public class AlertDialogs {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void ShowAlertDialogWithListview(Context context, HashMap<String,Iron_Info> x, String title)
    {

        if(x==null)
            return;
        String[] strings = Arrays.stream(x.values().toArray()).map(Object::toString).toArray(String[]::new);

        //Create sequence of items
        final CharSequence[] Animals = (CharSequence[]) strings;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(title);
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
}
