package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.edgeDetection.EdgeDetection;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

@RestController
@RequestMapping("/edge-detection")
public class EdgeDetectionController {

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

    @CrossOrigin
    @PostMapping(value = "/robinson", consumes = { "multipart/form-data" })
    public String robinson(@RequestPart("img") MultipartFile file) {
        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applyRobinson(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/kirsch", consumes = { "multipart/form-data" })
    public String kirsch(@RequestPart("img") MultipartFile file) {
        try {
            byte [] content = file.getBytes();

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", EdgeDetection.applyKirsch(image), mob);

            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
