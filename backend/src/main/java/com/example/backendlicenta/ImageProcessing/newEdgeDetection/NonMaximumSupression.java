package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class NonMaximumSupression {

    public NonMaximumSupression() {

    }

    private boolean isBiggerThanNeighbours(int crtX, int crtY, double theta, Mat mag) {
        int ax, ay;


        theta = theta < 0 ? (theta + 180) : theta;

        if ((theta >= 0) && (theta < 45)) {
            ax = 1;
            ay = 0;
        } else if ((theta >= 45) && (theta < 90)) {
            ax = 1;
            ay = 1;
        } else if ((theta >= 90) && (theta < 135)) {
            ax = 0;
            ay = 1;
        } else if ((theta >= 135) && (theta <= 180)) {
            ax = -1;
            ay = 1;
        } else {
            throw new RuntimeException("theta (" + theta + ") not good");
        }

        int nextY = crtY + ay;
        int nextX = crtX + ax;

        int prevY = crtY - ay;
        int prevX = crtX - ax;

        return mag.get(crtY, crtX)[0] > mag.get(nextY, nextX)[0] && mag.get(crtY, crtX)[0] > mag.get(prevY, prevX)[0];
    }

    public Mat apply(Mat image) {
        Mat outputImage = new Mat();

        /*Convert the image to grayscale*/
        Imgproc.cvtColor(image, outputImage, Imgproc.COLOR_RGB2GRAY, 1);

        int width = image.width();
        int height = image.height();

        double[] blackPixel = {0.0};
        double[] whitePixel = {255.0};
        double theta;

        GradientComponents gradientComponents = Gradient.apply(image);
        Mat mag = gradientComponents.getMagnitude();

        double max = Core.minMaxLoc(gradientComponents.getMagnitude()).maxVal;
        MatOfDouble mean = new MatOfDouble();
        MatOfDouble std = new MatOfDouble();
        Core.meanStdDev(gradientComponents.getMagnitude(), mean, std);

        double thresholdUp = mean.toArray()[0] + 2 * std.toArray()[0];
        double thresholdDown = thresholdUp/2;

        for (int crtY = 1; crtY < height - 1; crtY++) {
            for (int crtX = 1; crtX < width - 1; crtX++) {

                theta = Math.toDegrees(gradientComponents.getPhase().get(crtY, crtX)[0]);
                theta = (360 - theta) % 180;

                if (isBiggerThanNeighbours(crtX, crtY, theta, mag) && isBiggerThanNeighbours(crtX, crtY, (theta + 45) % 180, mag)) {
                    if (gradientComponents.getMagnitude().get(crtY, crtX)[0] < thresholdDown) {
                        outputImage.put(crtY, crtX, blackPixel);
                    } else if (gradientComponents.getMagnitude().get(crtY, crtX)[0] > thresholdUp) {
                        outputImage.put(crtY, crtX, whitePixel);
                    } else {
                        outputImage.put(crtY, crtX, 255.0 * gradientComponents.getMagnitude().get(crtY, crtX)[0] / max);
                    }
                } else {
                    outputImage.put(crtY, crtX, blackPixel);
                }
            }
        }

        Core.multiply(outputImage,new Scalar(255.0/max ),outputImage);

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB, 3);
        return outputImage;
    }
}
