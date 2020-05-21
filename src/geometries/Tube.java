
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
     Point3D _axisPoint;
     Vector _axisDirection;

// ***************** Constructors ********************** //

    public Tube(double _radius, Point3D _axisPoint, Vector _axisDirection) {
        super(_radius);
        this._axisPoint = new Point3D(_axisPoint);
        this._axisDirection = new Vector(_axisDirection);
    }

    public Tube(double _radius) {
        super(_radius);
    }

    public Tube(RadialGeometry r) {
        super(r);
    }

// ***************** Getters ********************** //


    public Point3D get_axisPoint() {
        return new Point3D(_axisPoint);
    }

    public Vector get_axisDirection() {
        return new Vector(_axisDirection);
    }




    @Override
    public Vector getNormal(Point3D point) {

        return null;
    }


    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
