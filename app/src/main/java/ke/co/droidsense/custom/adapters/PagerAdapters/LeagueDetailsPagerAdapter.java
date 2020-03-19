package ke.co.droidsense.custom.adapters.PagerAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import ke.co.droidsense.custom.Fragments.LeagueDetailsFragment;
import ke.co.droidsense.custom.models.Items.LeagueData;

public class LeagueDetailsPagerAdapter extends FragmentPagerAdapter {
    //Member variables.
    private List<LeagueData> leagueDataList;

    //Constructor.
    public LeagueDetailsPagerAdapter(@NonNull FragmentManager fm, int behavior, List<LeagueData> leagueData) {
        super( fm, behavior );
        leagueDataList = leagueData;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LeagueDetailsFragment.newInstance( leagueDataList.get( position ) );
    }

    @Override
    public int getCount() {
        return leagueDataList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return leagueDataList.get( position ).getStrLeague();
    }
}
