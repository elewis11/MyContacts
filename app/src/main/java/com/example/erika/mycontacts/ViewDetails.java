package com.example.erika.mycontacts;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
    EditText ViewDetailsGroupEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //gets the intent and the extras placed in the intent from the Main Activity
        bundle = this.getIntent().getExtras();

        // get the id in the Bundle
        id = bundle.getLong("_id");

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // call DBHandler method that gets contact list name
        String contactListName = dbHandler.getContactListName((int) id);
        String contactListAddress = dbHandler.getContactListAddress((int) id);
        String contactListNumber = dbHandler.getContactListNumber((int) id);
        String contactListEmail = dbHandler.getContactListEmail((int) id);
        String contactListGroup = dbHandler.getContactListGroup((int) id);

        ViewDetailsNameEditText = findViewById(R.id.ViewDetailsNameEditText);
        ViewDetailsAddressEditText = findViewById(R.id.ViewDetailsAddressEditText);
        ViewDetailsNumberEditText = findViewById(R.id.ViewDetailsNumberEditText);
        ViewDetailsEmailEditText = findViewById(R.id.ViewDetailsEmailEditText);
        ViewDetailsGroupEditText = findViewById(R.id.ViewDetailsGroupEditText);

        ViewDetailsNameEditText.setText(contactListName);
        ViewDetailsAddressEditText.setText(contactListAddress);
        ViewDetailsNumberEditText.setText(contactListNumber);
        ViewDetailsEmailEditText.setText(contactListEmail);
        ViewDetailsGroupEditText.setText(contactListGroup);

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
                //initialize an Intent for the Add Contacts Activity, start intent, return true if the id in the item selected is for the AddContacts Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
    /*        case R.id.action_edit_contact:
                //initialize an Intent for the Edit Details Activity, start intent, return true if the id in the item selected is for the Edit Details Activity.
                intent = new Intent(this, EditDetails.class);
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;*/
            case R.id.action_delete_contact:
                deleteContactList();
                return true;
            case R.id.action_view_groups:
                //initialize an Intent for the View Groups Activity, start intent, return true if the id in the item selected is for the View Groups Activity.
                intent = new Intent(this, ViewGroups.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openEditContact(MenuItem menuItem){
        intent = new Intent(this, EditDetails.class);
        intent.putExtra("_id", id);
        startActivity(intent);
    }

    public void deleteContactList(){
        //creates a new ViewDetails object of DeleteContact
        DeleteContact foo = new DeleteContact();
        //gets the context from this activity and sends it along
        foo.setContext(this);
        //passes a bundle to the DeleteContact class
        foo.setArguments(bundle);
        //shows the dialog
        foo.show(getSupportFragmentManager(), "sfm");
    }
}
