package ke.co.droidsense.custom.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ke.co.droidsense.custom.ApiInterface.SportsDbApi;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.ViewModels.LeaguesViewModel;
import ke.co.droidsense.custom.adapters.LeaguesAdapter;
import ke.co.droidsense.custom.models.Items.League;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    //Member Variables.
    private SportsDbApi sportsDbApi;
    @BindView(R.id.leaguesRecyclerView)
    RecyclerView recyclerView;
    private LeaguesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<League> leaguesList;
    private LeaguesViewModel leaguesViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //Initialization.
        Timber.plant( new Timber.DebugTree() );

        //Progress Bar.
        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Fetching Leagues..." );
        progressDialog.show();

        //Initialize items
        recyclerView = findViewById( R.id.leaguesRecyclerView );
        leaguesList = new ArrayList<>();

        //Set up recyclerView.
        setupRecyclerView();

        //Get Data from Api.
        getLeaguesData();

    }

    //Setup Recycler.
    private void setupRecyclerView() {
        //Check if adapter is null.
        if (adapter == null) {
            //Create adapter instance.
            linearLayoutManager = new LinearLayoutManager( MainActivity.this );
            adapter = new LeaguesAdapter( leaguesList, MainActivity.this );

            recyclerView.setLayoutManager( linearLayoutManager );
            recyclerView.setAdapter( adapter );
            recyclerView.setHasFixedSize( true );

        } else {
            //Notify if data changes.
            adapter.notifyDataSetChanged();
        }

    }

    //Method to fetch LeaguesResult Data from SportsDb Api.
    private void getLeaguesData() {
        //ViewModel setup.
        leaguesViewModel = ViewModelProviders.of( this ).get( LeaguesViewModel.class );
        leaguesViewModel.getLeaguesLiveData().observe( this, new Observer<LeaguesResult>() {
            @Override
            public void onChanged(LeaguesResult leaguesResult) {
                //
                List<League> leagues = leaguesResult.getLeagues();
                leaguesList.addAll( leagues );
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        } );
    }

    // //Check if ok.
    //                switch (leaguesResource.status){
    //
    //                    //Case Success.
    //                    case SUCCESS:
    //                        Timber.tag( "SUCCESS" ).e( leaguesResource.status.toString() );
    //                        //Check if data is null.
    //                        if (leaguesResource.data != null){
    //                            //Handle data response.
    //                            Timber.tag( "leaguesResource" ).e( leaguesResource.data.getLeagues().toString() );
    //                            LeaguesResult leaguesResult = leaguesResource.data;
    //
    //                            //Create List data holder.
    //                            List<League> leagueItems = leaguesResult.getLeagues();
    //
    //                            //Add Items to ArrayList.
    //                            leaguesList.addAll( leagueItems );
    //
    //                            //Notify Adapter
    //                            adapter.notifyDataSetChanged();
    //
    //                            //SnackBar to show data string to User.
    //                            Snackbar.make(getWindow().getDecorView(), leaguesResource.data.toString(), Snackbar.LENGTH_LONG)
    //                                    .setAction("OK", null)
    //                                    .show();
    //                        }
    //                        break;
    //
    //                    //case Error.
    //                    case ERROR:
    //                        //SnackBar to show Error to User.
    //                        Timber.tag( "ERROR" ).e( leaguesResource.status.toString() );
    //                        assert leaguesResource.message != null;
    //                        Snackbar.make(getWindow().getDecorView(), leaguesResource.message, Snackbar.LENGTH_LONG)
    //                                .setAction("OK", null)
    //                                .show();
    //                        break;
    //
    //                        //Case Loading.
    //                    case LOADING:
    //                        //SnackBar to show Error to User.
    //                        Timber.tag( "LOADING" ).e( leaguesResource.status.toString() );
    //                        assert leaguesResource.message != null;
    //                        Snackbar.make(getWindow().getDecorView(), leaguesResource.message, Snackbar.LENGTH_LONG)
    //                                .setAction("Cool", null)
    //                                .show();
    //                        break;
    //                }


}
