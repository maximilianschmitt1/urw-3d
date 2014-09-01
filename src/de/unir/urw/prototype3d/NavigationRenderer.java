package de.unir.urw.prototype3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Mesh;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.example.R;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.MemoryHelper;

public class NavigationRenderer implements GLSurfaceView.Renderer, RotationListener {
    
    World world;
    FrameBuffer frameBuffer;
    Drawable baseTexture;
    RGBColor backgroundColor = new RGBColor(50, 50, 100);
    Box path;
    Box path2;
    Object3D ground;
    
    public NavigationRenderer(Context context) {
    	Texture ground_tex = new Texture(BitmapHelper.rescale(BitmapHelper.convert(context.getResources().getDrawable(R.drawable.ground_tex)), 16, 16));
		Texture path_tex = new Texture(BitmapHelper.rescale(BitmapHelper.convert(context.getResources().getDrawable(R.drawable.path_tex)), 16, 16));
		Texture arrow_tex = new Texture(BitmapHelper.rescale(BitmapHelper.convert(context.getResources().getDrawable(R.drawable.arrow_tex)), 16, 16));
		
		TextureManager.getInstance().addTexture("ground_tex", ground_tex);
		TextureManager.getInstance().addTexture("path_tex", path_tex);
		TextureManager.getInstance().addTexture("arrow_tex", arrow_tex);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }

        frameBuffer = new FrameBuffer(gl, width, height);
        
        world = new World();
        world.setAmbientLight(20, 20, 20);
        
        path = new Box(200,2,500);  
        Mesh m = path.getMesh();
        float[] bb = m.getBoundingBox();
        for (float f: bb) {
        	Logger.log(""+f);
        }
        path.calcTextureWrapSpherical();
        path.translate(-100, 00, 0);
        path.setTexture("path_tex");
        path.strip();
        path.build();
        path.setRotationPivot(new SimpleVector(0,0,0));
        
        path2 = new Box(200,2,500);
        path2.calcTextureWrapSpherical();
        path2.translate(-100, 0, -500);
        path2.setTexture("path_tex");
        path2.strip();
        path2.build();

		ground = Primitives.getPlane(1, 1000);
		ground.calcTextureWrapSpherical();
		ground.translate(0, 0, 0);
		ground.rotateX((float) (90 * Math.PI/180));
		ground.setTexture("ground_tex");
		ground.strip();
		ground.build();
		
		//world.addObject(ground);
        world.addObject(path);
        world.addObject(path2);
        
        Light light = new Light(world);
		light.setIntensity(1000, 1000, 1000);
		
		SimpleVector lightPosition = new SimpleVector();
        lightPosition.set(path.getTransformedCenter());
        lightPosition.y -= 100;
        lightPosition.z -= 100;
        light.setPosition(lightPosition);

        Camera cam = world.getCamera();
        cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
        cam.setOrientation(new SimpleVector(0, 0, 1), new SimpleVector(0, -1, 0));
        cam.setPosition(0, -100, -200);
        cam.rotateCameraX((float) (20 * Math.PI/180));

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
		//
		path.rotateY(rotationY);
		//path.rotateX(rotationX);
	}
}
