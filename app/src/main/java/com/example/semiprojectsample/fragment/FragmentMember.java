package com.example.semiprojectsample.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.activity.JoinActivity;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.db.FileDB;




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
        txtMemId.setText("ID : " + memberBean.memId);
        txtMemPass.setText("Password : " +memberBean.mempass);
        txtMemName.setText("Name : " +memberBean.memName);
        txtMemDate.setText("Register Date : " +memberBean.memDate);

        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();

            }
        });

        view.findViewById(R.id.btnDrop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog("정말 탈퇴하시겠습니까?");
            }
        });

         return view;
    }

    public void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("탈퇴하기");
        builder.setMessage(msg);

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MemberBean loginMem = FileDB.getLoginMember(getContext());
                FileDB.delMember(getContext(), loginMem.memId);
                Toast.makeText(getActivity(), "탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public static Bitmap getResizedBitmap(Bitmap srcBmp, int size, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap resized = Bitmap.createScaledBitmap(srcBmp, width, height, true);
        return resized;
    }
}
