package sample;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello World");

        Locale locale = new Locale("fr");
        ResourceBundle labelsResourceBundle = ResourceBundle.getBundle("resources.Labels", locale);

        System.out.println(labelsResourceBundle.getString("createAccount"));
    }
}
