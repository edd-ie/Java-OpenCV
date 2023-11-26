module com.opencv.computervision {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;


    opens com.opencv.computervision to javafx.fxml;
    exports com.opencv.computervision;
}