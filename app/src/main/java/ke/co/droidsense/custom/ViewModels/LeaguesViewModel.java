package ke.co.droidsense.custom.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import ke.co.droidsense.custom.Repositories.LeaguesRepository;
import ke.co.droidsense.custom.models.Leagues;

public class LeaguesViewModel extends AndroidViewModel {
    //Member variables.
    private LiveData<Leagues> leaguesLiveData = new MediatorLiveData<>();
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
            leaguesLiveData = leaguesRepository.getAllLeagues();
        }
    }

    //Create getter for LiveData object.
    public LiveData<Leagues> getLeaguesLiveData() {
        return leaguesLiveData;
    }
}
