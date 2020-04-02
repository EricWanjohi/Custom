package ke.co.droidsense.custom.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.droidsense.custom.Constants.Constants;
import ke.co.droidsense.custom.R;
import ke.co.droidsense.custom.models.User;
import timber.log.Timber;

import static android.util.Base64.encodeToString;


public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    //Member Variables.
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabaseReference;

    //Views.
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userImage)
    ImageView userImageUrl;
    @BindView(R.id.userEmail)
    TextView userMail;
    @BindView(R.id.displayName)
    TextView displayName;
    @BindView(R.id.userPhone)
    TextView editPhone;
    @BindView(R.id.userEmailEdit)
    TextView editEMail;
    @BindView(R.id.editProfile)
    Button editProfile;

    //Strings.
    String Phone;
    String email;
    private User user;
    private DatabaseReference userSpecificReference;
    private String userId;
    private Uri currentUserPhotoUrl;
    private String currentProfPicPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_profile );

        //Initializations.
        ButterKnife.bind( this );

        //Get Firebase database and reference.
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabaseReference = firebaseDatabase.getReference( Constants.USER );

        //Get Auth object instance.
        firebaseAuth = FirebaseAuth.getInstance();

        //Get User Object from Firebase.
        currentUser = firebaseAuth.getCurrentUser();

        //Get User details from realtime database.
        getUserDataFromDb();

        //Get User input.
//        assert currentUser != null;
//        email = currentUser.getEmail();
//        Phone = editPhone.getText().toString().trim();
//        userImageUrl.getDrawable();


        //Set Click Listener.
        editProfile.setOnClickListener( this );


    }

    //Get data from Db
    private void getUserDataFromDb() {
        //Log
        Timber.tag( "getUserDataFromDb : " ).e( "Starts" );
        //Create a query
        Query checkUserData = userDatabaseReference.orderByChild( "email" ).equalTo( "dedy" );
        //Add Single event listener.
        checkUserData.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check data exists.
                if (dataSnapshot.exists()) {
                    //Create data holder items.
                    String emailFromDb = dataSnapshot.child( "email" ).getValue( String.class );
                    String phoneFromDb = dataSnapshot.child( "phone" ).getValue( String.class );
                    String fullNameFromDb = dataSnapshot.child( "fullName" ).getValue( String.class );

                    //log data
                    Timber.tag( emailFromDb + ": " ).e( emailFromDb );
                    Timber.tag( phoneFromDb + ": " ).e( phoneFromDb );
                    Timber.tag( fullNameFromDb + ": " ).e( fullNameFromDb );

                    //set data to editTexts.
                    userName.setText( fullNameFromDb );
                    userMail.setText( emailFromDb );
                    editPhone.setText( phoneFromDb );


                } else {
                    editPhone.setError( "No user with this number exists" );
                    editPhone.setFocusable( true );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    //Get User details.
//    private void getUserDetails() {
//        userDatabaseReference.addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //Create User object.
//                user = dataSnapshot.getValue( User.class );
//
//                //Strings
//                assert user != null;
//                String uName = currentUser.getDisplayName();
//                String uEmail = currentUser.getEmail();
//                String uPhone = currentUser.getPhoneNumber();
//                currentUserPhotoUrl = currentUser.getPhotoUrl();
//
//                //Get User id
//                userId = currentUser.getUid();
//
//                //Get Specific user reference.
//                userSpecificReference = firebaseDatabase.getReference( Constants.USER );
//
//                //Set Text on the views.
//                if (currentUser.getUid().equals( userId )) {
//
//                    //Set View Strings.
//                    userName.setText( uName );
//                    userMail.setText( uEmail );
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //Toast.
//                Toast.makeText( UserProfileActivity.this, "User data fetch failed...", Toast.LENGTH_SHORT ).show();
//            }
//        } );
//    }

    //Edit User Email Details.
    private void editUserEmailDetails(String email) {
        //Check string is not empty.
        if (email != null && !email.equals( "" ) && !email.isEmpty()) {
            //true
            currentUser.updateEmail( email );
            //Get New details.
//            getUserDetails();
        } else {
            //false
            //Toast to user.
            Toast.makeText( this, "Email is empty...", Toast.LENGTH_SHORT ).show();
        }
    }

    //Edit and update user PhoneNumber
    private void editUserPhoneDetails(String phone) {
        //Check is not null or empty.
        if (Phone != null && !Phone.equals( "" ) && !Phone.isEmpty()) {

            //true

        } else {
            //Toast
            Toast.makeText( this, "Phone number is Empty.", Toast.LENGTH_SHORT ).show();
        }
    }

    //Edit and update user Photo.
    private void editUserProfilePhoto(Bitmap bitmap) {
        //Check if string path is empty or null.
        if (bitmap != null) {
            //true

        } else {
            //false
            //Toast
            Toast.makeText( this, "Path to image is empty.", Toast.LENGTH_SHORT ).show();
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
        switch (view.getId()) {
            //Case editProfile
            case R.id.editProfile:
                //edit User data.
                editUserEmailDetails( email );
                break;

            //case userImage.
            case R.id.userImage:
                //Zoom and add button to edit image
                editImageAndSaveToFirebase();
                break;
        }
    }

    //Edit to remove image and update ui from latest data snapshot.
    private void editImageAndSaveToFirebase() {
        //Member variables.

        //Delete from Firebase.


        //Remove and Update database.
        userDatabaseReference.child( currentUser.getUid() ).child( "userImageUrl" ).push().setValue( null );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get menu inflater
        MenuInflater menuInflater = getMenuInflater();

        //Inflate menu.
        menuInflater.inflate( R.menu.menu_photo, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //Case Photo.
            case R.id.action_photo:
                onCameraIconClicked();
            default:
                break;
        }

        return false;
    }

    //Handle camera icon click and figure out choices.
    private void onCameraIconClicked() {
        //Check Camera and External storage Write permissions.
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.CAMERA )
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                == PackageManager.PERMISSION_GRANTED) {
            //true hence Launch Camera.
            launchCamera();
        } else {
            //false hence Request permissions.
            String[] permissionsRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //Check SDK Level
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions( permissionsRequest, CAMERA_PERMISSION_REQUEST_CODE );
            }
        }

    }

    //Handle Permissions request.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        //Check if constant is ok
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            //true
            //Check permissions granted.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Since permissions granted, launch camera.
                launchCamera();
            } else {
                //Toast Permissions restrictions to user.
                Toast.makeText( this, R.string.camera_permissions, Toast.LENGTH_SHORT ).show();
            }
        }
    }

    //Handle result saving after camera takes image.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        //Check resultant Activity result.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Create Toast to alert user.
            Toast.makeText( this, "Image saved...", Toast.LENGTH_SHORT ).show();

            //Get ImageView Dimensions.
            int targetWidth = userImageUrl.getWidth();
            int targetHeight = userImageUrl.getHeight();

            //Get Bitmap Dimensions.
            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile( currentProfPicPath, bitmapFactoryOptions );

            //Get Image Dimensions.
            int imageWidth = bitmapFactoryOptions.outWidth;
            int imageHeight = bitmapFactoryOptions.outHeight;

            //Decode the image to fit view
            bitmapFactoryOptions.inSampleSize = calculateInSampleSize( bitmapFactoryOptions, targetWidth, targetHeight );
            bitmapFactoryOptions.inPurgeable = true;
            bitmapFactoryOptions.inJustDecodeBounds = false;

            //Decode file at saved location
            Bitmap bitmap = BitmapFactory.decodeFile( currentProfPicPath, bitmapFactoryOptions );

            //SetImage
            userImageUrl.setImageBitmap( bitmap );
            encodeBitmapAndSaveToFirebase( bitmap );

