package com.example.backendlicenta.ImageProcessing.edgeDetection;

import org.opencv.core.Mat;

public interface IEdgeDetection {

    Mat apply(Mat image);

}
