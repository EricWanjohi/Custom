package ke.co.droidsense.custom.adapters.PagerAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import ke.co.droidsense.custom.Fragments.LeagueDetailsFragment;
import ke.co.droidsense.custom.models.Country;
import ke.co.droidsense.custom.models.countryItem;

public class LeagueDetailsPagerAdapter extends FragmentPagerAdapter {
    //Member variables.
    private List<countryItem> countryItemList;
    private Country country;

    //Constructor.
    public LeagueDetailsPagerAdapter(@NonNull FragmentManager fm, int behavior, List<countryItem> countryItem) {
        super( fm, behavior );
        countryItemList = countryItem;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LeagueDetailsFragment.newInstance( countryItemList.get( position ) );
    }

    @Override
    public int getCount() {
        return countryItemList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return countryItemList.get( position ).getStrLeague();
    }
}
