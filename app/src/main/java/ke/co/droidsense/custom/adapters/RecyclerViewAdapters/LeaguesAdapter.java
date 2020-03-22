package ke.co.droidsense.custom.adapters.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.Items.League;

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
        holder.idLeague.setText( "ID: " + league.getIdLeague() );

        //Set Tag on item
        holder.itemView.setTag( league );

    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    //Create ViewHolder.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Member Variables.
        TextView strLeague, idLeague, strSport;

        //ViewHolder item.
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            //Initialize view objects.
            strLeague = itemView.findViewById( R.id.strLeague );
            strSport = itemView.findViewById( R.id.strSport );
            idLeague = itemView.findViewById( R.id.idLeague );

            //Set Click listener on item.
            itemView.setOnClickListener( this );

        }

        @Override
        public void onClick(View view) {
            //Get tag.
            League league = (League) view.getTag();

            //Get Position.
            int position = getLayoutPosition();

            //Handle click listener.
            if (view.getTag() == league) {
                //Toast
                Toast.makeText( context, league.getStrLeague(), Toast.LENGTH_LONG ).show();
            }
        }
    }
}
