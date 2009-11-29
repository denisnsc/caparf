package com.googlecode.caparf.algorithms.spp2d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import com.googlecode.caparf.framework.spp2d.Algorithm;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Output;

/**
 * NextFit algorithm with {@code O(n log n)} time complexity.
 *
 * @param <I> algorithm input
 * @param <O> algorithm output
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class NextFit<I extends Input, O extends Output> implements Algorithm<I, O> {
  /**
   * Segment of horizontal slice in a packing. Each segment is either fully
   * occupied by some rectangle or free. Segment is represented by two
   * x-coordinates of leftmost and rightmost points which remains the same
   * for the whole segment's "life". Previous and next segments can change
   * depending on current y-coordinate. Each segment has maximal y-coordinate.
   * If current y-coordinate is greater than it then the segment makes no sense.
   */
	private static class Segment {
		/** Id corresponding to no rectangle. */
		static final int ID_NO_RECT = -1;

		/** x-coordinate of left segment point. */
		int xl;

		/** x-coordinate of right segment point. */
		int xr;

		/** segment's maximal y-coordinate. */
		int y;

		/** Previous segment, may be null. */
		Segment prev;

		/** Next segment, may be null. */
		Segment next;

		/** Id of rectangle that occupies segment or {@code ID_NO_RECT}. */
		int rectId;

		Segment(int xl, int xr, int y, int rectId) {
			this.xl = xl;
			this.xr = xr;
			this.y = y;
			this.rectId = rectId;
			this.prev = null;
			this.next = null;
		}
	}

	/** First-In-First-Out queue that stores free segments. */
	Deque<Segment> queue;

	/** Priority queue that stores segments occupied by rectangles. */
	PriorityQueue<Segment> heap;

	/** List of all rectangles, reference to {@code input.getRactangles()}. */
	List<Input.Rectangle> rects;

	/** Positions of rectangles */
	List<Output.Point2D> placement;

	/** Total number of all and placed rectangles correspondingly. */
	int totalRects, placedRects;

	@Override
	public void solve(I input, O output) {
		rects = input.getRectangles();
		placedRects = 0;

		placement = new ArrayList<Output.Point2D>(rects.size());
		for (int i = 0; i < rects.size(); i++) {
		  placement.add(new Output.Point2D());
		}

		queue = new LinkedList<Segment>();
		queue.addFirst(new Segment(0, input.getStripWidth(), 0, Segment.ID_NO_RECT));

		heap = new PriorityQueue<Segment>(totalRects,
		    new Comparator<Segment>() {
					@Override
					public int compare(Segment o1, Segment o2) {
						return o1.y - o2.y;
					}
				});

		// current y-coordinate
		int y0 = 0;

		while (placedRects < rects.size()) {
			if (!queue.isEmpty()) {
				Segment freeSegment = queue.pollFirst();
				// try to place next rectangle in order to freeSegment
				while (freeSegment.xr - freeSegment.xl >= rects.get(placedRects).width) {
					Segment rectSegment = new Segment(freeSegment.xl,
					    freeSegment.xl + rects.get(placedRects).width,
					    y0 + rects.get(placedRects).height,
							placedRects);
					freeSegment.xl = rectSegment.xr;

					// Update segments links
					rectSegment.prev = freeSegment.prev;
					if (rectSegment.prev != null) {
					  rectSegment.prev.next = rectSegment;
					}
					rectSegment.next = (freeSegment.xr == rectSegment.xr) ? freeSegment.next : freeSegment;
					if (rectSegment.next != null) {
					  rectSegment.next.prev = rectSegment;
					}

					// Save coordinates of newly placed rectangle and add it to heap
					placement.get(placedRects).x = rectSegment.xl;
					placement.get(placedRects).y = y0;
					heap.add(rectSegment);
					placedRects += 1;
				}
			} else {
    		// Change current y-coordinate to the least y-coordinate of segments in heap
    		y0 = heap.peek().y;
    		// and remove all segments with y-coordinate equal to new current
    		// y-coordinate from heap
    		while (heap.peek().y == y0) {
    		  Segment segment = heap.poll();
    		  segment.rectId = Segment.ID_NO_RECT;
    		  // Merge with previous segment
    		  if (segment.prev != null && segment.prev.rectId == Segment.ID_NO_RECT) {
    		    // We need to remove segment.prev from queue. segment.prev can be
    		    // only the last element in the queue
    		    if (queue.peekLast() == segment.prev) {
    		      queue.pollLast();
    		    }
    		    segment.xl = segment.prev.xl;
    		    segment.prev = segment.prev.prev;
    		    if (segment.prev != null) {
    		      segment.prev.next = segment;
    		    }
    		  }
    		  // Merge with next segment
    		  if (segment.next != null && segment.next.rectId == Segment.ID_NO_RECT) {
    		    segment.xr = segment.next.xr;
    		    segment.next = segment.next.next;
    		    if (segment.next != null) {
    		      segment.next.prev = segment;
    		    }
    		  }
    		}
			}
		}

		output.setPlacement(placement);
	}
}
