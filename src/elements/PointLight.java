package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this class represents color at a given point
 * @author levi and david
 * */

public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    //factors for attenuation with distance
    protected double _kC;
    protected double _kL;
    protected double _kQ;



    /**
     * Initialize Lights color intensity , and the positions
     *
     * @param _intensity
     * @param _position
     */

    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = kQ;
    }



    /**
     * calculate the intensity of color of point on the geometry
     *
     * @param p the lighted point
     * @return intensity of color af point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        return (_intensity.reduce(_kC + _kL * d + _kQ * dsquared));
    }

    /**
     *
     * calculates the direction of the light
     * ray from the light source to the point
     *
     * @param p the lighted point
     * @return The direction of the light rays that hit the point
     */
    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalized();
    }

}
