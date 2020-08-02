package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * @class for directional light
 * its composed by the intensity and an direction vector
 * @author levi and david
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector _direction; //the direction of the light, the distance doesnt matter


   /**
   * constructor who receive the direction and the intensity
   * @param _direction
    * @param _intensity
   * */

    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = new Vector(_direction.normalized());
    }


    /***
     *
     *
     * @param p the lighted point
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /***
     *
     * @param p the lighted point
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

}
