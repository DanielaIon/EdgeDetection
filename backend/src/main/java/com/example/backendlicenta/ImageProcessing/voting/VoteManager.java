package com.example.backendlicenta.ImageProcessing.voting;

import com.example.backendlicenta.ImageProcessing.voting.binarization.BinarizationThresholdStrategy;
import com.example.backendlicenta.ImageProcessing.voting.trust.TrustReevaluationStrategy;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VoteManager {

    private Map<String, Voter> votersRegistry;

    private Map<String, TrustReevaluationStrategy> trustReevaluationRegistry;

    private Map<String, BinarizationThresholdStrategy> binarizationThresholdRegistry;

    public VoteManager(Map<String, Voter> votersRegistry,
                       Map<String, TrustReevaluationStrategy> trustReevaluationRegistry,
                       Map<String, BinarizationThresholdStrategy> binarizationThresholdRegistry) {
        this.votersRegistry = votersRegistry;
        this.trustReevaluationRegistry = trustReevaluationRegistry;
        this.binarizationThresholdRegistry = binarizationThresholdRegistry;
    }

    private void validateVoterTrust(Map<String, Double> voterTrust) {
        List<String> aux = voterTrust.keySet().stream()
                .filter(voterName -> !votersRegistry.containsKey(voterName))
                .collect(Collectors.toList());

        if (aux.size() > 0) {
            throw new IllegalArgumentException("No voters with names: " + Arrays.toString(aux.toArray()));
        }
    }

    private void validateElectionParameters(Map<String, Double> voterTrust, String trustReevaluation, String binarizationThreshold) {
        List<String> aux = voterTrust.keySet().stream()
                .filter(voterName -> !votersRegistry.containsKey(voterName))
                .collect(Collectors.toList());

        if (aux.size() > 0) {
            throw new IllegalArgumentException("No voters with names: " + Arrays.toString(aux.toArray()));
        }

        if (!trustReevaluationRegistry.containsKey(trustReevaluation)) {
            throw new IllegalArgumentException("No trust evaluation strategy with name: " + trustReevaluation);
        }

        if (!binarizationThresholdRegistry.containsKey(binarizationThreshold)) {
            throw new IllegalArgumentException("No binarization threshold finding strategy with name: " + binarizationThreshold);
        }
    }

    public VoteResult elect(Mat image, Map<String, Double> voterTrust, String trustReevaluation, String binarizationThreshold, Double trustThreshold) {
        // check input parameters validity
        validateElectionParameters(voterTrust, trustReevaluation, binarizationThreshold);

        double[][] votes = new double[image.height()][image.width()];

        // calculate total trust
        double total = voterTrust.values().stream().mapToDouble(d -> d).sum();

        // voters cast their vote
        Map<String, boolean[][]> voteMap = voterTrust.keySet().stream()
                .collect(Collectors.toMap(
                        voterName -> voterName,
                        voterName -> votersRegistry.get(voterName).vote(image, binarizationThresholdRegistry.get(binarizationThreshold))
                ));

        // count votes
        for (String key : voterTrust.keySet()) {
            boolean[][] vote = voteMap.get(key);
            for (int i = 0; i < vote.length; i++) {
                for (int j = 0; j < vote[i].length; j++) {
                    if (vote[i][j]) {
                        votes[i][j] += voterTrust.get(key);
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
        voteMap.forEach((voterName, vote) ->
                voterTrust.put(voterName, trustReevaluationRegistry.get(trustReevaluation).reevaluate(voterTrust.get(voterName), vote, output))
        );

        return new VoteResult(output, voterTrust);
    }

    public List<String> getVoterNames() {
        return new ArrayList<>(votersRegistry.keySet());
    }

    public List<String> getTrustReevaluationNames() {
        return new ArrayList<>(trustReevaluationRegistry.keySet());
    }

    public List<String> getBinarizationThresholdNames() {
        return new ArrayList<>(binarizationThresholdRegistry.keySet());
    }

    public void setState(String state) {

    }

}
