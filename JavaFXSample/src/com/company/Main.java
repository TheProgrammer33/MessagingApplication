package com.company;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
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
import java.time.LocalDate;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("TextBox Input");

            BorderPane layout = new BorderPane();
            textHandler tH = new textHandler();
            imageHandler iH = new imageHandler();
            TextInputDialogBox textBox = new TextInputDialogBox();
            TilePane tilePane = new TilePane();
            TextFieldBox textFieldBox = new TextFieldBox();

            Button button = new Button("Click me!");

//            TextInputDialog textInputDialog = textBox.getTextInputBox();


//            EventHandler<ActionEvent> buttonEvent = new EventHandler<ActionEvent>()
//            {
//                public void handle(ActionEvent e)
//                {
//                    textInputDialog.show();
//                }
//            };

            tilePane.getChildren().add(textFieldBox.getTextInputBox());
            tilePane.getChildren().add(button);

            Group root = new Group(tilePane);

            Scene scene = new Scene(root,400,233);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println(scene.getWidth() + " " + scene.getHeight());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

