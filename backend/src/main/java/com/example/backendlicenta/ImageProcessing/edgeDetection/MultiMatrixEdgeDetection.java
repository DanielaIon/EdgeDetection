package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MultiMatrixConvolution implements IConvolution {

    private int[][] gx, gy;

    private int size;

    private int threshold;

    public MultiMatrixConvolution(int[][] gx, int[][] gy, int size, int threshold) {
        this.gx = gx;
        this.gy = gy;
        this.size = size;
        this.threshold = threshold;
    }

    @Override
    public Mat apply(Mat image) {
        Mat grayscaleImage = new Mat();

        Mat outputImage;

        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_RGB2GRAY,1);

        outputImage = grayscaleImage.clone();

        double xComputedValue, yComputedValue, finalValue;

        for (int row = 1; row <grayscaleImage.rows() - size + 1; row++) {
            for (int col = 1; col <grayscaleImage.cols() - size + 1 ; col++) {
                xComputedValue = yComputedValue = 0;
                for (int i = 0; i < size ; i++) {
                    for (int j = 0; j < size ; j++) {
                        xComputedValue += grayscaleImage.get(row + i - 1,col + j - 1)[0] * gx[i][j];
                        yComputedValue += grayscaleImage.get(row + i - 1,col + j - 1)[0] * gy[i][j];
                    }
                }
                finalValue = Math.sqrt((xComputedValue * xComputedValue) + (yComputedValue * yComputedValue))  / threshold;
                outputImage.put(row, col, finalValue);

            }
        }

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }
}
