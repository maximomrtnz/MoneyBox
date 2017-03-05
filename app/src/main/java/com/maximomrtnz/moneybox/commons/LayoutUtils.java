package com.maximomrtnz.moneybox.commons;

import android.content.Context;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by Maxi on 2/27/2017.
 */

public class LayoutUtils {

    public static void setSpinner(Context context, Spinner spinner, int categoryList){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,categoryList, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public static void setSpinner(Context context, Spinner spinner, int categoryList, String initialValue){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,categoryList, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if(!TextUtils.isEmpty(initialValue)){
            setSpinnerSelectionByValue(spinner,adapter, initialValue);
        }

    }

    public static void setSpinner(Context context, Spinner spinner, List<String> list){

        setSpinner(context,spinner,list,null);
    }

    public static void setSpinner(Context context, Spinner spinner, List<String> list, String initialValue){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if(!TextUtils.isEmpty(initialValue)){
            setSpinnerSelectionByValue(spinner,adapter, initialValue);
        }

    }

    public static void setSpinnerSelectionWithoutCallingListener(final Spinner spinner, final int selection) {
        final AdapterView.OnItemSelectedListener l = spinner.getOnItemSelectedListener();
        spinner.setOnItemSelectedListener(null);
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(selection);
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setOnItemSelectedListener(l);
                    }
                });
            }
        });
    }

    public static void setSpinnerSelectionByValue(Spinner spinner, ArrayAdapter adapter, String value){
        if (!value.equals(null)) {
            int spinnerPosition = adapter.getPosition(value);
            spinner.setSelection(spinnerPosition);
        }
    }
}
