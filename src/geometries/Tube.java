
/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends RadialGeometry{
    private Ray _ray;

    /**
     * this is the basic constructor for a ray:
     * he receive ray and radius
     *
     * @param radius
     * @param ray
     * @throws IllegalArgumentException when the radius is equal or smaller to zero so we don't have a tube
     */
    public Tube(Ray ray, double radius) {
        super(radius);
        if (radius <= 0)
            throw new IllegalArgumentException("It's not possible to have radius equals to 0");
        this._ray = new Ray(ray);
    }

    /**
     * simple function get
     *
     * @return the ray
     */
    public Ray getRay() {
        return _ray;
    }

    /**
     * the fucnction should return the normal of tube in size one
     * normalize(p-center) = normal
     * @param point- he recive a point on cycle tube
     * @return a normal of the tube normalize
     */
    @Override
    public Vector getNormal(Point3D point) {
        // distance from axis start to the projection of the point onto axis
        double scaleNumber = _ray.get_direction().dotProduct(point.subtract(_ray.get_P0()));
        // he found the point on the border of the tube  0
        Point3D o = _ray.get_P0().add(_ray.get_direction().scale(scaleNumber));
        // the normal of tube his the point on the border of the tub minus the central (p0-0)
        Vector normal_tube = point.subtract(o);
        //rerun normal tube vector in size one
        return normal_tube.normalize();
    }

    /**
     * @param ray - he his the ray that insert the object
     * @return a list of all intersection
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }


    @Override
    public String toString() {
        return "ray: " + _ray +
                ", radius: " + _radius;
    }
}
