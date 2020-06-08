package renderer;

import primitives.*;
import geometries.*;
import elements.*;
import scene.Scene;

import java.util.*;

/**
 * th object of this class his to create matrix of picture
 */
public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;

    /**
     * Build Render object with a scene and image writer
     *
     * @param imgWr Image Writer object
     * @param sc    scene object
     */
    public Render(ImageWriter imgWr, Scene sc) {
        _scene = sc;
        _imageWriter = imgWr;
    }

    public void renderImage() {
        java.awt.Color background = _scene.getBackground().getColor();
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        double distance = _scene.getDistance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx(); //columns
        int Ny = _imageWriter.getNy(); //rows
        //pixels grid
        for (int row = 0; row < Ny; ++row) {
            for (int column = 0; column < Nx; ++column) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, column, row, distance, width, height);
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(column, row, background);
                } else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    java.awt.Color pixelColor = calcColor(closestPoint).getColor();
                    _imageWriter.writePixel(column, row, pixelColor);
                }
            }
        }
    }
    /**
     * Finding the closest point to the P0 of the camera.
     * @param  intersectionPoints list of points, the function should find from
     * this list the closet point to P0 of the camera in the scene.
     * @return  the closest point to the camera
     */
    private Point3D getClosestPoint(List<Point3D> intersectionPoints) {
        Point3D result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().getP0();

        for (Point3D pt: intersectionPoints ) {
            double distance = p0.distance(pt);
            if (distance < mindist){
                mindist= distance;
                result =pt;
            }
        }

        return  result;
    }

    /**
     * function to draw a grid on our image by pixsel
     *
     * @param interval number that the pixels are multiple of this number, are part of the grid.
     */
    public void printGrid(int interval, java.awt.Color color) {
        int nY =  _imageWriter.getNy();
        int nX =  _imageWriter.getNx();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Calculate the color intensity in a point
     * @param point the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(Point3D point) {
        return _scene.getAmbientLight().getIntensity();
    }


    /**
     * Create the image file in jpeg format
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

}
