package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries implements Intersectable {
    List<Intersectable> intersectableList = new ArrayList<>();

    /**
     * constructor received many geometries and adding them to the list
     *
     * @param geometries geometries to add the list
     */
    /* *********constructor*******/
    public Geometries(Intersectable... geometries) {
        intersectableList.addAll(Arrays.asList(geometries));
    }

    public List<Intersectable> getIntersectableList() {
        return intersectableList;
    }

    /**
     * function who adding geometry shape to list of the class
     *
     * @param geometry geometry shape
     */
    public void add(Intersectable... geometry) {
        intersectableList.addAll(Arrays.asList(geometry));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if (intersectableList.isEmpty())
            return null;

        List<Point3D> intersections = null;

        for (Intersectable geo : intersectableList) {
            List<Point3D> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<Point3D>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }


}
