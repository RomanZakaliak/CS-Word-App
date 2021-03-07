package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


@SuppressWarnings("FieldCanBeLocal")
public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth mAuth;

    private ImageView profileImage;
    private TextView nameTxt;
    private TextView emailTxt;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        showUserData(currentUser);

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
    }


    private void showUserData(FirebaseUser currentUser){
        navigationView = findViewById(R.id.nav_view);

        nameTxt = (TextView)navigationView.getHeaderView(0).findViewById(R.id.name_txt);
        nameTxt.setText(currentUser.getDisplayName());

        emailTxt = (TextView)navigationView.getHeaderView(0).findViewById(R.id.email_txt);
        emailTxt.setText(currentUser.getEmail());

        profileImage = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.profile_image);
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

        Intent intent;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_words:
                Intent intentAddWord = new Intent(DashboardActivity.this, WordsActivity.class);
                startActivity(intentAddWord);
                break;
            case R.id.nav_rules:
                Toast.makeText(this, "rules", Toast.LENGTH_SHORT).show();
                Intent intentRules = new Intent(DashboardActivity.this, RulesActivity.class);
                navigationView.setCheckedItem(R.id.nav_home);
                startActivity(intentRules);

                break;
            case R.id.nav_useful:
                Toast.makeText(this, "useful", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_home);
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
                            finish();;
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
}

