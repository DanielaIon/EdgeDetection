package com.example.backendlicenta.ImageProcessing.newEdgeDetection;

public class PixelCoordonates {
    int x;
    int y;
    String direction = "";

    public PixelCoordonates(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelCoordonates that = (PixelCoordonates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public String toString() {
        return "PixelCoordonates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
