package com.example.backendlicenta.ImageProcessing.voting.trust;

import org.opencv.core.Mat;

public class TrustReevaluationPercent implements TrustReevaluationStrategy {

    private final double percent;

    public TrustReevaluationPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public Double reevaluate(Double crtTrust, boolean[][] vote, Mat result) {
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
