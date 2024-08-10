package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometric shapes that can be intersected by rays.
 * It implements the Intersectable interface, allowing it to find intersections with a given ray.
 * This class supports adding multiple geometric shapes and finding their intersections collectively.
 * <p>
 * Author: Shneor and Emanuel
 */
public class Geometries extends Container {


    /**
     * A list to hold all the geometric shapes that can be intersected.
     */
    private final List<Container> containerList = new LinkedList<>();

    /**
     * Default constructor for creating an empty collection of geometries.
     */
    public Geometries() {
    }

    /**
     * get the containerList
     *
     * @return the containerList
     */
    public List<Container> getContainerList() {
        return containerList;
    }

    /**
     * Constructor that initializes the collection with one or more geometric shapes.
     *
     * @param geometries one or more geometric shapes to be added to the collection.
     */
    public Geometries(Container... geometries) {
        add(geometries);
    }

    //region modifying the List

    /**
     * Adds one or more geometric shapes to the collection.
     *
     * @param geometries one or more geometric shapes to be added to the collection.
     */
    public void add(Container... geometries) {
        containerList.addAll(List.of(geometries));
    }

    /**
     * remove method allow to remove (even zero) geometries from the composite class
     *
     * @param geometries which we want to add to the composite class
     * @return the geometries after the remove
     */
    public Geometries remove(Container... geometries) {
        containerList.removeAll(List.of(geometries));
        return this;
    }
//endregion

//region flattening

    /**
     * method to flatten the geometries list
     */
    public void flatten() {
        // copy the containers to a new temporary list
        Geometries new_geometries = new Geometries(containerList.toArray(new Container[0]));
        // clear the original list of containers
        containerList.clear();
        // call the second function which will make sure we only
        // have containers with simple instances of geometry
        flatten(new_geometries);
    }

    /**
     * recursive func to flatten the geometries list (break the tree)
     * receives a Geometries instance, flattens it and adds the shapes to this current instance
     *
     * @param new_geometries geometries
     */
    private void flatten(Geometries new_geometries) {
        // loop through the temporary list
        for (Container container : new_geometries.containerList) {
            // if the container contains only a simple geometry
            if (container instanceof Geometries geometries) {
                // so send it recursively to this function
                // which needs to get flattened as well
                flatten(geometries);
            } else {
                // add it to the original list
                containerList.add(container);
            }
        }
    }
//endregion

    //region turning on and off the bvh acceleration.

    /**
     * turns on or off the bvh acceleration in all the sub geometries using a recursive call
     * @param on whether the bvh should be on or off
     * if on==true will also generate bounding boxes where it can
     */
    public void turnOnOffBvh(boolean on) {
        turnOnOffBvh(this, on);
    }

    /**
     *  turns on or off the bvh acceleration in all the sub geometries using a recursive call
     *  @param on whether the bvh should be on or off
     * @param geometries the current geometries that we are turning on or off the bvh feature
     *  if on==true will also generate bounding boxes where it can
     *
     */
    private void turnOnOffBvh(Geometries geometries, boolean on) {
        for (Container geometry : geometries.containerList) {
            geometry.setBvh(on);
            if (on && geometry.getBoundingBox()==null)
                geometry.setBoundingBox();
            if (geometry instanceof Geometries subGeometries)
                turnOnOffBvh(subGeometries, on);
        }
    }
    //endregion

    /**
     *
     */
    public void buildBvhTree(){
        flatten();
        boolean perfect=setImperfectBoundingBox();
        buildBvhTree(getBoundingBox());
        checkForUnBoundability();

    }
    public void buildBvhTree(BoundingBox box){

    }
    /**
     * automated build bounding volume hierarchy tree
     * with default edges = false
     * so it will calculate the distance between the edges
     */
    public void buildBinaryBvhTree() {
        buildBinaryBvhTree(false);
    }

