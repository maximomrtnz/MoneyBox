package com.maximomrtnz.moneybox.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maximomrtnz.moneybox.R;
import com.maximomrtnz.moneybox.activities.MainActivity;
import com.maximomrtnz.moneybox.adapters.MovementRecyclerViewAdapter;
import com.maximomrtnz.moneybox.commons.DateUtils;
import com.maximomrtnz.moneybox.commons.LayoutUtils;
import com.maximomrtnz.moneybox.model.Movement;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovementListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovementListFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MovementRecyclerViewAdapter mRecyclerViewAdapter;
    private TextView mAmount;
    private Spinner mMonths;
    private Spinner mYears;

    public MovementListFragment(){
        super();
    }

    public static MovementListFragment newInstance() {
        MovementListFragment fragment = new MovementListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movement_list, container, false);

        mAmount = (TextView)rootView.findViewById(R.id.amount);

        mMonths = (Spinner)rootView.findViewById(R.id.months);
        mYears = (Spinner)rootView.findViewById(R.id.years);

        // Load months into months spinner
        LayoutUtils.setSpinner(getContext(),mMonths,R.array.months_array);


        mMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Long fromDate = DateUtils.getFirstDate((2016+mYears.getSelectedItemPosition()),mMonths.getSelectedItemPosition());

                Long toDate = DateUtils.getLastDate((2016+mYears.getSelectedItemPosition()),mMonths.getSelectedItemPosition());

                loadMovements(fromDate,toDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Set current month
        LayoutUtils.setSpinnerSelectionWithoutCallingListener(mMonths,Calendar.getInstance().get(Calendar.MONTH));

        // Load years into year spinner
        ArrayList<String> years = new ArrayList<String>();

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 2016; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        LayoutUtils.setSpinner(getContext(),mYears,years);

        mYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Long fromDate = DateUtils.getFirstDate((2016+mYears.getSelectedItemPosition()),mMonths.getSelectedItemPosition());

                Long toDate = DateUtils.getLastDate((2016+mYears.getSelectedItemPosition()),mMonths.getSelectedItemPosition());

                loadMovements(fromDate,toDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LayoutUtils.setSpinnerSelectionWithoutCallingListener(mYears,years.size()-1);

        Long fromDate = DateUtils.getFirstDate((2016+mYears.getSelectedItemPosition()),mMonths.getSelectedItemPosition());

        Long toDate = DateUtils.getLastDate((2016+mYears.getSelectedItemPosition()),mMonths.getSelectedItemPosition());

        loadMovements(fromDate,toDate);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.movement_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerViewAdapter = new MovementRecyclerViewAdapter(getContext(), mMovements, new MovementRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onRecyclerViewListClicked(Movement movement) {
                onClickedMovement(movement);
            }
        });

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return rootView;
    }

    public void onClickedMovement(final Movement movement){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(R.array.movement_options_dialog_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        // Edit
                        ((MainActivity)getActivity()).showMovementDialog(movement);
                        break;
                    default:
                        // Delete
                        ((MainActivity)getActivity()).deleteMovement(movement);
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onMovementListChange() {

        mAmount.setText(mAmountExpenses.toString());

        mRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerViewAdapter.notifyDataSetChanged();
    }
}
