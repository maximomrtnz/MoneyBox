package com.maximomrtnz.moneybox.fragments;


import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maximomrtnz.moneybox.commons.DateUtils;
import com.maximomrtnz.moneybox.model.Movement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxi on 3/4/2017.
 */

public abstract class BaseFragment extends Fragment {

    protected FirebaseAuth mFirebaseAuth;
    protected FirebaseUser mFirebaseUser;
    protected DatabaseReference mDatabase;
    protected Query mMovementsQuery;
    protected List<Movement> mMovements = new ArrayList<>();
    protected Double mAmountIncomes = 0d;
    protected Double mAmountExpenses = 0d;

    public BaseFragment(){

        //get firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        // get firebase database instance
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // get current user
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

    }

    public void loadMovements(Long fromDate, Long toDate){

        mMovementsQuery = mDatabase.child("users").child(mFirebaseUser.getUid()).child("movements").orderByChild("date").startAt(fromDate).endAt(toDate);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mMovements.clear();

                Double amount = 0d;

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Movement movement = ds.getValue(Movement.class);
                    movement.setId(ds.getKey());
                    if(movement.getType().toLowerCase().equals("income")){
                        mAmountExpenses+=movement.getAmount();
                    }else {
                        mAmountIncomes += movement.getAmount();
                    }
                    mMovements.add(movement);
                }

                onMovementListChange();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(databaseError.toException());
            }
        };

        mMovementsQuery.addValueEventListener(valueEventListener);

    }

    protected abstract void onMovementListChange();

}
