package com.example.erika.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ViewDetails extends AppCompatActivity {

    // declare an Intent - used to start Activities
    Intent intent;

    Bundle bundle;

    // declare a long to store the id passed from the Main Activity
    long id;

    // declaring a DBHandler
    DBHandler dbHandler;

    EditText ViewDetailsNameEditText;
    EditText ViewDetailsAddressEditText;
    EditText ViewDetailsNumberEditText;
    EditText ViewDetailsEmailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = this.getIntent().getExtras();

        // get the id in the Bundle
        id = bundle.getLong("_id");

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // call DBHandler method that gets shopping list name
        String contactListName = dbHandler.getContactListName((int) id);
        String contactListAddress = dbHandler.getContactListAddress((int) id);
        String contactListNumber = dbHandler.getContactListNumber((int) id);
        String contactListEmail = dbHandler.getContactListEmail((int) id);

        ViewDetailsNameEditText = findViewById(R.id.ViewDetailsNameEditText);
        ViewDetailsAddressEditText = findViewById(R.id.ViewDetailsAddressEditText);
        ViewDetailsNumberEditText = findViewById(R.id.ViewDetailsNumberEditText);
        ViewDetailsEmailEditText = findViewById(R.id.ViewDetailsEmailEditText);

        ViewDetailsNameEditText.setText(contactListName);
        ViewDetailsAddressEditText.setText(contactListAddress);
        ViewDetailsNumberEditText.setText(contactListNumber);
        ViewDetailsEmailEditText.setText(contactListEmail);

        // set title of the View List activity to shopping list name
        this.setTitle(contactListName);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the item selected
        switch(item.getItemId()){
            case R.id.action_home:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, AddContact.class);
                /*    intent.putExtra("_id", id);*/
                startActivity(intent);
                return true;
     /*       case R.id.action_delete:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, DeleteContact.class);
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;*/
            case R.id.action_edit_contact:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, EditDetails.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
