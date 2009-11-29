package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.AlgorithmInput;

import java.util.List;

/**
 * Input for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Input implements AlgorithmInput {

  /** Rectangle item. */
	public static class Rectangle {
		public int width;
		public int height;
	}

	/** List of rectangle items. */
	private final List<Rectangle> rectangles;

	/** Width of strip. */
	private final int stripWidth;

	/**
	 * Constructs input for 2 Dimensional Strip Packing Problem.
	 *
	 * @param rectangles rectangle items to be packed
	 * @param stripWidth width of strip
	 */
	public Input(List<Rectangle> rectangles, int stripWidth) {
	  this.rectangles = rectangles;
	  this.stripWidth = stripWidth;
	}

	/**
	 * Returns all rectangle items to be packed. Order of items matters.
	 *
	 * @return rectangle items to be packed
	 */
	public List<Rectangle> getRectangles() {
	  return rectangles;
	}

	/**
	 * Returns width of strip into which rectangle items to be packed.
	 *
	 * @return width of strip
	 */
	public int getStripWidth() {
	  return stripWidth;
	}
}
