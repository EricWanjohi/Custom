package ke.co.droidsense.custom.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.adapters.PagerAdapters.LeagueDetailsPagerAdapter;
import ke.co.droidsense.custom.models.Items.LeagueData;

public class LeagueDetailsActivity extends AppCompatActivity {
    //Member Variables.
    @BindView(R.id.leagueDetailsViewPager)
    ViewPager viewPager;
    private LeagueDetailsPagerAdapter leagueDetailsPagerAdapter;
    private List<LeagueData> leagueDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_league_details );

        //Initializations.
        ButterKnife.bind( this );

        leagueDataList = Parcels.unwrap( getIntent().getParcelableExtra( "leaguesList" ) );
        int startingPosition = getIntent().getIntExtra( "position", 0 );

        //Init Adapter.
        leagueDetailsPagerAdapter = new LeagueDetailsPagerAdapter( getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                leagueDataList );

        //Set Adapter.
//        viewPager.setAdapter( leagueDetailsPagerAdapter );
//        viewPager.setCurrentItem( startingPosition );
    }
}
