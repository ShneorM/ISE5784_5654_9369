package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * class to contain the proper methods for setting a bounding box for both geometry and geometries
 */
public abstract class Container extends Intersectable {

    /**
     * every Intersectable composite have his bounding volume, which represented by a bounding box
     */
    protected BoundingBox boundingBox = null;

    private boolean bvh=true;

    /**
     * get the boundingBox
     * @return the boundingBox
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * method sets the values of the bounding volume of the intersectable component
     * this implementation is for constructing new bounding box if necessary/needed
     */
    abstract void setBoundingBox();

    /**
     * get if we need use the bvh acceleration
     * @return bvh
     */
    public boolean isBvh() {
        return bvh;
    }

    /**
     * set whether we want to use bvh acceleration
     * @param bvh true or false
     * @return this
     */
    public Container setBvh(boolean bvh) {
        this.bvh = bvh;
        return this;
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if(isBvh()&& getBoundingBox()!=null&& !getBoundingBox().intersectBV(ray,maxDistance)){
            return null;
        }
        return super.findGeoIntersections(ray,maxDistance);
    }
}
