package com.example.billy.billproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    ArrayList<String> newMyStringList;

    private static final int MAIN_REQUEST_CODE = 30;
    // data key to retrieve data from intent. Public so we can retrieve data in DetailActivity
    public static final String DATA_KEY = "myDataKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Busy App");  // enter this to change the title bar name or change it at the strings.xml file
        setSupportActionBar(toolbar);

        setView();
        setAdapter();


        activity_result = new Intent(this, ResultActivity.class);




        // click on position to open up the List to add listings
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                String newTitle = (String) parent.getAdapter().getItem(position);
                intent.putExtra("Title", newTitle);
                startActivity(intent);


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


    private void setView(){
        enterText = (EditText) findViewById(R.id.enterText1);
        myListView = (ListView) findViewById(R.id.listView1);

    }

    private void setAdapter(){
        myStringList = new ArrayList<>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringList);
        myListView.setAdapter(myAdapter);

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (data != null) {
                    myStringList = data.getStringArrayListExtra(DATA_KEY);
                    printData();
                }
            } else  if (requestCode == RESULT_CANCELED){
                Log.w("Main", "Failed to get new list back");
            }
        }
    }

    private void printData(){
        if (myStringList == null){
            return;
        }
        for (String item : myStringList){
            Log.d("Main", item);
        }
    }*/

}
