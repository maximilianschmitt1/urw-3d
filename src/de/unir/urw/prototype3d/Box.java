package de.unir.urw.prototype3d;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

@SuppressWarnings("serial")
public class Box extends Object3D {
	 public Box(int width, int height, int depth) {
	  super(12);

	  SimpleVector upperLeftFront = new SimpleVector(0,0,0);
	  SimpleVector upperRightFront = new SimpleVector(width,0,0);
	  SimpleVector lowerLeftFront = new SimpleVector(0,height,0);
	  SimpleVector lowerRightFront = new SimpleVector(width,height,0);

	  SimpleVector upperLeftBack = new SimpleVector(0,0,depth);
	  SimpleVector upperRightBack = new SimpleVector(width,0,depth);
	  SimpleVector lowerLeftBack = new SimpleVector(0,height,depth);
	  SimpleVector lowerRightBack = new SimpleVector(width,height,depth);

	  // Front
	  this.addTriangle(upperLeftFront, lowerLeftFront, upperRightFront);
	  this.addTriangle(upperRightFront, lowerLeftFront, lowerRightFront);

	  // Back
	  this.addTriangle(upperLeftBack, upperRightBack, lowerLeftBack);
	  this.addTriangle(upperRightBack, lowerRightBack, lowerLeftBack);

	  // Upper
	  this.addTriangle(upperLeftBack, upperLeftFront, upperRightBack);
	  this.addTriangle(upperRightBack, upperLeftFront, upperRightFront);

	  // Lower
	  this.addTriangle(lowerLeftBack, lowerRightBack, lowerLeftFront);
	  this.addTriangle(lowerRightBack, lowerRightFront, lowerLeftFront);

	  // Left
	  this.addTriangle(upperLeftFront, upperLeftBack, lowerLeftFront);
	  this.addTriangle(upperLeftBack, lowerLeftBack, lowerLeftFront);

	  // Right
	  this.addTriangle(upperRightFront, lowerRightFront, upperRightBack);
	  this.addTriangle(upperRightBack, lowerRightFront, lowerRightBack);
	  
	 }
	 
	}