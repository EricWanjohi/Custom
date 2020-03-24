package ke.co.droidsense.custom.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import ke.co.droidsense.custom.R;

public class UserProfileActivity extends AppCompatActivity {
    //Member Variables.
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_profile );

        //Initializations.
        ButterKnife.bind( this );

        //Get Firebase database and reference.
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabaseReference = firebaseDatabase.getReference( "Users" );

        //Get Auth object instance.
        firebaseAuth = FirebaseAuth.getInstance();

        //Get User Object from Firebase.
        currentUser = firebaseAuth.getCurrentUser();

        //Get User details from realtime database.

    }

    //Override onStart.
    @Override
    protected void onStart() {
        super.onStart();

        //Add authStateListener.
//        firebaseAuth.addAuthStateListener( authStateListener );
    }

    //Override onStop.
    @Override
    protected void onStop() {
        super.onStop();

        //Remove AuthStateListener.
//        if (authStateListener != null){
//            firebaseAuth.removeAuthStateListener( authStateListener );
//        }
    }
}
