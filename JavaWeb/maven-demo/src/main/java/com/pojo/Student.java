package com.pojo;

public class Student {
    private String stuId;
    private String stuName;
    private String stuSex;
    private String stuBirth;
    private String stuAddress;
    private String stuPhone;


    public Student() {
    }

    public Student(String stuId, String stuName, String stuSex, String stuBirth, String stuAddress, String stuPhone) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.stuBirth = stuBirth;
        this.stuAddress = stuAddress;
        this.stuPhone = stuPhone;
    }

    /**
     * 获取
     * @return stuId
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 设置
     * @param stuId
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 获取
     * @return stuName
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * 设置
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 获取
     * @return stuSex
     */
    public String getStuSex() {
        return stuSex;
    }

    /**
     * 设置
     * @param stuSex
     */
    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    /**
     * 获取
     * @return stuBirth
     */
    public String getStuBirth() {
        return stuBirth;
    }

    /**
     * 设置
     * @param stuBirth
     */
    public void setStuBirth(String stuBirth) {
        this.stuBirth = stuBirth;
    }

    /**
     * 获取
     * @return stuAddress
     */
    public String getStuAddress() {
        return stuAddress;
    }

    /**
     * 设置
     * @param stuAddress
     */
    public void setStuAddress(String stuAddress) {
        this.stuAddress = stuAddress;
    }

    /**
     * 获取
     * @return stuPhone
     */
    public String getStuPhone() {
        return stuPhone;
    }

    /**
     * 设置
     * @param stuPhone
     */
    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String toString() {
        return "Student{stuId = " + stuId + ", stuName = " + stuName + ", stuSex = " + stuSex + ", stuBirth = " + stuBirth + ", stuAddress = " + stuAddress + ", stuPhone = " + stuPhone + "}";
    }
}
