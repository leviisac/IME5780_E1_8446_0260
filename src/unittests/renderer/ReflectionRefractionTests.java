/**
 * 
 */
package unittests.renderer;

import geometries.*;
import org.junit.Test;

import elements.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
						new Point3D(0, 0, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), 2,
				0.0004, 0.0000006, new Vector(-1, 1, 2)));

		ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage(false);
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

		scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150)
				  , 2, 0.00001, 0.000005,new Vector(-1, 1, 4)));

		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage(false);
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 *  producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(60, -50, 50)));

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), 2, 4E-5, 2E-7, new Vector(0, 0, 1)));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage(false);
		render.writeToImage();
	}

	/**
	 * Produce a picture of a many geometries lighted by a directional light with refraction and reflection
	 */
	@Test
	public void Mytest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000))
		);
		Sphere s = new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0),
				30, new Point3D(0, 0, -50));
		Tube t1 = new Tube(
				new Color(java.awt.Color.YELLOW),
				new Material(0.2, 0.2, 30, 0.6, 1),
				2,
				new Ray(new Point3D(0, 0, -50),
				new Vector(0, 90, -50)));



		scene.addGeometries(t1,s);


		scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));
		ImageWriter imageWriter = new ImageWriter("myTest", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage(false);
		render.writeToImage();
	}


	/**
	 * Produce a picture of a many geometries lighted by a directional light with refraction and reflection
	 */
	@Test
	public void coronaSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(
				new Point3D(0, 0, -200),
				new Vector(0, 0, 1),
				new Vector(0, -1, 0)));
		scene.setDistance(200);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.1));


		scene.addGeometries(new Plane(
				new Color(java.awt.Color.black),
				new Material(0,1,150,0.2,0.8),
				new Point3D(0,0,110),
				new Vector(0,-0.724,0.6896)
		));

		scene.addGeometries(new Plane(
				new Color(29,25,37),
				new Material(0,1,150,0.2,0.8),
				new Point3D(0,0,110),
				new Vector(-0.0453,-0.417,-0.907)
		));

		scene.addGeometries(new Sphere(
				new Color(java.awt.Color.green),
				new Material(0.5, 0.5, 100), 3.5,
				new Point3D(-15, 0, 3)));



		//**********************************  The big sphere ************************************//

		scene.addGeometries(
				new Sphere(
						new Color(java.awt.Color.BLUE),
						new Material(0.5, 0.5, 100,0.6,0.4), 50,
						new Point3D(0, 0, 50)));



		scene.addLights(new DirectionalLight(new Color(255, 191, 191), new Vector(1, -1, 1)));

		ImageWriter imageWriter = new ImageWriter("coronaSphere", 200, 200, 1500, 1500);
		Render render = new Render(imageWriter, scene);

		render.renderImage(false);
		render.writeToImage();
	}
}
