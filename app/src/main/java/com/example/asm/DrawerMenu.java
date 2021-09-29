package com.example.asm;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class DrawerMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationBarView.OnItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FrameLayout frameLayout;
    CreateExpendituresFragment createExpendituresFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_menu);
        initView();
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawer_nav_view);
        toolbar = findViewById(R.id.drawer_toolbar);

        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                        R.string.open_menu, R.string.close_menu);
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);

        //gán fragment vào frame layout
        frameLayout = findViewById(R.id.main_content);
        createExpendituresFragment = new CreateExpendituresFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, createExpendituresFragment, CreateExpendituresFragment.class.getName())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("main", String.valueOf(item.getItemId()));
        return false;
    }
}
