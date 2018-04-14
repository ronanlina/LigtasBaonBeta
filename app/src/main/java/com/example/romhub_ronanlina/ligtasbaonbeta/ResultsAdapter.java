package com.example.romhub_ronanlina.ligtasbaonbeta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ResultsAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private Double mBudget;
    private ArrayList<DataSnapshot> mSnapshotList;
    private Query mQuery;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ResultsAdapter(Activity activity, DatabaseReference ref, Double average_price, String location){
        mActivity = activity;
        mBudget = average_price;
        mDatabaseReference = ref.child("restaurants");

        mQuery = mDatabaseReference.orderByChild("barangay").equalTo(location);
        mQuery.addChildEventListener(mListener);


        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder{
        TextView resName;
        TextView resLocation;
        TextView resBudget;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public Restaurants getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(Restaurants.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){ // checks if it have a row available to recycle
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.result_list, parent, false);// create row

            //store all the bit and pieces of row in new view holder
            final ViewHolder holder = new ViewHolder();
            holder.resName = (TextView) convertView.findViewById(R.id.name);
            holder.resLocation = (TextView) convertView.findViewById(R.id.location);
            holder.resBudget = (TextView) convertView.findViewById(R.id.budget);
            holder.params = (LinearLayout.LayoutParams) holder.resName.getLayoutParams();
            convertView.setTag(holder);// saves the view holder for later

        }

        final Restaurants res = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String restoName = res.getName();
        holder.resName.setText(restoName);

        String restoLocation = res.getBarangay();
        holder.resLocation.setText(restoLocation);

        Double restoAvePrice = res.getAverage_price();

        if(restoAvePrice > mBudget){
            holder.resBudget.setText("Not recommended : Average price too high for your budget!");
        }
        else{
            holder.resBudget.setText("Save : Php " + String.format("%.2f", mBudget - restoAvePrice) + " !");
        }


        return convertView;
    }
}
