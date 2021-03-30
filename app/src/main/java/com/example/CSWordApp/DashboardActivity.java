package com.example.CSWordApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    private static final String TAG = "DashboardActivity";

    private FirebaseAuth mAuth;

    private ImageView profileImage;
    private TextView nameTxt;
    private TextView emailTxt;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private FirebaseUser currentUser = null;

    private CardView cardWords, cardRules, cardNotifications, cardUseful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        try{
            showUserData(currentUser);
        } catch (Exception ex){
            Log.w(TAG, ex.getMessage(), ex);
        }


        //------------------Hooks------------------
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //------------------Tool Bar------------------
        setSupportActionBar(toolbar);
        // ------------------Nav drawer menu------------------

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        cardWords = findViewById(R.id.card_words);
        cardWords.setOnClickListener(this);
        cardRules = findViewById(R.id.card_rules);
        cardRules.setOnClickListener(this);
        cardNotifications = findViewById(R.id.card_notifications);
        cardNotifications.setOnClickListener(this);
        cardUseful = findViewById(R.id.card_useful);
        cardUseful.setOnClickListener(this);

    }


    private void showUserData(FirebaseUser currentUser){
        navigationView = findViewById(R.id.nav_view);

        nameTxt = navigationView.getHeaderView(0).findViewById(R.id.name_txt);
        nameTxt.setText(currentUser.getDisplayName());

        emailTxt = navigationView.getHeaderView(0).findViewById(R.id.email_txt);
        emailTxt.setText(currentUser.getEmail());

        profileImage = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        Picasso.get().load(currentUser.getPhotoUrl()).into(profileImage);
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_words:
                Intent intentAddWord = new Intent(DashboardActivity.this, WordsActivity.class);
                startActivity(intentAddWord);
                break;
            case R.id.nav_rules:
                Intent intentRules = new Intent(DashboardActivity.this, RulesActivity.class);
                navigationView.setCheckedItem(R.id.nav_home);
                startActivity(intentRules);
                break;
            case R.id.nav_useful:
                Intent intentUseful = new Intent(DashboardActivity.this, HelpfulActivity.class);
                navigationView.setCheckedItem(R.id.nav_home);
                startActivity(intentUseful);
                break;
            case R.id.nav_reminder:
                Intent intentReminder = new Intent(DashboardActivity.this, ReminderActivity.class);
                navigationView.setCheckedItem(R.id.nav_home);
                startActivity(intentReminder);
                break;
            case R.id.nav_logout:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(DashboardActivity.this);
                a_builder.setMessage("Ти дійсно бажаєш вийти з облікового запису?")
                        .setCancelable(false)
                        .setPositiveButton("Так", (dialog, which) -> {
                            FirebaseAuth.getInstance().signOut();

                            GoogleSignIn.getClient(getBaseContext(),
                                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                            ).signOut();

                            Intent intent1 = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent1);
                            finish();
                        })
                        .setNegativeButton("Ні", (dialog, which) -> {
                            navigationView.setCheckedItem(R.id.nav_home);
                            dialog.cancel();
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Вихід");
                alert.show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_words:
                Intent intentAddWord = new Intent(DashboardActivity.this, WordsActivity.class);
                startActivity(intentAddWord);
                break;
            case R.id.card_rules:
                Intent intentRules = new Intent(DashboardActivity.this, RulesActivity.class);
                startActivity(intentRules);
                break;
            case R.id.card_useful:
                Intent intentUseful = new Intent(DashboardActivity.this, HelpfulActivity.class);
                startActivity(intentUseful);
                break;
            case R.id.card_notifications:
                Intent intentReminder = new Intent(DashboardActivity.this, ReminderActivity.class);
                startActivity(intentReminder);
                break;
            default:
                break;
        }
    }

}

