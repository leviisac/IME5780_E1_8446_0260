/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;
import primitives.*;

/***
 * this is an abstract class for all the geometries bodys
 * @author levi and david
 */
public abstract class Geometry implements Intersectable {

    public abstract Vector getNormal(Point3D point);
    protected Color _emission;
    protected Material _material;



  /*********************constructors**********************/


/**
 * constructor for the class geometry
 *
 * @param _emission
 * @param _material
 * */
    public Geometry(Color _emission,Material _material) {
        this._emission = _emission;
        this._material=_material;
    }


    /**
     * constructor who receives color
     * @param _emission
     */
    public Geometry(Color _emission) {
        this(_emission , Material.DEFAULT);
    }


    /**
     * Default constructor
     *
     */
    public Geometry() {
        this._emission = new Color(java.awt.Color.BLUE);
        this._material = Material.DEFAULT;
    }
/**********************getters/setters***********************/


    /**
     * get _emission
     * @return _emission
     */
    public Color get_emission() {
        return _emission;
    }


    /**
     *
     * @return _material
     */
    public Material get_material() {
        return _material;
    }
}
