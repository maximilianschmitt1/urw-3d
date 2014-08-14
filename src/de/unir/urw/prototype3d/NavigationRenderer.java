package de.unir.urw.prototype3d;

import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class NavigationRenderer implements GLSurfaceView.Renderer, RotationListener {
    
    World world;
    FrameBuffer frameBuffer;
    Drawable baseTexture;
    RGBColor backgroundColor = new RGBColor(50, 50, 100);
    Box path;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }

        frameBuffer = new FrameBuffer(gl, width, height);

        world = new World();
        world.setAmbientLight(20, 20, 20);
        
        path = new Box(200,5,500);
        path.translate(-100, 00, 0);
        path.strip();
        path.build();
        
        Light light = new Light(world);
		light.setIntensity(250, 250, 250);
		
		SimpleVector lightPosition = new SimpleVector();
        lightPosition.set(path.getTransformedCenter());
        lightPosition.y -= 100;
        lightPosition.z -= 100;
        light.setPosition(lightPosition);

        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
        cam.setOrientation(new SimpleVector(0, 0, 1), new SimpleVector(0, -1, 0));
        cam.setPosition(0, -50, -200);
        cam.rotateCameraX((float) (30 * Math.PI/180));
        
        world.addObject(path);

        MemoryHelper.compact();
    }

    public void onDrawFrame(GL10 gl) {
        frameBuffer.clear(backgroundColor);
        world.renderScene(frameBuffer);
        world.draw(frameBuffer);
        frameBuffer.display();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

	@Override
	public void onRotation(float rotationX, float rotationY) {
		path.rotateY(rotationY);
		path.rotateX(rotationX);
	}
}
