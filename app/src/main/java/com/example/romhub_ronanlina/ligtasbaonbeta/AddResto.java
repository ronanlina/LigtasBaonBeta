package com.example.romhub_ronanlina.ligtasbaonbeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddResto extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;

    private EditText mResName;
    private EditText mAvePrice;
    private Spinner mBarangay;
    private Button mAdd;
    private Restaurants mRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resto);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mResName = (EditText) findViewById(R.id.restoNameText);
        mAvePrice = (EditText) findViewById(R.id.avePriceText);
        mBarangay = (Spinner) findViewById(R.id.barangaySpin);
        mAdd = (Button) findViewById(R.id.addButton);

        //populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.barangay,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBarangay.setAdapter(adapter);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAdd();
            }
        });
    }

    private void attemptAdd(){
        String resName = mResName.getText().toString();
        String avePrice = mAvePrice.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(resName)) {
            mResName.setError(getString(R.string.error_field_required));
            focusView = mResName;
            cancel = true;
        }

        if (TextUtils.isEmpty(avePrice)) {
            mAvePrice.setError(getString(R.string.error_field_required));
            focusView = mAvePrice;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            addSuggestion();
        }
    }

    private void addSuggestion(){
        String resName = mResName.getText().toString();
        Double avePrice = Double.parseDouble(mAvePrice.getText().toString());
        String cBarangay = mBarangay.getSelectedItem().toString().toLowerCase();

        mRes = new Restaurants(resName,avePrice,cBarangay);
        mDatabaseReference.child("restaurants").push().setValue(mRes);

        Toast.makeText(this,"Thank you for your suggestion!", Toast.LENGTH_SHORT).show();
    }
}
