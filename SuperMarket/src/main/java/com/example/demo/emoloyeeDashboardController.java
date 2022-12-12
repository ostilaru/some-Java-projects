package com.example.demo;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class emoloyeeDashboardController implements Initializable{
    @FXML
    private TableColumn<employeeData, String> Gender_col;

    @FXML
    private TableColumn<employeeData, String> Name_col;

    @FXML
    private TableColumn<employeeData, String> Password_col;

    @FXML
    private Button addProductAdd_btn;

    @FXML
    private Button addProductClear_btn;

    @FXML
    private Button addProductDelete_btn;

    @FXML
    private Button addProductUpdate_btn;

    @FXML
    private TextField addProduct_Date_box;

    @FXML
    private TextField addProduct_Quantity_text;

    @FXML
    private ComboBox<?> addProduct_Type_combox;

    @FXML
    private AnchorPane addProduct_form;

    @FXML
    private TextField addProduct_guarantee_text;

    @FXML
    private TextField addProduct_price_text;

    @FXML
    private TextField addProduct_search;

    @FXML
    private TableView<productData> addProduct_tableview;

    @FXML
    private Button addProducts_btn;

    @FXML
    private TextField addProducts_text;

    @FXML
    private Button close_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Label dashboard_Active_employee;

    @FXML
    private Label dashboard_Today_income;

    @FXML
    private Label dashboard_Total_income;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button employeeClear_btn;

    @FXML
    private Button employeeDelete_btn;

    @FXML
    private TableColumn<employeeData, String> employeeID_col;

    @FXML
    private TextField employeeID_text;

    @FXML
    private Button employeeSave_btn;

    @FXML
    private Button employeeUpdate_btn;

    @FXML
    private TextField employee_Name_text;

    @FXML
    private Button employee_btn;

    @FXML
    private AnchorPane employee_form;

    @FXML
    private ComboBox<?> employee_gender_combox;

    @FXML
    private TextField employee_password_text;

    @FXML
    private TableView<employeeData> employees_tableview;

    @FXML
    private Button logout;

    @FXML
    private Button minisize_btn;

    @FXML
    private TableColumn<productData, String> productDate_col;

    @FXML
    private TableColumn<productData, String> productGuarantee_col;

    @FXML
    private TableColumn<productData, String> productName_col;

    @FXML
    private TableColumn<productData, String> productPrice_col;

    @FXML
    private TableColumn<productData, String> productQuantity_col;

    @FXML
    private TableColumn<productData, String> productType_col;

    @FXML
    private BorderPane main_form;

    @FXML
    private Label employeeName;

    private double x = 0;
    private double y = 0;
    //mysql tools
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void addProductsAdd(){

        String insertProduct = "INSERT INTO Goods " +
                "(productName,productType,productQuantity,productDate,guaranteePeriod,productPrice)"
                + "VALUES(?,?,?,?,?,?)";
        connect = database.connectDb();

        try {
            Alert alert;
            // 添加前检查商品信息是否填写完善
            if(addProducts_text.getText().isEmpty()
                || addProduct_Type_combox.getSelectionModel().getSelectedItem() == null
                || addProduct_Quantity_text.getText().isEmpty()
                || addProduct_Date_box.getText().isEmpty()
                || addProduct_guarantee_text.getText().isEmpty()
                || addProduct_price_text.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
            }else{
                // 添加商品前首先检查数据库里面是否存在该商品
                String check = "SELECT productName FROM Goods WHERE productName = '"+addProducts_text.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);
                // 如果存在同名商品
                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("productName: "+addProducts_text.getText()+"was already exist!");
                    alert.showAndWait();
                }else { // 不存在同名商品，允许添加
                    // create mysql INSERT
                    prepare = connect.prepareStatement(insertProduct);
                    prepare.setString(1, addProducts_text.getText());
                    prepare.setString(2, (String)addProduct_Type_combox.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addProduct_Quantity_text.getText());
                    prepare.setString(4, addProduct_Date_box.getText());
                    prepare.setString(5, addProduct_guarantee_text.getText());
                    prepare.setString(6, addProduct_price_text.getText());

                    // excute mysql INSERT
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // 更新商品列表
                    addProductsShowData();
                    // Clear the TextFields after INSERT
                    addProductsClear();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProductsUpdate(){
        // create mysql UPDATE
        String updateProduct = "UPDATE Goods SET productType = '"
            +addProduct_Type_combox.getSelectionModel().getSelectedItem()+"', productQuantity = '"
            +addProduct_Quantity_text.getText()+"', productDate = '"
            +addProduct_Date_box.getText()+"', guaranteePeriod = '"
            +addProduct_guarantee_text.getText()+"', productPrice = '"
            +addProduct_price_text.getText()+"' WHERE productName = '"
            +addProducts_text.getText()+"'";

        // link to mysql
        connect = database.connectDb();

        try{
            Alert alert;
            // 更新商品前检查商品信息是否填写完善
            if(addProducts_text.getText().isEmpty()
                    || addProduct_Type_combox.getSelectionModel().getSelectedItem() == null
                    || addProduct_Quantity_text.getText().isEmpty()
                    || addProduct_Date_box.getText().isEmpty()
                    || addProduct_guarantee_text.getText().isEmpty()
                    || addProduct_price_text.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
            }else{  // excute mysql UPDATE
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE productName: "+ addProducts_text.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                // IF Click OK then excute mysql UPDATE
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(updateProduct);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // 更新商品列表
                    addProductsShowData();
                    // Clear the TextFields after UPDATE
                    addProductsClear();

                }else{
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProductsDelete(){

        String deleteProduct = "DELETE FROM Goods WHERE productName = '"+addProducts_text.getText()+"'";

        connect = database.connectDb();

        try {
            Alert alert;
            // 删除商品前检查商品信息是否填写完善
            if(addProducts_text.getText().isEmpty()
                    || addProduct_Type_combox.getSelectionModel().getSelectedItem() == null
                    || addProduct_Quantity_text.getText().isEmpty()
                    || addProduct_Date_box.getText().isEmpty()
                    || addProduct_guarantee_text.getText().isEmpty()
                    || addProduct_price_text.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
            }else{  // excute mysql DELETE
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE productName: " + addProducts_text.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                // IF Click OK then excute mysql DELETE
                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(deleteProduct);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // 更新商品列表
                    addProductsShowData();
                    // Clear the TextFields after DELETE
                    addProductsClear();
                }else return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProductsClear(){
        addProducts_text.setText("");
        addProduct_Type_combox.getSelectionModel().clearSelection();
        addProduct_Quantity_text.setText("");
        addProduct_Date_box.setText("");
        addProduct_guarantee_text.setText("");
        addProduct_price_text.setText("");
    }

    private String[] typeList = {"蔬菜水果", "生活用品", "饮料"};
    public void addProductsTypeList(){
        List<String> listS = new ArrayList<>();

        for(String data: typeList){
            listS.add(data);
        }

        ObservableList typeData = FXCollections.observableArrayList(listS);
        addProduct_Type_combox.setItems(typeData);
    }

    public void addProductsSearch(){

        FilteredList<productData> filter = new FilteredList<>(addProductList, e-> true);

        addProduct_search.textProperty().addListener((Observable, oldValue, newValue)->{

            filter.setPredicate(predicateProductData ->{

                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String searchKey = newValue.toString();
                // It can read item, first we need to return true
                if(predicateProductData.getProductName().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProductType().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProductQuantity().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProduceDate().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getGuaranteePeriod().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(predicateProductData.getProductPrice().toLowerCase().contains(searchKey)){
                    return true;
                }
                else return false;
            });
        });

        SortedList<productData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addProduct_tableview.comparatorProperty());
        addProduct_tableview.setItems(sortList);

    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public ObservableList<productData> addProductListData(){ // 展示商品信息表（员工端）
        ObservableList<productData> prodList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Goods";
        // link to mysql
        connect = database.connectDb();

        try{
            productData prod;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                prod = new productData(result.getString("productName"),
                        result.getString("productType"),
                        result.getInt("productQuantity"),
                        result.getString("productDate"),
                        result.getInt("guaranteePeriod"),
                        result.getInt("productPrice")
                ); // 创建表项
                // System.out.println( result.getDate("productDate").toString());
                prodList.add(prod);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return prodList;
    }

    private ObservableList<productData> addProductList;
    public void addProductsShowData(){
        addProductList = addProductListData();
        productName_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productType_col.setCellValueFactory(new PropertyValueFactory<>("productType"));
        productQuantity_col.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productDate_col.setCellValueFactory(new PropertyValueFactory<>("produceDate"));
        productGuarantee_col.setCellValueFactory(new PropertyValueFactory<>("guaranteePeriod"));
        productPrice_col.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        addProduct_tableview.setItems(addProductList);
    }

    public void addProductsSelect(){
        productData prod = addProduct_tableview.getSelectionModel().getSelectedItem();
        int num = addProduct_tableview.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }

        addProducts_text.setText(prod.getProductName());
        addProduct_Quantity_text.setText(prod.getProductQuantity());
        addProduct_guarantee_text.setText(prod.getGuaranteePeriod());
        addProduct_price_text.setText(prod.getProductPrice());
        addProduct_Date_box.setText(prod.getProduceDate());

    }

    public void employeeSave(){ // 增加员工

        String insertEmployee = "INSERT INTO employee (employee_ID,password,employeeName,gender)" +
                "VALUES(?,?,?,?)";
        connect = database.connectDb();

        try{
            Alert alert;
            // 如果信息有空则报错
            if(employeeID_text.getText().isEmpty()
                || employee_password_text.getText().isEmpty()
                || employee_Name_text.getText().isEmpty()
                || employee_gender_combox.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {

                String checkExist = "SELECT employee_ID FROM employee WHERE employee_ID = '"
                        +employeeID_text.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkExist);
                // 如果已经存在该员工
                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + employeeID_text.getText() + "was already exist!");
                    alert.showAndWait();
                }else { // 不存在则可以添加
                    prepare = connect.prepareStatement(insertEmployee);
                    prepare.setString(1, employeeID_text.getText());
                    prepare.setString(2, employee_password_text.getText());
                    prepare.setString(3, employee_Name_text.getText());
                    prepare.setString(4, (String) employee_gender_combox.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Saved!");
                    alert.showAndWait();

                    // 更新员工列表显示
                    employeesShowListData();
                    // 清空 text
                    employeesReset();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String[] genderList = {"Male", "Female"};
    public void employeesGender(){
        List<String> genderL = new ArrayList<>();

        for(String data: genderList){
            genderL.add(data);
        }

        ObservableList listG = FXCollections.observableArrayList(genderL);
        employee_gender_combox.setItems(listG);
    }

    public void employeeUpdate(){
        // create mysql UPDATE
        String updateEmployee = "UPDATE employee SET password = '"
        + employee_password_text.getText()+"', employeeName = '"
        + employee_Name_text.getText()+"', gender = '"
        + employee_gender_combox.getSelectionModel().getSelectedItem()
                +"' WHERE employee_ID = '"+employeeID_text.getText()+"'";

        connect = database.connectDb();

        try{
            Alert alert;
            // 更新前先检查信息是否填写完整
            if(employeeID_text.getText().isEmpty()
                || employee_password_text.getText().isEmpty()
                || employee_Name_text.getText().isEmpty()
                || employee_gender_combox.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{  // excute mysql UPDATE

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Employee ID: " + employeeID_text.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(updateEmployee);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // 更新员工列表显示
                    employeesShowListData();
                    // 清空 text
                    employeesReset();
                }else return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void employeesDelete(){

        String deleteEmployee = "DELETE FROM employee WHERE employee_ID = '"
            +employeeID_text.getText()+"'";
        connect = database.connectDb();

        try{
            Alert alert;
            // 删除前先检查信息是否填写完整
            if(employeeID_text.getText().isEmpty()
                    || employee_password_text.getText().isEmpty()
                    || employee_Name_text.getText().isEmpty()
                    || employee_gender_combox.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Employee ID: " + employeeID_text.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteEmployee);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // 更新员工列表显示
                    employeesShowListData();
                    // 清空 text
                    employeesReset();
                }else return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void employeesReset(){   // 清空 text
        employeeID_text.setText("");
        employee_password_text.setText("");
        employee_Name_text.setText("");
        employee_gender_combox.getSelectionModel().clearSelection();;
    }

    public ObservableList<employeeData> employeesListData(){

        ObservableList<employeeData> emData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee";

        connect = database.connectDb();

        try{

            employeeData employeeD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                employeeD = new employeeData(result.getString("employee_ID"),
                        result.getString("password"),
                        result.getString("employeeName"),
                        result.getString("gender"));
                emData.add(employeeD);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return emData;
    }

    private ObservableList<employeeData> employeesList;
    public void employeesShowListData(){
        employeesList = employeesListData();

        employeeID_col.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        Password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        Name_col.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        Gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));

        employees_tableview.setItems(employeesList);
    }

    public void employeeSelect(){
        employeeData employeeD = employees_tableview.getSelectionModel().getSelectedItem();
        int num = employees_tableview.getSelectionModel().getSelectedIndex();

        if(num < -1){
            return;
        }

        employeeID_text.setText(employeeD.getEmployeeID());
        employee_password_text.setText(employeeD.getPassword());
        employee_Name_text.setText(employeeD.getEmployeeName());
        // employee_gender_combox

    }

    public void logout(){
        try{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
                logout.getScene().getWindow().hide();
                // 重新回到登录界面
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
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

                scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

                stage.setScene(scene);
                stage.show();
            }else return;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void purchaseCustomerID(){

    }

    public void dashboardDisplayActiveEmployees(){
        String sql = "SELECT COUNT(employee_ID) FROM employee";

        connect = database.connectDb();

        int countE = 0;

        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if(result.next()){
                countE = result.getInt("COUNT(employee_ID)");
            }
            dashboard_Active_employee.setText(String.valueOf(countE));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardDisplayIncomeToday(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM customer_receipt WHERE date = '"+sqlDate+"'";
        int sumT = 0;
        connect = database.connectDb();

        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if(result.next()){
                sumT = result.getInt("SUM(total)");
            }
            dashboard_Today_income.setText("$"+String.valueOf(sumT));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTotalIncome(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM customer_receipt ";
        int sumT = 0;
        connect = database.connectDb();

        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if(result.next()){
                sumT = result.getInt("SUM(total)");
            }
            dashboard_Total_income.setText("$"+ String.valueOf(sumT));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dashboardChart(){
        dashboard_chart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";

        connect = database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));
            }

            dashboard_chart.getData().add(chart);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void displayEmployeeName(){
        employeeName.setText(getData.employeeName);
    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            addProduct_form.setVisible(false);
            employee_form.setVisible(false);
            // 增加一个选中效果
            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
            addProducts_btn.setStyle("-fx-background-color: transparent");
            employee_btn.setStyle("-fx-background-color: transparent");

            dashboardDisplayActiveEmployees();
            dashboardDisplayIncomeToday();
            dashboardDisplayTotalIncome();
            dashboardChart();

        }else if(event.getSource() == addProducts_btn){
            dashboard_form.setVisible(false);
            addProduct_form.setVisible(true);
            employee_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
            employee_btn.setStyle("-fx-background-color: transparent");

            addProductsShowData();
            addProductsTypeList();
            addProductsSearch();

        }else if(event.getSource() == employee_btn){
            dashboard_form.setVisible(false);
            addProduct_form.setVisible(false);
            employee_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            addProducts_btn.setStyle("-fx-background-color: transparent");
            employee_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");

            employeesShowListData();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addProductsShowData();
        dashboardDisplayActiveEmployees();
        dashboardDisplayTotalIncome();
        dashboardDisplayIncomeToday();
        dashboardChart();
        addProductsTypeList();
        displayEmployeeName();
        employeesShowListData();
        employeesGender();

    }
}
