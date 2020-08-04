package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class GaussianFilter {

    int width;
    int height;
    int sigma;

    public GaussianFilter(int width, int height, int sigma){
        this.width = width;
        this.height = height;
        this.sigma = sigma;
    }
    public Mat apply(Mat image) {
        Mat grayscaleImage = new Mat();
        Mat outputImage = new Mat();

        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_RGB2GRAY,1);

        /*Apply gaussian blur*/
        Imgproc.GaussianBlur(grayscaleImage, outputImage,new Size(width, height), sigma);

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }
}
