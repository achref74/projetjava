package edu.esprit.tests;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.util.Timer;
import java.util.TimerTask;

public class test extends Application {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private ImageView imageView = new ImageView();

    @Override
    public void start(Stage primaryStage) {
        VideoCapture capture = new VideoCapture(0); // 0 is the ID of the built-in camera, change it if necessary

        Timer timer = new Timer();
        TimerTask frameGrabber = new TimerTask() {
            @Override
            public void run() {
                Mat frame = new Mat();
                if (capture.read(frame)) {
                    Image imageToShow = SwingFXUtils.toFXImage(null, null);
                    imageView.setImage(imageToShow);
                }
            }
        };

        timer.schedule(frameGrabber, 0, 33); // Schedule the task to run every 33 milliseconds

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Camera Input Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Convert a Mat object (OpenCV) to a BufferedImage (Java Swing) for display
    // This method is necessary for the conversion


    public static void main(String[] args) {
        launch(args);
    }
}
