package ke.co.droidsense.custom.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.ViewModels.LeaguesByCountryViewModel;
import ke.co.droidsense.custom.ViewModels.ViewModelFactory.LeaguesByCountryViewModelFactory;
import ke.co.droidsense.custom.adapters.LeaguesByCountryAdapter;
import ke.co.droidsense.custom.models.Country;
import ke.co.droidsense.custom.models.Country_;

public class QueryActivity extends AppCompatActivity {
    //Member variables.
    @BindView(R.id.queryRecyclerView)
    RecyclerView recyclerView;
    private LeaguesByCountryAdapter leaguesByCountryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Country_> country_list;
    private Intent intent;
    private LeaguesByCountryViewModelFactory leaguesByCountryViewModelfactory;
    private LeaguesByCountryViewModel leaguesByCountryViewModel;
    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_query );

        //Initializations.
        ButterKnife.bind( this );

        //Get Intent Extras.
        intent = getIntent();
        query = intent.getStringExtra( Constants.SEARCH_QUERY );

        //Setup RecyclerView.
        setUpRecyclerView();

        //Get Leagues by Country.
        getLeagueByCountry( query );
    }

    //Get Queried Leagues by country string.
    private void getLeagueByCountry(String query) {
        leaguesByCountryViewModelfactory = new LeaguesByCountryViewModelFactory( query, this.getApplication() );
        leaguesByCountryViewModel = ViewModelProviders.of( this, leaguesByCountryViewModelfactory ).get( LeaguesByCountryViewModel.class );

        leaguesByCountryViewModel.getCountryLiveData().observe( this, new Observer<Country>() {
            @Override
            public void onChanged(Country country) {
                //
            }
        } );
    }

    //Setup RecyclerView.
    private void setUpRecyclerView() {
        //Check if adapter is null.
        if (leaguesByCountryAdapter == null) {
            //Create and set recyclerView adapter.

        } else {
            //Notify adapter of changes.
//            leaguesByCountryAdapter.notifyDatasetChanged();
        }

    }
}
