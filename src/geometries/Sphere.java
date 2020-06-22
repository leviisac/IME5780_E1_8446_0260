/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;


/**
 * sphere class represents sphere in 3D Cartesian coordinate
 * for represents a sphere 3D Cartesian we need radius and point 3D
 *
 * @author levi and david
 */


public class Sphere extends RadialGeometry{



    private Point3D _center;

    // ***************** Constructors ********************** //
    /**
     * this is a basic constructor it receives two param radius and center
     *
     * @param emission
     * @param radius - the radius of the sphere
     * @param center - point 3d that his the location of the sphere
     */
    public Sphere(Color emission, double radius, Point3D center) {
        this(radius,center);
        this._emission = emission;
    }

    public Sphere(Color _emissionLight, Material _material, double _radius, Point3D _center) {
        this(_emissionLight,_radius,_center);
        this._material = _material;
    }
    /**
     * this is a basic copy  constructor it receives an sphere and copy to our sphere the radius and the center
     *
     * @param sphere
     */
    public Sphere (Sphere sphere){this(sphere._radius,sphere._center);}

    /**
     * this is a basic constructor it receives two param radius and center
     *
     * @param radius - the radius of the sphere
     * @param center - point 3d that his the location of the sphere
     */
    public Sphere(double radius, Point3D center){
        super(radius);
        this._center=center;
    }



    // ***************** Getters ********************** //
    public Point3D getCenter(){return  new Point3D(_center);}//return a new Point3D with the same value of _center



    // ***************** Operations ******************** //
    /**
     * the function find the normal vector of the sphere at a specific point 3d
     * normalize(p-center) = normal
     * @param p the specified point
     * @return new vector normal of the sphere in size one
     */
    @Override
    public Vector getNormal(Point3D p) {
        Vector normal_sphere = p.subtract(_center);
        return normal_sphere.normalize();
    }


    /**
     * @param ray - he his the ray that insert the object
     * @return a list of all intersection
     */

    public List<GeoPoint>  findIntersections(Ray ray) {
        Point3D p0 = ray.get_P0();
        Vector v = ray.get_direction();
        Vector u;
        // 𝑢 = 𝑂 − 𝑃0 distance from the center and the p0
        try {
            u = _center.subtract(p0);
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this,ray.getTargetPoint(_radius)));
        }
        //tm=v*u the distance between p0 and the point with makes 90 degrees with the center
        double tm = alignZero(v.dotProduct(u));
        //d=u^2+tm^2 distance between the center and the point that  makes 90 degrees with the center
        double dsquared;
        if (tm == 0)
            dsquared = u.lengthSquared();
        else {
            dsquared = u.lengthSquared() - tm * tm;
        }
        double thsquared = alignZero(_radius * _radius - dsquared);

        if (thsquared <= 0) return null;
        //th=radius*radius-d*d between p1 and center
        double th = alignZero(Math.sqrt(thsquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;

        if (t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, (ray.getTargetPoint(t1)))
                    ,new GeoPoint(this, (ray.getTargetPoint(t2)))); //P1 , P2
        if (t1 > 0)
            return List.of(new GeoPoint(this,(ray.getTargetPoint(t1)))); //just one point
        if (t2 > 0)
            return List.of(new GeoPoint(this,(ray.getTargetPoint(t2))));
        return null;
    }

    /***
     * check if the given sphere is equal to our sphere
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Objects.equals(_center, sphere._center);
    }


}
