package com.maximomrtnz.moneybox.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.maximomrtnz.moneybox.R;
import com.maximomrtnz.moneybox.model.Movement;

import java.util.Date;

/**
 * Created by Maxi on 2/16/2017.
 */

public class MovementDialogFragment extends DialogFragment {

    private Movement mMovement;

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

        final EditText amount = (EditText) v.findViewById(R.id.amount);
        final EditText type = (EditText) v.findViewById(R.id.type);
        final EditText description = (EditText) v.findViewById(R.id.description);
        final EditText category = (EditText) v.findViewById(R.id.category);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);

        builder.setTitle(getActivity().getString(R.string.movement));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mMovement.setAmount(Double.valueOf(amount.getText().toString()));
                mMovement.setDescription(description.getText().toString());
                mMovement.setCategory(category.getText().toString());
                mMovement.setType(type.getText().toString());

                // TODO: Investigate DatePicker and replace this part
                mMovement.setDate(new Date());

                ((MovementDialogListener)getActivity()).onPositiveButtonClicked(mMovement);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

}
