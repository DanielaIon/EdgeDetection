package com.example.backendlicenta.ImageProcessing.voting.binarization;

import org.opencv.core.Mat;

public class Binarization {

    public static boolean[][] apply(Mat image, BinarizationThresholdStrategy thresholdSelection) {
        boolean[][] votes = new boolean[image.height()][image.width()];

        double threshold = thresholdSelection.selectThreshold(image);

        for (int row = 0; row < image.height(); row++) {
            for (int column = 0; column < image.width(); column++) {
                if (image.get(row, column)[0] >= threshold) {
                    votes[row][column] = true;
                }
            }
        }

        return votes;
    }
}
