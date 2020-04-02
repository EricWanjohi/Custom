package ke.co.droidsense.custom.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.User;
import timber.log.Timber;

public class Register extends AppCompatActivity implements View.OnClickListener {
    //Member Variables.
    @BindView(R.id.log_in_text_btn)
    TextView loginButtonText;
    @BindView(R.id.sign_up_btn)
    Button signUpButton;
    @BindView(R.id.email)
    TextInputLayout email;
    @BindView(R.id.phone)
    TextInputLayout phone;
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.confirm_password)
    TextInputLayout confirm_password;
    @BindView(R.id.full_name)
    TextInputLayout full_name;
    @BindView(R.id.register_text)
    TextView registerHeader;

    //FirebaseDatabase and DataReference.
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference registeredAccountsDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;

    //String values.
    private String emailText;
    private String phoneText;
    private String passwordText;
    private String confirmPasswordText;
    private String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        //Initializations.
        ButterKnife.bind( this );

        //Change Font.
        Typeface sun_valley_font = Typeface.createFromAsset( getAssets(), "fonts/Sun_Valley-Demo.ttf" );
        registerHeader.setTypeface( sun_valley_font );

        //Init FirebaseDatabase and DatabaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        registeredAccountsDatabaseReference = firebaseDatabase.getReference( Constants.USER );

        //Firebase Auth.
        firebaseAuth = FirebaseAuth.getInstance();

        //createAuthStateListener.
        createAuthStateListener();

        //ProgressDialog.
        createAuthProgressDialog();

        //Set ClickListeners.
        signUpButton.setOnClickListener( this );
        loginButtonText.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {
        //Switch statement.
        switch (view.getId()) {

            //Case loginText clicked.
            case R.id.log_in_text_btn:
                //Transition to Login Activity.
                transitionToLoginActivity();
                break;

            //Case signUpBtn.
            case R.id.sign_up_btn:
                //Create User.
                createNewUser();
                break;
        }
    }

    //Override OnStart to check if User is signed in.
    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in.
        firebaseAuth.addAuthStateListener( authStateListener );
    }

    //Override onStop to remove listener.
    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener( authStateListener );
        }
    }

    private void createAuthProgressDialog() {
        //Progress Dialog.
        progressDialog = new ProgressDialog( this );
        progressDialog.setTitle( "Loading..." );
        progressDialog.setMessage( "Authenticating with Firebase..." );
        progressDialog.setCancelable( false );
    }

    //Transitioning to Login.
    private void transitionToLoginActivity() {
        //Create new Intent.
        Intent loginIntent = new Intent( this, Login.class );
        loginIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( loginIntent );
        finish();

    }

    //Validate passwords.
    private boolean isValidPassword(String pass, String confirmPass) {
        //Validate Password & Confirm Password Length.
        if (pass.length() < 6) {
            //set Error.
            password.setError( "Minimum length should be 6 characters." );
            return false;
        } else if (!pass.equals( confirmPass )) {
            //Set Error.
            confirm_password.setError( "Passwords do not match." );
            return false;
        }
        return true;
    }

    //Validate Email Format.
    private boolean isValidEmail(String emailText) {
        boolean isGoodEmail = (emailText != null && Patterns.EMAIL_ADDRESS.matcher( email.getEditText().getText().toString() ).matches());
        //Email.
        if (!isGoodEmail) {
            //Set Error.
            email.setError( "Please enter a valid Email." );
            return false;
        }
        return isGoodEmail;
    }

    //Validate inputs
    private boolean isValidName(String name) {
        //Input
        if (name.equals( "" )) {
            //Set Error.
            full_name.setError( "Name cannot be empty." );
            return false;
        }
        return true;
    }

    //Listen for authentication state.
    private void createAuthStateListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Get Firebase user.
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if User is null.
                if (user != null) {
                    //Create Intent to transition to MainActivity.
                    Intent intent = new Intent( Register.this, MainActivity.class );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( intent );
                    finish();
                }
            }
        };
    }

    //Create New User.
    private void createNewUser() {

        //Get Strings from Text input.
        emailText = email.getEditText().getText().toString().trim();
        phoneText = phone.getEditText().getText().toString().trim();
        passwordText = password.getEditText().getText().toString().trim();
        confirmPasswordText = confirm_password.getEditText().getText().toString().trim();
        fullName = full_name.getEditText().getText().toString().trim();

        //Validate Inputs.
        boolean validEmail = isValidEmail( emailText );
        boolean validName = isValidName( fullName );
        boolean validPassword = isValidPassword( passwordText, confirmPasswordText );

        if (!validEmail || !validName || !validPassword) return;

        //Show progress Dialog.
        progressDialog.show();

        //Get User instance.
        User user = new User( emailText, phoneText, passwordText, confirmPasswordText, fullName );

        //Log
        Timber.tag( "emailText: " ).e( emailText );
        Timber.tag( "phoneText: " ).e( phoneText );
        Timber.tag( "passwordText: " ).e( passwordText );
        Timber.tag( "confirmPasswordText: " ).e( confirmPasswordText );
        Timber.tag( "fullName: " ).e( fullName );

        //Save value to firebase.
        registeredAccountsDatabaseReference.child( phoneText ).push().setValue( user );

        //Create User with Email and password.
        firebaseAuth.createUserWithEmailAndPassword( emailText, passwordText )
                .addOnCompleteListener( Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Dismiss progressDialog.
                        progressDialog.dismiss();
                        //Check if Task is successful.
                        if (task.isSuccessful()) {

                            //Create User Profile using UserProfileChangeRequest class
//                            createUserProfile( Objects.requireNonNull( task.getResult() ).getUser() );
                            //Toast
                            Toast.makeText( Register.this, "Registration succeeded...", Toast.LENGTH_LONG ).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText( Register.this, "Authentication Failed...", Toast.LENGTH_LONG ).show();
                        }
                    }
                } );

    }

    //Create User Profile
    private void createUserProfile(final FirebaseUser firebaseUser) {
        //Use UserProfileChangeRequest builder patter.
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName( fullName )
                .build();
        firebaseUser.updateProfile( addProfileName ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Log.
                Timber.tag( "Display Name: " ).e( firebaseUser.getDisplayName() );
            }
        } );
    }
}
