package com.base.sc.biz.common;

import java.util.Date;

import com.base.sc.util.DateUtil;

public class DateTimeField {
    private Date datetime;
    private String val;

    public DateTimeField() {
    }

    public DateTimeField(Date datetime) {
        this.datetime = datetime;
        this.val = DateUtil.getDatetimeFormat(this.datetime);
    }

    public DateTimeField(long time) {
        this.datetime = new Date(time);
        this.val = DateUtil.getDatetimeFormat(this.datetime);
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
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
