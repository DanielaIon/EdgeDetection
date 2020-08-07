package com.example.backendlicenta.ImageProcessing.voting;

import com.example.backendlicenta.ImageProcessing.edgeDetection.IEdgeDetection;
import com.example.backendlicenta.ImageProcessing.voting.binarization.Binarization;
import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdStrategy;
import org.opencv.core.Mat;

public class Voter {

    private IEdgeDetection edgeDetection;

    public Voter(IEdgeDetection edgeDetection) {
        this.edgeDetection = edgeDetection;
    }

    public boolean[][] vote(Mat image, BinarizationThresholdStrategy thresholdSelection) {
        Mat output = edgeDetection.apply(image);
        return Binarization.apply(output, thresholdSelection);
    }

}
