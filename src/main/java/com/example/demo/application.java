package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * The file that opens up and initialises the inventory management system program and window
 * @author Robert Couldridge
 * @version 2.0
 * @since 2.0
 */
public class application extends Application {

    storeActions storeInstance = new storeActions();
    @Override
    public void start(Stage stage) throws IOException {

        // load primary FXML file (gui.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(application.class.getResource("gui.fxml"));

        // create the scene with a set size
        Scene scene = new Scene(fxmlLoader.load(), 836, 622);

        // disable the ability to resize the window
        stage.setResizable(false);

        // set the title of the window
        stage.setTitle("Car Parts Inventory Management System");
        stage.setScene(scene);

        // display the window
        stage.show();

        // ensure if the window is closed the transaction report is wiped
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