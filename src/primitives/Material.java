package primitives;

/***
 * class for the hold of the attenuation and shininess factors for model phong
 * @author levi and david
 */
public class Material {
    double _kD;
    double _kS;
    int nShininess; //brilho
    double _kT; // k transparency
    double _kR; // k reflection

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
     * Constructor with transparency and reflection parameters
     *
     * @param _kD
     * @param _kS
     * @param _nShininess
     * @param _kT
     * @param _kR
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR){
        this._kD = _kD;
        this._kS = _kS;
        this.nShininess = _nShininess;
        this._kT = _kT;
        this._kR = _kR;

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

    public double get_kT() {
        return _kT;
    }

    public double get_kR() {
        return _kR;
    }
}
