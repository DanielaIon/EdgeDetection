package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Gradient {
    private static int[][] gx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private static int[][] gy = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};


    public static GradientComponents apply(Mat image) {
        Mat grayscaleImage = new Mat();

        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_RGB2GRAY,1);

        Mat magnitude = grayscaleImage.clone();
        Mat phase = grayscaleImage.clone();

        double xComputedValue, yComputedValue, magnitudeValue, phaseValue;

        for (int row = 1; row <grayscaleImage.rows() - 2; row++) {
            for (int col = 1; col <grayscaleImage.cols() - 2; col++) {
                xComputedValue = yComputedValue = 0;
                for (int i = 0; i < 3 ; i++) {
                    for (int j = 0; j < 3 ; j++) {
                        xComputedValue += grayscaleImage.get(row + i - 1,col + j - 1)[0] * gx[i][j];
                        yComputedValue += grayscaleImage.get(row + i - 1,col + j - 1)[0] * gy[i][j];
                    }
                }
                magnitudeValue = Math.sqrt((xComputedValue * xComputedValue) + (yComputedValue * yComputedValue));
                magnitude.put(row, col, magnitudeValue);

                phaseValue = Math.atan2(yComputedValue,xComputedValue);
                phase.put(row, col, phaseValue);
            }
        }

        return new GradientComponents(phase, magnitude);
    }
}
