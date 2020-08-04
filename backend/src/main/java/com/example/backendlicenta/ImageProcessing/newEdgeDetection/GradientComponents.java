package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

import org.opencv.core.Mat;

public class GradientComponents {
    private Mat phase;
    private Mat magnitude;

    public GradientComponents(Mat phase, Mat magnitude) {
        this.phase = phase;
        this.magnitude = magnitude;
    }

    public Mat getPhase() {
        return phase;
    }


    public Mat getMagnitude() {
        return magnitude;
    }

}
