package com.example.billy.billproject1;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    EditText enterText;
    ArrayAdapter<String> myAdapter;
    ArrayList<String> myDataList;
    ArrayList<String> dummyList;
    ArrayList<String> returnDataList = new ArrayList<>();



    private static int currentPosition;
    private ArrayList<ArrayList<String>> myMasterDataList;
    private static final int MAIN_REQUEST_CODE = 30;
    public static final String DATA_KEY = "myDataKey";
    public static final String NEW_DATA_KEY = "myNewDataKey";
    public static final String APP_TITLE = "Busy App";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(APP_TITLE);  // enter this to change the title bar name or change it at the strings.xml file
        setSupportActionBar(toolbar);

        dummyList = new ArrayList<>();
        myMasterDataList = new ArrayList<>();

        setView();
        setAdapter();
        onClickItem();
        floatButton();
        onItemLongClick();





    }


    public void onClickItem(){
        // click on position to open up the List to add to do list
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                // Intent intent (the intent can use other name, just make sure to use it throughout)

                String newTitle = (String) parent.getAdapter().getItem(position);
                intent.putExtra("Title", newTitle); // this part is for my title bar name
                intent.putExtra(DATA_KEY, currentPosition);
                intent.putExtra(NEW_DATA_KEY, myMasterDataList.get(currentPosition));
                startActivityForResult(intent, MAIN_REQUEST_CODE);




            }
        });
    }

    public void floatButton(){
        // to add user input to viewlist and update adapter.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = enterText.getText().toString();


                if (userText.isEmpty()) {
                    Snackbar.make(view, "No data entered", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    myDataList.add(userText);
                    myMasterDataList.add(returnDataList);
                    myAdapter.notifyDataSetChanged();
                    enterText.getText().clear();

                }
            }
        });
    }

    public void onItemLongClick() {
        // delete position
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myDataList.remove(position);
                myMasterDataList.remove(position);
                myAdapter.notifyDataSetChanged();

                Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;
            }
        });
    }


    private void setView(){
        enterText = (EditText) findViewById(R.id.enterText1);
        myListView = (ListView) findViewById(R.id.listView1);
    }

    private void setAdapter(){

        myDataList = new ArrayList<>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myDataList);
        myListView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (data != null) {
                        dummyList = data.getStringArrayListExtra(NEW_DATA_KEY);
                        myMasterDataList.set(currentPosition, dummyList);
                    Log.i("Main", "back in main" + dummyList.get(0));
                    Log.i("Main", "is it on master" + myMasterDataList.get(currentPosition).get(0));
                }
            } else  if (requestCode == RESULT_CANCELED){
                Log.w("Main", "Failed to get new list back");
            }
        }
    }




}
