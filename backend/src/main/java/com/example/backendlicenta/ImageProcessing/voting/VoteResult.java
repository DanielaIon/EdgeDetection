package com.example.backendlicenta.ImageProcessing.voting;

import org.opencv.core.Mat;

import java.util.Map;

public class VoteResult {

    private Mat output;

    private Map<String, Double> trust;

    public VoteResult(Mat output, Map<String, Double> trust) {
        this.output = output;
        this.trust = trust;
    }

    public Mat getOutput() {
        return output;
    }

    public Map<String, Double> getTrust() {
        return trust;
    }

}
