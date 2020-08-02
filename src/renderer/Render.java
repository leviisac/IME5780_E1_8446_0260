package renderer;

import elements.Camera;
import elements.LightSource;
import primitives.Material;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Color;
import scene.Scene;
import renderer.ImageWriter;

import java.util.List;

import static primitives.Util.alignZero;


/**
 * Class for rendering Scene
 *
 * @author levi and david
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;
   // private static final double DELTA = 0.1;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    // ***************** Constructors ********************** //

    public Render(Scene _scene) {
        this._scene = _scene;
    }

    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
    }



    // ***************** Getters/Setters ********************** //

    /**
     * Getter
     *
     * @return the _scene
     */
    public Scene get_scene() {
        return _scene;
    }


    // ***************** Operations ******************** //

    /**
     *  Throws rays through the all pixels and for each ray - if it's got
     *  intersection points with the shapes of the scene - paints the closest point
     */
    public void renderImage(boolean  anti_aliasing) {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        //Nx and Ny are the number of pixels in the rows and columns of the view plane
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        //width and height are the width and height of the image.
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        double distance = _scene.getDistance();
        if (anti_aliasing){
            for (int row = 0; row < nY; ++row)
                for (int column = 0; column < nX; ++column) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);

                    List<Ray> rayList = camera.constructBeamThroughPixel(nX, nY, column, row, distance, width, height);
                    _imageWriter.writePixel(column, row, closestPoint == null ? background : averageColor(rayList).getColor());
                }
    }
    else{

    for (int row = 0; row < nY; ++row)
        for (int column = 0; column < nX; ++column) {
            Ray ray = camera.constructRayThroughPixel(nX,nY,column,row,distance,width,height);
            GeoPoint closestPoint = findClosestIntersection(ray);
            _imageWriter.writePixel(column,row,closestPoint == null ? background : calcColor(closestPoint, ray).getColor());

        }
            }

    }


    /**
     * Calculate the average of a color in a pixel
     *
     * @param rayBeam
     * @return
     */
    private Color averageColor(List<Ray> rayBeam){
        java.awt.Color background = _scene.getBackground().getColor();
        Color color = new Color(0,0,0);
        for(Ray ray : rayBeam){
            color = findClosestIntersection(ray) == null ? color.add(_scene.getBackground())
                    : color.add(calcColorAdvanced(findClosestIntersection(ray),ray));
        }

        return color.reduce(rayBeam.size());
    }
    /**
     * Calculates reflected color on point according to Phong model.
     * Calls for recursive helping function.
     *
     * @param geopoint
     * @param inRay
     * @return
     */
    private Color calcColorAdvanced(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }

    /**
     *  Prints grid for the background of the image for test
     *
     * @param interval
     *                 - The interval between line to line
     * @param separator
     *
     */
    public void printGrid(int interval, java.awt.Color separator) {

        double columns = this._imageWriter.getNx();
        double rows = this._imageWriter.getNy();

        //writing the lines
        for(int row =0; row<rows;++row)
            for (int column = 0; column<columns ; ++column)
                if(column % interval == 0 || row % interval ==0)
                    _imageWriter.writePixel(column,row,separator);
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }



    /**
     * Calculates reflected color on point according to Phong model.
     * Calls for recursive helping function.
     *
     * @param geopoint
     * @param inRay
     * @return
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }


    /**
     * Helping function for main calcColor
     * Calculates reflected color on point according to Phong model
     *
     * @param gp
     * @return color
     */
    private Color calcColor(GeoPoint gp, Ray inRay, int level, double k)  {

        if (level == 0 || k < MIN_CALC_COLOR_K) {
            return Color.BLACK;
        }

        Color color = gp.getGeometry().get_emission();

        Vector v = gp.getPoint().subtract(_scene.getCamera().getP0()).normalize(); //direction from point of view to point
        Vector n = gp.getGeometry().getNormal(gp.getPoint()); //normal ray to the surface at the point

        Material material = gp.getGeometry().get_material();

        int nShininess = material.getnShininess(); //degree of light shining of the material

        double kd = material.get_kD(); //degree of diffusion of the material
        double ks = material.get_kS(); //degree of light return shining of the material


        if(_scene.getLightSources() != null) {
            for (LightSource lightSource : _scene.getLightSources()) {

                Vector l = lightSource.getL(gp.getPoint()); //the ray of the light
                double nl = alignZero(n.dotProduct(l)); //dot-product n*l
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) { // Check that sign(l∙n) == sign (v∙n) according to Phong reflectance model
                    double ktr = transparency(lightSource, l, n, gp);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(gp.getPoint()).scale(ktr);
                        color = color.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                    }
                }
            }
        }

        if (level == 1)
            return Color.BLACK;

        double kr = material.get_kR();//degree of material reflection
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K){
            Ray reflectedRay = constructReflectedRay(gp.getPoint(), inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null){
                color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
            }
        }

        double kt = material.get_kT(); //degree of material transparency
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K){
            Ray refractedRay = constructRefractedRay(gp.getPoint(), inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null){
                color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
            }
        }

        return color;
    }



    /**
     * Returns transparency factor on specific point
     *
     * @param ls light source
     * @param l
     * @param n
     * @param geoPoint
     * @return transparency factor
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint){

        Vector lightDirection = l.scale(-1); // from point to light source

        //Generate ray by using the light direction
        Ray lightRay = new Ray(geoPoint.getPoint(), lightDirection, n);

        //Find intersections with the lightRay
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return 1.0;

        double lightDistance = ls.getDistance(geoPoint.getPoint());
        double ktr = 1.0;
        //For each intersection point with ray we shoot “shadow rays”
        for (GeoPoint gp : intersections) {
            //check if a light source is blocked by other objects
            if (alignZero(gp.getPoint().distance(geoPoint.getPoint()) - lightDistance) <= 0){
                //Get the kt of this geometry and save it for the next geometry
                ktr *= gp.getGeometry().get_material().get_kT();
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0; // No transparency
            }
        }
        return ktr;
    }


    /**
     * Checking for shading between a dot and the light source that is signed
     * @param light
     * @param l
     * @param n
     * @param geoPoint
     * @return
     */
    @Deprecated
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        //Generate ray by using the light direction
        Ray lightRay = new Ray(geoPoint.getPoint(), lightDirection, n);


        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return true; // no intersections and shades found


        //For each intersection point with ray we shoot “shadow rays”
        double lightDistance = light.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            //check if a light source is blocked by other objects
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 && gp.geometry.get_material().get_kT() == 0)
                return false; // Shade has by founded
        }
        return true;
    }

    /**
     *
     * @param val
     * @return
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Diffusive component of light reflection.
     * @param kd
     *          - degree of light return of the material (double)
     * @param nl
     *          -  dot-product n*l
     * @param lightIntensity
     *          - the intensity of the light as it reaches the object (color)
     * @return intensity of diffusive color (color)
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        if (nl < 0) {
            nl = -nl;
        }
        return lightIntensity.scale(nl * kd);
    }

    /**
     * Calculating the specular light
     *
     * @param ks
     *             - degree of light return shining of the material (double)
     * @param l
     *             - the ray of the light
     * @param n
     *             - normal ray to the surface at the point
     * @param nl
     *             - dot-product n*l
     * @param v
     *             - A ray on the observer's side (camera, etc.)
     * @param nShininess
     *             - degree of light shining of the material (int)
     * @param lightIntensity
     *             - light intensity at the point
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {

        double p = nShininess;


        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return lightIntensity.scale(ks * Math.pow(minusVR, p));
    }

    /**
     *  Finds the closest point to the camera from all intersection points
     *
     * @param intersectionPoints
     * @return  - The closest point to the camera
     *
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {

        // initialization
        GeoPoint result = null;
        double minDistance = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().getP0();

        for (GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);

            if (distance < minDistance) {
                minDistance = distance;
                result = geo;
            }
        }
        return result;

    }


    /**
     * Finds intersections of a ray with the scene geometries and get the
     * closest intersection point to the ray head. Returns null if there are
     * no intersection.
     * @param ray intersecting the scene
     * @return closest point
     */
    private GeoPoint findClosestIntersection(Ray ray){
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_P0();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint gp : intersections){
            double distance = ray_p0.distance(gp.getPoint());

            if (distance < closestDistance){
                closestPoint = gp;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    /**
     * Returns refracted ray with delta moving
     *
     * @param pointGeo
     * @param inRay
     * @param n
     * @return refracted ray
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n ){
        return new Ray(pointGeo, inRay.get_direction(), n);
    }
    /**
     * Returns reflected Ray with delta moving
     *
     * @param pointGeo
     * @param inRay
     * @param n
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n){
        Vector v = inRay.get_direction();
        double vn = v.dotProduct(n);

        if (vn == 0)
            return null;

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r , n);
    }
}
