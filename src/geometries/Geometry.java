/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;
import primitives.Point3D;
import primitives.Vector;
import primitives.Coordinate;

public interface Geometry {
    public abstract Vector getNormal(Point3D point);
}
