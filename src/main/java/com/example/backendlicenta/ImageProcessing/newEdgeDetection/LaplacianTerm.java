package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

import com.example.backendlicenta.ImageProcessing.edgeDetection.EdgeDetection;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class LaplacianTerm {

    public Mat apply(Mat image) {
        GaussianFilter gaussianFilter = new GaussianFilter(45,45,0);
        NonMaximumSupression nonMaximumSupression = new NonMaximumSupression();
        GradientComponents gradientComponents = Gradient.apply(image);

        Mat outputImage = new Mat();
        Imgproc.cvtColor(image, outputImage, Imgproc.COLOR_RGB2GRAY, 1);

        Mat NMS = nonMaximumSupression.apply(image);
        Mat LoG = EdgeDetection.applyLaplaceDiag(image);
        Mat GRADIENT_PHASE = gradientComponents.getPhase();

        int width = NMS.width();
        int height = NMS.height();

        double[] blackPixel = {0.0};
        double[] whitePixel = {255.0};


        //no zero crossing => discarded
        //zero crossing from LOG
        for (int crtY = 1; crtY < height - 1; crtY++) {
            for (int crtX = 1; crtX < width - 1; crtX++) {

                if (LoG.get(crtY, crtX)[0] < 127.0) {
                    outputImage.put(crtY, crtX, NMS.get(crtY, crtX)[0]);
                } else {
                    outputImage.put(crtY, crtX, blackPixel);
                }
            }
        }

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }
}
