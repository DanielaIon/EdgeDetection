package com.example.backendlicenta.ImageProcessing.voting.trust;

import org.opencv.core.Mat;

public interface TrustReevaluationStrategy {
    Double reevaluate(Double crtTrust, boolean[][] vote, Mat result);
}
