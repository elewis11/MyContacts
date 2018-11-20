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
    private static Context mContext;

    Bundle bundle;

    // declare a long to store the id passed from the Main Activity
    long id;

    // declaring a DBHandler
    static DBHandler dbHandler;

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
        //set the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //set the current context
        mContext = this;
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

            case android.R.id.home:
                //utilizes the back button in the action bar to go back to the previous activity
                finish();
                //refreshes the page that's being gone back to
                recreate();
                return true;
            case R.id.action_home:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the View Details Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Add Contacts Activity, start intent, return true if the id in the item selected is for the View Details Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
            case R.id.action_edit_contact:
                //initialize an Intent for the Edit Details Activity, start intent, return true if the id in the item selected is for the View Details Activity.
                intent = new Intent(this, EditDetails.class);
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            case R.id.action_delete_contact:
                deleteContactList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void deleteContactList(){
        //creates a new ViewDetails object of DeleteContact
        ViewDetails.DeleteContact foo = new ViewDetails.DeleteContact();
        //passes a bundle to the DeleteContact class
        foo.setArguments(bundle);
        //shows the dialog
        foo.show(getSupportFragmentManager(), "sfm");
    }

    public static class DeleteContact extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            Bundle bundle = this.getArguments();
            final long id = bundle.getLong("_id");

            builder.setMessage("Are you sure you want to delete this contact?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int diaId) {

                            //delete the contact
                            dbHandler.deleteContactList((int) id);

                            // displays toast saying the user successfully deleted the contact
                            //using the static mContext provided in the declarations
                            Toast.makeText(mContext, "Contact Deleted", Toast.LENGTH_LONG).show();

                            // goes back to the MainActivity after the contact has been deleted by
                            //using the static mContext provided in the declarations
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int diaId) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    /*
    public void deleteContactList(){
        bundle = this.getIntent().getExtras();

        // get the id in the Bundle
        id = bundle.getLong("_id");

        // sends the id to the deleteContactList method in the database (dbHandler object)
        dbHandler.deleteContactList((int) id);

        // displays
        Toast.makeText(this, "Contact Deleted", Toast.LENGTH_LONG).show();

        // goes back to the MainActivity after the contact has been added
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}
