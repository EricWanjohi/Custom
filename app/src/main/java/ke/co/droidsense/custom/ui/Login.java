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

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.R;
import timber.log.Timber;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //Member Variables.
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.email)
    TextInputLayout email;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.sign_up_text)
    TextView signUpText;
    @BindView(R.id.login_header)
    TextView loginHeader;

    //FirebaseAuth.
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    //Strings
    private String loginEmailText;
    private String passwordText;

    //Progress Dialog.
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        //Initializations.
        ButterKnife.bind( this );

        //Auth.
        firebaseAuth = FirebaseAuth.getInstance();

        //Auth Listener implementation.
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Handle successful login check and transition.
                //Create User Object from getCurrentUser.
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //Check if user is null.
                if (user != null) {
                    //Create new Intent
                    Intent loginIntent = new Intent( Login.this, MainActivity.class );
                    loginIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( loginIntent );
                    finish();
                }
            }
        };

        //Change Font.
        Typeface sun_valley_font = Typeface.createFromAsset( getAssets(), "fonts/Sun_Valley-Demo.ttf" );
        loginHeader.setTypeface( sun_valley_font );

        //Set Click listeners.
        loginButton.setOnClickListener( this );
        signUpText.setOnClickListener( this );
    }

    //Create progress dialog.
    private void createProgressDialog() {
        progressDialog = new ProgressDialog( Login.this );
        progressDialog.setMessage( "Signing in user: " + "\n" + loginEmailText );
        progressDialog.setCancelable( false );
        progressDialog.show();
    }

    @Override
    public void onClick(View view) {
        //Check view Clicked.
        switch (view.getId()) {
            //Case SignUp text.
            case R.id.sign_up_text:
                //Intent to transition to Register Activity.
                startActivity( new Intent( Login.this, Register.class ) );
                break;

            //Case Login button.
            case R.id.loginButton:
                //Handle login.
                login();

                //Create ProgressDialog.
                createProgressDialog();
                break;
        }
    }

    //Override onStart adding auth listener.
    @Override
    protected void onStart() {
        super.onStart();
        //Add authListener to auth object.
        firebaseAuth.addAuthStateListener( authStateListener );

    }

    //Override onStop removing listeners.
    @Override
    protected void onStop() {
        super.onStop();
        //Clear /remove any authListener.
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener( authStateListener );
        }
    }

    //Method to handle Login of User
    private void login() {
        //Collect user input.
        loginEmailText = email.getEditText().getText().toString().trim();
        passwordText = password.getEditText().getText().toString().trim();

        Timber.tag( "Phone: " ).e( loginEmailText );
        Timber.tag( "Password: " ).e( passwordText );
        //Check input validity.
        boolean validEmail = isEmailValid( loginEmailText );
        boolean validPassword = isPasswordValid( passwordText );

        //Check all params.
        if (!validEmail || !validPassword) return;

        //Login in User.
        firebaseAuth.signInWithEmailAndPassword( loginEmailText, passwordText )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //check task.
                        if (task.isSuccessful()) {
                            //Toast to test.
                            Toast.makeText( Login.this, "Logging In User: " + loginEmailText, Toast.LENGTH_LONG ).show();
                        } else {
                            //Toast Error
                            Toast.makeText( Login.this, "Authentication failed...", Toast.LENGTH_SHORT ).show();
                        }
                        progressDialog.dismiss();
                    }
                } );
    }

    //Check Email validity.
    private boolean isEmailValid(String loginEmailText) {
        //Check email.
        boolean isGoodEmail = (loginEmailText != null && Patterns.EMAIL_ADDRESS.matcher( loginEmailText ).matches() && !loginEmailText.equals( "" ));

        if (!isGoodEmail) {
            email.setError( "Use a valid email." );
        }
        return isGoodEmail;
    }

    //Check Password is not null.
    private boolean isPasswordValid(String passwordText) {
        boolean isPassNotNull = (passwordText != null && !passwordText.equals( "" ));

        //Check password length.
        if (isPassNotNull && passwordText.length() < 6) {
            password.setError( "Password cannot be less than 6 characters" );
        }
        return isPassNotNull;
    }

}
