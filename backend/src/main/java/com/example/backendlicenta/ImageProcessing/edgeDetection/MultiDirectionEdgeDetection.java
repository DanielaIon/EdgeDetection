package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MultiDirectionEdgeDetection implements IEdgeDetection {

    private List<int[][]> matrixes;

    private int size;

    private int threshold;

    public MultiDirectionEdgeDetection(List<int[][]> matrixes, int size, int threshold) {
        this.matrixes = matrixes;
        this.size = size;
        this.threshold = threshold;
    }

    @Override
    public Mat apply(Mat image) {
        Mat grayscaleImage = new Mat();
        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_RGB2GRAY,1);


        Mat outputImage = new Mat(grayscaleImage.size(),grayscaleImage.type());
        double finalValue;

        for (int row = 1; row <grayscaleImage.rows() - size + 1; row++) {
            for (int col = 1; col <grayscaleImage.cols() - size + 1; col++) {
                PriorityQueue<Double> list = new PriorityQueue<>(new Comparator<Double>() {
                    @Override
                    public int compare(Double d2, Double d1) {
                        return Double.compare(d1, d2);
                    }
                });
                for (int[][] matrix : matrixes) {
                    double ComputedValue = 0;
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            ComputedValue += grayscaleImage.get(row + i - 1, col + j - 1)[0] * matrix[i][j];
                        }
                    }
                    list.add(ComputedValue);
                }

                finalValue = list.poll();

                outputImage.put(row, col, finalValue);

            }
        }

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);

        return outputImage;
    }
}
