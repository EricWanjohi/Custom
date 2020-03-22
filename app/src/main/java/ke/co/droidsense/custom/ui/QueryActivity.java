package ke.co.droidsense.custom.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.ViewModels.LeaguesByCountryViewModel;
import ke.co.droidsense.custom.ViewModels.ViewModelFactory.LeaguesByCountryViewModelFactory;
import ke.co.droidsense.custom.adapters.LeaguesByCountryAdapter;
import ke.co.droidsense.custom.models.Country;
import ke.co.droidsense.custom.models.countryItem;

public class QueryActivity extends AppCompatActivity {
    //Member variables.
    @BindView(R.id.queryRecyclerView)
    RecyclerView recyclerView;
    private LeaguesByCountryAdapter leaguesByCountryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<countryItem> country_Item_list;
    private Intent intent;
    private LeaguesByCountryViewModelFactory leaguesByCountryViewModelfactory;
    private LeaguesByCountryViewModel leaguesByCountryViewModel;
    private String query;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_query );

        //Initializations.
        ButterKnife.bind( this );

        //Progress Dialog.
        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Fetching Leagues in " + query );
        progressDialog.show();

        //Get Intent Extras.
        intent = getIntent();
        query = intent.getStringExtra( Constants.LEAGUE_SEARCH_QUERY );

        //Init ArrayList.
        country_Item_list = new ArrayList<>();

        //Setup RecyclerView.
        setUpRecyclerView();

        //Get Leagues by Country.
        getLeagueByCountry( query );
    }

    //Get Queried Leagues by countryItem string.
    private void getLeagueByCountry(String query) {
        leaguesByCountryViewModelfactory = new LeaguesByCountryViewModelFactory( query, this.getApplication() );
        leaguesByCountryViewModel = ViewModelProviders.of( this, leaguesByCountryViewModelfactory ).get( LeaguesByCountryViewModel.class );

        leaguesByCountryViewModel.getCountryLiveData().observe( this, new Observer<Country>() {
            @Override
            public void onChanged(Country country) {
                //TODO: Create check for null response returned.

                //Snack bar on the UI.
                Snackbar.make( getWindow().getDecorView(), "Data is up: YAAAAY!!!", Snackbar.LENGTH_LONG )
                        .setAction( "OK", null )
                        .show();

                //Add response data to arrayList.
                List<countryItem> countryItems = country.getCountryItems();
                country_Item_list.addAll( countryItems );
                leaguesByCountryAdapter.notifyDataSetChanged();

                //Dismiss Progress Dialog.
                progressDialog.dismiss();
            }
        } );
    }

    //Setup RecyclerView.
    private void setUpRecyclerView() {
        //Check if adapter is null.
        if (leaguesByCountryAdapter == null) {
            //Create and set recyclerView adapter.
            linearLayoutManager = new LinearLayoutManager( QueryActivity.this );
            leaguesByCountryAdapter = new LeaguesByCountryAdapter( country_Item_list, QueryActivity.this );

            //Set RecyclerView.
            recyclerView.setAdapter( leaguesByCountryAdapter );
            recyclerView.setLayoutManager( linearLayoutManager );
            recyclerView.setHasFixedSize( true );

        } else {
            //Notify adapter of changes.
            leaguesByCountryAdapter.notifyDataSetChanged();
        }

    }
}
