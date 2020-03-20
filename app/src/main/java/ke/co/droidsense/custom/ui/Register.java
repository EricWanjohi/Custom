package ke.co.droidsense.custom.ui;

import android.content.Intent;
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

    //FirebaseDatabase and DataReference.
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
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

        //Firebase Auth.
        firebaseAuth = FirebaseAuth.getInstance();

        //Set ClickListeners.
        signUpButton.setOnClickListener( this );

        loginButtonText.setOnClickListener( this );

    }

    //Create New User.
    private void createNewUser() {
        //Get Strings from Text input.
        emailText = email.getText().toString().trim();
        phoneText = phone.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        confirmPasswordText = confirm_password.getText().toString().trim();
        fullName = full_name.getText().toString().trim();

        //Create User with Email and password.
        firebaseAuth.createUserWithEmailAndPassword( emailText, passwordText )
                .addOnCompleteListener( Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
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

    //Transitioning to Login.
    private void transitionToLoginActivity() {
        //Create new Intent.
        Intent loginIntent = new Intent( this, Login.class );
        loginIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( loginIntent );
        finish();

    }

    //Validate User input data.
    private void validateInputData() {

        //Check Input data not empty.
        checkIfInputEmpty();

        //Validate Email address format is ok.
        checkEmailFormat();


        //validate Password equals Confirm Password.
        checkPasswords();

    }

    //Validate passwords.
    private void checkPasswords() {
        //Passwords.
        if (!passwordText.equals( confirmPasswordText )) {
            password.setError( "Passwords do not match." );
        }

        //Validate Password & Confirm Password Length.
        if (passwordText.length() < 6) {
            //set Error.
            password.setError( "Minimum length should be 6 characters." );
        }

        if (confirmPasswordText.length() < 6) {
            //Set Error.
            confirm_password.setError( "Minimum length should be 6 characters." );
        }
    }

    //Validate Email Format.
    private void checkEmailFormat() {
        //Email.
        if (!Patterns.EMAIL_ADDRESS.matcher( email.getText().toString() ).matches()) {
            //Set Error.
            email.setError( "Email must be a valid address." );
        }
    }

    //Validate inputs
    private void checkIfInputEmpty() {
        //Input
        if (full_name.getText().toString().isEmpty()) {
            //Set Error.
            full_name.setError( "Name cannot be empty." );
        }

        if (password.getText().toString().isEmpty()) {
            //Set Error.
            password.setError( "Password cannot be empty." );
        }

        if (confirm_password.getText().toString().isEmpty()) {
            //Set Error.
            confirm_password.setError( "Confirm Password cannot be empty" );
        }

        if (email.getText().toString().isEmpty()) {
            //Set Error message.
            email.setError( "Email cannot be empty." );
        }
    }

    //Override OnStart to check if User is signed in.
    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }


    //@Override
    //            public void onClick(View v) {
    //                //Init FirebaseDatabase and DatabaseReference;
    //                firebaseDatabase = FirebaseDatabase.getInstance();
    //                databaseReference = firebaseDatabase.getReference();
    //
    //
    //
    //                //Get User instance.
    //                User user = new User( fullName, emailText, phoneText, passwordText, confirmPasswordText );
    //
    //                //Save value to firebase.
    //                databaseReference.child( phoneText ).setValue( user );
    //
    //
    //
    //                //Transition to MainActivity.
    //                Intent intent = new Intent( Register.this, MainActivity.class );
    //                intent.putExtra( "fullName", fullName );
    //                intent.putExtra( "email", emailText );
    //                intent.putExtra( "phone", phoneText );
    //                intent.putExtra( "password", passwordText );
    //                intent.putExtra( "confirmPass", confirmPasswordText );
    //                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
    //                startActivity( intent );
    //                finish();
    //            }
}
