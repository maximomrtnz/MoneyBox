package com.maximomrtnz.moneybox.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maximomrtnz.moneybox.R;
import com.maximomrtnz.moneybox.adapters.MovementRecyclerViewAdapter;
import com.maximomrtnz.moneybox.model.Movement;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovementListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovementListFragment extends Fragment {

    private List<Movement> mMovements;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MovementRecyclerViewAdapter mRecyclerViewAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference mMovementsReference;

    public MovementListFragment() {
        // Required empty public constructor
    }

    public static MovementListFragment newInstance() {
        MovementListFragment fragment = new MovementListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movement_list, container, false);

        //get firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        // get firebase database instance
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // get current user
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        loadMovements();

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.movement_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerViewAdapter = new MovementRecyclerViewAdapter(getContext(),mMovements);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void loadMovements(){

        mMovements = new ArrayList();

        mMovementsReference = mDatabase.child("users").child(mFirebaseUser.getUid()).child("movements");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mMovements.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    mMovements.add(ds.getValue(Movement.class));
                }

                mRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(databaseError.toException());
            }
        };

        mMovementsReference.addValueEventListener(valueEventListener);

    }

}
