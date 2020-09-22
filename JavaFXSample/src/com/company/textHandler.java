package com.company;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class textHandler {
    public textHandler() {}

    public Text textNode()
    {
        Text textLine = new Text();

        textLine.setText("Hello World");

        textLine.setX(98);
        textLine.setY(110);

        textLine.setFont(Font.font("Comic Sans", 40));

        return textLine;
    }
}
