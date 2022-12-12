package com.example.demo;

public class employeeData {

    private String employeeID;
    private String password;
    private String employeeName;
    private String gender;

    public employeeData(String employeeID, String password, String employeeName, String gender) {
        this.employeeID = employeeID;
        this.password = password;
        this.employeeName = employeeName;
        this.gender = gender;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getPassword() {
        return password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getGender() {
        return gender;
    }

}
