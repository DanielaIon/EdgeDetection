package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;

public class ConvolutionFactory {

    private static ConvolutionFactory INSTANCE = null;

    public static ConvolutionFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConvolutionFactory();
        }
        return INSTANCE;
    }

    private ConvolutionFactory() {}

    public IEdgeDetection laplace(){
        return new SingleMatrixEdgeDetection(new int[][]{{0, -1, 0}, {-1, 4, -1},{0, -1, 0}});
    }

    public IEdgeDetection laplaceDiag(){
        return new SingleMatrixEdgeDetection(new int[][]{{-1, -1, -1}, {-1, 8, -1},{-1, -1, -1}});
    }

    public IEdgeDetection sobel(){
        int[][] gx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] gy = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        return new MultiMatrixEdgeDetection(gx,gy,3, 1);
    }

    public IEdgeDetection prewitt(){
        int[][] gx = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
        int[][] gy = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};

        return new MultiMatrixEdgeDetection(gx,gy,3, 1);
    }

    public IEdgeDetection sobelFeldman(){
        int[][] gx = {{-3, 0, 3}, {-10, 0, 10}, {-3, 0, 3}};
        int[][] gy = {{-3, -10, -3}, {0, 0, 0}, {3, 10, 3}};

        return new MultiMatrixEdgeDetection(gx,gy,3, 8);
    }

    public IEdgeDetection scharr(){
        int[][] gx = {{47, 0, -47}, {162, 0, -162}, {47, 0, -47}};
        int[][] gy = {{47, 162, 47}, {0, 0, 0}, {-47, -162, -47}};

        return new MultiMatrixEdgeDetection(gx,gy,3, 127);
    }

    public IEdgeDetection roberts(){
        int[][] gx = {{1, 0}, {0, -1}};
        int[][] gy = {{0, -1}, {1, 0}};

        return new MultiMatrixEdgeDetection(gx,gy,2, 1);
    }

}
