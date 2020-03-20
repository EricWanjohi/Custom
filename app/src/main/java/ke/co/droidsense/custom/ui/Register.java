package ke.co.droidsense.custom.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.User;

public class Register extends AppCompatActivity implements View.OnClickListener {
    //Member Variables.
    @BindView(R.id.log_in_text_btn)
    TextView loginButtonText;
    @BindView(R.id.sign_up_btn)
    Button signUpButton;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirm_password;
    @BindView(R.id.full_name)
    EditText full_name;
    @BindView(R.id.register_text)
    TextView registerHeader;
    FirebaseUser firebaseUser;


    //FirebaseDatabase and DataReference.
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;
    User isUserRegistered;

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

        //ProgressDialog.
        createAuthProgressDialog();

        //Change Font.
        Typeface sun_valley_font = Typeface.createFromAsset( getAssets(), "fonts/Sun_Valley-Demo.ttf" );
        registerHeader.setTypeface( sun_valley_font );


        //Firebase Auth.
        //Init FirebaseDatabase and DatabaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        //createAuthStateListener.
        createAuthStateListener();

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
                progressDialog.setMessage( "Creating Account..." );
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
        FirebaseAuth.getInstance().signOut();
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
        if (passwordText.length() < 6) {
            //set Error.
            password.setError( "Minimum length should be 6 characters." );
            return false;
        } else if (!passwordText.equals( confirmPasswordText )) {
            //Set Error.
            confirm_password.setError( "Passwords do not match." );
            return false;
        }
        return true;
    }

    //Validate Email Format.
    private boolean isValidEmail(String emailText) {
        boolean isGoodEmail = (emailText != null && Patterns.EMAIL_ADDRESS.matcher( email.getText().toString() ).matches());
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
        emailText = email.getText().toString().trim();
        phoneText = phone.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        confirmPasswordText = confirm_password.getText().toString().trim();
        fullName = full_name.getText().toString().trim();

        //Validate Inputs.
        boolean validEmail = isValidEmail( emailText );
        boolean validName = isValidName( fullName );
        boolean validPassword = isValidPassword( passwordText, confirmPasswordText );

        if (!validEmail || !validName || !validPassword) return;

        //Show progress Dialog.
        progressDialog.show();

        //Get User instance.
        User user = new User( fullName, emailText, phoneText, passwordText, confirmPasswordText );

        //Save value to firebase.
        databaseReference.child( phoneText ).setValue( user );

        //Create User with Email and password.
        firebaseAuth.createUserWithEmailAndPassword( emailText, passwordText )
                .addOnCompleteListener( Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Dismiss progressDialog.
                        progressDialog.dismiss();
                        //Check if Task is successful.
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText( Register.this, "Authentication Failed...", Toast.LENGTH_LONG ).show();
                        }
                    }
                } );
    }
}
