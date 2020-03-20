package ke.co.droidsense.custom.ViewModels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ke.co.droidsense.custom.Repositories.LeaguesRepository;
import ke.co.droidsense.custom.models.Country;

public class LeaguesByCountryViewModel extends ViewModel {
    //Member variables.
    private String country;
    private Application application;
    private LeaguesRepository leaguesRepository;
    private LiveData<Country> countryLiveData;


    //Constructor.
    public LeaguesByCountryViewModel(String country, Application application) {
        this.country = country;
        this.application = application;

        //Initialize ViewModel
        initializeVieModel();
    }

    //Init ViewModel.
    private void initializeVieModel() {
        //Check if liveData is null.
        if (countryLiveData == null) {
            //Fetch data to enable observation and init repo.
            leaguesRepository = LeaguesRepository.getLeaguesRepository( application );
            countryLiveData = leaguesRepository.getLeaguesInCountry( country );
        }
    }

    //Get LiveData method used by Activity.
    public LiveData<Country> getCountryLiveData() {
        return countryLiveData;
    }
}
