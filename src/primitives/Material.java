package primitives;

/***
 * class for the hold of the attenuation and shininess factors for model phong
 * @author levi and david
 */
public class Material {
    double _kD;
    double _kS;
    int nShininess; //brilho

    public final static Material DEFAULT = new Material(0d,0d,0);
    /**
     * constructor for the class Material
     *
     * @param _kD
     * @param _kS
     * @param nShininess
     */
    public Material(double _kD, double _kS, int nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this.nShininess = nShininess;
    }

    /**
     * @return _kD
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * @return _kS
     */
    public double get_kS() {
        return _kS;
    }


    /**
     * @return nShininess
     */
    public int getnShininess() {
        return nShininess;
    }
}
