package ke.co.droidsense.custom.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ke.co.droidsense.custom.models.Items.League;

public class LeagueDetailsAdapter extends RecyclerView.Adapter<LeagueDetailsAdapter.ViewHolder> {
    //Member Variables.
    private List<League> leagueList;
    private Context context;

    //Constructor
    public LeagueDetailsAdapter(List<League> leagueList, Context context) {
        this.leagueList = leagueList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //ViewHolder class.
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Member Variables.
        private TextView strLeague, idLeague, strSport, strCurrentSeason, intFormedYear, strDivision, strDescriptionEN, strFacebook, strTwitter, strYoutube;
        private ImageView strBadge;

        //View Holder.
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
        }
    }
}
