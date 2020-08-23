package com.example.backendlicenta.service;

import com.example.backendlicenta.ImageProcessing.edgeDetection.ConvolutionFactory;
import com.example.backendlicenta.ImageProcessing.voting.VoteResult;
import com.example.backendlicenta.ImageProcessing.voting.Voter;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VotingService {

    private final Map<String, Voter> votersRegistry;

    public VotingService() {
        this.votersRegistry = new HashMap<String, Voter>() {
            {
                put("Laplace", new Voter(ConvolutionFactory.getInstance().laplace()));
                put("Laplace with diagonals", new Voter(ConvolutionFactory.getInstance().laplaceDiag()));
                put("Sobel", new Voter(ConvolutionFactory.getInstance().sobel()));
                put("Sobel-Feldman", new Voter(ConvolutionFactory.getInstance().sobelFeldman()));
                put("Scharr", new Voter(ConvolutionFactory.getInstance().scharr()));
                put("Prewitt", new Voter(ConvolutionFactory.getInstance().prewitt()));
                put("Roberts", new Voter(ConvolutionFactory.getInstance().roberts()));
            }
        };
    }

    private void validateVoterTrust(Map<String, Double> voterTrust) {
        List<String> aux = voterTrust.keySet().stream()
                .filter(voterName -> !votersRegistry.containsKey(voterName))
                .collect(Collectors.toList());

        if (aux.size() > 0) {
            throw new IllegalArgumentException("No voters with names: " + Arrays.toString(aux.toArray()));
        }
    }

    private void validateElectionParameters(Map<String, Double> voterTrust, Double binarizationThreshold) {
        List<String> aux = voterTrust.keySet().stream()
                .filter(voterName -> !votersRegistry.containsKey(voterName))
                .collect(Collectors.toList());

        if (aux.size() > 0) {
            throw new IllegalArgumentException("No voters with names: " + Arrays.toString(aux.toArray()));
        }

        if (binarizationThreshold == null || 255 < binarizationThreshold || binarizationThreshold < 0) {
            throw new IllegalArgumentException("Binarization threshold must be a value between 0 and 255");
        }
    }

    public VoteResult elect(Mat image, Map<String, Double> voterTrust, Double binarizationThreshold, Double trustThreshold, Double trustAdjustPercent) {
        // check input parameters validity
        validateElectionParameters(voterTrust, binarizationThreshold);

        double[][] votes = new double[image.height()][image.width()];

        // calculate total trust
        double total = voterTrust.values().stream().mapToDouble(d -> d).sum();

        // voters cast their vote
        Map<String, boolean[][]> voteMap = voterTrust.keySet().stream()
                .collect(Collectors.toMap(
                        voterName -> voterName,
                        voterName -> votersRegistry.get(voterName).vote(image, binarizationThreshold)
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
                voterTrust.put(voterName, reevaluateTrust(voterTrust.get(voterName), vote, output, binarizationThreshold, trustAdjustPercent))
        );

        return new VoteResult(output, voterTrust);
    }

    public List<String> getVoterNames() {
        return new ArrayList<>(votersRegistry.keySet());
    }

    private Double reevaluateTrust(Double crtTrust, boolean[][] vote, Mat result, Double binarizationThreshold, Double adjustPercent) {
        int errors = 0;
        int corrects = 0;

        for (int i = 0; i < vote.length; i++) {
            for (int j = 0; j < vote[i].length; j++) {
                boolean correctVote = result.get(i, j)[0] >= binarizationThreshold;

                if (!vote[i][j] && !correctVote) {
                    continue;
                }

                if (vote[i][j] == correctVote) {
                    corrects += 1;
                } else {
                    errors += 1;
                }
            }
        }

        double change = crtTrust * adjustPercent;
        if (corrects > errors) {
            return crtTrust + change;
        } else {
            return crtTrust - change;
        }
    }


}
