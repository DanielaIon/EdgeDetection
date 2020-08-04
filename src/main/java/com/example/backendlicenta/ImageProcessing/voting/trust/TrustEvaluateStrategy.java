package com.example.backendlicenta.ImageProcessing.voting.trust;

import org.opencv.core.Mat;

public interface TrustEvaluateStrategy {
    Double evaluate(Double crtTrust, boolean[][] vote, Mat result);
}
