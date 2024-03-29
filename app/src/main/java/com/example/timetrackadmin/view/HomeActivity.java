package com.example.timetrackadmin.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.timetrackadmin.R;
import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.example.timetrackadmin.view.company.FragmentCompany;
import com.example.timetrackadmin.view.user.FragmentMyAccount;
import com.example.timetrackadmin.view.user.FragmentUsers;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headview = navigationView.getHeaderView(0);
//        getSupportActionBar().setTitle("Hello! "+ SharedPreferenceConfig.getInstance().readFirstName());
        TextView firstname = (TextView) headview.findViewById(R.id.name_navhead);
        TextView email = (TextView) headview.findViewById(R.id.email_navhead);

        firstname.setText(SharedPreferenceConfig.getInstance().readFirstName());
        email.setText(SharedPreferenceConfig.getInstance().readUserEmail());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, new FragmentHome()).commit();
            navigationView.setCheckedItem(R.id.home_option);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                SharedPreferenceConfig spc = SharedPreferenceConfig.getInstance();
                spc.writeLoginStatus(false);
                spc.writeToken("");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home_option:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new FragmentHome()).commit();
                break;
            case R.id.my_account:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new FragmentMyAccount()).commit();
                break;
            case R.id.user_option:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new FragmentUsers()).commit();
                break;
            case R.id.company_option:
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new FragmentCompany()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
