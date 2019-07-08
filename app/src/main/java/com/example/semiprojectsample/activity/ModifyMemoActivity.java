package com.example.semiprojectsample.activity;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.semiprojectsample.R;

import com.example.semiprojectsample.fragment.FragmentModifyCamera;
import com.example.semiprojectsample.fragment.FragmentModifyMemo;
import com.google.android.material.tabs.TabLayout;

public class ModifyMemoActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("메모 수정"));
        mTabLayout.addTab(mTabLayout.newTab().setText("사진 수정"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //viewPager 생성
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        //tab 이랑 adapter 연결
        mViewPager.setAdapter(mViewPagerAdapter);
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
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private int tabSize;

        public ViewPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.tabSize = count;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new FragmentModifyMemo();
                case 1:
                    return new FragmentModifyCamera();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabSize;
        }
    }


}
