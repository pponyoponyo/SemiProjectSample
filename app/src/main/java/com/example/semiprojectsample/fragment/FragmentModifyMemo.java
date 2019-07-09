package com.example.semiprojectsample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.bean.MemoBean;
import com.example.semiprojectsample.db.FileDB;


public class FragmentModifyMemo extends Fragment {

    public  EditText editMemo;
    private int selectedMemoIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_memo,container,false);

        editMemo = view.findViewById(R.id.editMemo);

        long memoId = getActivity().getIntent().getLongExtra("memoId", -1);
        MemoBean memoBean = FileDB.getMemo(getActivity(), memoId);

        if(memoBean != null) {
            editMemo.setText( memoBean.memo );
        }

        return view;
    }

}
