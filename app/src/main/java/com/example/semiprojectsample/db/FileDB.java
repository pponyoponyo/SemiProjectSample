package com.example.semiprojectsample.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.bean.MemoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDB {

    private static final String  FILE_DB = "FileDB";
    private static Gson mGson = new Gson();

    public static SharedPreferences getsp (Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_DB,Context.MODE_PRIVATE);
        return sp;
    }
    //새로운 멤버 추가
    public static void addMember (Context context, MemberBean memberBean){
        //1. 기존의 멤버 리스트를 불러온다
    List<MemberBean> memberList = getMemberList(context);

        //2. 기존의 멤버 리스트에 추가한다.
        memberList.add(memberBean);

        //3. 멤버 리스트를 저장한다.
        String listStr = mGson.toJson(memberList);

        //4.저장
        SharedPreferences.Editor editor = getsp(context).edit();
        editor.putString("memberList",listStr);
        editor.commit();
        //commit 를 사용해야 저장됨
    }

    //기존 멤버 교체
    public static void setMember (Context context,MemberBean memberBean){
        //전체 멤버 리스트
        List<MemberBean> memberList = getMemberList(context);
        if(memberList.size() == 0) return;

        for(int i = 0;i<memberList.size();i++){ //for each 는 리스트 받아서 그냥 값만 가져오는
            MemberBean bean = memberList.get(i);
            if(TextUtils.equals(bean.memid,memberBean.memid)){
                //같은 멤버 ID 를 찾았다.
                memberList.set(i,memberBean);
                break;
            }
        }
        //새롭게 update 된 리스트를 저장한다.
        String jsonStr = mGson.toJson(memberList);
        //멤버 리스트를 저장한다.
        SharedPreferences.Editor editor = getsp(context).edit();
        editor.putString("memberList",jsonStr);
        editor.commit();

    }

    public static void addMemo(Context context, String memid, MemoBean memoBean){
        MemberBean findMember = getFindMember(context,memid);
        if(findMember == null)return;

        List<MemoBean> memoList = findMember.memoList;
        if(memoList == null){
            memoList = new ArrayList<>();
        }

        //고유 id 생성
        memoBean.memoId = System.currentTimeMillis();
        memoList.add(memoBean);
        findMember.memoList = memoList;

        //저장
        setMember(context,findMember);
    }


    public static List<MemberBean> getMemberList (Context context){
        String listStr = getsp(context).getString("memberList",null);

        //저장된 리스트가 없을 경우 새로운 리스트를 반환
        if(listStr == null){
            return new ArrayList<MemberBean>();
        }

        //Gson 으로 변환한다
        List<MemberBean> memberLsit = mGson.fromJson(listStr,new TypeToken<List<MemberBean>>(){}.getType());

        return memberLsit;
    }

    public static MemberBean getFindMember(Context context , String memId){
        //1. 멤버 리스트를 가져온다.
        List<MemberBean> memberList = getMemberList(context);
        //2. for 문 돌면서 해당 아이디를 찾는다.
        for(MemberBean bean : memberList){
            if(TextUtils.equals(bean.memid,memId)){
                return bean;
            }
        }
        //3. 찾았을 경우는 해당 memberBean 을 리턴한다.
        //3-2 못찾았을 경우는? null 리턴

        return null;
    }

    //로그인한 MEmberBean 을 저장한다.
    public static void setLoginMember(Context context, MemberBean bean){
        if(bean != null){
            String str = mGson.toJson(bean);
            SharedPreferences.Editor editor = getsp(context).edit();
            editor.putString("loginMemberBean",str);
            editor.commit();
        }
    }

    //로그인한 memberBean 을 취득한다.

    public static MemberBean getLoginMember(Context context){
        String str = getsp(context).getString("loginMemberBean",null);
        if(str == null) return null;
        MemberBean memberBean = mGson.fromJson(str,MemberBean.class);
        return memberBean;

    }

    //기존 메모 교체
    public static void setMemo (Context context , String memId , MemoBean memoBean){

    }

    public static void delMemo (Context context, String memId, int memoid){

    }

    public static List<MemoBean> getMemoList(Context context, String memid){
        MemberBean findMem = getFindMember(context, memid);
        if(findMem == null)return null;
        if(findMem.memoList == null){
            return new ArrayList<>();
        }else{
            return findMem.memoList;
        }
    }

}