    /**
     * automated build bounding volume hierarchy tree
     *
     * @param edges whether to calculate between the edges (true) or centers (false)
     */
    public void buildBinaryBvhTree(boolean edges) {
        boolean on = this.isBvh();
        // flatten the list of Geometries
        this.flatten();

        //for better performance moves all geometries to an array so it will be O(1) to do get(i)
        //this will allow us to save O(n^2) time on redundant pairs

        Container[] containersInArray = containerList.toArray(new Container[0]);
        // define reusable variables (improved performance)
        double distanceSquared, best;
        boolean allAreBounded = false;
        Container bestGeometry1, bestGeometry2, geometry1 = null, geometry2 = null;

        // while any container contains more than one geometry
        while (!allAreBounded) {
            bestGeometry1 = bestGeometry2 = null;
            best = Double.POSITIVE_INFINITY;
            allAreBounded = true;

            // loop through the containers
            for (int i = 0; i < containersInArray.length; i++) {
                geometry1 = containersInArray[i];
                if (geometry1.getBoundingBox() == null)
                    geometry1.setBoundingBox();
                if (geometry1.getBoundingBox() == null)
                    //this means that geometry1 is unbound-able
                    continue;

                for (int j = i + 1; j < containersInArray.length; j++) {
                    geometry2 = containersInArray[j];

                    if (geometry2.getBoundingBox() == null)
                        geometry2.setBoundingBox();
                    if (geometry2.getBoundingBox() == null)
                        //this means geometry2 is unbound-able
                        continue;

                    // measure the distanceSquared between every couple of bounding boxes
                    distanceSquared = geometry1.boundingBox.boundingBoxDistanceSquared(geometry2.boundingBox, edges);
                    // if the distanceSquared is the lowest possible
                    if (distanceSquared < best) {
                        // define the best distanceSquared as the minimal one we have found
                        best = distanceSquared;
                        // define the best candidates to be together in a container
                        bestGeometry1 = geometry1;
                        bestGeometry2 = geometry2;
                    }
                }
            }
            // after we have determined the best geometries to couple in a container,
            // create new container which contains the geometries
            /* when bestGeometry1 is not null, bestGeometry2 is also not null*/
            if (bestGeometry1 != null) {
                //if we got to here then there is still to bound
                allAreBounded = false;

                Geometries combined = new Geometries(bestGeometry1, bestGeometry2);

                //will add and remove from containerList
                add(combined);
                // and remove the same ones from the original tree
                remove(bestGeometry1, bestGeometry2);


                //and now we need to update containersInArray

                int i;
                for (i = 0; i < containersInArray.length; i++) {
                    if (containersInArray[i].equals(bestGeometry1) || containersInArray[i].equals(bestGeometry2)) {
                        containersInArray[i] = combined;
                        //will remember the i to continue searching from here
                        break;
                    }
                }
                for (; i < containersInArray.length; i++) {
                    if (containersInArray[i].equals(bestGeometry1) || containersInArray[i].equals(bestGeometry2)) {
                        //will remember i as the index to remove
                        break;
                    }
                }


                // Create a new array of size containersInArray.length - 1
                Container[] newArray = new Container[containersInArray.length - 1];

                // Copy elements, skipping the element to be removed
                for (int k = 0, j = 0; k < containersInArray.length; k++) {
                    if (k != i) {
                        newArray[j++] = containersInArray[k];
                    }
                }

                containersInArray = newArray;
            }
        }
        //after our algorithm we want to unpack

        //if the setting was for false we want to keep it that way,
        if (!on)
            turnOnOffBvh(false);
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (isBvh() && boundingBox != null && !boundingBox.intersectBV(ray))
            return null;
        List<GeoPoint> res = null, shapeGeoPoints;
        for (var shape : containerList) {
            shapeGeoPoints = shape.findGeoIntersectionsHelper(ray, maxDistance);
            if (shapeGeoPoints != null) {
                if (res == null)
                    res = new LinkedList<>();
                res.addAll(shapeGeoPoints);
            }
        }
        return res;
    }




    @Override
    public void setBoundingBox() {
        //to avoid writing the same code twice we will use the function setImperfectBoundingBox.
        //but it will be longer since if we would do it here we could return immediately
        //when we see that at least one of the geometries is unbound-able
        if(!setImperfectBoundingBox())
            boundingBox=null;
        //else the bounding box will already contain the right boundingBox
    }

    /**
     * set an imperfect bounding box that will contain only bound-able elements and will ignore unbound-able ones
     *  important: needs to be used carefully , usually with the second function that will put back null if necessary
     * @return whether the box is actually perfect or it should be null
     */
    private boolean setImperfectBoundingBox() {
        boolean isPerfect = true;
        if (containerList.isEmpty()) {
            boundingBox = null;
            return false;
        }
        double xMin, xMax, yMin, yMax, zMin, zMax;
        xMin = yMin = zMin = Double.POSITIVE_INFINITY;
        xMax = yMax = zMax = Double.NEGATIVE_INFINITY;
        BoundingBox box;
        for (var container : containerList) {
            if (container instanceof Geometries innerGeometries) {
                //set the imperfect box for the innerGeometries
                if (!innerGeometries.setImperfectBoundingBox())
                    isPerfect = false;
            } else if (container.boundingBox == null) {
                container.setBoundingBox();
            }
            box = container.boundingBox;

            if (box == null) {
                //meaning that the container is a plane or contain an infinite object or an unimplemented one
                isPerfect = false;
                continue;
            }
            xMin = Math.min(xMin, box.getMinX());
            yMin = Math.min(yMin, box.getMinY());
            zMin = Math.min(zMin, box.getMinZ());
            xMax = Math.max(xMax, box.getMaxX());
            yMax = Math.max(yMax, box.getMaxY());
            zMax = Math.max(zMax, box.getMaxZ());

        }

        boundingBox = new BoundingBox(xMin, xMax, yMin, yMax, zMin, zMax);
        return isPerfect;
    }
    private void checkForUnBoundability(){
        checkForUnBoundability(this);
    }
    public void checkForUnBoundability(Geometries geometries){
        for(Container geometry:geometries.getContainerList()){
            geometry.setBoundingBox();
            if(geometry.getBoundingBox()==null) {
                boundingBox = null;
                return;
            }
        }
    }


}
