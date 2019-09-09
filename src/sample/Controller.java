package sample;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static javafx.application.Application.launch;

public class Controller {
    public static void main(String[] args) {
        launch(args);
    }
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
}
