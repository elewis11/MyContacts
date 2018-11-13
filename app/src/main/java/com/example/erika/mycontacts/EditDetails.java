package com.example.erika.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditDetails extends AppCompatActivity {

    // declare an Intent - used to start Activities
    Intent intent;

    Bundle bundle;

    // declare a long to store the id passed from the Main Activity
    long id;

    // declaring a DBHandler
    DBHandler dbHandler;

    EditText EditableNameEditText;
    EditText EditableAddressEditText;
    EditText EditableNumberEditText;
    EditText EditableEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");
        //set the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = this.getIntent().getExtras();

        // get the id in the Bundle
        id = bundle.getLong("_id");

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        String contactListName = dbHandler.getContactListName((int) id);
        String contactListAddress = dbHandler.getContactListAddress((int) id);
        String contactListNumber = dbHandler.getContactListNumber((int) id);
        String contactListEmail = dbHandler.getContactListEmail((int) id);

        EditableNameEditText = findViewById(R.id.EditableNameEditText);
        EditableAddressEditText = findViewById(R.id.EditableAddressEditText);
        EditableNumberEditText = findViewById(R.id.EditableNumberEditText);
        EditableEmailEditText = findViewById(R.id.EditableEmailEditText);

        EditableNameEditText.setText(contactListName);
        EditableAddressEditText.setText(contactListAddress);
        EditableNumberEditText.setText(contactListNumber);
        EditableEmailEditText.setText(contactListEmail);

        EditableNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_details, menu);
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
                return true;/*
            case R.id.action_home:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;*/
            case R.id.action_add_contact:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
         /*   case R.id.action_delete_contact:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Method to edit the contact. Receives a MenuItem parameter (button in the menu)
     * @param menuItem
     */
    public void editContact(MenuItem menuItem){
        String name = EditableNameEditText.getText().toString();
        String address = EditableAddressEditText.getText().toString();
        String phone = EditableNumberEditText.getText().toString();
        String email = EditableEmailEditText.getText().toString();

        bundle = this.getIntent().getExtras();

        // get the id in the Bundle
        id = bundle.getLong("_id");


        dbHandler.updateContactList((int) id, name, address, phone, email);

        Toast.makeText(this, "Contact Updated", Toast.LENGTH_LONG).show();

        /* goes back to the MainActivity after the contact has been added */
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
