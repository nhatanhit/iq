package com.example.anh.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.anh.iqtest.R;
import com.example.anh.listener.CustomListener;
import com.example.anh.utils.KeyValueDb;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    CustomListener.TimePicker mListener;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //get number of questions from shared preferences
        String numberQuestions =  KeyValueDb.getValue(getActivity().getApplicationContext(), "number_of_question");
        if(!numberQuestions.equals("0") && !numberQuestions.equals("")) {
            String title = "Set Time Test for " + numberQuestions + " questions";
            getDialog().setTitle(title);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);



        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mListener.onTimeSet(view,hourOfDay,minute);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CustomListener.TimePicker)activity;

        }
        catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CustomListener.TimePicker ");
        }
    }
}
