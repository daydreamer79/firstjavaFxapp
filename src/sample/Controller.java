package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static javafx.application.Application.launch;

public class Controller {
    public static void main(String[] args) {
        launch(args);
    }
    //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
   private VideoCapture capture = new VideoCapture();

    @FXML
    private Button button;
    @FXML
    private ImageView currentFrame;

    @FXML
    protected void startCamera(ActionEvent event) {

        private VideoCapture capture = new VideoCapture();
        Runnable frameGrabber = new Runnable() {};
        this.timer =Executors.newSingleThreadScheduledExecutor();
            this.timer.scheduleAtFixedRate(frameGrabber,0,33,TimeUnit.MILLISECONDS);

            if(this.capture.isOpened())

            {
                System.out.println(" Capture opened");
            }

            //this.capture.release(); if you want to release the capture  before the destructor is called
            Mat frame = new Mat();
            this.capture.read(frame);
            Imgproc.cvtColor(frame,frame,Imgproc.COLOR_BGR2GRAY);
            MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png",frame,buffer);
        new Image(new ByteArrayInputStream(buffer.toArray()));
            Image imageToShow = grabFrame();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentFrame.setImage(imageToShow);
            }
        }

    }
}
