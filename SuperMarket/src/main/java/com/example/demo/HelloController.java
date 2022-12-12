package com.example.demo;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button customer_close;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private Hyperlink customer_hyperlink;

    @FXML
    private Button customer_login;

    @FXML
    private PasswordField customer_password;

    @FXML
    private Hyperlink customer_sign_up;

    @FXML
    private TextField customer_username;

    @FXML
    private TextField employee_ID;

    @FXML
    private Button employee_close;

    @FXML
    private AnchorPane employee_form;

    @FXML
    private Hyperlink employee_hyperlink;

    @FXML
    private Button employee_login;

    @FXML
    private PasswordField employee_password;

    @FXML
    private Hyperlink employee_sign_up;

    @FXML
    private AnchorPane left_form;

    @FXML
    private AnchorPane main_form;

    // database tools
    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    private double x = 0;
    private double y = 0;



    public void setCustomer_login(){

        String customerData = "SELECT * FROM customer WHERE username = ? and password = ?";
        connect = database.connectDb();

        try {
            Alert alert;
            // 需要检查 Text 非空
            if(customer_username.getText().isEmpty() || customer_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            }else {
                prepare = connect.prepareStatement(customerData);
                prepare.setString(1, customer_username.getText());
                prepare.setString(2, customer_password.getText());
                result = prepare.executeQuery(); // 访问 mysql

                if(result.next()){

                    getData.employeeName = customer_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    customer_login.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("customerSystem.fxml"));


                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event)->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event)->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                        stage.setOpacity(.9);   // 拖拽窗口时不透明度为 0.8
                    });

                    root.setOnMouseReleased((MouseEvent event)->{
                        stage.setOpacity(1);    // 鼠标释放时不透明度恢复为 1
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    // scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
                    scene.getStylesheets().add(getClass().getResource("employee.css").toExternalForm());

                    stage.setScene(scene);
                    stage.show();

                }else {
                    // username or password wrong
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Username or Password Wrong!");
                    alert.showAndWait();
                }
            }

            prepare = connect.prepareStatement(customerData);
            prepare.setString(1, customer_username.getText());  // 从输入界面获取 Username 字符串
            prepare.setString(2, customer_password.getText());  // 从输入界面获取 Password 字符串
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setEmployee_login(){
        String employeeData = "SELECT * FROM employee WHERE employee_ID = ? and password = ?";
        connect = database.connectDb();

        try {
            Alert alert;
            // 需要检查 Text 非空
            if(employee_ID.getText().isEmpty() || employee_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            }else {
                prepare = connect.prepareStatement(employeeData);
                prepare.setString(1, employee_ID.getText());
                prepare.setString(2, employee_password.getText());
                result = prepare.executeQuery(); // 访问 mysql

                if(result.next()){
                    // 通过 ID 访问 mysql 获取 Name
                    getData.employee_ID = employee_ID.getText();
                    getData.employeeName = result.getString("employeeName");
                    getData.employee_password = employee_password.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    customer_login.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("employeeSystem.fxml"));


                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event)->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event)->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                        stage.setOpacity(.9);   // 拖拽窗口时不透明度为 0.9
                    });

                    root.setOnMouseReleased((MouseEvent event)->{
                        stage.setOpacity(1);    // 鼠标释放时不透明度恢复为 1
                    });


                    scene.getStylesheets().add(getClass().getResource("employee.css").toExternalForm());

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                }else {
                    // username or password wrong
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("EmployeeID or Password Wrong!");
                    alert.showAndWait();
                }
            }

            prepare = connect.prepareStatement(employeeData);
            prepare.setString(1, employee_ID.getText());  // 从输入界面获取 Username 字符串
            prepare.setString(2, employee_password.getText());  // 从输入界面获取 Password 字符串
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void close(){
        System.exit(0);
    }

    public void switchForm(ActionEvent event){  // 切换顾客与员工登陆页面
        if(event.getSource() == customer_hyperlink){
            customer_form.setVisible(false);
            employee_form.setVisible(true);
        }else if (event.getSource() == employee_hyperlink){
            customer_form.setVisible(true);
            employee_form.setVisible(false);
        }
    }

    // public void

    public void initialize(URL url, ResourceBundle rb){
        // TODO
    }
}