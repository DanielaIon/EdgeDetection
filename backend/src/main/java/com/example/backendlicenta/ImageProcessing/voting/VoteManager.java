package com.example.backendlicenta.ImageProcessing.voting;

import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdStrategy;
import com.example.backendlicenta.ImageProcessing.voting.trust.TrustEvaluateStrategy;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VoteManager {

    private Map<Voter, Double> votersRegistry;

    private BinarizationThresholdStrategy binarizationThreshold;

    private Double trustThreshold;

    private TrustEvaluateStrategy trustEvaluator;

    public VoteManager(List<Voter> voters, TrustEvaluateStrategy trustEvaluator, Double trustThreshold, BinarizationThresholdStrategy binarizationThreshold) {
        this.votersRegistry = voters.stream().collect(Collectors.toMap(v -> v, v -> 1.0));
        this.trustEvaluator = trustEvaluator;
        this.trustThreshold = trustThreshold;
        this.binarizationThreshold = binarizationThreshold;
    }

    public Mat elect(Mat image) {
        double[][] votes = new double[image.height()][image.width()];

        // calculate total trust
        double total = votersRegistry.values().stream().mapToDouble(d -> d).sum();

        // voters cast their vote
        Map<Voter, boolean[][]> voteMap = votersRegistry.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getKey().vote(image, binarizationThreshold)));

        // count votes
        for (Voter key : votersRegistry.keySet()) {
            boolean[][] vote = voteMap.get(key);
            for (int i = 0; i < vote.length; i++) {
                for (int j = 0; j < vote[i].length; j++) {
                    if (vote[i][j]) {
                        votes[i][j] += votersRegistry.get(key);
                    }
                }
            }
        }

        // make decision
        Mat output = new Mat(image.height(), image.width(), CvType.CV_8U, Scalar.all(0));
        for (int i = 0; i < votes.length; i++) {
            for (int j = 0; j < votes[i].length; j++) {
                if (votes[i][j] / total >= trustThreshold) {
                    output.put(i, j, 255);
                }
            }
        }

        // reevaluate voters trust
        voteMap.forEach((voter, vote) ->
            votersRegistry.put(voter, trustEvaluator.evaluate(votersRegistry.get(voter), vote, output))
        );

        return output;
    }

    

}
