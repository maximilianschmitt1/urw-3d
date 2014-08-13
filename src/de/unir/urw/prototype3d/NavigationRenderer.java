package de.unir.urw.prototype3d;

import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class NavigationRenderer implements GLSurfaceView.Renderer {
    
    World world;
    FrameBuffer frameBuffer;
    Light light;
    Object3D cube;
    Drawable baseTexture;
    RGBColor backgroundColor = new RGBColor(50, 50, 100);
    
    private Object3D ground = null;
	private Object3D path = null;
	
	public static final float PI = (float) Math.PI;

    public NavigationRenderer(Drawable baseTexture) {
        this.baseTexture = baseTexture;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }

        frameBuffer = new FrameBuffer(gl, width, height);

        world = new World();
        world.setAmbientLight(20, 20, 20);

        light = new Light(world);
        light.setIntensity(250, 250, 250);

        Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(baseTexture), 64, 64));

        if (!TextureManager.getInstance().containsTexture("texture")) {
            TextureManager.getInstance().addTexture("texture", texture);
        }
        
        Box box = new Box(500,200,500);
        box.calcTextureWrapSpherical();
        box.setTexture("texture");
        box.strip();
        box.build();
        
		Logger.log("Ground  x: " + box.getTranslation().x + "y: " + box.getTranslation().y + "z: " + box.getTranslation().z);
		

		
		//Logger.log("Path  x: " + path.getTranslation().x + "y: " + path.getTranslation().y + "z: " + path.getTranslation().z);

        world.addObject(box);
        //world.addObject(path);

        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
        cam.setFovAngle(PI/2);
        cam.lookAt(box.getTransformedCenter());

        SimpleVector sv = new SimpleVector();
        sv.set(box.getTransformedCenter());
        sv.y -= 100;
        sv.z -= 100;
        light.setPosition(sv);
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
}