package com.example.storeqrscanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.storeqrscanner.activities.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Home2 extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Scanner.class));
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0) ;

        TextView userName = (TextView) header.findViewById(R.id.profileName);
        TextView userEmail = (TextView) header.findViewById(R.id.profileEmail);

        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String name = preferences.getString("userName","");
        String email = preferences.getString("userEmail","");

//        userName.setText(name);
//        userEmail.setText(email);
//        if (name.isEmpty()){
//            recreate();
//        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        Boolean isManager = preferences.getBoolean("isManager",false);
        if (!isManager){
            MenuItem addMenuItem = (MenuItem) menu.findItem(R.id.action_addItem);
            MenuItem manageMenuItem = (MenuItem) menu.findItem(R.id.action_itemsList);
            addMenuItem.setVisible(false);
            manageMenuItem.setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home2.this, LoginActivity.class));
                SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("saveLogin", false);
                editor.putBoolean("isManager",false);
                editor.putString("userName","");
                editor.putString("userEmail","");
                editor.apply();
                finish();
                return true;
            case R.id.action_addItem:
                startActivity(new Intent(Home2.this, AddItem.class));
                return true;
            case R.id.action_itemsList:
                startActivity(new Intent(Home2.this, item_list.class));
                return true;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

}