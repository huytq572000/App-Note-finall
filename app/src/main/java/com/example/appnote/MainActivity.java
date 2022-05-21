package com.example.appnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appnote.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private  static  final int FRAGMENT_GHICHU=0;
    private  static  final int FRAGMENT_LOINHAC=1;
    private  static  final int FRAGMENT_THUNGRAC=2;
    private  static  final int FRAGMENT_SEARCH=3;
    private int mCurrentFragment = FRAGMENT_GHICHU;
    private ViewPager viewpager;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ghi chú");

        mDrawerLayout = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().findItem(R.id.nav_ghi_chu).setChecked(true);
//


        //
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.getMenu().findItem(R.id.bottom_ghichu).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_ghichu) {
                    getSupportActionBar().setTitle("Ghi chú");
                    openFragmentGhiChu();
                } else if (id == R.id.bottom_loinhac) {
                    openFragmentLoiNhac();
                    getSupportActionBar().setTitle("Lời nhắc");
                } else if (id == R.id.bottom_thungrac) {
                    openFragmentThungRac();
                    getSupportActionBar().setTitle("Thùng rác");
                }
                else if (id == R.id.bottom_search) {
                    openFragmentSearch();
                    getSupportActionBar().setTitle("Tìm kiếm");
                }
                return true;
            }
        });
        //
        //
        viewpager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mCurrentFragment = FRAGMENT_GHICHU;
                        navigationView.getMenu().findItem(R.id.nav_ghi_chu).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.bottom_ghichu).setChecked(true);
                    break;
                    case 1:
                        mCurrentFragment = FRAGMENT_LOINHAC;
                        navigationView.getMenu().findItem(R.id.nav_loi_nhac).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.bottom_loinhac).setChecked(true);
                        break;
                    case 2:
                        mCurrentFragment = FRAGMENT_THUNGRAC;
                        navigationView.getMenu().findItem(R.id.nav_thung_rac).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.bottom_thungrac).setChecked(true);
                        break;
                    case 3:
                        mCurrentFragment = FRAGMENT_SEARCH;
                        navigationView.getMenu().findItem(R.id.nav_search).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.bottom_search).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_ghi_chu){
           openFragmentGhiChu();
            getSupportActionBar().setTitle("Ghi chú");
        }else if(id == R.id.nav_loi_nhac){
            openFragmentLoiNhac();
            getSupportActionBar().setTitle("Lời nhắc");
        }else if(id == R.id.nav_thung_rac){
            openFragmentThungRac();
            getSupportActionBar().setTitle("Thùng rác");
        }
        else if(id == R.id.nav_search){
            openFragmentSearch();
            getSupportActionBar().setTitle("Tìm kiếm");
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragmentGhiChu(){
        if(mCurrentFragment != FRAGMENT_GHICHU){
            viewpager.setCurrentItem(0);
            mCurrentFragment = FRAGMENT_GHICHU;
        }
    }
    private void openFragmentLoiNhac(){
        if(mCurrentFragment != FRAGMENT_LOINHAC){
            viewpager.setCurrentItem(1);
            mCurrentFragment = FRAGMENT_LOINHAC;
        }
    }

    private void openFragmentThungRac(){
        if(mCurrentFragment != FRAGMENT_THUNGRAC){
            viewpager.setCurrentItem(2);
            mCurrentFragment = FRAGMENT_THUNGRAC;
        }
    }

    private void openFragmentSearch(){
        if(mCurrentFragment != FRAGMENT_SEARCH){
            viewpager.setCurrentItem(3);
            mCurrentFragment = FRAGMENT_SEARCH;
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer((GravityCompat.START));
        }else{
            super.onBackPressed();
        }
    }

}