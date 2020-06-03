/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static primitives.Util.isZero;

public class Triangle extends Polygon {

    Point3D _p1;//the first point of the triangle
    Point3D _p2;//the second point of the triangle
    Point3D _p3;//the third point of the triangle


    // ***************** Constructors ********************** //
    //constructor that receive tree points and Initializing the tree point of the triangle with their values
    public Triangle(Point3D p1, Point3D p2, Point3D p3)
    {
         super(p1,p2,p3);
        _p1=new Point3D(p1);
        _p2=new Point3D(p2);
        _p3=new Point3D(p3);
    }


    //copy constructor (receive a triangle and copy your values to ""this.Triangle"")
    public Triangle(Triangle copy) {
        this._p1 = new Point3D(copy._p1);
        this._p2 = new Point3D(copy._p2);
        this._p3 = new Point3D(copy._p3);
    }



// ***************** Getters ********************** //


    public Point3D getP1() {
        return new Point3D(_p1);
    }//return a new Point3D with the value of this._p1
    public Point3D getP2() {
        return new Point3D(_p2);
    }//return a new Point3D with the value of this._p2
    public Point3D getP3() {return new Point3D(_p3);  }//return a new Point3D with the value of this._p3





    /**
     * @param ray - he his the ray that insert the object
     * @return list of Intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = _plane.findIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_P0();
        Vector v = ray.get_direction();
        //we creat the tree vector
        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double side1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(side1)) return null;
        double side2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(side2)) return null;
        double side3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(side3)) return null;

        if ((side1 > 0 && side2 > 0 && side3 > 0) || (side1 < 0 && side2 < 0 && side3 < 0)) return intersections;

        return null;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Triangle other = (Triangle) obj;
        if (_p1 == null) {
            if (other._p1 != null)
                return false;
        } else if (!_p1.equals(other._p1))
            return false;
        if (_p2 == null) {
            if (other._p2 != null)
                return false;
        } else if (!_p2.equals(other._p2))
            return false;
        if (_p3 == null) {
            if (other._p3 != null)
                return false;
        } else if (!_p3.equals(other._p3))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "_p1=" + _p1 +
                ", _p2=" + _p2 +
                ", _p3=" + _p3 +
                '}';
    }






}