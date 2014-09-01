package de.unir.urw.prototype3d;

import com.threed.jpct.SimpleVector;

public class PathFactory {
	
	int PATH_HEIGHT = 2;
	Box path1;
	
	public PathFactory(int width, SimpleVector[] waypoints) {
		double depth = waypoints[1].y - waypoints[0].y;
		path1 = new Box(width,PATH_HEIGHT,(int) depth);
		
		
	}
}
