package com.opencv.computervision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

class DetectFaceDemo {
    public void run() {
        System.out.println("\nRunning DetectFaceDemo");

        // Create a face detector from the cascade file in the
        String folder = "X:\\Code\\Java\\ComputerVision\\src\\main\\resources\\";

        String cascadePath = folder+"lbpcascade_frontalface.xml";
        String imgPath = folder +"lena.png";

        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load(cascadePath);
        Mat image = Imgcodecs.imread(imgPath);

        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.printf("Detected %s faces%n", faceDetections.toArray().length);

        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        // Save the visualized detection.
        String filename = folder+"faceDetection.png";
        System.out.printf("Writing %s%n", filename);
        Imgcodecs.imwrite(filename, image);
    }
}


public class FaceDetection {
    public static void main(String[] args) {
        System.out.println("Starting face detection...");
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new DetectFaceDemo().run();
    }
}
