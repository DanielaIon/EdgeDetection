package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class EdgeDetection {

    public static Mat applyCanny(Mat image, int threshold1, int threshold2) {
        Mat outputImage = new Mat();

        Imgproc.Canny(image, outputImage, threshold1, threshold2,3);

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }

    public static Mat applyLaplace(Mat image){
        int[][] laplace = {{0, -1, 0}, {-1, 4, -1},{0, -1, 0}};

        return applySingleMatrix(image,laplace);
    }

    public static Mat applyLaplaceDiag(Mat image){
        int[][] laplaceDiag = {{-1, -1, -1}, {-1, 8, -1},{-1, -1, -1}};

        return applySingleMatrix(image,laplaceDiag);
    }


    public static Mat applySobel(Mat image){
        int[][] gx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] gy = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        return applyMatrix(image,gx,gy,3, 1);
    }

    public static Mat applyPrewitt(Mat image){
        int[][] gx = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
        int[][] gy = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};

        return applyMatrix(image,gx,gy,3, 1);
    }

    public static Mat applySobelFeldman(Mat image){
        int[][] gx = {{-3, 0, 3}, {-10, 0, 10}, {-3, 0, 3}};
        int[][] gy = {{-3, -10, -3}, {0, 0, 0}, {3, 10, 3}};

        return applyMatrix(image,gx,gy,3, 8);
    }

    public static Mat applyScharr(Mat image){
        int[][] gx = {{47, 0, -47}, {162, 0, -162}, {47, 0, -47}};
        int[][] gy = {{47, 162, 47}, {0, 0, 0}, {-47, -162, -47}};

        return applyMatrix(image,gx,gy,3,127);
    }

    public static Mat applyRoberts(Mat image){
        int[][] gx = {{1, 0}, {0, -1}};
        int[][] gy = {{0, -1}, {1, 0}};

        return applyMatrix(image,gx,gy,2,1);
    }

    public static Mat applyRobinson (Mat image){
        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{-1, 0, 1}, {-2, 0, 2},{-1, 0, 1}});//N
        list.add(new int[][]{{0, 1, 2}, {-1, 0, 1},{-2, -1, 0}});//NW
        list.add(new int[][]{{1, 2, 1}, {0, 0, 0},{-1, -2, -1}});//W
        list.add(new int[][]{{2, 1, 0}, {1, 0, -1},{0, -1, -2}});//SW
        list.add(new int[][]{{1, 0, -1}, {2, 0, -2},{1, 0, -1}});//S
        list.add(new int[][]{{0, -1, -2}, {1, 0, -1},{2, 1, 0}});//SE
        list.add(new int[][]{{-1, -2, -1}, {0, 0, 0},{1, 2, 1}}); //E
        list.add(new int[][]{{-2, -1, 0}, {-1, 0, 1},{0, 1, 2}}); //NE

        return applyMultiDirection(image,list,3);
    }

    public static Mat applyKirsch (Mat image){
        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{-3, -3, 5}, {-3, 0, 5},{-3, -3, 5}});//N
        list.add(new int[][]{{-3, 5, 5}, {-3, 0, 5},{-3, -3, -3}});//NW
        list.add(new int[][]{{5, 5, 5}, {-3, 0, -3},{-3, -3, -3}});//W
        list.add(new int[][]{{5, 5, -3}, {5, 0, -3},{-3, -3, -3}});//SW
        list.add(new int[][]{{5, -3, -3}, {5, 0, -3},{5, -3, -3}});//S
        list.add(new int[][]{{-3, -3, -3}, {5, 0, -3},{5, 5, -3}});//SE
        list.add(new int[][]{{-3, -3, -3}, {-3, 0, -3},{5, 5, 5}}); //E
        list.add(new int[][]{{-3, -3, -3}, {-3, 0, 5},{-3, 5, 5}}); //NE

        return applyMultiDirection(image,list,3);
    }

    private static Mat applyMatrix(Mat image, int[][] gx, int[][] gy, int size, int threshold) {
        Mat grayscaleImage = new Mat();

        Mat outputImage;

        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_RGB2GRAY,1);

        outputImage = grayscaleImage.clone();

        double xComputedValue, yComputedValue, finalValue;

        for (int row = 1; row <grayscaleImage.rows() - size + 1; row++) {
            for (int col = 1; col <grayscaleImage.cols() - size + 1 ; col++) {
                xComputedValue = yComputedValue = 0;
                for (int i = 0; i <size ; i++) {
                    for (int j = 0; j <size ; j++) {
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


    private static Mat applySingleMatrix(Mat image, int[][] matrix) {
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

    private static Mat applyMultiDirection(Mat image, List<int[][]> matrixes, int size) {
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
