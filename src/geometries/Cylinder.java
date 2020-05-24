/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Cylinder extends Tube {
    private double _height; //cylinder is a tube with limit


    /**
     * this is the basic constructor for a tube :
     * it receive ray and radius
     *
     * @param ray- the ray
     * @param radius- the radius
     * @param height- the height of the cylinder (limited with height)
     * @throws IllegalArgumentException the radius is equal or smaller to zero so we don't have a cylinder
     */

    public Cylinder(Ray ray, double radius, double height) {
        super(ray, radius);
        if (height <= 0)    //if the radius equal on small to zero so we don't can't have a cylinder so his return IllegalArgumentException
            throw new IllegalArgumentException("height must be equal or superior to zero.");
        this._height = height;
    }

    /**
     * simple function get
     *
     * @return the height of the cylinder
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _ray=" + getRay() +
                ", _radius=" + getRadius() +
                '}';
    }

    /**
     * function to find the normal of the cylinder
     * t= v*(p-p0)
     * o=t*v +p0
     * n=normalize(p-o)
     * @param point to find the normal
     * @return a normal of the cylinder normalize (new vector in size one)
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D p0 = getRay().get_P0();
        Vector v = getRay().get_direction();

        // projection of P-O on the ray:
        double t;
        try {
            t = point.subtract(p0).dotProduct(v);
        } catch (IllegalArgumentException e) { // P = O - center of the 1st base
            return v;
        }

        // if the point is at a base
        if (isZero(t) || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        p0 = p0.add(v.scale(t));
        return point.subtract(p0).normalize();
    }
}
