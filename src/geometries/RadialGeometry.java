package geometries;


/***
 * classe responsável por circunferências
 * @author levi and david
 */
public abstract class RadialGeometry extends Geometry {

    double  _radius;

    /**
     * RadialGeometry Constructor receiving radius
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }


    //copy constructor
    public RadialGeometry(RadialGeometry geo){
        this._radius=geo.getRadius();
    }



    public double getRadius(){return this._radius;}//return the _radius


    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
