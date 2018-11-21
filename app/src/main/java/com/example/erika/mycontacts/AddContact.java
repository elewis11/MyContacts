package com.example.erika.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    // declare an Intent - used to start Activities
    Intent intent;

    //declare EditTexts - used to reference the EditTexts in the resource file
    EditText nameEditText;
    EditText addressEditText;
    EditText phoneEditText;
    EditText emailEditText;

    //declare DBHandler
    DBHandler dbhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back button in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set the title in the action bar
        getSupportActionBar().setTitle("Add Contact");

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        //sets the structure of an entered phone number
        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        //initialize DBHandler
        dbhandler = new DBHandler(this, null);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"", "Family", "Friends", "Work"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
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
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createContact(MenuItem menuItem){
        //get data input in EditTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();

        //trim Strings and see if any are equal to an empty String
     //   if (name.trim().equals("")){
        if (name.trim().isEmpty()){
            //required data hasn't been input, so display Toast
         //   Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            //returns an error if the name isn't valid
            nameEditText.setError("Please enter a name");
        }
        else if(!email.trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
    //    else if(!isValidEmail(email.trim())){
            //returns an error if the email address isn't valid
            emailEditText.setError("Please enter a valid email address");
        }
        else if(!phone.trim().isEmpty() && phone.trim().length() < 14){
            //returns an error if the phone number isn't valid
            phoneEditText.setError("Please enter a valid phone number");
        }
        else {
            //required data has been input, update the database and display a different Toast
            dbhandler.addContactList(name, address, phone, email);
            Toast.makeText(this, "Contact Added", Toast.LENGTH_LONG).show();

            // goes back to the MainActivity after the contact has been added
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


/*
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }*/
}
