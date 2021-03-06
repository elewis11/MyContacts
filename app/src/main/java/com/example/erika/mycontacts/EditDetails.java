package com.example.erika.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    //declare a SpinnerActivity to interact with the item chosen by the spinner
    SpinnerActivity groupSpinner;

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

        //initialize strings to the information stored in the database
        String contactListName = dbHandler.getContactListName((int) id);
        String contactListAddress = dbHandler.getContactListAddress((int) id);
        String contactListNumber = dbHandler.getContactListNumber((int) id);
        String contactListEmail = dbHandler.getContactListEmail((int) id);
        String contactListGroup = dbHandler.getContactListGroup((int) id);

        //makes edit texts findable by their id
        EditableNameEditText = findViewById(R.id.EditableNameEditText);
        EditableAddressEditText = findViewById(R.id.EditableAddressEditText);
        EditableNumberEditText = findViewById(R.id.EditableNumberEditText);
        EditableEmailEditText = findViewById(R.id.EditableEmailEditText);

        //set the edit text equal to the prefilled data
        EditableNameEditText.setText(contactListName);
        EditableAddressEditText.setText(contactListAddress);
        EditableNumberEditText.setText(contactListNumber);
        EditableEmailEditText.setText(contactListEmail);

        //sets the structure of an entered phone number
        EditableNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        //sets the maximum amount of lines that can be entered to 2
        EditableAddressEditText.addTextChangedListener(new EditTextLinesLimiter(EditableAddressEditText, 2));

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"", "Family", "Friends", "Work"};
        //gets the group names from the database
  //      String[] items = dbHandler.getGroupNames();
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //make a new spinner activity to deal with the selection made from the spinner's dropdown
        groupSpinner = new SpinnerActivity();
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        //sets the selected item from the spinner's dropdown
        groupSpinner.set(dropdown);

        //prefills the spinner with the group last added by the user
        for(int i= 0; i < dropdown.getAdapter().getCount(); i++)
        {
            if(dropdown.getAdapter().getItem(i).toString().contains(contactListGroup))
            {
                dropdown.setSelection(i);
            }
        }
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
                return true;
            case R.id.action_home:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Add Contact Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, AddContact.class);
                startActivity(intent);
                return true;
            case R.id.action_delete_contact:
                deleteContactList();
                return true;
      /*      case R.id.action_view_groups:
                //initialize an Intent for the View Groups Activity, start intent, return true if the id in the item selected is for the View Groups Activity.
                intent = new Intent(this, ViewGroups.class);
                startActivity(intent);
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

        if (name.trim().isEmpty()){
            //returns an error if the name isn't valid
            EditableNameEditText.setError("Please enter a name");
        }
        else if(!email.trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //returns an error if the email address isn't valid
            EditableEmailEditText.setError("Please enter a valid email address");
        }
        else if(!phone.trim().isEmpty() && phone.trim().length() < 14){
            //returns an error if the phone number isn't valid
            EditableNumberEditText.setError("Please enter a valid phone number");
        }
        else {

            //runs the updateContactList method in the DBHandler class
            dbHandler.updateContactList((int) id, name, address, phone, email, groupSpinner.getGroupName());

            //makes a toast appear after a user successfully updates a contact's information
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_LONG).show();

            // goes back to the MainActivity after the contact has been edited
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void deleteContactList() {
        //creates a new EditDetails object of DeleteContact
        DeleteContact goo = new DeleteContact();
        goo.setContext(this);
        //passes a bundle to the DeleteContact class
        goo.setArguments(bundle);
        //shows the dialog
        goo.show(getSupportFragmentManager(), "sfm2");
    }

    public class EditTextLinesLimiter implements TextWatcher {
        private EditText editText;
        private int maxLines;
        private String lastValue = "";

        public EditTextLinesLimiter(EditText editText, int maxLines) {
            this.editText = editText;
            this.maxLines = maxLines;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            lastValue = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editText.getLineCount() > maxLines) {
                int selectionStart = editText.getSelectionStart() - 1;
                editText.setText(lastValue);
                if (selectionStart >= editText.length()) {
                    selectionStart = editText.length();
                }
                editText.setSelection(selectionStart);
            }
        }
    }

}
