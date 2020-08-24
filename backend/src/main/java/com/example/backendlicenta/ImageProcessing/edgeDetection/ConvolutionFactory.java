package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;

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

    public IEdgeDetection robinson (){

        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{-1, 0, 1}, {-2, 0, 2},{-1, 0, 1}});//N
        list.add(new int[][]{{0, 1, 2}, {-1, 0, 1},{-2, -1, 0}});//NW
        list.add(new int[][]{{1, 2, 1}, {0, 0, 0},{-1, -2, -1}});//W
        list.add(new int[][]{{2, 1, 0}, {1, 0, -1},{0, -1, -2}});//SW
        list.add(new int[][]{{1, 0, -1}, {2, 0, -2},{1, 0, -1}});//S
        list.add(new int[][]{{0, -1, -2}, {1, 0, -1},{2, 1, 0}});//SE
        list.add(new int[][]{{-1, -2, -1}, {0, 0, 0},{1, 2, 1}}); //E
        list.add(new int[][]{{-2, -1, 0}, {-1, 0, 1},{0, 1, 2}}); //NE

        return new MultiDirectionEdgeDetection(list,3, 1);
    }

    public IEdgeDetection kirsch (){

        List<int[][]> list = new ArrayList<>();
        list.add(new int[][]{{-3, -3, 5}, {-3, 0, 5},{-3, -3, 5}});//N
        list.add(new int[][]{{-3, 5, 5}, {-3, 0, 5},{-3, -3, -3}});//NW
        list.add(new int[][]{{5, 5, 5}, {-3, 0, -3},{-3, -3, -3}});//W
        list.add(new int[][]{{5, 5, -3}, {5, 0, -3},{-3, -3, -3}});//SW
        list.add(new int[][]{{5, -3, -3}, {5, 0, -3},{5, -3, -3}});//S
        list.add(new int[][]{{-3, -3, -3}, {5, 0, -3},{5, 5, -3}});//SE
        list.add(new int[][]{{-3, -3, -3}, {-3, 0, -3},{5, 5, 5}}); //E
        list.add(new int[][]{{-3, -3, -3}, {-3, 0, 5},{-3, 5, 5}}); //NE

        return new MultiDirectionEdgeDetection(list,3, 1);
    }




}
