package com.xlq.hospital.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable {
    private String id;

    private String orderCode;

    private String patientId;

    private String doctorId;

    private String departmentId;

    private String doctorName;

    private String doctorMobile;

    private String patientName;

    private String patientMobile;

    private String patientIdCard;

    private Date appointmentTime;

    private String remark;

    private BigDecimal appointmentFee;

    private BigDecimal treatFee;

    private String resutDesc;

    private Integer score;

    private String evaluate;

    private String status;

    private String statusDesc;

    private Date createTime;

    private Date lastUpdateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public String getPatientIdCard() {
        return patientIdCard;
    }

    public void setPatientIdCard(String patientIdCard) {
        this.patientIdCard = patientIdCard;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAppointmentFee() {
        return appointmentFee;
    }

    public void setAppointmentFee(BigDecimal appointmentFee) {
        this.appointmentFee = appointmentFee;
    }

    public BigDecimal getTreatFee() {
        return treatFee;
    }

    public void setTreatFee(BigDecimal treatFee) {
        this.treatFee = treatFee;
    }

    public String getResutDesc() {
        return resutDesc;
    }

    public void setResutDesc(String resutDesc) {
        this.resutDesc = resutDesc;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", patientId=").append(patientId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", doctorName=").append(doctorName);
        sb.append(", doctorMobile=").append(doctorMobile);
        sb.append(", patientName=").append(patientName);
        sb.append(", patientMobile=").append(patientMobile);
        sb.append(", patientIdCard=").append(patientIdCard);
        sb.append(", appointmentTime=").append(appointmentTime);
        sb.append(", remark=").append(remark);
        sb.append(", appointmentFee=").append(appointmentFee);
        sb.append(", treatFee=").append(treatFee);
        sb.append(", resutDesc=").append(resutDesc);
        sb.append(", score=").append(score);
        sb.append(", evaluate=").append(evaluate);
        sb.append(", status=").append(status);
        sb.append(", statusDesc=").append(statusDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}