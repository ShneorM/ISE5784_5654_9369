package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a 3D scene that can be rendered.
 * It contains the name of the scene, the background color, the ambient light, and the geometries within the scene.
 * The Scene class provides methods to set the background color, ambient light, and geometries.
 *
 * @see geometries.Geometries
 * @see lighting.AmbientLight
 * @see primitives.Color
 *
 * @author Shneor and Emanuel
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public String name;

    /**
     * The background color of the scene. Default is black.
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light in the scene. Default is no ambient light.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * The geometries in the scene.
     */
    public Geometries geometries = new Geometries();

    /**
     *
     */
    public List<LightSource> lights=new LinkedList<>();
    /**
     * Constructs a new Scene with the specified name.
     *
     * @param name the name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background the background color to set.
     * @return the Scene instance (for method chaining).
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light in the scene.
     *
     * @param ambientLight the ambient light to set.
     * @return the Scene instance (for method chaining).
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries the geometries to set.
     * @return the Scene instance (for method chaining).
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources in the scene.
     * This allows the addition or modification of the light sources used for rendering the scene.
     *
     * @param lights the list of light sources to set
     * @return the Scene object itself for method chaining
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
