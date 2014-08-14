package de.unir.urw.prototype3d;

import android.view.MotionEvent;

public class TouchRotation {
	private float lastX = -1, lastY = -1, rotateX = 0, rotateY = 0;
	private RotationListener rotationListener;
	
	public TouchRotation(RotationListener rotationListener) {
		this.rotationListener = rotationListener;
	}
	
	public void onTouchEvent(MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = motionEvent.getX();
				lastY = motionEvent.getY();

				break;

			case MotionEvent.ACTION_MOVE:
				float movementX = motionEvent.getX() - lastX;
				float movementY = motionEvent.getY() - lastY;

				lastX = motionEvent.getX();
				lastY = motionEvent.getY();
				
				rotateX = movementX / -100f;
				rotateY = movementY / -100f;
				
				rotationListener.onRotation(rotateX, rotateY);

				break;

			case MotionEvent.ACTION_UP:
				lastX = -1;
				lastY = -1;

				rotateX = 0;
				rotateY = 0;
				
				rotationListener.onRotation(rotateX, rotateY);

				break;
		}
	}
	
	public float getRotateX() {
		return rotateX;
	}
	
	public float getRotateY() {
		return rotateY;
	}
}
