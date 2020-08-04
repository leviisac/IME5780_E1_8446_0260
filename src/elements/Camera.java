package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;


/**
 * camera class represents camera in 3D Cartesian coordinate
 *
 * @author levi and david
 */


public class Camera {

    Point3D _p0;
    Vector _vto;
    Vector _vup;
    Vector _vright;
    // number of rows and columns in one pixel for supersampling
    private static final int SUPERSAMPLING_NUM = 15;
    /**
     * @param p0-the  place of the camera
     * @param vto-where the vector point outgoing from the camera
     * @param vup-the vector vertical to vto
     */
    public Camera(Point3D p0, Vector vto, Vector vup) {


        //if the two vectors are not orthogonal throw exception ,it will be ortogonal only if vto x vup == 0
        if (vto.dotProduct(vup) != 0)
            throw new IllegalArgumentException("the vto not orthogonal to vup ");
        _p0 = new Point3D(p0);
        _vto = vto.normalized();
        _vup = vup.normalized();
        // vright = vto x vup
        _vright = _vto.crossProduct(_vup).normalize();


    }


    public Point3D getP0() {
        return _p0;
    }

    public Vector getVto() {
        return _vto;
    }

    public Vector getVup() {
        return _vup;
    }

    public Vector getVright() {
        return _vright;
    }




    /**
     * the func should create ray witch point
     *
     * @param nX number         of pixels in the x axis
     * @param nY number         of pixels in the y axis
     * @param j               horizontal index of pixel (from left to right)
     * @param i vertical        index of pixel (from up to down)
     * @param screenDistance   the distance between the _p0 and pc where the image are located
     * @param screenWidth width of the screen
     * @param screenHeight    height of the screen
     * @return ray where outgoing construct Ray Through Pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
                                        double screenHeight) {

        if (isZero(screenDistance)) throw new IllegalArgumentException("distance cannot be 0");

        // Pc is the screen center (Pc = P0 + distance*Vto)
        Point3D Pc = _p0.add(_vto.scale(screenDistance));

        Point3D pIJ = getPixelCenter(Pc, nX, nY, j, i, screenWidth, screenHeight);

        Vector vIJ = pIJ.subtract(_p0);
        return new Ray(_p0, vIJ.normalize());
    }
    /**
     * Construct ray through pixel for super sampling method
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public List<Ray> constructBeamThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
                                               double screenHeight) {
        List<Ray> beam = new LinkedList<>();

        if (isZero(screenDistance)) throw new IllegalArgumentException("distance cannot be 0");

        // get the image center with the formula P_center = P0 + distance*Vto
        Point3D P_center = _p0.add(_vto.scale(screenDistance)); //image center

        // get the center of the pixel
        Point3D Pixel_center = getPixelCenter(P_center, nX, nY, j, i, screenWidth, screenHeight);

        double Ry = screenHeight / nY; // pixel height
        double Rx = screenWidth / nX; // pixel width

        double Sry = Ry / (SUPERSAMPLING_NUM - 1); // subpixel height
        double Srx = Rx / (SUPERSAMPLING_NUM - 1); // subpixel width

        // Move Pixel_center to the pixel top left corner
        double X0 = ((- (SUPERSAMPLING_NUM - 1) / 2d) * Srx);
        double Y0 = ((- (SUPERSAMPLING_NUM - 1) / 2d) * Sry);

        Pixel_center = Pixel_center.add(_vright.scale(X0));
        Pixel_center = Pixel_center.add(_vup.scale(-Y0));

        // pIJS is moving on grid
        Point3D pIJS = Pixel_center;

        for (i = 0; i < SUPERSAMPLING_NUM ; ++i) {
            for (j = 0; j < SUPERSAMPLING_NUM; ++j) {

                // Create an Adding Ray to the beam
                Vector vIJ = pIJS.subtract(_p0);
                beam.add(new Ray(_p0, vIJ.normalize()));

                // Next point on i
                pIJS = pIJS.add(_vright.scale(Srx));
            }
            // Next Point on j
            pIJS = Pixel_center.add(_vup.scale(- Sry * (j + 1)));
        }
        return beam;
    }





    /**
     @param nX
      *            - number of pixels in the screen width
      * @param nY
     *            - number of pixels in the screen height
     * @param j
     *             - "y" rate of the pixel
     * @param i
     *             - "x" rate of the pixel
     * @param screenWidth
     *
     * @param screenHeight
     *
     * @return pixel middle
     */

    public Point3D getPixelCenter(Point3D center ,int nX, int nY, int j, int i, double screenWidth, double screenHeight) {

        double Ry = screenHeight / nY; // pixel height
        double Rx = screenWidth / nX; // pixel width

        double Yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double Xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D pIJ = new Point3D(center);  // pIJ is the point on the middle of the given pixel

        if (!isZero(Xj)) pIJ = pIJ.add(_vright.scale(Xj));
        if (!isZero(Yi)) pIJ = pIJ.add(_vup.scale(-Yi));
        return pIJ;
    }


    @Override
    public String toString() {
        return "Camera{" +
                "_p0=" + _p0 +
                ", _vUp=" + _vup +
                ", _vTo=" + _vto +
                ", _vRight=" + _vright +
                '}';
    }

}
