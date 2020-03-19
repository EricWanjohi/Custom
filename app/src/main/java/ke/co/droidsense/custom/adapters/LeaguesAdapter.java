package ke.co.droidsense.custom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.League;

public class LeaguesAdapter extends RecyclerView.Adapter<LeaguesAdapter.ViewHolder> {
    //Member variables.
    private List<League> leagueList;
    private Context context;

    //Constructor.
    public LeaguesAdapter(List<League> leagueList, Context context) {
        this.leagueList = leagueList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.leagues_item_layout, parent, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get Data holder object.
        League league = leagueList.get( position );

        //Bind Data.
        holder.strLeague.setText( league.getStrLeague() );
        holder.strSport.setText( league.getStrSport() );
        holder.idLeague.setText( league.getIdLeague() );

    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    //Create ViewHolder.
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Member Variables.
        TextView strLeague, idLeague, strSport;

        //ViewHolder item.
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            //Initialize view objects.
            strLeague = itemView.findViewById( R.id.strLeague );
            strSport = itemView.findViewById( R.id.strSport );
            idLeague = itemView.findViewById( R.id.idLeague );

        }
    }
}
