package ke.co.droidsense.custom.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import ke.co.droidsense.custom.ApiInterface.SportsDbApi;
import ke.co.droidsense.custom.Daos.LeaguesDao;
import ke.co.droidsense.custom.Database.LeaguesDb;
import ke.co.droidsense.custom.models.Leagues;
import ke.co.droidsense.custom.utils.RetrofitClient;

public class LeaguesRepository {
    //Member Variables.
    private static LeaguesRepository leaguesRepository;
    private SportsDbApi sportsDbApi;
    private LeaguesDb leaguesDb;
    private LeaguesDao leaguesDao;
    private LiveData<Leagues> leaguesLiveData;

    //Constructor
    private LeaguesRepository(Application application) {
        //Initialize components.
        leaguesDb = LeaguesDb.getLeaguesDbInstance( application );
        leaguesDao = leaguesDb.leaguesDao();
        sportsDbApi = RetrofitClient.getRetrofit().create( SportsDbApi.class );
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

    //Fetch Leagues data from SportsDbApi.
    public LiveData<Leagues> getAllLeagues() {
        return null;
    }
}
