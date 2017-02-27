package com.maximomrtnz.moneybox.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maximomrtnz.moneybox.R;
import com.maximomrtnz.moneybox.commons.DateUtils;
import com.maximomrtnz.moneybox.commons.LayoutUtils;
import com.maximomrtnz.moneybox.model.Movement;
import com.maximomrtnz.moneybox.pickers.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Maxi on 2/16/2017.
 */

public class MovementDialogFragment extends DialogFragment implements DatePickerFragment.DatePickerFragmentListener{

    private Movement mMovement;
    private TextView mDate;
    private TextView mAmount;
    private TextView mDescription;
    private Spinner mCategory;
    private Spinner mType;

    public interface  MovementDialogListener{
        public void onPositiveButtonClicked(Movement movement);
    }

    public static MovementDialogFragment newInstance(Movement movement) {

        MovementDialogFragment f = new MovementDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable("movement",movement);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mMovement = getArguments().getParcelable("movement");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_movement_dialog, null);

        mAmount = (EditText) v.findViewById(R.id.amount);
        mType = (Spinner) v.findViewById(R.id.type_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.type_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mType.setAdapter(adapter);

        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCategoryList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDescription = (EditText) v.findViewById(R.id.description);
        mCategory = (Spinner) v.findViewById(R.id.category);

        mDate = (TextView) v.findViewById(R.id.date);

        mDate.setText(DateUtils.calendarToString(DateUtils.getCalendarFromTimeInMillis(mMovement.getDate()),"dd/MM/yyyy"));

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);

        builder.setTitle(getActivity().getString(R.string.movement));


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.create();

        return dialog;

    }


    @Override
    public void onStart(){
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null) {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(isFormValid()) {

                        mMovement.setAmount(Double.valueOf(mAmount.getText().toString()));
                        mMovement.setDescription(mDescription.getText().toString());
                        mMovement.setCategory(mCategory.getSelectedItem().toString());
                        mMovement.setType(mType.getSelectedItem().toString());

                        if(mMovement.getDate()==null){
                            mMovement.setDate(DateUtils.getCurrentTime());
                        }

                        ((MovementDialogListener) getActivity()).onPositiveButtonClicked(mMovement);

                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = DatePickerFragment.newInstance(DateUtils.getCalendarFromTimeInMillis(mMovement.getDate()));
        newFragment.show(getFragmentManager(), "datePicker");
        newFragment.setTargetFragment(this,0);
    }

    @Override
    public void onPositiveButtonClicked(Calendar date) {
        mMovement.setDate(date.getTimeInMillis());
        mDate.setText(DateUtils.calendarToString(date,"dd/MM/yyyy"));
    }

    public boolean isFormValid(){

        boolean isFormValid = true;

        if(mAmount.getText().toString().trim().length() == 0) {
            mAmount.setError(getString(R.string.amount_is_required));
            isFormValid = false;
        }

        return isFormValid;

    }

    private void setCategoryList(){

        int categoryList = (mType.getSelectedItemPosition()==0)?R.array.income_categories_array:R.array.expense_categories_array;

        LayoutUtils.setSpinner(getContext(),mCategory,categoryList);

    }

}
