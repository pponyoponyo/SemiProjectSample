package com.example.semiprojectsample.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.activity.ModifyMemoActivity;
import com.example.semiprojectsample.activity.NewMemoActivity;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.bean.MemoBean;
import com.example.semiprojectsample.db.FileDB;

import java.util.ArrayList;
import java.util.List;



public class FragmentMemo extends Fragment {

    private ListView mLstMemo;
    public ListAdapter adapter;
    public MemberBean loginMem;
    public List<MemoBean> memoList = new ArrayList<>();
    public final static int Saved = 1004;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo,container,false);

        view.findViewById(R.id.btnNewMemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewMemoActivity.class);
                startActivityForResult(intent,Saved);
            }
        });

        mLstMemo = view.findViewById(R.id.lstMemo);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loginMem = FileDB.getLoginMember(getContext());
        memoList = FileDB.getMemoList(getContext(),loginMem.memid);

        //adapter 생성 및 적용
        adapter = new ListAdapter(memoList,getContext());
        //list view 에 adapter 설정
        mLstMemo.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Saved){
            memoList = FileDB.getMemoList(getContext(),loginMem.memid);
            adapter.setMemoList(memoList);
            adapter.notifyDataSetChanged();
        }

    }

    class ListAdapter extends BaseAdapter {
        List<MemoBean> memoList; // 원본 데이터
        Context mContext;
        LayoutInflater inflater;

        public ListAdapter( List<MemoBean> memoList , Context context){
            this.memoList = memoList;
            this.mContext = context;
            this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        public void setMemoList(List<MemoBean> memoList){
            //items 갱신을 위한 함수
            this.memoList = memoList;
        }

        @Override
        public int getCount() {
            return memoList.size();
        }

        @Override
        public Object getItem(int position) {
            return memoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = inflater.inflate(R.layout.memo_form,null);

            //객체 획득
            ImageView memoImg = convertView.findViewById(R.id.memoProfile);
            TextView memoDetail = convertView.findViewById(R.id.memoDetail);
            TextView memoDate = convertView.findViewById(R.id.memoDate);

            //i 번째 객체 획득
            final MemoBean memo = memoList.get(position);

            //ui 에 적용
            Bitmap bitmap = BitmapFactory.decodeFile(memo.memoPicPath);
            Bitmap resizedBmp = getResizedBitmap(bitmap, 4, 100, 100);
            memoImg.setImageBitmap(resizedBmp);
            memoDetail.setText(memo.memo);
            memoDate.setText(memo.memoDate);

            convertView.findViewById(R.id.btnModify).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),ModifyMemoActivity.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap srcBmp, int size, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap resized = Bitmap.createScaledBitmap(srcBmp, width, height, true);
        return resized;
    }

}
