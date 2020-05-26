/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package unittests.geometries;

import org.junit.Test;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Sphere;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Test method for {@link geometries.Plane}.
 * Plane test class test plane in 3D Cartesian coordinate
 * we test the class plan if the func and param works good
 */
public class PlaneTest{

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:test when the  plane with two identical points
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 1),
                    new Point3D(1, 0, 0));
            fail("Constructed a plane with two same vertices");
        } catch (IllegalArgumentException e) {
        }
        //TC02: test wrong plane with three identical points
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 1),
                    new Point3D(0, 0, 1));
            fail("Constructed a plane with three same vertices");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * we test the if the func get normal work correctly
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:we need to receive
        Point3D receivedPoint = new Point3D(2, 2, 2);
        Point3D p1 = new Point3D(5, 4, 0);
        Point3D p2 = new Point3D(5, 6, 0);
        Point3D p3 = new Point3D(7, 8, 0);
        Plane plane = new Plane(p1, p2, p3);
        assertEquals(1, plane.getNormal(receivedPoint).length(), 0.00001);
        assertEquals(0, p1.subtract(p2).dotProduct(plane.getNormal(receivedPoint)), 0.00001);
        assertEquals(0, p1.subtract(p3).dotProduct(plane.getNormal(receivedPoint)), 0.00001);
    }


}