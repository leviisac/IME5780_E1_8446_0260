package elements;


import primitives.Color;

public class AmbientLight {
    private Color _intensity;

    /**
     *the func refill light
     * @param ia-refill light
     * @param ka-promotes light refill
     */
    public  AmbientLight(Color ia, double ka) {
        this._intensity = ia.scale(ka);
    }
    public Color getIntensity() {
        return _intensity;
    }
}
