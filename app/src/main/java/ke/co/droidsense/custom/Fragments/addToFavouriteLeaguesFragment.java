package ke.co.droidsense.custom.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.models.countryItem;

public class addToFavouriteLeaguesFragment extends DialogFragment {
    countryItem country = new countryItem();

    //Use a builder class to handle events.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setTitle( "Add to favourites." );
        builder.setMessage( "Do you want to add this League to favourites?" );

        //Positive button.
        builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DatabaseReference favouriteLeaguesReference = FirebaseDatabase
                        .getInstance()
                        .getReference( Constants.FAVOURITE_LEAGUES );
                //Save item to firebase.
                favouriteLeaguesReference.push().setValue( country );

                //Toast.
                Toast.makeText( getActivity(), "Added to favourites", Toast.LENGTH_SHORT ).show();

                dismiss();
            }
        } );

        builder.setNegativeButton( "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.
                Toast.makeText( getActivity(), "Canceling operation.", Toast.LENGTH_SHORT ).show();

                dismiss();
            }
        } );

        return builder.create();
    }
}
