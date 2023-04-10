package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1. 注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2. 获取连接
        String url = "jdbc:mysql://localhost:3306/woodwood";
        String user = "root";
        String password = "jl20020426";
        Connection connection = DriverManager.getConnection(url, user, password);

        // 3. 定义sql语句
        String sql = "update TbStudent set stuname = 'zhangsan' where stuid = 1001";

        // 4. 获取执行sql的对象
        Statement statement = connection.createStatement();

        // 5. 执行sql
        int count = statement.executeUpdate(sql);

        // 6. 处理结果
        System.out.println(count);

        // 7. 释放资源
        statement.close();
        connection.close();
    }
}
