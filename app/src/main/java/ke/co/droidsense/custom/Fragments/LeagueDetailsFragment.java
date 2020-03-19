package ke.co.droidsense.custom.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.Items.LeagueData;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueDetailsFragment extends Fragment implements View.OnClickListener {
    //Member Variables.
    @BindView(R.id.strBadge)
    ImageView strBadge;
    @BindView(R.id.strFacebook)
    TextView strFacebook;
    @BindView(R.id.strTwitter)
    TextView strTwitter;
    @BindView(R.id.strYoutube)
    TextView strYoutube;
    @BindView(R.id.idLeague)
    TextView idLeague;
    @BindView(R.id.strSport)
    TextView strsport;
    @BindView(R.id.strCurrentSeason)
    TextView strCurrentSeason;
    @BindView(R.id.intFormedYear)
    TextView intFormedYear;
    @BindView(R.id.strDivision)
    TextView strDivision;
    @BindView(R.id.strDescriptionEN)
    TextView strDescriptionEN;
    @BindView(R.id.strLeague)
    TextView strLeague;
    private LeagueData leagueData;

    //ButterKnife.

    //Empty constructor.
    public LeagueDetailsFragment() {
        // Required empty public constructor
    }

    //Create new Instance of LeagueDetailsFragment.
    public static LeagueDetailsFragment newInstance(LeagueData leagueData) {
        //
        LeagueDetailsFragment leagueDetailsFragment = new LeagueDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable( "leagueData", Parcels.wrap( leagueData ) );
        leagueDetailsFragment.setArguments( bundle );

        return leagueDetailsFragment;
    }

    //Override OnCreate.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //Unwrap Parcels
        leagueData = Parcels.unwrap( getArguments().getParcelable( "leagueData" ) );

        //Set OnClickListeners.
        strFacebook.setOnClickListener( this );
        strTwitter.setOnClickListener( this );
        strYoutube.setOnClickListener( this );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_league_details, container, false );

        //ButterKnife.
        ButterKnife.bind( this, view );

        //Bind ImageView.
        Picasso.get().load( leagueData.getStrBadge() ).centerCrop().into( strBadge );

        //Set Text.
        idLeague.setText( leagueData.getIdLeague() );
        strsport.setText( leagueData.getStrSport() );
        strCurrentSeason.setText( leagueData.getStrCurrentSeason() );
        intFormedYear.setText( leagueData.getIntFormedYear() );
        strDivision.setText( leagueData.getStrDivision() );
        strDescriptionEN.setText( leagueData.getStrDescriptionEN() );
        strLeague.setText( leagueData.getStrLeague() );

        return view;
    }

    @Override
    public void onClick(View view) {
        //Switch statement
        switch (view.getId()) {
            //Case Facebook.
            case R.id.strFacebook:
                //Create new Implicit Intent.
                Intent facebookPageIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( leagueData.getStrFacebook() ) );
                startActivity( facebookPageIntent );
                break;

            //Case Twitter.
            case R.id.strTwitter:
                //create new Implicit Intent.
                Intent twitterhandleIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( leagueData.getStrTwitter() ) );
                startActivity( twitterhandleIntent );
                break;

            //Case Youtube.
            case R.id.strYoutube:
                //Create new Implicit Intent.
                Intent youtubeChannelIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( leagueData.getStrYoutube() ) );
                startActivity( youtubeChannelIntent );
                break;
        }
    }
}
