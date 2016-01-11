package com.example.anh.utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Created by anh on 1/6/16.
 */
public class Screen {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static ArrayList<TableRow> getAllTableRow(TableLayout tableLayout) {
        ArrayList<TableRow> result = new ArrayList<>();
        for(int i = 0 ; i < tableLayout.getChildCount();i++) {
            View v = tableLayout.getChildAt(i);
            if(v instanceof  TableRow) {
                result.add((TableRow)v);
            }
        }
        return result;
    }
    public static ArrayList<LinearLayout> getAllTableCell(TableLayout tableLayout) {
        ArrayList<TableRow> tableRows = new ArrayList<>();
        ArrayList<LinearLayout> result = new ArrayList<>();
        for(int i = 0 ; i < tableLayout.getChildCount();i++) {
            View v = tableLayout.getChildAt(i);
            if(v instanceof  TableRow) {
                tableRows.add((TableRow)v);
            }
        }

        for(int i = 0; i < tableRows.size();i++) {
            TableRow tr = tableRows.get(i);
            for(int j = 0 ; j < tr.getChildCount();j++) {
                View v = tr.getChildAt(j);
                if(v instanceof  LinearLayout) {
                    result.add((LinearLayout)v);
                }
            }
        }

        return result;
    }


}
