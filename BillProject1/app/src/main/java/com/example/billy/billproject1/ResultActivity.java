package com.example.billy.billproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    Intent activity_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setView();
        changeTitleText();
        setIntent();


        

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userText = listingText.getText().toString();


                if(userText.isEmpty())
                {
                    Snackbar.make(view, "No data entered", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    myResultStringList.add(userText);
                    myAdapter.notifyDataSetChanged();
                    listingText.getText().clear();
                }
            }
        });

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


    private void setIntent(){
        activity_result = new Intent(this, MainActivity.class);

        myResultStringList = new ArrayList<>();

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myResultStringList);
        myListView.setAdapter(myAdapter);


    }

}
