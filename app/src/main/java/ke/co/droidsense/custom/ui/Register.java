package ke.co.droidsense.custom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import ke.co.droidsense.custom.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
    //Member Variables.
    TextView loginButtonText;
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
        signUpButton = findViewById( R.id.sign_up_btn );
        loginButtonText = findViewById( R.id.log_in_text_btn );

        //Set ClickListeners.
        signUpButton.setOnClickListener( this );
        loginButtonText.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {
        //Switch statement.
        switch (view.getId()) {

            //Case signUpButton clicked.
            case R.id.sign_up_btn:
                //Validate values.
                validateInputData();

                //Create Account.
                createAccount();
                break;
            case R.id.log_in_text_btn:
                //Transition to Login Activity.
                transitionToLoginActivity();
                break;
        }
    }

    //Transitioning to Login.
    private void transitionToLoginActivity() {
        //Create new Intent.
        Intent loginIntent = new Intent( this, Login.class );
        startActivity( loginIntent );
        finish();

    }

    //Create Account.
    private void createAccount() {
        //Get Strings from Text input.
        emailText = email.getText().toString();
        phoneText = phone.getText().toString();
        passwordText = password.getText().toString();
        confirmPasswordText = confirm_password.getText().toString();
        fullName = full_name.getText().toString();

        //Transition to MainActivity.
        Intent intent = new Intent( this, MainActivity.class );
        intent.putExtra( "email", emailText );
        intent.putExtra( "phone", phoneText );
        intent.putExtra( "password", passwordText );
        intent.putExtra( "confirmPass", confirmPasswordText );
        intent.putExtra( "fullName", fullName );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
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


}
