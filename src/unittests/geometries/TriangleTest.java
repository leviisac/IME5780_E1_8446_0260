package unittests.geometries;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

/**
Test method for {@link geometries.Triangle}.
 *  Triangle test class test triangle in 3D Cartesian coordinate
 *  @author Aaron
 */

public class TriangleTest {
    /**
     * we test if the constructor find the problem in this case
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:with two same points and so it's not a triangle it's a vector
        try {
            new Triangle( new Point3D(0, 0, 0), new Point3D(0, 0, 0), new Point3D(1, 2, 3));
            fail("Constructed a vector not a triangle ");
        } catch (IllegalArgumentException e) {

        }
    }


}