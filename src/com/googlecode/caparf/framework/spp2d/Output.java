package com.googlecode.caparf.framework.spp2d;

import java.util.List;

import com.googlecode.caparf.framework.AlgorithmOutput;

public interface Output extends AlgorithmOutput {

	public static class Point2D {
		int x;
		int y;
	}
	
	void setOutput(List<Point2D> placement);
}
