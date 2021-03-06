package primitives;


import static primitives.Util.isZero;

/**
 * this class represent a ray :
 * A ray is a part of a line that begins at a particular point
 * (called the endpoint) and extends endlessly in one direction
 *
 * @author levi and david
 */
public class Ray {


    private Point3D _P0;
    private Vector _direction;
    private static final double DELTA = 0.1;


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
    }


    public Ray(Point3D point, Vector direction, Vector normal) {
        // head + normal.scale(+-DELTA)
        _direction = new Vector(direction).normalized();

        double nv = normal.dotProduct(direction);

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        _P0 = point.add(normalDelta);
    }

    /**
     * ***************** Getters/Setters ********************** //
      */

    public void set_P0(Point3D _P0) {
        this._P0 = new Point3D(_P0);
    }

    public Point3D get_P0() {
//        return new Point3D(_P0);
        return _P0;

    }

    public void set_direction(Vector _direction) {
        //this._direction = new Vector(_direction);
        this._direction = _direction.normalized();
    }

    /**
     * P0 + v*t get a point in the ray , with the given distance
     * @param distance from the ray head
     * @return the point
     */

    public Point3D getTargetPoint(double distance) {
        return isZero(distance ) ? _P0 : _P0.add(_direction.scale(distance));
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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Ray))
            return false;
        Ray ray2 = (Ray)obj;
        return this._direction.equals(ray2._direction) && this._P0.equals(ray2._P0);
    }
}


