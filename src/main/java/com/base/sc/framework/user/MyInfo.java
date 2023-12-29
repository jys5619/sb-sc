package com.base.sc.framework.user;

public class MyInfo {
    private static MyInfo myInfo;
    private String userId;

    protected MyInfo() {
    }

    public static MyInfo getinstance() {
        if (myInfo == null) {
            myInfo = new MyInfo();
            myInfo.setUserId("JYS5619");
        }

        return myInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
