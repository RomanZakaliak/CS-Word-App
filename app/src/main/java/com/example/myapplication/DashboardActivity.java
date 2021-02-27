package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private ImageView profileImage;
    private TextView idTxt;
    private TextView nameTxt;
    private TextView emailTxt;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        showUserData(currentUser);
        handleLogOut();
    }

    private void showUserData(FirebaseUser currentUser){
        nameTxt = (TextView) findViewById(R.id.name_txt);
        nameTxt.setText(currentUser.getDisplayName());

        emailTxt = (TextView) findViewById(R.id.email_txt);
        emailTxt.setText(currentUser.getEmail());

        profileImage = (ImageView) findViewById(R.id.profile_image);
        Picasso.get().load(currentUser.getPhotoUrl()).into(profileImage);
    }

    private void handleLogOut(){

        btnSignOut = (Button) findViewById(R.id.sign_out_btn);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                GoogleSignIn.getClient(getBaseContext(),
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                ).signOut();

                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}