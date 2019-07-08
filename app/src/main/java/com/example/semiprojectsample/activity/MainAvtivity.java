package com.example.semiprojectsample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.fragment.FragmentMember;
import com.example.semiprojectsample.fragment.FragmentMemo;
import com.google.android.material.tabs.TabLayout;

public class MainAvtivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("메모"));
        mTabLayout.addTab(mTabLayout.newTab().setText("회원 정보"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //viewPager 생성
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        //tab 이랑 adapter 연결
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private int tabSize;

        public ViewPagerAdapter(FragmentManager fm,int count) {
            super(fm);
            this.tabSize = count;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new FragmentMemo();
                case 1:
                    return new FragmentMember();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabSize;
        }
    }


}
