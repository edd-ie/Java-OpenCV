package com.opencv.computervision;

import javafx.application.Application;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Dashboard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.VERSION);
    }
}
