package com.example.erika.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

        getSupportActionBar().setTitle("Add Contact");

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        //initialize DBHandler
        dbhandler = new DBHandler(this, null);
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
            case R.id.action_home:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, AddContact.class);
             /*   intent.putExtra("_id", id);*/
                startActivity(intent);
                return true;
     /*       case R.id.action_delete:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, DeleteContact.class);
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;*/
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
        if (name.trim().equals("")){
            //required data hasn't been input, so display Toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
        else{
          /*  if (name.trim().equals("")){

            }*/
            //required data has been input, update the database and display a different Toast
            dbhandler.addContactList(name, address, phone, email);
            Toast.makeText(this, "Contact Added", Toast.LENGTH_LONG).show();

            /* goes back to the MainActivity after the contact has been added */
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
