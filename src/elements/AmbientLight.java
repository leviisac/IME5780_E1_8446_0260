package elements;


import primitives.Color;


/**
 * class for the ambient light in the scene
 * @author levi and david
 */
public class AmbientLight extends Light {


    /**
     *the func refill light
     * @param ia-refill light
     * @param ka-promotes light refill
     */
    public  AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }

}
