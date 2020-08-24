package com.example.backendlicenta.web;

import com.example.backendlicenta.ImageProcessing.edgeDetection.ConvolutionFactory;
import com.example.backendlicenta.ImageProcessing.edgeDetection.IEdgeDetection;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merge")
public class MergeController {

    private Map<String, IEdgeDetection> edgeDetectionMap;

    public MergeController() {
        this.edgeDetectionMap = new HashMap<>();

        this.edgeDetectionMap.put("Laplacian", ConvolutionFactory.getInstance().laplace());
        this.edgeDetectionMap.put("Robinson", ConvolutionFactory.getInstance().robinson());
        this.edgeDetectionMap.put("Kirsch", ConvolutionFactory.getInstance().kirsch());
        this.edgeDetectionMap.put("Laplacian with diagonals", ConvolutionFactory.getInstance().laplaceDiag());
        this.edgeDetectionMap.put("Prewitt", ConvolutionFactory.getInstance().prewitt());
        this.edgeDetectionMap.put("Roberts", ConvolutionFactory.getInstance().roberts());
        this.edgeDetectionMap.put("Scharr", ConvolutionFactory.getInstance().scharr());
        this.edgeDetectionMap.put("Sobel", ConvolutionFactory.getInstance().sobel());
        this.edgeDetectionMap.put("Sobel-Feldman", ConvolutionFactory.getInstance().sobelFeldman());
    }

    @CrossOrigin
    @GetMapping(value = "")
    public List<String> getOptions() {
        return new ArrayList<>(edgeDetectionMap.keySet());
    }

    @CrossOrigin
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String merge(@RequestPart("img") MultipartFile file, @RequestParam("options") String optionList) {
        try {
            byte [] content = file.getBytes();
            List<String> options = Arrays.asList(optionList
                    .replace("\"", "")
                    .replace("]", "")
                    .replace("[", "")
                    .split(","));

            //Citeste imaginea
            Mat image = Imgcodecs.imdecode(new MatOfByte(content), -1);

            double total = options.size();

            if (options.stream().anyMatch(op -> !edgeDetectionMap.containsKey(op))
                    || options.stream().distinct().count() != total) {
                return null;
            }

            List<Mat> results = options.stream()
                    .map(op -> edgeDetectionMap.get(op))
                    .map(ed -> ed.apply(image))
                    .collect(Collectors.toList());

            Mat output = new Mat();
            Imgproc.cvtColor(image, output, Imgproc.COLOR_RGB2GRAY,1);

            int height = output.height();
            int width = output.width();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int row = i, col = j;

                    output.put(row, col, results.stream()
                            .map(img -> img.get(row, col)[0] / total)
                            .mapToDouble(Double::doubleValue)
                            .sum());
                }
            }

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".png", output, mob);
            return DatatypeConverter.printBase64Binary(mob.toArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
