package ke.co.droidsense.custom.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.User;
import timber.log.Timber;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    //Member Variables.
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabaseReference;

    //Views.
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userEmail)
    TextView userMail;
    @BindView(R.id.displayName)
    TextInputLayout displayName;
    @BindView(R.id.userPhone)
    TextInputLayout editPhone;
    @BindView(R.id.userEmailEdit)
    TextInputLayout editEMail;
    @BindView(R.id.editProfile)
    Button editProfile;
    //Strings.
    String Phone;
    String email;
    private User user;
    private DatabaseReference userSpecificReference;
    private String userId;


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

        //Get User input.
        email = editEMail.getEditText().getText().toString().trim();
        Phone = editPhone.getEditText().getText().toString().trim();

        //Set Click Listener.
        editProfile.setOnClickListener( this );

        //Get User details from realtime database.
        getUserDetails();
    }

    //Get User details.
    private void getUserDetails() {
        userDatabaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Create User object.
                user = dataSnapshot.getValue( User.class );

                //Get User id
                userId = currentUser.getUid();

                //Log
                Timber.tag( "UserId" ).e( userId );

                //Get Specific user reference.
                userSpecificReference = firebaseDatabase.getReference().child( Constants.USER ).child( "User" );

                //Set Text on the views.
                assert user != null;
                if (currentUser.getUid().equals( userId )) {

                    //Strings
                    String uName = dataSnapshot.child( userId ).child( "fullName" ).getValue( String.class );
                    String uEmail = dataSnapshot.child( userId ).child( "email" ).getValue( String.class );

                    //Set View Strings.
                    userName.setText( uName );
                    userMail.setText( uEmail );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.
                Toast.makeText( UserProfileActivity.this, "User data fetch failed...", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    //Edit User Details.
    private void editUserDetails(String email) {
        //Update data on firebase.
        //Check string is not empty.
        if (!TextUtils.isEmpty( email )) {
            currentUser.updateEmail( email );
            //Get New details.
            getUserDetails();
        } else {
            //Toast to user.
            Toast.makeText( this, "Email is empty...", Toast.LENGTH_SHORT ).show();
        }
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

    //Handle click events.
    @Override
    public void onClick(View view) {
        //Check button id
        if (view.getId() == R.id.editProfile) {
            editUserDetails( email );
        }
    }
}
