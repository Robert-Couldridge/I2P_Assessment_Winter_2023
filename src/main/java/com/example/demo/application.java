package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class application extends Application {

    storeActions storeInstance = new storeActions();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(application.class.getResource("gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 836, 622);
        stage.setResizable(false);
        stage.setTitle("Car Parts Inventory Management System");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                storeInstance.clearTransactionReport();
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}