package ke.co.droidsense.custom.ViewModels.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ke.co.droidsense.custom.ViewModels.LeaguesByCountryViewModel;

public class LeaguesByCountryViewModelFactory implements ViewModelProvider.Factory {
    //Member variables.
    private String country;
    private Application application;

    //Constructor.
    public LeaguesByCountryViewModelFactory(String country, Application application) {
        this.country = country;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LeaguesByCountryViewModel( country, application );
    }

}
