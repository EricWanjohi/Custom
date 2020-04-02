package ke.co.droidsense.custom.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.countryItem;
import timber.log.Timber;

public class LeaguesByCountryAdapter extends RecyclerView.Adapter<LeaguesByCountryAdapter.ViewHolder> {
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 100;
    //Member Variables.
    private List<countryItem> country_Item_list;
    private Context context;
    private String websiteString;
    private ke.co.droidsense.custom.models.countryItem countryItem;
    private List<ke.co.droidsense.custom.models.countryItem> countryItemSaved;
    private DatabaseReference favouriteLeaguesReference;
    private String http;

    //Constructor
    public LeaguesByCountryAdapter(List<countryItem> country_Item_list, Context context) {
        this.country_Item_list = country_Item_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create View.
        View view = LayoutInflater.from( context ).inflate( R.layout.country_item_layout, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get Item holder
        countryItem = country_Item_list.get( position );

        //Bind View.
        holder.strLeague.setText( countryItem.getStrLeague() );
        holder.idLeague.setText( "ID: " + countryItem.getIdLeague() );
        holder.strSport.setText( countryItem.getStrSport() );
        holder.strCurrentSeason.setText( "Season: " + countryItem.getStrCurrentSeason() );
        holder.intFormedYear.setText( "Formed Year: " + countryItem.getIntFormedYear() );
        holder.strDivision.setText( "Division: " + countryItem.getStrDivision() );
        holder.strDescriptionEN.setText( countryItem.getStrDescriptionEN() );

        //Bind Image.
        Picasso.get().load( countryItem.getStrBadge() ).resize( MAX_HEIGHT, MAX_WIDTH ).centerCrop().into( holder.strBadge );

        //Set Tag on Item.
        holder.itemView.setTag( countryItem );
    }

    @Override
    public int getItemCount() {
        return country_Item_list.size();
    }

    //Remove Country item from favourites
    private void removeCountryItemFromFavourites(countryItem countryItem) {
        //Get DatabaseReference.
        favouriteLeaguesReference = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FAVOURITE_LEAGUES )
                .child( Constants.SAVED_LEAGUE );

        //Get strLeague name
        String strLeague = countryItem.getStrLeague();

        //Get item to delete.
        favouriteLeaguesReference.orderByChild( "strLeague" ).equalTo( strLeague )
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

    //Add Country item to favourites.
    private void addCountryItemToFavourites(countryItem countryItem) {
        //Create list holder
        countryItemSaved = new ArrayList<>();

        //Add to list.
        countryItemSaved.add( countryItem );

        //Get Database reference.
        favouriteLeaguesReference = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FAVOURITE_LEAGUES )
                .child( Constants.SAVED_LEAGUE );

        //Get strLeague name
        String strLeague = countryItem.getStrLeague();

        //Save item to firebase.
        favouriteLeaguesReference.push().setValue( countryItem );


        //Remove item value from firebase.
        Toast.makeText( context, countryItem.getStrLeague() + " added to favourites.", Toast.LENGTH_LONG ).show();
    }

    //Check if website url is null
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

    //ViewHolder class.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Member variables.
        private TextView strLeague, idLeague, strSport, strCurrentSeason, intFormedYear, strDivision, strDescriptionEN, strWebsite;
        private ImageView strBadge, strFavourites;

        //ViewHolder.
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            //Initializations.
            strLeague = itemView.findViewById( R.id.strLeague );
            idLeague = itemView.findViewById( R.id.idLeague );
            strSport = itemView.findViewById( R.id.strSport );
            strCurrentSeason = itemView.findViewById( R.id.strCurrentSeason );
            intFormedYear = itemView.findViewById( R.id.intFormedYear );
            strDivision = itemView.findViewById( R.id.strDivision );
            strDescriptionEN = itemView.findViewById( R.id.strDescriptionEN );
            strWebsite = itemView.findViewById( R.id.strWebsite );
            strBadge = itemView.findViewById( R.id.strBadge );
            strFavourites = itemView.findViewById( R.id.strFavourite );

            //Set Click Listeners
            strWebsite.setOnClickListener( this );
            strFavourites.setOnClickListener( this );

            //Set Click listener on item.
            itemView.setOnClickListener( this );

        }

        @Override
        public void onClick(View view) {
            //Create switch statement for efficiency reasons
            switch (view.getId()) {

                //Case Website.
                case R.id.strWebsite:

                    //Check if the web url is ok
                    checkIfWebsiteUrlIsOk();
                    break;

                //Case Favourites.
                case R.id.strFavourite:
                    //Boolean variable isFavourite.
                    boolean isFavourite = false;

                    //Check if Favourites image clicked to enable saving to favourites list.
                    if (isFavourite ^= true) {

                        //Add item at position 0 to favourites.
                        addCountryItemToFavourites( countryItem );

                        //Set Image drawable.
                        strFavourites.setImageResource( R.drawable.ic_favorite_white_48dp );

                        //Reset boolean variable
                        isFavourite = false;
                    } else {

                        //Toast to user.
//                        Toast.makeText( context, "Removed from Favourites.", Toast.LENGTH_SHORT ).show();

                        //Remove item at position 0 from Favourites.
                        removeCountryItemFromFavourites( countryItem );

                        //Set Image drawable.
                        strFavourites.setImageResource( R.drawable.ic_favorite_border_white_48dp );

                        //Reset boolean variable
                        isFavourite = true;
                    }
                    break;
            }
        }
    }
}