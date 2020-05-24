package primitives;



/**
 * this class represent a ray :
 * A ray is a part of a line that begins at a particular point
 * (called the endpoint) and extends endlessly in one direction
 */
public class Ray {


    private Point3D _P0;
    private Vector _direction;


     /****************** Constructors ********************** //
      */


    /**
     *  constructor that receive a Ray variable that contains the point of origin and the direction
     */
    public Ray(Ray ray){
        this._P0 = ray.get_P0();
        this._direction = ray.get_direction();
    }

    /**
     *constructor
     * @param _P0
     * @param _direction
     */
    public Ray(Point3D _P0, Vector _direction) {
        this.set_P0(_P0);
        this.set_direction(_direction);
        this._direction.normalize();
    }



    /**
     * ***************** Getters/Setters ********************** //
      */

    public void set_P0(Point3D _P0) {
        this._P0 = new Point3D(_P0);
    }

    public Point3D get_P0() {
        return new Point3D(_P0);

    }

    public void set_direction(Vector _direction) {
        this._direction = new Vector(_direction);
    }

    public Vector get_direction() {
        return new Vector(_direction);
    }

    // ***************** Administration ********************** //
    @Override
    public String toString()
    {

        return "direction: " + _direction.toString();
    }
}


