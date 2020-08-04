package com.example.backendlicenta.ImageProcessing.voting.binarization;

import org.opencv.core.Mat;

public class BinarizationThresholdFixed implements BinarizationThresholdStrategy {

    private final double threshold;

    public BinarizationThresholdFixed(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public double selectThreshold(Mat image) {
        return threshold;
    }
}
