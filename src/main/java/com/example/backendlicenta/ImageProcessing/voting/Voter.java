package com.example.backendlicenta.ImageProcessing.voting;

import com.example.backendlicenta.ImageProcessing.voting.binarization.Binarization;
import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdStrategy;
import org.opencv.core.Mat;

public abstract class Voter {

    public abstract Mat detectEdges(Mat image);

    public boolean[][] vote(Mat image, BinarizationThresholdStrategy thresholdSelection) {
        Mat output = detectEdges(image);
        return Binarization.apply(output, thresholdSelection);
    }
}
