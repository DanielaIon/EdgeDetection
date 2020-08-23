package com.example.backendlicenta.ImageProcessing.voting;

import com.example.backendlicenta.ImageProcessing.edgeDetection.IEdgeDetection;
import org.opencv.core.Mat;

public class Voter {

    private IEdgeDetection edgeDetection;

    public Voter(IEdgeDetection edgeDetection) {
        this.edgeDetection = edgeDetection;
    }

    public boolean[][] vote(Mat image, Double binarizationThreshold) {
        Mat output = edgeDetection.apply(image);
        return apply(output, binarizationThreshold);
    }

    private boolean[][] apply(Mat image, Double binarizationThreshold) {
        boolean[][] votes = new boolean[image.height()][image.width()];

        for (int row = 0; row < image.height(); row++) {
            for (int column = 0; column < image.width(); column++) {
                if (image.get(row, column)[0] >= binarizationThreshold) {
                    votes[row][column] = true;
                }
            }
        }

        return votes;
    }

}
