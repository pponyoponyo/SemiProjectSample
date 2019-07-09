package com.example.semiprojectsample.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.semiprojectsample.fragment.FragmentModifyCamera;
import com.example.semiprojectsample.fragment.FragmentModifyMemo;
import com.google.android.material.tabs.TabLayout;

public class ModifyMemoActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private long mMemoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_memo);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("메모 수정"));
        mTabLayout.addTab(mTabLayout.newTab().setText("사진 수정"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mMemoId = getIntent().getLongExtra("memoId",-1);

        findViewById(R.id.btnCancelM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.btnDeleteM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog("정말 삭제하시겠습니까?");

            }
        });
        findViewById(R.id.btnSaveM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProc();
            }
        });
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

    public void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("메모 삭제하기");
        builder.setMessage(msg);

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileDB.delMemo(getBaseContext(),mMemoId);
                finish();
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void saveProc(){
        //1. 첫번째 fragment 의 editText 값을 받아온다.
        FragmentModifyMemo f0 = (FragmentModifyMemo) mViewPagerAdapter.instantiateItem(mViewPager,0);

        //2. 두번째 fragment 의 mPhotopath 값을 가져온다.
        FragmentModifyCamera f1 = (FragmentModifyCamera)  mViewPagerAdapter.instantiateItem(mViewPager,1);
        String memoStr = "";

        EditText edtWriteMemo = f0.getView().findViewById(R.id.editMemo);
        memoStr = edtWriteMemo.getText().toString();
        String photoPath = f1.mPhotoPath;


        MemoBean memoBean = FileDB.getMemo(this, mMemoId);

        if(memoStr.equals("")){
            Toast.makeText(this,"메모를 작성해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        memoBean.memo = memoStr;
        if(photoPath != null) {
            memoBean.memoPicPath = photoPath;
        }

        FileDB.setMemo(this, memoBean);

        Toast.makeText(this,"메모가 수정되었습니다.",Toast.LENGTH_SHORT).show();

        finish();

    }


}
