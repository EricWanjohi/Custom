package ke.co.droidsense.custom.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.ViewModels.LeaguesViewModel;
import ke.co.droidsense.custom.adapters.RecyclerViewAdapters.LeaguesAdapter;
import ke.co.droidsense.custom.models.Items.League;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    //Member Variables.
    @BindView(R.id.leaguesRecyclerView)
    RecyclerView recyclerView;
    private LeaguesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<League> leaguesList;
    private LeaguesViewModel leaguesViewModel;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference searchQueryDatabaseReference;
    private String saveLeague = Constants.LEAGUE_SEARCH_QUERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //Initialization.
        Timber.plant( new Timber.DebugTree() );
        firebaseDatabase = FirebaseDatabase.getInstance();
        searchQueryDatabaseReference = firebaseDatabase.getReference();

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
                //Add response data to arrayList.
                List<League> leagues = leaguesResult.getLeagues();
                leaguesList.addAll( leagues );
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        } );
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get menu inflater
        MenuInflater menuInflater = getMenuInflater();

        //Inflate menu.
        menuInflater.inflate( R.menu.search_view_menu, menu );

        //Get SharedPreference.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
        editor = sharedPreferences.edit();

        //Find menuItem.
        MenuItem menuItem = menu.findItem( R.id.searchView );
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Add to SharedPreference.
                addToSharedPreference( query );

                //getLeagueByQuery
                getLeagueQueryData( query );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );
        return true;
    }

    //Transition to LeagueActivity to show search item.
    private void getLeagueQueryData(String searchQuery) {
        //Save query to firebase.
        searchQueryDatabaseReference.child( saveLeague ).push().setValue( searchQuery );

        //Create Intent to transition to search activity
        Intent searchTermIntent = new Intent( MainActivity.this, QueryActivity.class );
        searchTermIntent.putExtra( Constants.LEAGUE_SEARCH_QUERY, searchQuery );
        startActivity( searchTermIntent );
        //Toast
        Toast.makeText( this, "Searching " + searchQuery, Toast.LENGTH_SHORT ).show();
    }

    //Add to sharedPreference.
    private void addToSharedPreference(String query) {
        //Use Editor to add item
        editor.putString( Constants.LEAGUE_SEARCH_QUERY, query ).apply();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Check item clicked.
        if (item.getItemId() == R.id.logout) {
            //log out.
            Logout();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    //Logout
    private void Logout() {
        //Use FirebaseAuth to logout.
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( MainActivity.this, Login.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
        finish();
    }
}
