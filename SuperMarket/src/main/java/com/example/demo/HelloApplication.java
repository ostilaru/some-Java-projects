package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    private double x = 0;
    private double y = 0;
    private ContextMenuEvent event;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

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
        scene.getStylesheets().add(getClass().getResource("employee.css").toExternalForm());

        // String css = root.getClass().getResource("login.css").toExternalForm();
        // scene.getStylesheets().clear();
        // scene.getStylesheets().add(css);

        // design login form using css:

        // stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}