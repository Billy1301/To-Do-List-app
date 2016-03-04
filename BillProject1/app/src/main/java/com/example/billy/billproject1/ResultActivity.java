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
    ArrayList<String> myResultStringList;
    ArrayList<String> myCopyResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setView();
        changeTitleText();
        setAdapter();


        /*
        trail 4 for strikethrough.. customer adapter is not linking correctly
        myResultStringList = new ArrayList<>();
        myAdapter = new CustomAdapter<String> (this, android.R.layout.simple_list_item_1, myResultStringList);
        myListView.setAdapter(myAdapter);
        */



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

        // myCopyResultList = getData();




        // click once to strike through, click again to un-strike.
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               /* trial 4 -- customer adapter view is not working correctly
                myAdapter.getSelectedStrings().add(list[i]);
                myAdapter.notifyDataSetChanged(); */

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

        myAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, myResultStringList); // the Layout can be custom layout.
        myListView.setAdapter(myAdapter);

    }

    private ArrayList<String> getData(){
        Intent newList = getIntent();
        if (newList == null){
            return null;
        }
        return newList.getStringArrayListExtra(MainActivity.DATA_KEY);
    }

    private void printList(){
        if (myResultStringList == null){
            return;
        }
        for (String item : myResultStringList){
            Log.d("Detail", item);
        }
    }


    private void modifyList(){
        if (myResultStringList == null){
            return;
        }
        myCopyResultList = new ArrayList<>(myResultStringList.size());
        for (String item : myResultStringList){
            item += " back";
            myCopyResultList.add(item);
        }
    }


    private void sendNewListBack(){
        Intent newNewList = getIntent();
        if (newNewList == null){
            return;
        }
        newNewList.putExtra(MainActivity.DATA_KEY, myCopyResultList);
        setResult(RESULT_OK, newNewList);
        finish();
    }


    @Override
    public void onBackPressed() {
        sendDataBack();
    }


    private void sendDataBack(){

        modifyList();
        sendNewListBack();

    }

}
