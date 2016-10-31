package com.example.elias.care4cleaning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by elias on 30-10-2016.
 */

public class LoginDialogFragment extends DialogFragment {

    public LoginDialogFragment(){

    }

    OnPosetiveListener mCallback;

    public interface OnPosetiveListener{
        public void OnposetiveClicked();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mCallback = (OnPosetiveListener) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement OnPosetiveListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceSate){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        //set Dialog layout to new layout file

        return alert.create();
    }

}
