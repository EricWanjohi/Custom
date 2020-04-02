package ke.co.droidsense.custom.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.adapters.RecyclerViewAdapters.SavedLeaguesRecyclerViewHolder;
import ke.co.droidsense.custom.models.countryItem;
import timber.log.Timber;

public class SavedLeagues extends AppCompatActivity {
    //Member Variables
    DatabaseReference savedLeaguesReference;
    FirebaseRecyclerAdapter<countryItem, SavedLeaguesRecyclerViewHolder> firebaseRecyclerAdapter;

    //Get View
    @BindView(R.id.savedLeaguesRecycler)
    RecyclerView recyclerView;
    private SavedLeaguesRecyclerViewHolder SavedLeaguesRecyclerViewHolder;
    private List<countryItem> countryItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_saved_leagues );

        //Initializations.
        ButterKnife.bind( this );

        //Get Firebase Reference.
        savedLeaguesReference = FirebaseDatabase
                .getInstance()
                .getReference( Constants.FAVOURITE_LEAGUES )
                .child( Constants.SAVED_LEAGUE );

        //Log
        Timber.tag( "Reference string: " ).e( savedLeaguesReference.toString() );

        //Set up firebase adapter.
        setupFirebaseAdapter();
    }

    //Implement RecyclerAdapter
    private void setupFirebaseAdapter() {

        //Create new FirebaseRecyclerOptions object.
        FirebaseRecyclerOptions<countryItem> firebaseRecyclerOptions = new FirebaseRecyclerOptions
                .Builder<countryItem>()
                .setQuery( savedLeaguesReference, countryItem.class )
                .build();

        //Create new Adapter instance.
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<countryItem, SavedLeaguesRecyclerViewHolder>( firebaseRecyclerOptions ) {

            //onDataChanged.
            @Override
            public void onDataChanged() {
                super.onDataChanged();

                //Check adapter in not null.
                if (firebaseRecyclerAdapter != null) {
                    //create instance and save data to variable.
                    //Loop
                    for (int i = 0; i <= countryItems.size(); i++) {
                        //Get strLeague name`
//                        String strLeague = countryItems.get( i ).getStrLeague();


                        savedLeaguesReference.addListenerForSingleValueEvent( new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //Loop through changed data object
                                for (DataSnapshot changedDatasnapshot : dataSnapshot.getChildren()) {
                                    //Add child data
                                    countryItems.add( changedDatasnapshot.getValue( countryItem.class ) );
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Snackbar.make( recyclerView, databaseError.getMessage(), BaseTransientBottomBar.LENGTH_SHORT ).show();
                            }
                        } );

                    }
                    Snackbar.make( recyclerView, "Data is Fetched YAAAAAY!", BaseTransientBottomBar.LENGTH_SHORT ).show();
                } else {
                    //Alter view text
                    setContentView( R.layout.favourite_leagues_come_here );
                }
            }

            //onDatabaseError.
            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError( error );
                //Toast
                Timber.e( error.getMessage() );
            }

            @Override
            protected void onBindViewHolder(@NonNull SavedLeaguesRecyclerViewHolder holder, int position, @NonNull countryItem countryItem) {
                //
                View view = null;
                assert false;
                SavedLeaguesRecyclerViewHolder = new SavedLeaguesRecyclerViewHolder( view );
                SavedLeaguesRecyclerViewHolder.bindCountryItem( countryItem );
            }

            @NonNull
            @Override
            public SavedLeaguesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //Create View object.
                View view = LayoutInflater.from( getApplicationContext() ).inflate( R.layout.country_item_layout, parent, false );

                return new SavedLeaguesRecyclerViewHolder( view );
            }
        };

        //Set LayoutManager and Adapter.
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setAdapter( firebaseRecyclerAdapter );
        recyclerView.setHasFixedSize( true );
    }

    //OnStart
    @Override
    protected void onStart() {
        super.onStart();
        //Add Listener.
        firebaseRecyclerAdapter.startListening();
    }

    //OnStop.
    @Override
    protected void onStop() {
        super.onStop();
        //Remove Listener.
        //Check if adapter is null
        if (firebaseRecyclerAdapter != null) {
            //Remove listener.
            firebaseRecyclerAdapter.stopListening();
        }
    }
}
