package com.example.billy.billproject1;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView myTextView;
    ListView myListView;
    EditText listingText;
    ArrayAdapter<String> myAdapter;
    ArrayList<String> myResultStringList;
    boolean strikeThrough;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setView();
        changeTitleText();
        setAdapter();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userText = listingText.getText().toString();


                if (userText.isEmpty()) {
                    Snackbar.make(view, "No data entered", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    myResultStringList.add(userText);
                    myAdapter.notifyDataSetChanged();
                    listingText.getText().clear();
                }
            }
        });


        // trying to do the click to strike through for finish list - use boolean to make it work
        // set boolean back to true when you first click it to run the un-strike code for next click

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView strike = (TextView) view;


               // Log.d("ResultActivity", Integer.toString(strike.getPaintFlags()) );

               // trial 2
                /*int paintFlags = strike.getPaintFlags();
                strike.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);

                if(paintFlags == 1281){
                    strike.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);
                    strike.setPaintFlags(757);
                } else{

                    strike.setPaintFlags(strike.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    strike.setPaintFlags(1281);
                }*/

                // Trial 1
//               if (!strikeThrough) {
//                   strike.setPaintFlags(strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                   strikeThrough = true;
//               }
//               else  {
//                   strike.setPaintFlags(strike.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                   strikeThrough = false;
//               }
        }
    }

    );

    //long click to remove position
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myResultStringList.remove(position);
                myAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    private void setView(){

        myTextView = (TextView)findViewById(R.id.listingTextview);
        listingText = (EditText)findViewById(R.id.listingEdit);
        myListView = (ListView)findViewById(R.id.listingListView);

    }

    private void changeTitleText() {
        String extra = getIntent().getStringExtra("Title");
        myTextView.setText(extra);
    }


    private void setAdapter(){

        myResultStringList = new ArrayList<>();

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myResultStringList); // the Layout can be custom layout.
        myListView.setAdapter(myAdapter);


    }

}
