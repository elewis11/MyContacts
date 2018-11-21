package com.example.erika.mycontacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class DeleteContact extends DialogFragment {
    DBHandler dbHandler;
    Context mContext;

    public void setContext(Context context){
        //sets context based on context sent from the activity passing to this method
        mContext = context;
    }

    /**
     * creates a dialog to ask the user whether or not they truly want to delete the contact
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = this.getArguments();
        final long id = bundle.getLong("_id");
        dbHandler = new DBHandler(mContext, null);

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
