package com.example.billy.billproject1;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView myTextView;
    ListView myListView;
    EditText listingText;
    ArrayAdapter<String> myAdapter;
    ArrayList<String> myCopyResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setView();
        changeTitleText();
        myCopyResultList = getData();
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

                    myCopyResultList.add(userText);
                    myAdapter.notifyDataSetChanged();
                    listingText.getText().clear();

                }

            }
        });


        // click once to strike through, click again to un-strike.
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // trail 5!! after many hrs of research and trials, got help from the NY cohort :P
                TextView userStrikeThrough = (TextView) view;

                if (!myListView.isItemChecked(position) &&
                        !((userStrikeThrough.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0)) {

                    Log.i("[TAP]", "Strikethrough");
                    userStrikeThrough.setPaintFlags(userStrikeThrough.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    myListView.setItemChecked(position, true);
                }
                else {

                    Log.i("[TAP]", "Un-strike");
                    userStrikeThrough.setPaintFlags(userStrikeThrough.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    myListView.setItemChecked(position, false);
                }
            }
        }
    );

                //long click to remove position
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myCopyResultList.remove(position);
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

        myAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, myCopyResultList);
        myListView.setAdapter(myAdapter);

    }

    private ArrayList<String> getData(){
        Intent newList = getIntent();
        if (newList == null){
            return null;
        }
        return newList.getStringArrayListExtra(MainActivity.NEW_DATA_KEY);
    }


    private void sendNewListBack(){
        Intent newNewList = getIntent();
        if (newNewList == null){
            return;
        }
        newNewList.putExtra(MainActivity.NEW_DATA_KEY, myCopyResultList);
        setResult(RESULT_OK, newNewList);
        Log.d("Result", "Sending back");
        finish();
    }


    @Override
    public void onBackPressed() {
        sendDataBack();
        Log.d("ResultActivity", "Pressed Back Button");
    }


    private void sendDataBack(){
        sendNewListBack();
        //Log.d("ResultActivity", "send data back");

    }

}
