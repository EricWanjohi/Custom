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

import com.squareup.picasso.Picasso;

import java.util.List;

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
        countryItem countryItem = country_Item_list.get( position );

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

    //ViewHolder class.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Member variables.
        private TextView strLeague, idLeague, strSport, strCurrentSeason, intFormedYear, strDivision, strDescriptionEN, strWebsite;
        private ImageView strBadge;

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

            //Set Click Listeners
            strWebsite.setOnClickListener( this );

        }

        @Override
        public void onClick(View view) {
            //get Tag
            countryItem country = (countryItem) view.getTag();

            //Check item clicked.
            switch (view.getId()) {

                //Case FaceBook.
                case R.id.strWebsite:
                    //Check if link is null.
                    checkIfWebsiteUrl( country );
            }
        }
    }

    //Check if website url is null
    private void checkIfWebsiteUrl(countryItem country) {
        boolean isNull;
        websiteString = country.getStrWebsite().trim();

        //Perform Check.
        if (websiteString.isEmpty()) {
            isNull = true;
            //Toast.
            Toast.makeText( context, "We will notify you when " + country.getStrLeague() + " adds a link.", Toast.LENGTH_SHORT ).show();
        } else if (websiteString.equals( "" )) {
            isNull = true;
            //Toast.
            Toast.makeText( context, "We will notify you when " + country.getStrLeague() + " adds a link.", Toast.LENGTH_SHORT ).show();
        } else {
            isNull = false;
            //Implicit Intent.
            Uri websiteUri = Uri.parse( websiteString );
            Intent website = new Intent( Intent.ACTION_VIEW, websiteUri );
            Timber.tag( "Facebook Url: " ).e( websiteUri.toString() );
            context.startActivity( website );
        }
    }
}