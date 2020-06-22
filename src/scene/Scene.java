package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * scene class
 * contain 6 fields:
 * _name name of scene
 * _background color to paint the background of scene
 * _ambientLight light/color that lights the environment
 * _geometries objects/shapes that exist in scene
 * _camera point of view, that from there we look on the scene, through an imagine view plane
 * _distance distance from camere to the imagine view plane
 *
 *
 * @author levi and david
 */
public class Scene {
    private String _name;
    private Color _background = Color.BLACK;
    private AmbientLight _ambientLight = new AmbientLight(Color.BLACK, 0d);
    private Geometries _geometries = new Geometries();
    private Camera _camera;
    private double _distance;
    private List<LightSource> _lights;

    /**
     * constructor with only the name as a parameter
     *
     * @param name name of the scene
     */
    public Scene(String name) {
        this._name = name;
        this._background = null;
        this._ambientLight = null;
        this._camera = null;
        this._distance = 0.0;
        this._geometries = new Geometries(); // Initialize empty list
        this._lights = new LinkedList<LightSource>();
    }

    public String getName() {
        return _name;
    }


    /**
     * gets the List of the Lights in the scenee
     */
    public List<LightSource> getLightSources() {
        return _lights;
    }

    /**
     * adds a light source to the scene
     * @param lights
     */
    public void addLights(LightSource lights) {
        if(_lights == null){
            _lights = new ArrayList<>();
        }
        _lights.addAll(Arrays.asList(lights));
    }

    public Color getBackground() {
        return _background;
    }

    public void setBackground(Color _background) {
        this._background = _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public Camera getCamera() {
        return _camera;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public double getDistance() {
        return _distance;
    }

    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public void setGeometries(Geometries _geometries) {
        this._geometries = _geometries;
    }

    /**
     * function to add geometries to scene
     * @param geometries one or more geometries, such sphere or triangle
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }
}
