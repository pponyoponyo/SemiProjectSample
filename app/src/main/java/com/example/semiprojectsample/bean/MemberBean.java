package com.example.semiprojectsample.bean;

import java.util.List;

public class MemberBean {

    public String photopath;
    public String memid;
    public String mempass;
    public String memName;
    public String memDate;
    public List<MemoBean> memoList;

    public String getMemid() {
        return memid;
    }

    public String getMempass() {
        return mempass;
    }
}
