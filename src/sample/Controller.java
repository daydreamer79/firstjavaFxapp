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
import sun.text.SupplementaryCharacterData;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javafx.application.Application.launch;

public class Controller {
    public static void main(String[] args) {
        launch(args);
    }
    //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


    @FXML
    private Button button;
    @FXML
    private ImageView currentFrame;

    private ScheduledExecutorService timer;
    private VideoCapture capture = new VideoCapture();
    private boolean cameraActive = false;
    private static int cameraId = 0;

    @FXML
    protected void startCamera(ActionEvent event) {

        if (!this.cameraActive) {
            this.capture.open(cameraId);

            if (this.capture.isOpened()) {
                this.cameraActive = true;
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run() {
                        Mat frame = grabFrame();// en waar dit naar verwijst weet ik niet

                        Image imageToShow = Utils.mat2Image(frame);
                        // dit moet je van hun github code improteren
                        updateImageView(currentFrame, imageToShow);
                    }
                };


            }

            this.timer = Executors.newSingleThreadScheduledExecutor();
            this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
            this.button.setText("Stop Camera");
        } else {
            System.err.println("Impossible top open the camera connection");

        }
    }
    else

    {
        this.cameraActive = false;
        this.button.setText("Start Camera");

        this.stopAcquistion();
    }
}
           /* if(this.capture.isOpened())

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
        }*/

    private Mat grabFrame(){
        Mat frame = new Mat();
        if (this.capture.isOpened())
        {
            try {
                this.capture.read(frame);
                if(!frame.empty()){
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                }
            } catch (Exception e){
                System.err.println("Exception durig the image elaboration: "+ e);
            }

        }
        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed()
    {
        this.stopAcquisition();
    }

}