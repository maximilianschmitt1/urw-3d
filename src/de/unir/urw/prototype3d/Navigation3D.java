package de.unir.urw.prototype3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import com.threed.jpct.Logger;

public class Navigation3D extends Activity {
    private GLSurfaceView glSurfaceView;
    private NavigationRenderer navigationRenderer;
    private TouchRotation touchRotation;
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationRenderer = new NavigationRenderer();
        touchRotation = new TouchRotation(navigationRenderer);

        glSurfaceView = new GLSurfaceView(getApplication());
        glSurfaceView.setRenderer(navigationRenderer);

        setContentView(glSurfaceView);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
    	touchRotation.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
