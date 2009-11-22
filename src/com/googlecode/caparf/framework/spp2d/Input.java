package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.AlgorithmInput;

import java.util.List;

public interface Input extends AlgorithmInput {
	
	public static class Rectangle {
		int width;
		int height;
	}
	
	List<Rectangle> getRectangles();
	
	int getStripWidth();
}
