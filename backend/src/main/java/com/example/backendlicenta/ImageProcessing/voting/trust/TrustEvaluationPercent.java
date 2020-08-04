package com.example.backendlicenta.ImageProcessing.voting.trust;

import org.opencv.core.Mat;

public class TrustEvaluationPercent implements TrustEvaluateStrategy {

    private final double percent;

    public TrustEvaluationPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public Double evaluate(Double crtTrust, boolean[][] vote, Mat result) {
        int errors = 0;
        int corrects = 0;

        for (int i = 0; i < vote.length; i++) {
            for (int j = 0; j < vote[i].length; j++) {
                boolean correctVote = result.get(i, j)[0] > 128;

                if (vote[i][j] == correctVote) {
                    corrects += 1;
                } else {
                    errors += 1;
                }
            }
        }

        double change = crtTrust * percent;
        if (corrects > errors) {
            return crtTrust + change;
        } else {
            return crtTrust - change;
        }
    }
}
