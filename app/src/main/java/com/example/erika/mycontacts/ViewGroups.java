package com.example.erika.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewGroups extends AppCompatActivity {

    // declare an Intent - used to start Activities
    Intent intent;

    // declare a DBHandler - used to communicate with the database
    DBHandler dbHandler;

    // declare a groupLists CursorAdapter - used to link the data in the Cursor to the ListView
    GroupLists groupListsAdapter;

    // declare a ListView - used to reference the ListView in the resource file
    ListView groupListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Groups");

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize ListView
        groupListView = (ListView) findViewById(R.id.groupListView);

        // initialize groupLists CursorAdapter with the group list data in the database
        groupListsAdapter = new GroupLists(this, dbHandler.getGroupLists(), 0);

        // set CursorAdapter CursorAdapter on ListView
        groupListView.setAdapter(groupListsAdapter);
/*
        // register OnItemClickListener on ListView
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //initialize an Intent for the View Details Activity, start intent.
                intent = new Intent(ViewGroups.this, EditGroups.class);

                // put the group list id of the clicked row in the intent
                intent.putExtra("_id", id);

                // start the intent
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_groups, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the item selected
        switch(item.getItemId()){
            case android.R.id.home:
                //utilizes the back button in the action bar to go back to the previous activity
                finish();
                //refreshes the page that's being gone back to
                recreate();
                return true;
            case R.id.action_home:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
       /*     case R.id.action_add_group:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the fabCreateList floating action button gets clicked.
     * It starts the Add Group Activity.
     * @param view because the fabCreateList floating action button is considered a view, we must pass the method a View object.
     *//*
    public void openAddgroup(View view){
        //initialize an Intent for the Add group Activity, start intent.
        intent = new Intent(this, AddGroup.class);
        startActivity(intent);
    }*/

}
