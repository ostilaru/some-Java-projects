package com.example.mpdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("TbStudent")
public class User {
    private String stuid;
    private String stuname;
    private String stusex;
    private String stubirth;
    private String stuaddr;
    private String photo;


    public User() {
    }

    public User(String stuid, String stuname, String stusex, String stubirth, String stuaddr, String photo) {
        this.stuid = stuid;
        this.stuname = stuname;
        this.stusex = stusex;
        this.stubirth = stubirth;
        this.stuaddr = stuaddr;
        this.photo = photo;
    }

    /**
     * 获取
     * @return stuid
     */
    public String getStuid() {
        return stuid;
    }

    /**
     * 设置
     * @param stuid
     */
    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    /**
     * 获取
     * @return stuname
     */
    public String getStuname() {
        return stuname;
    }

    /**
     * 设置
     * @param stuname
     */
    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    /**
     * 获取
     * @return stusex
     */
    public String getStusex() {
        return stusex;
    }

    /**
     * 设置
     * @param stusex
     */
    public void setStusex(String stusex) {
        this.stusex = stusex;
    }

    /**
     * 获取
     * @return stubirth
     */
    public String getStubirth() {
        return stubirth;
    }

    /**
     * 设置
     * @param stubirth
     */
    public void setStubirth(String stubirth) {
        this.stubirth = stubirth;
    }

    /**
     * 获取
     * @return stuaddr
     */
    public String getStuaddr() {
        return stuaddr;
    }

    /**
     * 设置
     * @param stuaddr
     */
    public void setStuaddr(String stuaddr) {
        this.stuaddr = stuaddr;
    }

    /**
     * 获取
     * @return photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String toString() {
        return "User{stuid = " + stuid + ", stuname = " + stuname + ", stusex = " + stusex + ", stubirth = " + stubirth + ", stuaddr = " + stuaddr + ", photo = " + photo + "}";
    }
}
