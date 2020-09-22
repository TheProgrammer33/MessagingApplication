package com.company;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class imageHandler {
    public imageHandler() {}

    public ImageView imageNode() throws FileNotFoundException
    {
        Image logoBase = new Image(new FileInputStream("src/com/company/logoBase.png"));

        ImageView imageView = new ImageView(logoBase);

        imageView.setFitHeight(233);
        imageView.setFitWidth(400);

        imageView.setPreserveRatio(true);

        return imageView;
    }
}
