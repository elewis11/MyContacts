package com.example.erika.mycontacts;

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
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    // declare an Intent - used to start Activities
    Intent intent;

    // declare a DBHandler - used to communicate with the database
    DBHandler dbHandler;

    // declare a ContactLists CursorAdapter - used to link the data in the Cursor to the ListView
    ContactLists contactListsAdapter;

    // declare a ListView - used to reference the ListView in the resource file
    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize ListView
        contactListView = (ListView) findViewById(R.id.contactListView);

        // initialize ContactLists CursorAdapter with the contact list data in the database
        contactListsAdapter = new ContactLists(this, dbHandler.getContactLists(), 0);

        // set CursorAdapter CursorAdapter on ListView
        contactListView.setAdapter(contactListsAdapter);

        // register OnItemClickListener on ListView
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //initialize an Intent for the View Details Activity, start intent.
                intent = new Intent(MainActivity.this, ViewDetails.class);

                // put the content list id of the clicked row in the intent
                intent.putExtra("_id", id);

                // start the intent
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the item selected
        switch(item.getItemId()){
         /*   case R.id.action_home:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;*/
            case R.id.action_add_contact:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the fabCreateList floating action button gets clicked.
     * It starts the Add Contact Activity.
     * @param view because the fabCreateList floating action button is considered a view, we must pass the method a View object.
     */
    public void openAddContact(View view){
        //initialize an Intent for the Add Contact Activity, start intent.
        intent = new Intent(this, AddContact.class);
        startActivity(intent);
    }
}
