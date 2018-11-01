package com.example.erika.mycontacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        // set title of the View Details activity to contact's name
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
                startActivity(intent);
                return true;
            case R.id.action_delete_contact:
             //   deleteContact();
                return true;
            case R.id.action_edit_contact:
                //initialize an Intent for the View Details Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, EditDetails.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        /**
         * Method to edit the contact. Receives a MenuItem parameter (button in the menu)
         *//*
        public void deleteContact(){
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to delete this contact?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                              Toast.makeText(ViewDetails.this, "Contact Deleted", Toast.LENGTH_LONG).show();
                            /*deleteContact(id);*/
            /*            }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(ViewDetails.this, "Contact Not Deleted", Toast.LENGTH_LONG).show();
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
        }
    }*/
}
