package com.example.romhub_ronanlina.ligtasbaonbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private TextView mDisplayName;
    private EditText mBudget;
    private Spinner mLocation;
    private Button mFindButton;
    private ListView mResList;
    private Button mSuggest;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDisplayName = (TextView) findViewById(R.id.nameDisplay);
        mBudget = (EditText) findViewById(R.id.budgetText);
        mFindButton = (Button) findViewById(R.id.findButton);
        mLocation = (Spinner) findViewById(R.id.locationSpin);
        mResList = (ListView) findViewById(R.id.resList);
        mSuggest = (Button) findViewById(R.id.suggestButton);

        //populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.barangay,
                                                            android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLocation.setAdapter(adapter);

        mFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSearch();
            }
        });

        mSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,AddResto.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        greetUser();
    }

    private void greetUser(){

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        Query query = mDatabaseReference.child("users").orderByChild("email").equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot finalSnap :dataSnapshot.getChildren()){
                    user = finalSnap.getValue(User.class);
                    mDisplayName.setText(user.getName() + "!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void attemptSearch(){
        String budget = mBudget.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(budget)) {
            mBudget.setError(getString(R.string.error_field_budget));
            focusView = mBudget;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            fetchResults();
        }
    }

    private void fetchResults(){
        String barangay = mLocation.getSelectedItem().toString().toLowerCase();
        Double average_price = Double.parseDouble(mBudget.getText().toString());

        ResultsAdapter mAdapter = new ResultsAdapter(this,mDatabaseReference,average_price,barangay);
        mResList.setAdapter(mAdapter);
    }
}
