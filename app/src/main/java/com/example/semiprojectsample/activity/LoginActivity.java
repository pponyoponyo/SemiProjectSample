package com.example.semiprojectsample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.db.FileDB;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdtID,mEdtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtID = findViewById(R.id.edtId);
        mEdtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnJoin = findViewById(R.id.btnJoin);

        btnLogin.setOnClickListener(mBtnLoginClick);
        btnJoin.setOnClickListener(mBtnJoinClick);
    }

    private View.OnClickListener mBtnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            checkLogin();
        }
    };

    private void checkLogin() {

        MemberBean memberBean = FileDB.getFindMember(LoginActivity.this,mEdtID.getText().toString());

        if(memberBean == null){
            Toast.makeText(LoginActivity.this,"해당 아이디는 가입이 되어있지 않습니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.equals(memberBean.mempass,mEdtPass.getText().toString())){

            FileDB.setLoginMember(LoginActivity.this,memberBean);
            Intent intent = new Intent(LoginActivity.this, MainAvtivity.class);
            startActivity(intent);
            mEdtID.setText("");
            mEdtPass.setText("");

        }else{
            Toast.makeText(LoginActivity.this,"패스워드가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            return;
        }

    }


    private View.OnClickListener mBtnJoinClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
            startActivity(intent);
        }
    };
}
