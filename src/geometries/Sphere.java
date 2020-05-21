/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;

import primitives.Ray;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;


//this class represent a sphere


public class Sphere extends RadialGeometry{



    private Point3D _center;

    // ***************** Constructors ********************** //

    //copy constructor (receive one sphere and copy your values to ""this.sphere"")
    public Sphere (Sphere sphere){this(sphere._radius,sphere._center);}

    //constructor that receive the radius and the center point of the sphere and initializing the radius and the center point
    public Sphere(double radius, Point3D center){
        super();
        this._radius=radius;this._center=center;
    }



    // ***************** Getters ********************** //
    public Point3D getCenter(){return  new Point3D(_center);}//return a new Point3D with the same value of _center



    // ***************** Operations ******************** //

    public Vector getNormal(Point3D point){

        return null;
    }



}
