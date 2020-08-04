package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * this class represents the spot light
 * @author levi and david
 */

public class SpotLight extends PointLight {




    protected Vector _direction;

    /**
     * Initialize the vector of direction of the spot light
     *
     * @param _intensity
     * @param _position
     * @param _direction
     * @param _kC
     * @param _kL
     * @param _kQ
     */
    public SpotLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ, Vector _direction) {
        super(_intensity, _position, _kC, _kL,_kQ);
        this._direction = _direction;
    }




    /**
     * @param p the lighted point
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {

        Vector vector;
        if(p.subtract(_position).normalized() == null)
            vector = new Vector(_direction);
        else
            vector = p.subtract(_position).normalized();


        return super.getIntensity(p).scale(Math.max(0,_direction.dotProduct(vector)));

    }




}
