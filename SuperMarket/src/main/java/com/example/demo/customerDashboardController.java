package com.example.demo;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class customerDashboardController implements Initializable {

    @FXML
    private Button shoppingCart_Clear_btn;

    @FXML
    private Button purchase_Sure_btn;

    @FXML
    private TextField purchase_Price;

    @FXML
    private TextField purchase_Date;

    @FXML
    private TextField purchase_Guaruantee;

    @FXML
    private Button ShoppingCart_Delete_btn;

    @FXML
    private Button ShoppingCart_Update_btn;

    @FXML
    private TextField ShoppingCart_total_text;

    @FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button pruchase_Add_btn;

    @FXML
    private Button pruchase_Pay_btn;

    @FXML
    private TextField purchase_Name;

    @FXML
    private Button purchase_Clear_btn;

    @FXML
    private ComboBox<?> purchase_Type_combox;

    @FXML
    private Button purchase_btn;

    @FXML
    private TableColumn<productData, String> purchase_col_Date;

    @FXML
    private TableColumn<productData, String> purchase_col_Guaruantee;

    @FXML
    private TableColumn<productData, String> purchase_col_Name;

    @FXML
    private TableColumn<productData, String> purchase_col_Price;

    @FXML
    private TableColumn<productData, String> purchase_col_Quantity;

    @FXML
    private TableColumn<productData, String> purchase_col_Type;

    @FXML
    private Label purchase_customerName;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private TextField purchase_quantity;

    @FXML
    private TableView<productData> purchase_tableView;

    public void purchaseProductsSearch(){

        FilteredList<productData> filter = new FilteredList<>(purchaseProductList, e-> true);

        purchase_search_text.textProperty().addListener((Observable, oldValue, newValue)->{

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
        sortList.comparatorProperty().bind(purchase_tableView.comparatorProperty());
        purchase_tableView.setItems(sortList);

    }

    @FXML
    private Label purchase_total;

    @FXML
    private TextField shoppingCart_Name_text;

    @FXML
    private Button shoppingCart_Pay_btn;

    @FXML
    private TextField shoppingCart_Price_text;

    @FXML
    private TextField shoppingCart_Quantity_box;

    @FXML
    private TextField shoppingCart_Type_text;

    @FXML
    private TextField shoppingCart_Date_text;

    @FXML
    private TextField shopping_Guarantee_text;

    @FXML
    private Button shoppingCart_btn;

    @FXML
    private AnchorPane shoppingCart_form;

    @FXML
    private TextField purchase_search_text;

    @FXML
    private TableColumn<productData, String> shoppingCart_col_Date;

    @FXML
    private TableColumn<productData, String> shoppingCart_col_Guarantee;

    @FXML
    private TableColumn<productData, String> shoppingCart_col_Name;

    @FXML
    private TableColumn<productData, String> shoppingCart_col_Price;

    @FXML
    private TableColumn<productData, String> shoppingCart_col_Quantity;

    @FXML
    private TableColumn<productData, String> shoppingCart_col_Type;
    @FXML
    private TableView<productData> shoppingCart_tableView;

    private double x = 0;
    private double y = 0;

    //mysql tools
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private String[] typeList = {"蔬菜水果", "生活用品", "饮料"};

    public void purchaseProductsTypeList(){
        List<String> listS = new ArrayList<>();

        for(String data: typeList){
            listS.add(data);
        }

        ObservableList typeData = FXCollections.observableArrayList(listS);
        purchase_Type_combox.setItems(typeData);
    }

    public int total;

    public ObservableList<productData> shoppingCartListData() {  // display the ShoppingCart List
        ObservableList<productData> prodList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM shoppingCart";
        // link to mysql
        connect = database.connectDb();

        total = 0;

        try {
            productData prod;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                prod = new productData(result.getString("productName"),
                        result.getString("productType"),
                        result.getInt("productQuantity"),
                        result.getString("produceDate"),
                        result.getInt("guaruanteePeriod"),
                        result.getInt("Price")
                ); // 创建表项
                // System.out.println( result.getDate("productDate").toString());
                prodList.add(prod);
                total += result.getInt("Price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prodList;
    }

    private ObservableList<productData> shoppingCartProductList;

    public void shoppingCartProductsShowData(){ // display the Goods List
        shoppingCartProductList = shoppingCartListData();
        shoppingCart_col_Name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        shoppingCart_col_Type.setCellValueFactory(new PropertyValueFactory<>("productType"));
        shoppingCart_col_Quantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        shoppingCart_col_Date.setCellValueFactory(new PropertyValueFactory<>("produceDate"));
        shoppingCart_col_Guarantee.setCellValueFactory(new PropertyValueFactory<>("guaranteePeriod"));
        shoppingCart_col_Price.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        ShoppingCart_total_text.setText(String.valueOf(total));

        shoppingCart_tableView.setItems(shoppingCartProductList);
    }

    public void shoppingCartProductsSelect(){
        productData prod = shoppingCart_tableView.getSelectionModel().getSelectedItem();
        int num = shoppingCart_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }

        shoppingCart_Name_text.setText(prod.getProductName());
        shoppingCart_Quantity_box.setText(prod.getProductQuantity()); // 默认为 1 件商品
        shoppingCart_Type_text.setText(prod.getProductType());
        shoppingCart_Date_text.setText(prod.getProduceDate());
        shopping_Guarantee_text.setText(prod.getGuaranteePeriod());
        shoppingCart_Price_text.setText(prod.getProductPrice());

        // ShoppingCart_total_text.setText(prod.getProductPrice());
    }

    public void shoppingCartUpdate(){
        Alert alert;
        productData prod = shoppingCart_tableView.getSelectionModel().getSelectedItem();
        int num = shoppingCart_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }

        int newPrice = Integer.valueOf(shoppingCart_Quantity_box.getText()) * Integer.valueOf(prod.getProductPrice()) / Integer.valueOf(prod.getProductQuantity());
        shoppingCart_Price_text.setText(String.valueOf(newPrice));

        // create mysql UPDATE
        String updateProduct = "UPDATE shoppingCart SET productType = '"
                +shoppingCart_Type_text.getText()+"', productQuantity = '"
                +shoppingCart_Quantity_box.getText()+"', produceDate = '"
                +shoppingCart_Date_text.getText()+"', guaruanteePeriod = '"
                +shopping_Guarantee_text.getText()+"', Price = '"
                +newPrice+"' WHERE productName = '"
                +shoppingCart_Name_text.getText()+"'";

        // link to mysql
        connect = database.connectDb();

        try{
            // 更新商品前检查商品信息是否填写完善
            if(shoppingCart_Name_text.getText().isEmpty()
                    || shoppingCart_Type_text.getText().isEmpty()
                    || shoppingCart_Quantity_box.getText().isEmpty()
                    || shoppingCart_Date_text.getText().isEmpty()
                    || shopping_Guarantee_text.getText().isEmpty()
                    || shoppingCart_Price_text.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
            }else{  // excute mysql UPDATE
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE productName: "+ shoppingCart_Name_text.getText() + "?");

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
                    shoppingCartProductsShowData();
                    // Clear the TextFields after UPDATE
                    shoppingCart_Clear();
                }else return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void shoppingCartDelete(){
        String deleteProduct = "DELETE FROM shoppingCart WHERE productName = '"+shoppingCart_Name_text.getText()+"'";

        connect = database.connectDb();

        try {
            Alert alert;
            // 删除商品前检查商品信息是否填写完善
            if(shoppingCart_Name_text.getText().isEmpty()
                    || shoppingCart_Type_text.getText().isEmpty()
                    || shoppingCart_Quantity_box.getText().isEmpty()
                    || shoppingCart_Date_text.getText().isEmpty()
                    || shopping_Guarantee_text.getText().isEmpty()
                    || shoppingCart_Price_text.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
            }else{  // excute mysql DELETE
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE productName: " + shoppingCart_Name_text.getText() + "?");

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
                    shoppingCartProductsShowData();
                    // Clear the TextFields after DELETE
                    shoppingCart_Clear();
                }else return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void shoppingCart_Clear(){
        shoppingCart_Name_text.setText("");
        shoppingCart_Type_text.setText("");
        shoppingCart_Quantity_box.setText("");
        shoppingCart_Date_text.setText("");
        shopping_Guarantee_text.setText("");
        shoppingCart_Price_text.setText("");
    }

    public ObservableList<productData> purchaseProductListData(){   // display the Goods List
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

    private ObservableList<productData> purchaseProductList;
    public void purchaseProductsShowData(){ // display the Goods List
        purchaseProductList = purchaseProductListData();
        purchase_col_Name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_Type.setCellValueFactory(new PropertyValueFactory<>("productType"));
        purchase_col_Quantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        purchase_col_Date.setCellValueFactory(new PropertyValueFactory<>("produceDate"));
        purchase_col_Guaruantee.setCellValueFactory(new PropertyValueFactory<>("guaranteePeriod"));
        purchase_col_Price.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        purchase_tableView.setItems(purchaseProductList);
    }

    public void purchaseProductsSelect(){
        productData prod = purchase_tableView.getSelectionModel().getSelectedItem();
        int num = purchase_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }

        purchase_Name.setText(prod.getProductName());
        purchase_quantity.setText("1"); // 默认为 1 件商品
        //purchase_Type_combox.getSelectionModel().
        purchase_Date.setText(prod.getProduceDate());
        purchase_Guaruantee.setText(prod.getGuaranteePeriod());
        purchase_Price.setText(prod.getProductPrice());
        purchase_total.setText(String.valueOf(Integer.valueOf(purchase_quantity.getText()) *
                Integer.valueOf(purchase_Price.getText())));    // QUANTITY * Price

    }

    public void purchaseSure(){
        Alert alert;
        productData prod = purchase_tableView.getSelectionModel().getSelectedItem();
        int num = purchase_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }
        try{
            Integer check = Integer.valueOf(purchase_quantity.getText());
            // 检查购买数量是否超过库存
            if(check > Integer.valueOf(prod.getProductQuantity())){
                System.out.println("SO MUCH!");
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("You can not buy so much!");
                alert.showAndWait();
            }else{
                purchase_total.setText(String.valueOf(Integer.valueOf(purchase_quantity.getText()) *
                        Integer.valueOf(purchase_Price.getText())));    // QUANTITY * Price
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void addtoShoppingCart(){
        String insertProduct = "INSERT INTO shoppingCart " +
                "(productName,productType,productQuantity,produceDate,guaruanteePeriod,Price)"
                + "VALUES(?,?,?,?,?,?)";
        connect = database.connectDb();

        try {
            Alert alert;
            // 添加前检查商品信息是否填写完善
            if(purchase_Name.getText().isEmpty()
                    || purchase_Type_combox.getSelectionModel().getSelectedItem() == null
                    || purchase_quantity.getText().isEmpty()
                    || purchase_Date.getText().isEmpty()
                    || purchase_Guaruantee.getText().isEmpty()
                    || purchase_Price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
            }else{
                // 添加商品前首先检查数据库里面是否存在该商品
                String check = "SELECT productName FROM shoppingCart WHERE productName = '"+purchase_Name.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);
                // 如果存在同名商品
                if(result.next()){
                    System.out.println("have same goods");
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("productName: "+purchase_Name.getText()+"was already exist!");
                    alert.showAndWait();
                }else { // 不存在同名商品，允许添加
                    // System.out.println("yes");
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to Add productName: "+ purchase_Name.getText() + "to Shopping_Cart?");

                    Optional<ButtonType> option = alert.showAndWait();

                    if(option.get().equals(ButtonType.OK)){
                        // create mysql INSERT
                        prepare = connect.prepareStatement(insertProduct);
                        prepare.setString(1, purchase_Name.getText());
                        prepare.setString(2, (String)purchase_Type_combox.getSelectionModel().getSelectedItem());
                        prepare.setString(3, purchase_quantity.getText());
                        prepare.setString(4, purchase_Date.getText());
                        prepare.setString(5, purchase_Guaruantee.getText());
                        prepare.setString(6, purchase_total.getText());

                        // excute mysql INSERT
                        prepare.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();

                        // 更新商品列表
                        // purchaseProductsUpdate();
                        // addProductsShowData();
                        // Clear the TextFields after INSERT
                        purchaseClear();
                    }else return;

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void purchaseProductsUpdate(){   // should UPDATE mysql Goods after customer buy
        Alert alert;
        productData prod = purchase_tableView.getSelectionModel().getSelectedItem();
        int num = purchase_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1){
            return;
        }
        int newQuantity = Integer.valueOf(prod.getProductQuantity()) - Integer.valueOf(purchase_quantity.getText());

        // create mysql UPDATE
        String updateProduct = "UPDATE Goods SET productType = '"
                +purchase_Type_combox.getSelectionModel().getSelectedItem()+"', productQuantity = '"
                +String.valueOf(newQuantity)+"', productDate = '"
                +purchase_Date.getText()+"', guaranteePeriod = '"
                +purchase_Guaruantee.getText()+"', productPrice = '"
                +purchase_Price.getText()+"' WHERE productName = '"
                +purchase_Name.getText()+"'";

        // link to mysql
        connect = database.connectDb();

        try{
            // System.out.println("update");
            // 更新商品前检查商品信息是否填写完善
            if(purchase_Name.getText().isEmpty()
                    || purchase_Type_combox.getSelectionModel().getSelectedItem() == null
                    || purchase_quantity.getText().isEmpty()
                    || purchase_Date.getText().isEmpty()
                    || purchase_Guaruantee.getText().isEmpty()
                    || purchase_Price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{  // excute mysql UPDATE
                // System.out.println("update2");
                statement = connect.createStatement();
                statement.executeUpdate(updateProduct);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully!");
                alert.showAndWait();
                // 更新商品列表
                purchaseProductsShowData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void purchaseClear(){
        purchase_Name.setText("");
        purchase_Type_combox.getSelectionModel().clearSelection();
        purchase_quantity.setText("");
        purchase_Date.setText("");
        purchase_Guaruantee.setText("");
        purchase_Price.setText("");
        purchase_total.setText("$0.0");
    }

    public void purchasePay(){
        String sql = "INSERT INTO customer_receipt (customer_name,total,date) "+"VALUES(?,?,?)";
        connect = database.connectDb();
        try {
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to bug this product?");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
                // 1. update the products information in mysql Goods and display
                purchaseProductsUpdate();
                // 2. add to mysql receipt
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, getData.employeeName);
                prepare.setString(2, purchase_total.getText());
                prepare.setString(3, String.valueOf(sqlDate));
                prepare.executeUpdate();

                // 3. clear the text
                purchaseClear();

            }else return;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void purchaseReceipt(){

    }

    public void shoppingCartPay(){
        String sql = "INSERT INTO customer_receipt (customer_name,total,date) "+"VALUES(?,?,?)";
        connect = database.connectDb();
        try{
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Alert alert;
            if(purchase_tableView.getItems().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Your ShoppingCart is empty!");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to pay all the products in your ShoppingCart?");

                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    // 1. add to mysql receipt
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, getData.employeeName);
                    prepare.setString(2, ShoppingCart_total_text.getText());
                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully!");
                    alert.showAndWait();

                    // 2. update the products information in mysql Goods and display
                    ObservableList<productData> items = shoppingCart_tableView.getItems();
                    for (productData prod : items){
                        System.out.println("111");
                        int subQuantity = Integer.valueOf(prod.getProductQuantity());
                        // create mysql UPDATE
                        String updateProduct = "UPDATE Goods SET productQuantity = productQuantity -'"
                                +String.valueOf(subQuantity)+"' WHERE productName = '"
                                +prod.getProductName()+"'";
                        prepare = connect.prepareStatement(updateProduct);
                        prepare.executeUpdate();
                        purchaseProductsShowData();
                    }

                    // 3. clear the shopping_cart and display
                    String clear = "TRUNCATE TABLE shoppingCart";
                    prepare = connect.prepareStatement(clear);
                    prepare.executeUpdate();
                    shoppingCartProductsShowData();

                }else return;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void displayUsername(){
        purchase_customerName.setText(getData.employeeName);
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

    public void switch_Form(ActionEvent event){
        if(event.getSource() == shoppingCart_btn){
            purchase_form.setVisible(false);
            shoppingCart_form.setVisible(true);
            // add a click
            purchase_btn.setStyle("-fx-background-color: transparent");
            shoppingCart_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");

            shoppingCartProductsShowData();
        }
        else if(event.getSource() == purchase_btn){
            purchase_form.setVisible(true);
            shoppingCart_form.setVisible(false);
            // add a click
            purchase_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
            shoppingCart_btn.setStyle("-fx-background-color: transparent");

            purchaseProductsShowData();
            purchaseProductsTypeList();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        purchaseProductsTypeList();
        purchaseProductsShowData();
        displayUsername();
        shoppingCartProductsShowData();

    }

}
