package com.example.elias.care4cleaning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by elias on 30-10-2016.
 */

public class LoginDialogFragment extends DialogFragment {

    AlertDialog.Builder alert;
    String userInput = "";
    Resources resources;

    public LoginDialogFragment(){

    }

    public String getUserInput()
    {
        return userInput;
    }

    public LoginDialogFragment(Context context, String title, String message, String defaultInput) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        final EditText input = new EditText(context);
        input.setText(defaultInput);
        alert.setView(input);

        resources = context.getResources();

        alert.setPositiveButton(resources.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userInput = input.getText().toString();
                clickOk();

            }
        });

        alert.setNegativeButton(resources.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                clickCancel();
            }
        });

    }

    public LoginDialogFragment(Context context, String title, String message) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        final EditText input = new EditText(context);
        alert.setView(input);
        resources = context.getResources();



        alert.setPositiveButton(resources.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userInput = input.getText().toString();
                clickOk();

            }
        });

        alert.setNegativeButton(resources.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                clickCancel();
            }
        });


    }

    public void clickOk()
    {

    }

    public void clickCancel()
    {
    }


    public void show()
    {
        alert.show();
    }

}
