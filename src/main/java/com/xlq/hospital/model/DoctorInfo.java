package com.xlq.hospital.model;

import java.io.Serializable;
import java.util.Date;

public class DoctorInfo implements Serializable {
    private String id;

    private String userId;

    private String departmentId;

    private String departmentName;

    private String doctorDesc;

    private String expertDesc;

    private String professionalTitle;

    private Long score;

    private Integer appointmentCount;

    private Date createTime;

    private Date lastUpdateTime;

    private String col1;

    private String col2;

    private String col3;

    private String col4;


    private String departmentClassId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDoctorDesc() {
        return doctorDesc;
    }

    public void setDoctorDesc(String doctorDesc) {
        this.doctorDesc = doctorDesc;
    }

    public String getExpertDesc() {
        return expertDesc;
    }

    public void setExpertDesc(String expertDesc) {
        this.expertDesc = expertDesc;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Integer getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Integer appointmentCount) {
        this.appointmentCount = appointmentCount;
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

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getDepartmentClassId() {
        return departmentClassId;
    }

    public void setDepartmentClassId(String departmentClassId) {
        this.departmentClassId = departmentClassId;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", doctorDesc=").append(doctorDesc);
        sb.append(", expertDesc=").append(expertDesc);
        sb.append(", professionalTitle=").append(professionalTitle);
        sb.append(", score=").append(score);
        sb.append(", appointmentCount=").append(appointmentCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", col1=").append(col1);
        sb.append(", col2=").append(col2);
        sb.append(", col3=").append(col3);
        sb.append(", col4=").append(col4);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}