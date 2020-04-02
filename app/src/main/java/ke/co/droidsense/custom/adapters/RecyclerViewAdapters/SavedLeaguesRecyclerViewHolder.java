package ke.co.droidsense.custom.adapters.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.countryItem;
import ke.co.droidsense.custom.ui.SavedLeagues;
import timber.log.Timber;

public class SavedLeaguesRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Member Variables
    private static final int MAX_HEIGHT = 100;
    private static final int MAX_WEIGHT = 100;
    private View view;
    private Context context;
    private String websiteString;
    private ke.co.droidsense.custom.models.countryItem countryItem = new countryItem();
    private String http;
    private TextView strFavourites;
    private DatabaseReference savedLeaguesReference;

    //ViewHolder Constructor.
    public SavedLeaguesRecyclerViewHolder(@NonNull View itemView) {
        super( itemView );
        view = itemView;
        this.context = itemView.getContext();

        //Set OnClickListener.
        itemView.setOnClickListener( this );
    }

    //Bind views.
    public void bindCountryItem(countryItem countryItem) {
        //Get View items.
        TextView strLeague = view.findViewById( R.id.strLeague );
        TextView idLeague = view.findViewById( R.id.idLeague );
        TextView strSport = view.findViewById( R.id.strSport );
        TextView strCurrentSeason = view.findViewById( R.id.strCurrentSeason );
        TextView intFormedYear = view.findViewById( R.id.intFormedYear );
        TextView strDivision = view.findViewById( R.id.strDivision );
        TextView strDescriptionEN = view.findViewById( R.id.strDescriptionEN );
        TextView strWebsite = view.findViewById( R.id.strWebsite );
        ImageView strBadge = view.findViewById( R.id.strBadge );
        strFavourites = view.findViewById( R.id.strFavourite );

        //Bind Image.
        Picasso.get()
                .load( countryItem.getStrBadge() )
                .resize( MAX_HEIGHT, MAX_WEIGHT )
                .into( strBadge );

        //Bind TextViews.
        strLeague.setText( countryItem.getStrLeague() );
        idLeague.setText( "ID: " + countryItem.getIdLeague() );
        strSport.setText( countryItem.getStrSport() );
        strCurrentSeason.setText( "Season: " + countryItem.getStrCurrentSeason() );
        intFormedYear.setText( "Formed Year: " + countryItem.getIntFormedYear() );
        strDivision.setText( "Division: " + countryItem.getStrDivision() );
        strDescriptionEN.setText( countryItem.getStrDescriptionEN() );

        //Set Listeners.
        strWebsite.setOnClickListener( this );
        strFavourites.setOnClickListener( this );
    }

    //Handle Click events.
    @Override
    public void onClick(View view) {
        //Create switch statement to handle choices.
        switch (view.getId()) {
            //case website.
            case R.id.strWebsite:
                //handle strWebsite click here.
                checkIfWebsiteUrlIsOk();
                break;

            //case strFavourites.
            case R.id.strFavourite:
                //Handle strFavourites click here.
                //Boolean variable isFavourite.
                boolean isFavourite = true;

                //Check if Favourites image clicked to enable saving to favourites list.
                if (isFavourite) {

                    //Remove item at position 0 from Favourites.
                    removeCountryItemFromFavourites( countryItem );

                    //Reset boolean variable
                    isFavourite = false;
                }
                break;
        }

        //Create a List object holder
        final ArrayList<countryItem> countryItemArrayList = new ArrayList<>();

        //Get DatabaseReference.
        savedLeaguesReference = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FAVOURITE_LEAGUES )
                .child( Constants.SAVED_LEAGUE );

        //Create loop for items in list
        for (int i = 0; i <= countryItemArrayList.size(); i++) {
            //Loop
            //Get strLeague name
            String strLeague = countryItemArrayList.get( i ).getStrLeague();

            //Add Listener for single value Event.
            savedLeaguesReference.orderByChild( "strLeague" ).equalTo( strLeague ).limitToFirst( 20 )
                    .addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Update looping through ittems.
                            for (DataSnapshot savedLeaguesDatasnapshot : dataSnapshot.getChildren()) {
                                //Add Item.
                                countryItemArrayList.add( savedLeaguesDatasnapshot.getValue( countryItem.class ) );

                            }

                            //Create Transition by Intent.
                            Intent savedLeaguesIntent = new Intent( context, SavedLeagues.class );
                            context.startActivity( savedLeaguesIntent );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //Log Error.
                            Timber.tag( "Database Error: " ).e( databaseError.getMessage() );
                        }
                    } );
        }
    }

    //Remove Country item from favourites
    private void removeCountryItemFromFavourites(countryItem countryItem) {
        //Get DatabaseReference.
        savedLeaguesReference = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FAVOURITE_LEAGUES )
                .child( Constants.SAVED_LEAGUE );

        //Get strLeague name
        String strLeague = countryItem.getStrLeague();

        //Get item to delete.
        savedLeaguesReference.orderByChild( "strLeague" ).equalTo( strLeague )
                .addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Create for loop to get specific child object.
                        for (DataSnapshot childDataSnapShot : dataSnapshot.getChildren()) {

                            //Get child reference and set value to null.
                            childDataSnapShot.getRef().setValue( null );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Set Error
                        Timber.tag( "Database Error: " ).e( databaseError.toException().getMessage() );
                    }
                } );


        //Remove item value from firebase.
        Toast.makeText( context, countryItem.getStrLeague() + " removed from favourites.", Toast.LENGTH_LONG ).show();

    }

    //Check if website url is ok.
    private void checkIfWebsiteUrlIsOk() {

        //Create String from object information.
        websiteString = countryItem.getStrWebsite();

        //Append string to websiteString object.
        http = "http://";

        //Perform Check.
        if (websiteString != null && !websiteString.isEmpty()) {

            //Implicit Intent.
            Uri websiteUri = Uri.parse( http + websiteString );
            Intent website = new Intent( Intent.ACTION_VIEW, websiteUri );

            //Resolve activity if there is no browser detected.
            if (website.resolveActivity( context.getPackageManager() ) != null) {
                //Start Activity.
                context.startActivity( website );

                //Toast to user.
                Toast.makeText( context, "Opening in browser...", Toast.LENGTH_SHORT ).show();
            }

        } else {

            //Toast.
            Toast.makeText( context, "We will notify you when " + countryItem.getStrLeague() + " add a link.", Toast.LENGTH_LONG ).show();

        }
    }
}
