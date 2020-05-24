package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 Test method for {@link geometries.Tube}.
 *  tube test class test tube in 3D Cartesian coordinate
 *  @author Aaron
 */

public class TubeTest {

    /**
     * we test the constructor of tube
     */
    @Test
    public void testConstructor() {
        try {
            // ============ Equivalence Partitions Tests ==============

            //TC01:test build a tub e with a radius equals to zero
            double radius = 0;
            new Tube(new Ray(new Point3D(1, 2, 3), new Vector(1, 1, 1)), radius);
            fail("Constructed a tube with a null radius ");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * test the get ray by comparing the result of the func and the expected result
     */
    @Test
    public void getRay() {
        // ============ Equivalence Partitions Tests ==============

        // TC01:test the get ray by comparing the result of the func and the expected result
        try {
            Point3D p = new Point3D(2, 3, 4);
            Vector d = new Vector(1, 1, 1);
            double radius = 3;
            Ray myRay = new Ray(p, d);
            Tube myTube = new Tube(myRay, radius);
            Ray result = myTube.getRay();
            Ray expResult = new Ray(p, d);
            assertEquals(result, expResult);
        } catch (IllegalArgumentException e) {
        }
    }
}