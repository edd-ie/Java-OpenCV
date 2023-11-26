package com.opencv.computervision;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;

public class Camera extends Application {
    public VideoCapture camera;
    private Mat image;
    private VBox box;
    private Button button;
    private boolean running = false;
    public void webCapture(ActionEvent event) {
        if(!running){
            button.setText("Stop");
            running = true;
            camera = new VideoCapture(0); // Open default camera
            image = new Mat();
            ImageView imageView = new ImageView();

            box.getChildren().add(imageView); // Add ImageView to VBox

            new Thread(() -> { // Run in a separate thread
                while (true) {
                    camera.read(image); // Read image to matrix

                    // Convert matrix to byte array
                    MatOfByte buffer = new MatOfByte();
                    Imgcodecs.imencode(".jpg", image, buffer);
                    byte[] imageData = buffer.toArray();

                    // Convert byte array to InputStream
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);

                    // Create Image and update ImageView
                    Platform.runLater(() -> {
                        Image img = new Image(inputStream);
                        imageView.setImage(img);
                    });
                }
            }).start();
        }
        else{
            camera.release();
            running = false;
            if (!box.getChildren().isEmpty()) {
                box.getChildren().clear();
            }
            button.setText("Run");
        }
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Camera");

        button = new Button("Capture");
        Label label = new Label("View port");
        label.setStyle("-fx-font-size: 20; -fx-margin-left: 25%;");
        button.setStyle("-fx-font-size: 12; " +
                "-fx-margin-left: 50; " +
                "-fx-border-radius: 15;");

        button.setOnAction(this::webCapture);

        box = new VBox();
        box.setPrefHeight(450);
        box.setPrefWidth(450);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: black;");

        FlowPane root = new FlowPane(label, box, button);
        root.setPrefHeight(500);
        root.setPrefWidth(500);
        root.setOrientation(Orientation.VERTICAL);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
