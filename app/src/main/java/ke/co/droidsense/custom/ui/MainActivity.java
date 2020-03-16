package ke.co.droidsense.custom.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ke.co.droidsense.custom.ApiInterface.SportsDbApi;
import ke.co.droidsense.custom.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //Initialization.
        Timber.plant( new Timber.DebugTree() );

        //Get Data from Api.
        getLeaguesData();

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
            public void onResponse(Call<Leagues> call, Response<Leagues> response) {

                //Check if Successful.
                if (response.isSuccessful()) {

                    //Switch statement.
                    switch (response.code()) {

                        //Case 200: OK.
                        case 200:

                            //Log Data response.
                            Timber.tag( "Case OK: " ).e( String.valueOf( response.code() ) );

                            //Notify dataset changed.

                            break;

                        case 404:

                            //Log Data response.
                            Timber.e( "code 404" );

                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Leagues> call, Throwable t) {

                //Log failure.
                Timber.tag( "Failed: " ).e( t.getMessage() );
            }
        } );

    }
}
