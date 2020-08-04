package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.edgeDetection.EdgeDetection;
import com.example.backendlicenta.ImageProcessing.newEdgeDetection.*;
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
import java.io.*;
import java.util.List;


@RestController
public class EdgeDetectionController {

    private VoteManager voteManager;

    public EdgeDetectionController() {
        this.voteManager = new VoteManager(
                List.of(
                        new Voter() {
                            @Override
                            public Mat detectEdges(Mat image) {
                                return EdgeDetection.applySobel(image);
                            }
                        },
                        new Voter() {
                            @Override
                            public Mat detectEdges(Mat image) {
                                return EdgeDetection.applySobelFeldman(image);
                            }
                        },
                        new Voter() {
                            @Override
                            public Mat detectEdges(Mat image) {
                                return EdgeDetection.applyPrewitt(image);
                            }
                        },
                        new Voter() {
                            @Override
                            public Mat detectEdges(Mat image) {
                                return EdgeDetection.applyScharr(image);
                            }
                        }
                ),
                new TrustEvaluationPercent(0.1),
                0.5,
                new BinarizationThresholdFixed(128.0));
    }

    @CrossOrigin
    @PostMapping(value = "/test", consumes = { "multipart/form-data" })
    public String test(@RequestPart("img") MultipartFile file) {
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

    @CrossOrigin
    @PostMapping(value = "/sobel", consumes = { "multipart/form-data" })
    public String sobel(@RequestPart("img") MultipartFile file) {
        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applySobel(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/prewitt", consumes = { "multipart/form-data" })
    public String prewitt(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png",EdgeDetection.applyPrewitt(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/roberts", consumes = { "multipart/form-data" })
    public String roberts(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applyRoberts(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/scharr", consumes = { "multipart/form-data" })
    public String scharr(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applyScharr(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @CrossOrigin
    @PostMapping(value = "/laplace", consumes = { "multipart/form-data" })
    public String laplace(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applyLaplace(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/canny", consumes = { "multipart/form-data" })
    public String canny(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applyCanny(image,100,300), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/laplaced", consumes = { "multipart/form-data" })
    public String laplacediag(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png",EdgeDetection.applyLaplaceDiag(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/sobelFeldman", consumes = { "multipart/form-data" })
    public String sobelFeldman(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applySobelFeldman(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
