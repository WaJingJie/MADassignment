package sg.edu.np.mad.madassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "NP Library";
    //private static final int RC_SIGN_IN = ;
    Button loginbtn, signupbtn, returnselectbtn;
    SignInButton googlebtn;
    GoogleSignInClient mGoogleSignInClient;
    /*DBHandler dbHandler;

    String[] bookisbn = new String[]{"978-1-4028-9463-6","978-1-4028-9461-6","978-1-4028-9462-6"};
    String[] bookname = new String[]{"Introduction to programming","Introduction to android","Hello world"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.welcomelogin);
        signupbtn = findViewById(R.id.welcomesignup);
        returnselectbtn = findViewById(R.id.returnstudent);
//        dbHandler = new DBHandler(this,null,null,1);
//
//        //create initial catalog
//        for (int i=0; i< bookname.length;i++){
//            dbHandler.addbook(bookisbn[i],bookname[i],"Available");
//        }
//        //create initial catalog end

        signupbtn.setVisibility(View.VISIBLE);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Set the dimensions of the sign-in button.

        googlebtn = findViewById(R.id.googlebutton);
        googlebtn.setSize(SignInButton.SIZE_STANDARD);
        googlebtn.setVisibility(View.VISIBLE);
        //This method is to redirect the user to the login page
        Log.v(TAG, FILENAME + ": Redirecting to Login Page");
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginpage = new Intent(MainActivity.this, LoginPage.class);
                startActivity(loginpage);
            }
        });

        //This method is to redirect the user to the sign up page
        Log.v(TAG, FILENAME + ": Redirecting to Sign Up Page");
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuppage = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(signuppage);
            }
        });

        //This method is to redirect the user to the sign up page
        Log.v(TAG, FILENAME + ": Redirecting to Select Page");
        returnselectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnpage = new Intent(MainActivity.this, Select.class);
                startActivity(returnpage);
            }
        });

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.googlebutton:
                        signIn();
                        break;
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        //if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            //Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //handleSignInResult(task);
        //}
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account == null){
            signupbtn.setVisibility(View.VISIBLE);
            googlebtn.setVisibility(View.VISIBLE);
        }
        else{
            googlebtn.setVisibility(View.INVISIBLE);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