//            //Bundle Image result data.
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get( "data" );
//            userImageUrl.setImageBitmap( imageBitmap );
//            //encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    //Calculate isSampleSize
    private int calculateInSampleSize(BitmapFactory.Options bitmapFactoryOptions, int targetWidth, int targetHeight) {
        //Init
        int inSampleSize = 1;
        final int height = bitmapFactoryOptions.outHeight;
        final int width = bitmapFactoryOptions.outWidth;

        //Check if greater than required dimensions.
        if (height > targetHeight || width > targetWidth) {

            //Init half dimensions
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            /*Create a while loop to restrict the dimensions calculated from going lower than the required
             *  thumbnail dimensions.
             **/
            while ((halfHeight / inSampleSize) >= targetHeight && (halfWidth / inSampleSize) >= targetWidth) {
                //Double inSampleSize.
                inSampleSize *= 2;
            }

        }
        return inSampleSize;
    }

    //Encode Bitmap and save to firebase.
    private void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        //Create a temporary file storage as is being processed
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream );
        String encodedImage = encodeToString( byteArrayOutputStream.toByteArray(), android.util.Base64.DEFAULT );

        //Save to Firebase
        userDatabaseReference.child( currentUser.getUid() )
                .child( "userImageUrl" ).push().setValue( encodedImage );
    }

    //launch camera
    private void launchCamera() {

        //Create Uri
        Uri imageUri = FileProvider.getUriForFile( this, this.getApplicationContext().getPackageName() + ".provider", createImageFile() );

        //Create Implicit Intent.
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

        //Pass Intent Extras
        takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, imageUri );

        if (takePictureIntent.resolveActivity( this.getPackageManager() ) != null) {

            //Request write Permissions via camera.
            takePictureIntent.setFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION );

            //Start intent
            startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );

            //Toast.
            Toast.makeText( this, "Launching Camera...", Toast.LENGTH_SHORT ).show();
        }
    }

    //Create Image file
    private File createImageFile() {
        //Get timestamp.
        String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss" ).format( new Date() );

        //Create name template for image file.
        String imageName = "Profile_pic" + timeStamp + "_";
        //Get Storage Directory.
        File storageDir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        //Create new File
        File image = new File( storageDir, imageName + ".jpg" );
        // Save a file: path for use with ACTION_VIEW intents
        currentProfPicPath = image.getAbsolutePath();

        return image;
    }
}
