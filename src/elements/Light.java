package elements;

import primitives.Color;

/**
 * base class for all Lights in general
 *
 * @author levi and david
 */
public class Light {
    protected Color _intensity;


    //***************** Constructors **********************//

    /**
     * Initialize Lights color intensity
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }


    // ***************** Getters/Setters ********************** //

    /**
     * Gets the original Light intensity I<sub>0</sub>
     * @return Light intensity
     */
    public Color getIntensity(){
        return new Color(_intensity);
    }

}
