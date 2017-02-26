package com.maximomrtnz.moneybox.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.maximomrtnz.moneybox.commons.DateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Maxi on 2/18/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar mDate;

    public interface  DatePickerFragmentListener{
        public void onPositiveButtonClicked(Calendar date);
    }

    public static DatePickerFragment newInstance(Calendar date) {

        DatePickerFragment f = new DatePickerFragment();

        Bundle args = new Bundle();
        args.putLong("date",date.getTimeInMillis());
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDate = DateUtils.getCalendarFromTimeInMillis(getArguments().getLong("date"));

        int year = mDate.get(Calendar.YEAR);
        int month = mDate.get(Calendar.MONTH); // Note: zero based!
        int day = mDate.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        mDate.set(year,month,day);

        if(getTargetFragment()!=null) { // If we calling DatePicker from Fragment
            ((DatePickerFragmentListener) getTargetFragment()).onPositiveButtonClicked(mDate);
        }else{ // We are calling it from Activity
            ((DatePickerFragmentListener) getActivity()).onPositiveButtonClicked(mDate);
        }

    }

}
