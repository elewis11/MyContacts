package com.example.erika.mycontacts;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    String groupName;

    public void set(Spinner dropdown){
        //takes a spinner object and selects the item chosen by the user before sending it to the method
        dropdown.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // sets the name on the group based on the "position" of the item selected
         groupName = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public String getGroupName(){
        //returns the name of the group
        return groupName;
    }
}
