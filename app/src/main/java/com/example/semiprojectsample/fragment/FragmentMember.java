package com.example.semiprojectsample.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.db.FileDB;

import static com.example.semiprojectsample.activity.JoinActivity.getResizedBitmap;


public class FragmentMember extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_member,container,false);

        ImageView imgProfile = view.findViewById(R.id.imageView);
        TextView txtMemId = view.findViewById(R.id.txtMemId);
        TextView txtMemName = view.findViewById(R.id.txtMemName);
        TextView txtMemPass = view.findViewById(R.id.txtMemPass);
        TextView txtMemDate = view.findViewById(R.id.txtMemDate);

        MemberBean memberBean = FileDB.getLoginMember(getActivity());

        Bitmap bitmap = BitmapFactory.decodeFile(memberBean.photopath);
        Bitmap resizedBmp = getResizedBitmap(bitmap, 4, 100, 100);
        imgProfile.setImageBitmap(resizedBmp);
        txtMemId.setText(memberBean.memid);
        txtMemPass.setText(memberBean.mempass);
        txtMemName.setText(memberBean.memName);
        txtMemDate.setText(memberBean.memDate);

        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();

            }
        });
         return view;
    }
}
