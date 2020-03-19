package ke.co.droidsense.custom.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ke.co.droidsense.custom.Repositories.LeaguesRepository;
import ke.co.droidsense.custom.models.ResponseModels.LeaguesResult;
import timber.log.Timber;

public class LeaguesViewModel extends AndroidViewModel {
    //Member variables.
    private LiveData<LeaguesResult> leaguesLiveData;
    private LeaguesRepository leaguesRepository;


    //Create constructor matching super.
    public LeaguesViewModel(@NonNull Application application) {
        super( application );

        //Initialize ViewModel.
        initializeViewModel();

    }

    //Initialize ViewModel.
    private void initializeViewModel() {
        //Check if LiveData Object is null.
        if (leaguesLiveData == null) {
            //Fetch data through Repository.
            leaguesRepository = LeaguesRepository.getLeaguesRepository( getApplication() );
            leaguesLiveData = leaguesRepository.getLeagues();
            Timber.tag( "initializeViewModel" ).e( leaguesLiveData.toString() );
        }
    }

    //Create getter for LiveData object.
    public LiveData<LeaguesResult> getLeaguesLiveData() {
        Timber.tag( "getLeaguesLiveData" ).e( "createCall" );
        return leaguesLiveData;
    }

    //Save LeaguesResult.
    public void insertLeagues(LeaguesResult leaguesResult) {
        //Use Repository to insert new LeaguesResult Objects.
        leaguesRepository.saveLeagues( leaguesResult );
    }

    //Delete LeaguesResult.
    public void deleteLeagues(LeaguesResult leaguesResult) {
        //Use Repository to delete Objects.
        leaguesRepository.deleteLeagues( leaguesResult );
    }
}
