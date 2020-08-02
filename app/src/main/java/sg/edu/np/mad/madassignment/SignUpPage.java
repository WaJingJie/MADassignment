package sg.edu.np.mad.madassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage extends AppCompatActivity {
    private static final String FILENAME = "LoginPage.java";
    private static final String TAG = "NP Library";
    private EditText signupemail, signupname, signuppassword, signupconfirm;
    private Button submitbutton, cancelbutton;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);

        signupemail = findViewById(R.id.signupemail);
        signupname = findViewById(R.id.signupname);
        signuppassword = findViewById(R.id.signuppassword);
        signupconfirm = findViewById(R.id.signupconfirm);
        submitbutton = findViewById(R.id.signupsubmit);
        cancelbutton = findViewById(R.id.signupcancel);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        //This method occurs when the submit button is clicked by the user
        Log.v(TAG, FILENAME + ": Submit Button Clicked");
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = signupemail.getText().toString();
                final String name = signupname.getText().toString();
                String password = signuppassword.getText().toString();
                String confirm = signupconfirm.getText().toString();
                String phonenumber = "";
                int nofbooksborrowed = 0;
                int canborrow = 9;

                Log.v(TAG, FILENAME + ": Redirecting to Login Page");
                //This method is to check if any of the sign-up boxes are empty
                if(email.isEmpty() || password.isEmpty() || name.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(SignUpPage.this, "Please complete all the details.", Toast.LENGTH_SHORT).show();
                }
                //This method checks the two password fields match
                else if(!signuppassword.getText().toString().equals(signupconfirm.getText().toString())){
                    Toast.makeText(SignUpPage.this, "Password must be the same.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final UserData userdata = new UserData(email, name, phonenumber, password, nofbooksborrowed, canborrow);
                    //This adds the new user information into the database
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    writeNewStudent(user.getUid(), name, email);
                                    Log.v(TAG, FILENAME + ": New user successfully created !");
                                    Toast.makeText(SignUpPage.this, "Account created successfully!",
                                            Toast.LENGTH_SHORT).show();
                                    //This enters the data into the public static Userdata object
                                    LoginPage.userdata = userdata;
                                    Toast.makeText(SignUpPage.this, "Redirecting to login page.", Toast.LENGTH_SHORT).show();
                                    //redirect user to login page where user can either exit the app or login
                                    Log.v(TAG, FILENAME + ": Go to main page");
                                    Intent mainpage = new Intent(SignUpPage.this, LoginPage.class);
                                    startActivity(mainpage);

                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                        Toast.makeText(SignUpPage.this, "You need at least 6 characters.",
                                                Toast.LENGTH_SHORT).show();

                                    } catch (FirebaseAuthInvalidCredentialsException invalidEmail) {
                                        Toast.makeText(SignUpPage.this, "Invalid email.",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthUserCollisionException emailExists) {
                                        Toast.makeText(SignUpPage.this, "Email already exists.",
                                                Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        //Everything else
                                    }
                                    // If sign in fails, display a message to the user.
                                    Log.v(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                }

            }
        });


        //This method occurs when the cancel button is clicked by the user
        Log.v(TAG, FILENAME + ": Cancel Button Clicked");
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Return to welcome page");
                //This intent redirects the user back to the welcome page
                Intent welcomepage = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(welcomepage);
                reset();
                finish();
            }
        });
    }

    //this creates the new student object in users
    private void writeNewStudent(String userId, String name, String email) {
        ref.child("users").child(userId).child("username").setValue(name);
        ref.child("users").child(userId).child("phoneno").setValue("");
        ref.child("users").child(userId).child("email").setValue(email);
    }

    // This method resets the text in the textboxes in the Sign Up page
    public void reset(){
        Log.v(TAG, FILENAME + ": Resetting Sign Up page");
        signupemail.setHint("Enter Your Email");
        signupname.setHint("Enter Your Name");
        signuppassword.setHint("Enter Your Password");
        signupconfirm.setHint("Confirm Your Password");
    }

    protected void onStop(){
        super.onStop();
        finish();
    }
}
