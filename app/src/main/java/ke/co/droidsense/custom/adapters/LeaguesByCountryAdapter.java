package ke.co.droidsense.custom.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.Country_;

public class LeaguesByCountryAdapter extends RecyclerView.Adapter<LeaguesByCountryAdapter.ViewHolder> {
    //Member Variables.
    private List<Country_> country_list;
    private Context context;

    //Constructor
    public LeaguesByCountryAdapter(List<Country_> country_list, Context context) {
        this.country_list = country_list;
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
        Country_ country_ = country_list.get( position );

        //Bind View.
        holder.strLeague.setText( country_.getStrLeague() );
        holder.idLeague.setText( country_.getIdLeague() );
        holder.strSport.setText( country_.getStrSport() );
        holder.strCurrentSeason.setText( country_.getStrCurrentSeason() );
        holder.intFormedYear.setText( country_.getIntFormedYear() );
        holder.strDivision.setText( country_.getStrDivision() );
        holder.strDescriptionEN.setText( country_.getStrDescriptionEN() );
        holder.strFacebook.setText( country_.getStrFacebook() );
        holder.strTwitter.setText( country_.getStrTwitter() );
        holder.strYoutube.setText( country_.getStrYoutube() );

        //Bind Image.
        Picasso.get().load( country_.getStrBadge() ).centerCrop().into( holder.strBadge );


        //Set Tag on Item.
        holder.itemView.setTag( country_ );
    }

    @Override
    public int getItemCount() {
        return country_list.size();
    }

    //ViewHolder class.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Member variables.
        private TextView strLeague, idLeague, strSport, strCurrentSeason, intFormedYear, strDivision, strDescriptionEN, strFacebook, strTwitter, strYoutube;
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
            strFacebook = itemView.findViewById( R.id.strFacebook );
            strTwitter = itemView.findViewById( R.id.strTwitter );
            strYoutube = itemView.findViewById( R.id.strYoutube );

            //Set Click Listeners
            strYoutube.setOnClickListener( this );
            strFacebook.setOnClickListener( this );
            strTwitter.setOnClickListener( this );

        }

        @Override
        public void onClick(View view) {
            //get Tag
            Country_ country_ = (Country_) view.getTag();

            //Check item clicked.
            switch (view.getId()) {

                //Case FaceBook.
                case R.id.strFacebook:
                    //Implicit Intent.
                    Intent facebook = new Intent( Intent.ACTION_VIEW, Uri.parse( country_.getStrFacebook() ) );
                    context.startActivity( facebook );

                    //Case Twitter.
                case R.id.strTwitter:
                    //Implicit intent.
                    Intent twitter = new Intent( Intent.ACTION_VIEW, Uri.parse( country_.getStrTwitter() ) );
                    context.startActivity( twitter );

                    //Case Youtube.
                case R.id.strYoutube:
                    //Implicit intent.
                    Intent youtube = new Intent( Intent.ACTION_VIEW, Uri.parse( country_.getStrYoutube() ) );
                    context.startActivity( youtube );
            }
        }
    }
}
