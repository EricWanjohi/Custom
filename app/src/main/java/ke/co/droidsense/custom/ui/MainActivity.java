package ke.co.droidsense.custom.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ke.co.droidsense.custom.ApiInterface.SportsDbApi;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.adapters.LeaguesAdapter;
import ke.co.droidsense.custom.models.League;
import ke.co.droidsense.custom.models.Leagues;
import ke.co.droidsense.custom.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    //Member Variables.
    private RetrofitClient retrofitClient;
    private SportsDbApi sportsDbApi;
    @BindView(R.id.leaguesRecyclerView)
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private LeaguesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<League> leaguesList;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //Initialization.
        Timber.plant( new Timber.DebugTree() );

        //Initialize items
        recyclerView = findViewById( R.id.leaguesRecyclerView );
        leaguesList = new ArrayList<>();

        //get Intent Extras.
//        Intent registerIntent = getIntent();
//        String email = registerIntent.getStringExtra( "email" );
//        String phone = registerIntent.getStringExtra( "phone" );
//        String password = registerIntent.getStringExtra( "password" );
//        String confirm_password = registerIntent.getStringExtra( "confirmPass" );
//        String fullName = registerIntent.getStringExtra( "fullName" );

        //Create new User Object.
//        User user = new User( fullName, email, phone, password, confirm_password );

        //Get Firebase Reference.
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//
//        databaseReference.child( phone ).setValue(user);

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

    //Method to fetch Leagues Data from SportsDb Api.
    private void getLeaguesData() {

        //Setup sportsDbApi.
        sportsDbApi = RetrofitClient.getRetrofit().create( SportsDbApi.class );

        //Create Call request.
        Call<Leagues> call = sportsDbApi.getLeagues();

        //Log
        Timber.e( String.valueOf( call ) );

        //Queue request.
        call.enqueue( new Callback<Leagues>() {
            @Override
            public void onResponse(@NotNull Call<Leagues> call, @NotNull Response<Leagues> response) {
                //Check if response is successful.
                if (response.isSuccessful()) {
                    //Create response String.
                    String leaguesJson = response.body().toString();

                    //Switch statement.
                    switch (response.code()) {

                        //Case 200: OK.
                        case 200:
                            //Create data holder.
                            Leagues leagues = response.body();

                            //Log
                            Timber.tag( "LeaguesObj: " ).e( leagues.toString() );

                            //Create JsonObject from response.


//                            JSONObject leaguesJson = ;
//                            //Log Data response.
//                            Timber.tag( "Case OK: " ).e( String.valueOf( response.code() ) );
//
//                            //Notify dataset changed.
//                            adapter.notifyDataSetChanged();

                            break;

                        case 404:

                            //Log Data response.
                            Timber.e( "code 404" );

                            //Handle Error

                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Leagues> call, @NotNull Throwable t) {
                //Log Error.
                Log.e( "Failure: ", t.getMessage() );
            }
        } );

    }


}
