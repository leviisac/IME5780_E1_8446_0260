/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * class plane represents a plane in 3D cartesian coordinate system
 * @author levi and david
 */
public class Plane extends Geometry {
     Vector _vec;//the vector that is part of the plane
    private Point3D _Q;//the point that is part of the plane

    // ***************** Constructors ********************** //
    /**
     * this is a basic constructor who build a plane from 3 points int cartesian coordinates system
     *
     * @param p1 1st point
     * @param p2 2nd point
     * @param p3 3rd point
     */

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _Q = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();
        _vec=N;
        //_vec = N.scale(-1);

    }

    /**
     * this constructor build a plane from a single point and a vector that will be normalized
     *
     * @param q the point
     * @param normal the vector normalized we'll use to build the plane
     */
    public Plane(Vector normal, Point3D q) {
        this._vec = normal;
        this._Q = q;
    }

    /**
     * Plane Constructor receiving a point, normal vector and color
     * @param _emissionLight
     * @param _p
     * @param _normal
     */
    public Plane(Color _emissionLight, Point3D _p, Vector _normal) {
        this(_normal,_p);
        this._emission = _emissionLight;
    }

    public Plane(Color _emissionLight, Material _material, Point3D _p, Vector _normal) {
        this(_emissionLight,_p,_normal);
        this._material = _material;
    }



    public Plane(Plane p) {
        this._Q=p._Q;
        this._vec=p._vec;
    }
    // ***************** getters********************** //

    public Vector get_vec() {
        return _vec;
    }

    public Point3D get_Q() {
        return _Q;
    }


    // ****************************************************** //


    /**
     * this function allows us to compute the normal vector in a specific point in the plane
     *
     * @param p the point where we want to compute the normal vector
     * @return the normal vector computed in the specific point
     */
  @Override
  public Vector getNormal(Point3D p) {
      return _vec;
  }




    /**
     * this function returns the normal vector to the plane
     *
     * @return the vector normal to the plane
     */
    public Vector getNormal() {
        return getNormal(null);
    }




    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plane other = (Plane) obj;
        if (_Q == null) {
            if (other._Q != null)
                return false;
        } else if (!_Q.equals(other._Q))
            return false;
        if (_vec == null) {
            if (other._vec != null)
                return false;
        } else if (!_vec.equals(other._vec))
            return false;
        return true;
    }


    /**
     * @param ray - he his the ray that insert the object
     * @return a list of all intersection
     */

   public List<GeoPoint>  findIntersections(Ray ray) {
        Vector pq0;
        try {
            pq0 = _Q.subtract(ray.get_P0());
        } catch (IllegalArgumentException e) {
            return null;
        }

        double nv = alignZero(_vec.dotProduct(ray.get_direction()));
        //if the ray are paralle to the plan so is not intersections
        if (nv == 0)
            return null;

        // intersections point equal 𝑃 = 𝑃0 + 𝑡 ∙ 𝑣, 𝑡 ≥ 0
        double t = alignZero(_vec.dotProduct(pq0) / nv);
        //t>=0, and hence:
        if (t <= 0)
            return null;

        return List.of(new GeoPoint(this, ray.getTargetPoint(t)));
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_vec=" + _vec +
                ", _Q=" + _Q +
                '}';
    }
}
