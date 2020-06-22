package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;


/**
 *  this interface for all geometries that are able to intersect from a ray to their entity
 * @ author levi
 */
public interface Intersectable {

    /**
     * @param ray- he his the ray that insert the object
     * @return a list of all intersection
     */
    List<GeoPoint> findIntersections(Ray ray);


    public static class GeoPoint {


        public Geometry geometry;
        public Point3D point;


        /**
         * constructor of the class
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }



        // ***************** Getters/Setters ********************** //

        /**
         * @return _point
         */
        public Point3D getPoint(){
            return point;
        }

        /**
         * @return _geometry
         */
        public Geometry getGeometry(){
            return geometry;
        }


        /**
         * this function compare if the GeoPoints are equal.
         *
         * @param o
         * @return if they equal the function return 0(zero), if they aren't the
         * function return -1
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    Objects.equals(point, geoPoint.point);
        }


    }



}
