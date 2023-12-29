package com.base.sc.biz.common;

import java.util.Date;

import com.base.sc.util.DateUtil;

public class DateField {
    private Date date;
    private String val;

    public DateField() {
    }

    public DateField(Date date) {
        this.date = date;
        this.val = DateUtil.getDateFormat(this.date);
    }

    public DateField(long time) {
        this.date = new Date(time);
        this.val = DateUtil.getDateFormat(this.date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return this.val;
    }
}
