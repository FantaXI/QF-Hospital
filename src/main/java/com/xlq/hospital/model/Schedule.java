package com.xlq.hospital.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Schedule implements Serializable {
    private String id;

    private String doctorId;

    private Date scheduleDate;

    private Integer morningCount;

    private Integer morningTotal;

    private Integer afternoonCount;

    private Integer afternoonTotal;

    private Integer total;

    private Integer count;

    private Date createTime;

    private Date lastUpdateTime;

    private BigDecimal charge;

    private String doctorName;

    private String departmentName;

    private String mobile;

    private String week;

    private String detail;

    private String thatDay;

    public String getThatDay() {
        return thatDay;
    }

    public void setThatDay(String thatDay) {
        this.thatDay = thatDay;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getMorningCount() {
        return morningCount;
    }

    public void setMorningCount(Integer morningCount) {
        this.morningCount = morningCount;
    }

    public Integer getMorningTotal() {
        return morningTotal;
    }

    public void setMorningTotal(Integer morningTotal) {
        this.morningTotal = morningTotal;
    }

    public Integer getAfternoonCount() {
        return afternoonCount;
    }

    public void setAfternoonCount(Integer afternoonCount) {
        this.afternoonCount = afternoonCount;
    }

    public Integer getAfternoonTotal() {
        return afternoonTotal;
    }

    public void setAfternoonTotal(Integer afternoonTotal) {
        this.afternoonTotal = afternoonTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", scheduleDate=").append(scheduleDate);
        sb.append(", morningCount=").append(morningCount);
        sb.append(", morningTotal=").append(morningTotal);
        sb.append(", afternoonCount=").append(afternoonCount);
        sb.append(", afternoonTotal=").append(afternoonTotal);
        sb.append(", total=").append(total);
        sb.append(", count=").append(count);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", charge=").append(charge);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}