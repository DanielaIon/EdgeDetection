package com.example.backendlicenta.ImageProcessing.voting.binarization;

import org.opencv.core.Mat;

public interface BinarizationThresholdStrategy {
    double selectThreshold(Mat image);
}
