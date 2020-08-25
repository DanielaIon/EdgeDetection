package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.edgeDetection.EdgeDetection;
import com.example.backendlicenta.ImageProcessing.newEdgeDetection.*;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@RestController
@RequestMapping("/new-edge-detection-algorithm")
public class NewEdgeDetectionAlgorithmController {

    @CrossOrigin
    @PostMapping(value = "/gaussian-filter", consumes = { "multipart/form-data" })
    public String gaussianFilter(@RequestPart("img") MultipartFile file) {
        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            GaussianFilter gaussianFilter = new GaussianFilter(45,45,0);

            Imgcodecs.imencode(".png", gaussianFilter.apply(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/gradient", consumes = { "multipart/form-data" })
    public String gradient(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            GaussianFilter gaussianFilter = new GaussianFilter(45,45,0);
            Imgcodecs.imencode(".png",Gradient.apply(image).getMagnitude(), mob);
            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/nms", consumes = { "multipart/form-data" })
    public String nms(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            GaussianFilter gaussianFilter = new GaussianFilter(45,45,0);
            NonMaximumSupression nms = new NonMaximumSupression();
            Imgcodecs.imencode(".png", nms.apply(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/laplacian", consumes = { "multipart/form-data" })
    public String laplacian(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            LaplacianTerm laplacianTerm = new LaplacianTerm();
            Imgcodecs.imencode(".png", laplacianTerm.apply(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @CrossOrigin
    @PostMapping(value = "/edge-following", consumes = { "multipart/form-data" })
    public String edgeFollowing(@RequestPart("img") MultipartFile file) {

        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            EdgeFollowing edgeFollowing = new EdgeFollowing();
            Imgcodecs.imencode(".png", edgeFollowing.apply(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/final-result", consumes = { "multipart/form-data" })
    public String finalResult(@RequestPart("img") MultipartFile file) {
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
}
