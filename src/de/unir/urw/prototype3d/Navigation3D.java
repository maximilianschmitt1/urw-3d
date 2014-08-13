package de.unir.urw.prototype3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import com.threed.jpct.Logger;
import com.threed.jpct.example.R;

public class Navigation3D extends Activity {

    // Used to handle pause and resume...
    private GLSurfaceView glSurfaceView;
    private NavigationRenderer navigationRenderer = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationRenderer = new NavigationRenderer(getResources().getDrawable(R.drawable.ground));

        glSurfaceView = new GLSurfaceView(getApplication());
        glSurfaceView.setRenderer(navigationRenderer);

        setContentView(glSurfaceView);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Logger.log("touch down");
            return true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            Logger.log("touch up");
            return true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            Logger.log("touch moved");
            return true;
        }

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