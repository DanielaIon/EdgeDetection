package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.edgeDetection.ConvolutionFactory;
import com.example.backendlicenta.ImageProcessing.voting.VoteManager;
import com.example.backendlicenta.ImageProcessing.voting.VoteResult;
import com.example.backendlicenta.ImageProcessing.voting.Voter;
import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdFixed;
import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdStrategy;
import com.example.backendlicenta.ImageProcessing.voting.trust.TrustReevaluationPercent;
import com.example.backendlicenta.ImageProcessing.voting.trust.TrustReevaluationStrategy;
import com.example.backendlicenta.web.dto.voteDto;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/voting")
public class VotingController {

    private VoteManager voteManager;

    public VotingController() {
        this.voteManager = new VoteManager(
                new HashMap<String, Voter>() {
                    {
                        put("Laplace", new Voter(ConvolutionFactory.getInstance().laplace()));
                        put("Laplace with diagonals", new Voter(ConvolutionFactory.getInstance().laplaceDiag()));
                        put("Sobel", new Voter(ConvolutionFactory.getInstance().sobel()));
                        put("Sobel-Feldman", new Voter(ConvolutionFactory.getInstance().sobelFeldman()));
                        put("Scharr", new Voter(ConvolutionFactory.getInstance().scharr()));
                        put("Prewitt", new Voter(ConvolutionFactory.getInstance().prewitt()));
                        put("Roberts", new Voter(ConvolutionFactory.getInstance().roberts()));
                    }
                },
                new HashMap<String, TrustReevaluationStrategy>() {
                    {
                        put("Reevaluate by 10%", new TrustReevaluationPercent(0.10));
                        put("Reevaluate by 15%", new TrustReevaluationPercent(0.15));
                        put("Reevaluate by 20%", new TrustReevaluationPercent(0.20));
                        put("Reevaluate by 25%", new TrustReevaluationPercent(0.25));
                        put("Reevaluate by 30%", new TrustReevaluationPercent(0.30));
                        put("Reevaluate by 35%", new TrustReevaluationPercent(0.35));
                    }
                },
                new HashMap<String, BinarizationThresholdStrategy>() {
                    {
                        put("Fixed to 64", new BinarizationThresholdFixed(64.0));
                        put("Fixed to 96", new BinarizationThresholdFixed(96.0));
                        put("Fixed to 128", new BinarizationThresholdFixed(128.0));
                        put("Fixed to 160", new BinarizationThresholdFixed(160.0));
                        put("Fixed to 192", new BinarizationThresholdFixed(192.0));
                    }
                });
    }

    @CrossOrigin
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String voteEdges(@RequestPart("img") MultipartFile file,
                            @RequestParam("voters") String votersString,
                            @RequestParam("trustReevaluation") String trustReevaluation,
                            @RequestParam("binarizationThreshold") String binarizationThreshold,
                            @RequestParam("trustThreshold") Double trustThreshold) {
        try {
            byte[] content = file.getBytes();
            Map<String, Double> voters = Arrays.stream(votersString
                .replace("\"", "")
                .replace("{", "")
                .replace("}", "")
                .split(",")).collect(Collectors.toMap(
                    p -> p.split(":")[0],
                    p -> Double.valueOf(p.split(":")[1]))
                );


            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            VoteResult result = voteManager.elect(image, voters, trustReevaluation, binarizationThreshold, trustThreshold);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", result.getOutput(), mob);
            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/voters")
    public List<String> getVoters() {
        return voteManager.getVoterNames();
    }

    @CrossOrigin
    @GetMapping(value = "/trust-reevaluations")
    public List<String> getTrustReevaluations() {
        return voteManager.getTrustReevaluationNames();
    }

    @CrossOrigin
    @GetMapping(value = "/binarization-threshold")
    public List<String> getBinarizationThreshold() {
        return voteManager.getBinarizationThresholdNames();
    }
}
