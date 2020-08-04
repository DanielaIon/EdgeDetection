package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.*;
import java.util.function.Predicate;

public class EdgeFollowing  {

    public String nextDirection(String direction) {
        switch (direction.toUpperCase()) {
            case "E": return "NE";
            case "NE": return "N";
            case "N": return "NV";
            case "NV": return "V";
            case "V": return "SV";
            case "SV": return "S";
            case "S": return "SE";
            case "SE": return "E";

            default: return "";
        }
    }

    public String prevDirection(String direction) {
        switch (direction.toUpperCase()) {
            case "E": return "SE";
            case "NE": return "E";
            case "N": return "NE";
            case "NV": return "N";
            case "V": return "NV";
            case "SV": return "V";
            case "S": return "SV";
            case "SE": return "S";

            default: return "";
        }
    }

    public int xDirectionModifier(String direction) {
        if (direction.toUpperCase().contains("E")) {
            return 1;
        }

        if (direction.toUpperCase().contains("V")) {
            return -1;
        }

        return 0;
    }

    public int yDirectionModifier(String direction) {
        if (direction.toUpperCase().contains("N")) {
            return -1;
        }

        if (direction.toUpperCase().contains("S")) {
            return 1;
        }

        return 0;
    }

    public PixelCoordonates[] getNeighboursInDirection(Mat img, PixelCoordonates px) {

        PriorityQueue<PixelCoordonates> q = new PriorityQueue<>(
                (p1, p2) -> -Double.compare(getValue(img, p1), getValue(img, p2))
        );

        q.add(new PixelCoordonates( px.getX() + xDirectionModifier(px.getDirection()),
                                    px.getY() + yDirectionModifier(px.getDirection()),
                                    px.getDirection()));
        q.add(new PixelCoordonates( px.getX() + xDirectionModifier(nextDirection(px.getDirection())),
                                    px.getY() + yDirectionModifier(nextDirection(px.getDirection())),
                                    nextDirection(px.getDirection())));
        q.add(new PixelCoordonates( px.getX() + xDirectionModifier(prevDirection(px.getDirection())),
                                    px.getY() + yDirectionModifier(prevDirection(px.getDirection())),
                                    prevDirection(px.getDirection())));


        return q.toArray(PixelCoordonates[]::new);
    }

    public PixelCoordonates[] getSeedNeighbours(Mat image, PixelCoordonates px, VisitRegistry visitRegistry){

        List<PixelCoordonates> q = new LinkedList<>();

        String direction = px.getDirection();
        for (int i = 0; i < 8; i++) {
            q.add(new PixelCoordonates( px.getX() + xDirectionModifier(direction),
                                        px.getY() + yDirectionModifier(direction),
                                        direction));

            direction = nextDirection(direction);
        }

        return q.stream()
                .filter(Predicate.not(visitRegistry::isVisited))
                .filter(p -> getValue(image, p) > 0.0)
                .toArray(PixelCoordonates[]::new);
    }

    public PixelCoordonates[] getSeedPoints(Mat magnitude, Mat img){

        PriorityQueue<PixelCoordonates> q = new PriorityQueue<>((p1, p2) ->
                -Double.compare(getValue(magnitude, p1), getValue(magnitude, p2))
        );

        for (int row = 0; row < img.height(); row++) {
            for (int column = 0; column < img.width(); column++) {
                if (img.get(row, column)[0] > 0.0) {
                    q.add(new PixelCoordonates(column, row, "NE"));
                }
            }
        }
        System.out.println("Seeds: " + q.size());

        return q.toArray(new PixelCoordonates[0]);
    }

    public double getValue(Mat img, PixelCoordonates px) {
        try {
            return img.get(px.getY(), px.getX())[0];
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public boolean draw(Mat image, PixelCoordonates p, PixelCoordonates neighbour, int treshold){
        if(image.get(p.getX(),p.getY())[0] <= image.get(neighbour.getX(), neighbour.getY())[0] + treshold)
            return true;
        return false;
    }

    private class VisitRegistry {
        private boolean[][] visited;

        public VisitRegistry(int height, int width) {
            this.visited = new boolean[height][width];
        }

        public boolean isVisited(PixelCoordonates px) {
            try {
                return visited[px.getY()][px.getX()];
            } catch (ArrayIndexOutOfBoundsException ex) {
                return true;
            }
        }

        public void setVisited(PixelCoordonates px, boolean visited) {
            this.visited[px.getY()][px.getX()] = visited;
        }
    }


    public Mat apply(Mat image) {

        int width = image.width();
        int height = image.height();

        VisitRegistry visitRegistry = new VisitRegistry(height, width);

        LaplacianTerm laplacian = new LaplacianTerm();

        GradientComponents gradientComponents = Gradient.apply(image);
        Mat magnitude = gradientComponents.getMagnitude();

        image = laplacian.apply(image);

        Mat outputImage = new Mat(magnitude.rows(), magnitude.cols(), CvType.CV_8U, Scalar.all(0));

        int threshold = 10;

        List<PixelCoordonates> edge = new ArrayList<>();
        List<List<PixelCoordonates>> edges = new ArrayList<>();
        PixelCoordonates[] seedPixels = getSeedPoints(magnitude, image);
        for (PixelCoordonates seed : seedPixels) {
            PixelCoordonates[] neighbours = getSeedNeighbours(image, seed, visitRegistry);
            if (visitRegistry.isVisited(seed) || neighbours.length > 1) {
                continue;
            }

            edge.add(seed);
            visitRegistry.setVisited(seed, true);

            for (PixelCoordonates neigh : neighbours) {
                if (visitRegistry.isVisited(neigh)) {
                    continue;
                }

                edge.add(neigh);
                visitRegistry.setVisited(neigh, true);

                boolean done = false;
                while (!done) {
                    done = true;
                    for (PixelCoordonates crtPixel : getNeighboursInDirection(magnitude, neigh)) {

                        // if the chain is complete
                        if (crtPixel.equals(seed)) {
                            break;
                        }

                        if (visitRegistry.isVisited(crtPixel) || Math.abs(getValue(magnitude, crtPixel) - getValue(magnitude, neigh)) > threshold) {
                            continue;
                        }

                        done = false;
                        edge.add(crtPixel);

                        // removed current from stack

                        visitRegistry.setVisited(crtPixel, true);
                        neigh = crtPixel;
                    }

                    if (done) {
                        edges.add(edge);
                        edge = new ArrayList<>();
                    }
                }
            }
        }

        System.out.println("edges: " + edges.size());
        for (List<PixelCoordonates> crtEdge : edges) {
            for (PixelCoordonates px : crtEdge) {
                outputImage.put(px.getY(), px.getX(), 255);
            }
        }

        Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB,3);
        return outputImage;
    }
}
