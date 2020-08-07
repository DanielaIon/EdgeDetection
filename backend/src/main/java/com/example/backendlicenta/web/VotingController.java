package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.edgeDetection.ConvolutionFactory;
import com.example.backendlicenta.ImageProcessing.edgeDetection.EdgeDetection;
import com.example.backendlicenta.ImageProcessing.voting.VoteManager;
import com.example.backendlicenta.ImageProcessing.voting.Voter;
import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdFixed;
import com.example.backendlicenta.ImageProcessing.voting.trust.TrustEvaluationPercent;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/voting")
public class VotingController {

    private VoteManager voteManager;

    public VotingController() {
        this.voteManager = new VoteManager(
                Arrays.asList(
                    new Voter(ConvolutionFactory.getInstance().laplace()),
                    new Voter(ConvolutionFactory.getInstance().laplaceDiag()),
                    new Voter(ConvolutionFactory.getInstance().sobel()),
                    new Voter(ConvolutionFactory.getInstance().sobelFeldman()),
                    new Voter(ConvolutionFactory.getInstance().scharr()),
                    new Voter(ConvolutionFactory.getInstance().prewitt()),
                    new Voter(ConvolutionFactory.getInstance().roberts())),
                new TrustEvaluationPercent(0.1),
                0.5,
                new BinarizationThresholdFixed(128.0));
    }

    @CrossOrigin
    @PostMapping(value = "/", consumes = { "multipart/form-data" })
    public String voteEdges(@RequestPart("img") MultipartFile file) {
        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            image = voteManager.elect(image);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", image, mob);
            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
