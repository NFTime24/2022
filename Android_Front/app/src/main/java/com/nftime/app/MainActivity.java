package com.nftime.app;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nftime.app.fragment.FragmentPageMarket;
import com.nftime.app.fragment.FragmentPageCollection;
import com.nftime.app.fragment.FragmentPageHome;
import com.nftime.app.fragment.FragmentPageMyGallery;
import com.nftime.app.fragment.FragmentPageMyPage;
import com.nftime.app.objects.UserObj;

import java.util.HashMap;

//bottom navigation bar
public class MainActivity extends AppCompatActivity {
    public static String klaytnAddress;
    public static UserObj myUserInfo;
    public static HashMap<Integer, Integer> ownedNftIds2WorkIds;
    public static HashMap<Integer, Boolean> ownedWorkIds;

    private BottomNavigationView mBottomNV;
    
    FragmentPageCollection fragmentPageCollection;
    FragmentPageMyPage fragmentPageMyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setItemIconTintList(null);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());

                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "Main onStart");
    }

    public void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        // 각 페이지에 Fragment 설정
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (id == R.id.navigation_home) {
                fragment = new FragmentPageHome(this);
            } else if (id == R.id.navigation_gallery) {
                fragment = new FragmentPageCollection(this);
            } else if (id == R.id.navigation_collection) {
                fragment = new FragmentPageMyGallery(this);
            } else if (id == R.id.navigation_market) {
                fragment = new FragmentPageMarket(this);
            } else {
                fragment = new FragmentPageMyPage(this);

            }
            fragmentTransaction.add(R.id.content_layout, fragment, tag);

        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment).commit(); // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.}
    }

    public void onFragmentChange(int fragmentNum) {
        String tag = String.valueOf(fragmentNum);
        //프래그먼트의 번호에 따라 다르게 작동하는 조건문
        if(fragmentNum == R.id.navigation_gallery) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragmentPageCollection, tag);
        } else if(fragmentNum == R.id.navigation_myPage) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragmentPageMyPage, tag);
        }
    }
}