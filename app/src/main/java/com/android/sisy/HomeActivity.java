package com.android.sisy;



import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.android.ceritamahasiswaa.R;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView nMainNav;
    private FrameLayout nMainFrame;

    private HomeFragment homeFragment;
    private TipsFragment dailyFragment;
    private MydietFragment galleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nMainFrame = (FrameLayout)findViewById(R.id.main_frame);
        nMainNav = (BottomNavigationView)findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        dailyFragment = new TipsFragment();
        galleryFragment = new MydietFragment();
        setFragment(homeFragment);



        nMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){


                    case R.id.daily_nav :
                        setFragment(dailyFragment);
                        return true;

                    case R.id.gallery_nav :
                        setFragment(galleryFragment);
                        return true;

                    case R.id.music_nav :
                        setFragment(homeFragment);
                        return true;
//
//                    case R.id.profile_nav :
//                        setFragment(profileFragment);
//                        return true;

                    default:
                        return false;

                }

            }

            private void setFragment(Fragment fragment) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.commit();
            }
        });

    }
    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


}