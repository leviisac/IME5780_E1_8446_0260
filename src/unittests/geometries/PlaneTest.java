/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package unittests.geometries;

import geometries.Intersectable;
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

    /**
     * we test the find intersection function with ray and plane
     */
    @Test
    public void findIntersections() {
        Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(0, 2, 1), new Point3D(2, 0, 1));


        // ============ Equivalence Partitions Tests ==============

        //TC01: Ray's neither orthogonal nor parallel to the plane and intersects the plane (1 points)
        assertEquals("Ray's line out of plane", 1, plane.findIntersections(new Ray(new Point3D(0, -2, 0), new Vector(0, 2, 1))).size());

        //TC02: Ray's neither orthogonal nor parallel to the plane and not intersects the plane (0 points)
        assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(-1, -1, 0))));


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the plane

        // TC11: Ray included in the plane (0 points)
        assertEquals("Ray's line is not in the plane", null, plane.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));

        // TC12: Ray are not included in the plane (0 points)
        assertEquals("Ray's line is in plane", null, plane.findIntersections(new Ray(new Point3D(2, -1, 0), new Vector(-1, 0, 0))));


        // **** Group: Ray's line is orthogonal to the plane

        //TC13 Ray is  orthogonal to the plane before (0 points)
        assertEquals("the Ray is not orthogonal to the plane", null, plane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(0, 2, 0))));

        //TC14 Ray is  orthogonal to the plane after (0 points)
        assertEquals("the Ray is not orthogonal to the plane", null, plane.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(0, -2, 0))));

        //TC15 Ray is  orthogonal in the plane
        assertEquals("the Ray is not orthogonal in the plane", null, plane.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 1, 0))));

        //TC16 Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane (Q)
        assertEquals("Ray's not start at Q0", null, plane.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 2, -1))));


    }


}