package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginPage extends AppCompatActivity {
    private static final String FILENAME = "LoginPage.java";
    private static final String TAG = "NP Library";
    DBHandler dbHandler;
    private TextView loginemail, loginpassword;
    private Button submitbutton, cancelbutton;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    //This is a Userdata object that can be used among all the classes
    public static UserData userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);
        submitbutton = findViewById(R.id.loginsubmit);
        cancelbutton = findViewById(R.id.logincancel);

        //This method occurs when the submit button is clicked by the user
        Log.v(TAG, FILENAME + ": Submit Button Clicked");
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginemail.getText().toString();
                String password = loginpassword.getText().toString();
                Log.v(TAG, FILENAME + ": Logging in with: " + email + ": " + password);
                //This searches the database for the entered email
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    //this gets the current user
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    ref.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.getValue() != null) {
                                                Log.v(TAG, FILENAME + ": Valid User! Logging in");
                                                // redirect to home page
                                                Log.v(TAG, FILENAME + ": Redirecting to Home Page");
                                                Intent homepage = new Intent(LoginPage.this, StudentHomePage.class);
                                                startActivity(homepage);
                                            } else {
                                                // Invalid user
                                                Toast.makeText(LoginPage.this, "Invalid user!",
                                                        Toast.LENGTH_SHORT).show();
                                                //this signs out the user
                                                mAuth.signOut();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) { }
                                    });


                                } else {
                                    try {
                                        throw task.getException();

                                    } catch (FirebaseAuthInvalidCredentialsException invalidEmail) {
                                        Toast.makeText(LoginPage.this, "Invalid email.",
                                        Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginPage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                        });
            }
        });

        //This method occurs when the cancel button is clicked by the user
        Log.v(TAG, FILENAME + ": Cancel Button Clicked");
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomepage = new Intent(LoginPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
            }
        });

    }

    //This resets the login textboxes
    public void reset(){
        loginemail.setHint("Enter Your Email");
        loginpassword.setHint("Enter Your Password");
    }

    //This resets the password textbox
    public void resetPassword(){
        loginpassword.setHint("Enter Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

}
