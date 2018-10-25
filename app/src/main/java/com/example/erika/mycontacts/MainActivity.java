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

public class MainActivity extends AppCompatActivity {

    // declare an Intent - used to start Activities
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Contacts");
        intent = new Intent(this, AddContact.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                /*    intent.putExtra("_id", id);*/
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
            case R.id.action_home:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, AddContact.class);
            /*    intent.putExtra("_id", id);*/
                startActivity(intent);
                return true;
     /*       case R.id.action_delete:
                //initialize an Intent for the Main Activity, start intent, return true if the id in the item selected is for the Main Activity.
                intent = new Intent(this, DeleteContact.class);
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;*/
            case R.id.action_view_details:
                //initialize an Intent for the Create List Activity, start intent, return true if the id in the item selected is for the Create List Activity.
                intent = new Intent(this, ViewDetails.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
