package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class SingleMatrixConvolution implements IConvolution {

    private int[][] matrix;

    public SingleMatrixConvolution(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public Mat apply(Mat image) {
        Mat grayscaleImage = new Mat();

        Mat outputImage ;

        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_RGB2GRAY,1);

        outputImage = grayscaleImage.clone();

        double ComputedValue, finalValue;

        for (int x = 1; x <grayscaleImage.rows() - 2; x++) {
            for (int y = 1; y <grayscaleImage.cols() - 2 ; y++) {

                ComputedValue = 0;
                for (int i = 0; i <3 ; i++) {
                    for (int j = 0; j <3 ; j++) {
                        ComputedValue += grayscaleImage.get(x + i - 1,y + j - 1)[0] * matrix[i][j];
                    }
                }
                finalValue = 128.0 + ComputedValue;
                outputImage.put(x, y, finalValue);

            }
        }

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }

}
