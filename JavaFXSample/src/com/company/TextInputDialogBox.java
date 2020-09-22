package com.company;

import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextInputDialogBox {
    public TextInputDialogBox() {}

    public TextInputDialog getTextInputBox()
    {
        TextInputDialog textInputDialog = new TextInputDialog("Enter a value!");

        textInputDialog.setHeaderText("Enter a value!");

        return textInputDialog;
    }

}
