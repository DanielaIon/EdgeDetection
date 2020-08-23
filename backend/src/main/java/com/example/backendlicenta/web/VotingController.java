package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.voting.VoteResult;
import com.example.backendlicenta.service.VotingService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/voting")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @CrossOrigin
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String voteEdges(@RequestPart("img") MultipartFile file,
                            @RequestParam("voters") String votersString,
                            @RequestParam("binarizationThreshold") Double binarizationThreshold,
                            @RequestParam("trustThreshold") Double trustThreshold,
                            @RequestParam("trustReevaluation") Double trustAdjustPercent) {
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

            VoteResult result = votingService.elect(image, voters, binarizationThreshold, trustThreshold, trustAdjustPercent);

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
        return votingService.getVoterNames();
    }

}
