
/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;

import primitives.*;

import java.util.List;

/***
 * class represents a tube
 * @author levi and david
 */

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
     * Tube Constructor receiving radius and axis Ray and color
     *
     * @param emissionLight
     * @param radius
     * @param axisRay
     */
    public Tube(Color emissionLight, double radius, Ray axisRay) {
        this(axisRay,radius);
        this._emission = emissionLight;
    }

    public Tube(Color emissionLight, Material _material, double _radius, Ray _axisRay) {
        this(emissionLight,_radius,_axisRay);
        this._material = _material;

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
    public List<GeoPoint> findIntersections(Ray ray) {

        //Given ray (A + ta)
        Point3D pointA = new Point3D(ray.get_P0());
        Vector vectorA = new Vector(ray.get_P0());

        //Tube ray (B + tb)
        Point3D pointB = _ray.get_P0();
        Vector vectorB = _ray.get_direction();

        //if  vectorA  and pointA are parallel to tube (zero intersection points)
        if (Util.isOne(Math.abs(vectorA.dotProduct(vectorB))))
            return null;

        if(pointA.equals(pointB))
            pointB =_ray.getTargetPoint(-1);

        Vector pointAB = new Vector(pointA.subtract(pointB));

        double v1 = vectorA.dotProduct(vectorB);
        double v2 = pointAB.dotProduct(vectorB);

        double a, b, c;

        Vector vectorC;
        Vector vectorD = null;

        if(Util.isZero(v1))
            vectorC = vectorA;
        else
            vectorC = vectorA.subtract(vectorB.scale(v1));

        if(Util.isZero(v2))
            vectorD = pointAB;
        else if(!pointAB.equals(vectorB.scale(v2)))
            vectorD =pointAB.subtract(vectorB.scale(v2));

        if(Util.isZero(v2) || !pointAB.equals(vectorB.scale(v2))) {
            b = 2 * vectorC.dotProduct(vectorD);
            c = vectorD.dotProduct(vectorD) - _radius * _radius;
        } else {
            b = 0;
            c = -_radius * _radius;
        }

        a = vectorC.dotProduct(vectorC);
        double desc = b*b - 4*a*c;

        //if there is no solution
        if(desc < 0) return  null;

        //if there is one solution
        if(Util.isZero(desc)) return null;

        double direction1 = (-b+Math.sqrt(desc))/(2*a);
        double direction2 = (-b-Math.sqrt(desc))/(2*a);

        if(direction1 <=0 &&  direction2 <=0)
            return null;
        else if(direction1 > 0 && direction2 > 0)
            return List.of(
                    new GeoPoint(this,ray.getTargetPoint(direction1)),
                    new GeoPoint(this,ray.getTargetPoint(direction2)));
        else return List.of(
                    new GeoPoint(this,ray.getTargetPoint(direction1)));

    }

    @Override
    public String toString() {
        return "ray: " + _ray +
                ", radius: " + _radius;
    }
}
