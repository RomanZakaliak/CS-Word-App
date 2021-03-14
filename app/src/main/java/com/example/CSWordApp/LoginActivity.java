package com.example.CSWordApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 120;
    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //As a code below
        //addListenerOnButton();

        signInWithGoogle();
    }

    private void signInWithGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.loginButton);
        btnSignIn.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception exception = task.getException();

            if(task.isSuccessful()){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed
                    Log.w(TAG, "Google sign in failed", e);
                    Toast.makeText(this, R.string.google_login_error, Toast.LENGTH_SHORT).show();
                }
            } else{
                Log.w(TAG, exception.toString());
                Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, open dashboard activity
                        Log.d(TAG, "signInWithCredential:success");
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0,0);
                        toast.show();
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }

    //following code does not required in current state, but saved for potential changes in future
//    private void addListenerOnButton() {
//        btnLogin = (Button)findViewById(R.id.loginButton);
//
//        btnLogin.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//
//        btnTextRegistration = (TextView) findViewById(R.id.registrationLogin);
//        btnTextRegistration.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {   //закрити віртуальну клаву, якщо клік за межі поля вводу
//        View view = getCurrentFocus();
//        boolean ret = super.dispatchTouchEvent(event);
//
//        if (view instanceof EditText) {
//            View w = getCurrentFocus();
//            int scrcoords[] = new int[2];
//            w.getLocationOnScreen(scrcoords);
//            float x = event.getRawX() + w.getLeft() - scrcoords[0];
//            float y = event.getRawY() + w.getTop() - scrcoords[1];
//
//            if (event.getAction() == MotionEvent.ACTION_UP
//                    && (x < w.getLeft() || x >= w.getRight()
//                    || y < w.getTop() || y > w.getBottom()) ) {
//                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
//            }
//        }
//        return ret;
//    }


}