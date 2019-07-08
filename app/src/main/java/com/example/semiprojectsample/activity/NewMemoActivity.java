package com.example.semiprojectsample.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.bean.MemoBean;
import com.example.semiprojectsample.db.FileDB;
import com.example.semiprojectsample.fragment.FragmentCamera;
import com.example.semiprojectsample.fragment.FragmentMemoWrite;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewMemoActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        findViewById(R.id.btnCancel).setOnClickListener(mBtnClick);
        findViewById(R.id.btnSave).setOnClickListener(mBtnClick);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("메모 작성"));
        mTabLayout.addTab(mTabLayout.newTab().setText("사진 촬영"));
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

    private View.OnClickListener mBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch(view.getId()){
                case R.id.btnCancel:
                    finish();
                    break;

                case R.id.btnSave:
                    saveProc();
                    break;
            }

        }
    };
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
                    return new FragmentMemoWrite();
                case 1:
                    return new FragmentCamera();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabSize;
        }
    }

    private void saveProc(){
        //1. 첫번째 fragment 의 editText 값을 받아온다.
        FragmentMemoWrite f0 = (FragmentMemoWrite) mViewPagerAdapter.instantiateItem(mViewPager,0);

        //2. 두번째 fragment 의 mPhotopath 값을 가져온다.
        FragmentCamera f1 = (FragmentCamera)  mViewPagerAdapter.instantiateItem(mViewPager,1);
        String memoStr = "";

        EditText edtWriteMemo = f0.getView().findViewById(R.id.edtwriteMemo);
        memoStr = edtWriteMemo.getText().toString();
        String photoPath = f1.mPhotoPath;

        MemoBean memoBean = new MemoBean();

        if(memoStr.equals("")){
            Toast.makeText(this,"메모를 작성해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(photoPath == null){
            Toast.makeText(this,"사진을 촬영해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        memoBean.memo = memoStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        memoBean.memoDate = sdf.format(new Date());
        memoBean.memoPicPath = photoPath;

        MemberBean loginMember = FileDB.getLoginMember(this);

        FileDB.addMemo(this, loginMember.memid,memoBean);

        Toast.makeText(this,"메모가 추가되었습니다.",Toast.LENGTH_SHORT).show();

        finish();

    }

}
