package ke.co.droidsense.custom.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import ke.co.droidsense.custom.ApiInterface.SportsDbApi;
import ke.co.droidsense.custom.Daos.LeaguesDao;
import ke.co.droidsense.custom.Database.LeaguesDb;
import ke.co.droidsense.custom.models.Country;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;
import ke.co.droidsense.custom.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LeaguesRepository {
    //Member Variables.
    private static LeaguesRepository leaguesRepository;
    private SportsDbApi sportsDbApi;
    private LeaguesDb leaguesDb;
    private LeaguesDao leaguesDao;
    private LiveData<LeaguesResult> leaguesLiveData;

    //Constructor
    private LeaguesRepository(Application application) {
        //Initialize components.
        leaguesDb = LeaguesDb.getLeaguesDbInstance( application );
        leaguesDao = leaguesDb.leaguesDao();
        sportsDbApi = RetrofitClient.getRetrofit().create( SportsDbApi.class );
        leaguesLiveData = leaguesDao.getAllLeagues();
    }


    //Create Repository Instance getter.
    public static LeaguesRepository getLeaguesRepository(Application application) {
        //check if instance is null.
        if (leaguesRepository == null) {
            //Create new Instance.
            leaguesRepository = new LeaguesRepository( application );
        }
        return leaguesRepository;
    }


    //Using MutableLiveData
    public MutableLiveData<LeaguesResult> getLeagues() {

        //Initialize LiveData
        final MutableLiveData<LeaguesResult> leaguesResultMutableLiveData = new MutableLiveData<>();

        //Get LiveData using sportsDbApi.
        sportsDbApi.getLeagues().enqueue( new Callback<LeaguesResult>() {
            @Override
            public void onResponse(@NotNull Call<LeaguesResult> call, @NotNull Response<LeaguesResult> response) {
                //Check if response is ok.
                if (response.isSuccessful()) {
                    leaguesResultMutableLiveData.setValue( response.body() );
                }

            }

            @Override
            public void onFailure(@NotNull Call<LeaguesResult> call, @NotNull Throwable t) {
                Timber.tag( "Failure" ).e( t.getMessage() );
            }
        } );

        //Return LiveData Object.
        return leaguesResultMutableLiveData;
    }

    //Get Leagues by Country via MutableLiveData.
    public MutableLiveData<Country> getLeaguesInCountry(String country) {
        //Create MutableLiveData to store response.
        final MutableLiveData<Country> listMutableLiveData = new MutableLiveData<>();

        //Get LiveData using sportsDbApi.
        sportsDbApi.getLeaguesByCountry( country ).enqueue( new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                //Check if response is successful.
                if (response.isSuccessful()) {
                    listMutableLiveData.setValue( response.body() );
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        } );

        return listMutableLiveData;
    }

    ///////////////////////// Public methods to interact with data, db and viewmodel //////////////

    //Save LeaguesResult data via worker thread.
    public void saveLeagues(LeaguesResult leaguesResult) {
        new saveLeaguesTask( leaguesDao ).execute( leaguesResult );
    }

    //Delete LeaguesResult object via worker thread.
    public void deleteLeagues(LeaguesResult leaguesResult) {
        //Delete LeaguesResult.
        new deleteLeaguesTask( leaguesDao ).execute( leaguesResult );
    }

    //////////////////////////////// Worker Threads ////////////////////////////////////

    @WorkerThread
    //Background task extending AsyncTask to save LeaguesResult Objects.
    private static class saveLeaguesTask extends AsyncTask<LeaguesResult, Void, Void> {
        //Member Variables.
        private LeaguesDao leaguesDao;

        //Constructor.
        public saveLeaguesTask(LeaguesDao leaguesDao) {
            //Init dao.
            this.leaguesDao = leaguesDao;
        }

        @Override
        protected Void doInBackground(LeaguesResult... leagues) {
            //Save item at position 0.
            leaguesDao.insertLeagues( leagues[0] );
            return null;
        }
    }

    @WorkerThread
    //Background task extending AsyncTask to delete LeaguesResult Objects.
    private static class deleteLeaguesTask extends AsyncTask<LeaguesResult, Void, Void> {
        //Member variables.
        private LeaguesDao leaguesDao;

        //Constructor.
        public deleteLeaguesTask(LeaguesDao leaguesDao) {
            //Init Dao.
            this.leaguesDao = leaguesDao;
        }

        @Override
        protected Void doInBackground(LeaguesResult... leagues) {
            //Delete item at position 0.
            leaguesDao.deleteLeagues( leagues[0] );
            return null;
        }
    }
}
