package com.codeofthecoders.expansemanagerapp;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.junit.internal.ExactComparisonCriteria;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private DashBoardFragment dashBoardFragment;
    private IncomeFragment incomeFragment;
    private ExpanseFragment expanseFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= findViewById(R.id.my_toolbar);
        toolbar.setTitle("Expanse Manager");
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationbar);
        frameLayout = findViewById(R.id.main_frame);

        dashBoardFragment = new DashBoardFragment();
        incomeFragment = new IncomeFragment();
        expanseFragment = new ExpanseFragment();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.naView);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(dashBoardFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        setFragment(dashBoardFragment);
                        //bottomNavigationView.setItemBackgroundResource(R.color.dashboard);
                        break;
                    case R.id.income:
                        setFragment(incomeFragment);
                        //bottomNavigationView.setItemBackgroundResource(R.color.income);
                        break;
                    case R.id.expance:
                        setFragment(expanseFragment);
                        //bottomNavigationView.setItemBackgroundResource(R.color.expanse);
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });



    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        if(drawerLayout.isDrawerOpen(GravityCompat.END))
        {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void displaySelectedListener(int itemId)
    {
        Fragment fragment = null;

        switch (itemId)
        {
            case R.id.dashboard:
                fragment=new DashBoardFragment();
                break;

            case R.id.income:
                fragment=new IncomeFragment();
                break;

            case R.id.expance:
                fragment = new ExpanseFragment();
                break;
        }

        if(fragment!=null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        displaySelectedListener(item.getItemId());
        return true;
    }
}