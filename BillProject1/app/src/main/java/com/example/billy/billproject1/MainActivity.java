package com.example.billy.billproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    EditText enterText;
    ArrayAdapter<String> myAdapter;
    ArrayList<String> myStringList;
    Intent activity_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setView();
        activity_result = new Intent(this, ResultActivity.class);

        setAdapter();


        // click on position to open up the List to add listings
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                String newTitle = (String)parent.getAdapter().getItem(position);
                intent.putExtra("Title", newTitle);
                startActivity(intent);


        //        Snackbar.make(view, " Create your own acton!!!", Snackbar.LENGTH_LONG)  //remember to comment it out
        //                .setAction("Action", null).show();


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = enterText.getText().toString();


                if(userText.isEmpty())
                {
                    Snackbar.make(view, "No data entered", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    myStringList.add(userText);
                    myAdapter.notifyDataSetChanged();
                    enterText.getText().clear();
                }
            }
        });


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myStringList.remove(position);
                myAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setView(){
        enterText = (EditText) findViewById(R.id.enterText1);
        myListView = (ListView) findViewById(R.id.listView1);

    }

    public void setAdapter(){

        myStringList = new ArrayList<>();

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringList);
        myListView.setAdapter(myAdapter);

    }

}
