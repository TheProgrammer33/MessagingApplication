package com.company;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();

            String fxmlDocPath = "../MessageBox.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

            BorderPane root = (BorderPane) loader.load(fxmlStream);
            AnchorPane anchorPane = (AnchorPane) root.getChildren().get(0);
            TextField textField = (TextField) anchorPane.getChildren().get(0);
            Button sendButton = (Button) anchorPane.getChildren().get(1);

            textField.setFill(Color.WHITE);

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

