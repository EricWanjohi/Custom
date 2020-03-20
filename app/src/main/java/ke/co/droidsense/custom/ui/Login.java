package ke.co.droidsense.custom.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.R;
import timber.log.Timber;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //Member Variables.
    @BindView(R.id.password)
    TextInputLayout password;
    @BindView(R.id.full_name)
    TextInputLayout fullName;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.sign_up_text)
    TextView signUpText;
    @BindView(R.id.login_header)
    TextView loginHeader;

    //Strings
    private String loginPhoneText;
    private String passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        //Initializations.
        ButterKnife.bind( this );

        //Change Font.
        Typeface sun_valley_font = Typeface.createFromAsset( getAssets(), "fonts/Sun_Valley-Demo.ttf" );
        loginHeader.setTypeface( sun_valley_font );

        //Set Click listeners.
        loginButton.setOnClickListener( this );
        signUpText.setOnClickListener( this );
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
                break;
        }
    }

    //Method to handle Login of User
    private void login() {
        //Collect user input.
        loginPhoneText = fullName.getEditText().getText().toString().trim();
        passwordText = password.getEditText().getText().toString().trim();

        Timber.tag( "Phone: " ).e( loginPhoneText );
        Timber.tag( "Password: " ).e( passwordText );

        //Toast to test.
        Toast.makeText( Login.this, "Logging In User, " + loginPhoneText, Toast.LENGTH_LONG ).show();

    }
}
