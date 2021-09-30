/*
 * JAVADOC index.html file location: /Scheduler/doc/index.html
 *
 */

package main;


import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * Main class contains the main method of the application
 * and establishes a connection to the database.
 *
 * @author Joshua Kesler
 */
public class Main extends Application {


    /**
     * sets the initial stage of the application.
     *
     * @param primaryStage initial Stage
     * @throws Exception if error occurs during input or output
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setTitle("Welcome. Please Log In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * MAIN METHOD. starts application and establishes database connection
     * @param args args
     */
    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}