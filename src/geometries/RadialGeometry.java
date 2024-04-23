package geometries;

abstract public class RadialGeometry implements Geometry {
    protected final double radius;

    public RadialGeometry(double radius) throws IllegalArgumentException {
        if (radius < 0) throw new IllegalArgumentException("radius " + radius + " can't be negative");
        this.radius = radius;
    }
}
