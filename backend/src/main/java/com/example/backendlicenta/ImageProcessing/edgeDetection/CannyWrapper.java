package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CannyWrapper implements IEdgeDetection {

    private int threshold1, threshold2;

    public CannyWrapper(int threshold1, int threshold2) {
        this.threshold1 = threshold1;
        this.threshold2 = threshold2;
    }

    public Mat apply(Mat image) {
        Mat outputImage = new Mat();

        Imgproc.Canny(image, outputImage, threshold1, threshold2,3);

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }

}
