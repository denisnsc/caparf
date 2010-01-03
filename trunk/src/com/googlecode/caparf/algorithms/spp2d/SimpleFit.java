package com.googlecode.caparf.algorithms.spp2d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Output;

/**
 * SimpleFit can produce the same results as well-known NextFit and FirstFit
 * algorithms. Algorithm time and memory complexity is {@code O(n log n)}.
 * SimpleFit places rectangle items in bottom-left positions, never looking
 * back. It means that it can produce big gaps in resulting packing. Rectangle
 * items can be selected in 2 ways:
 * <ul>
 * <li>The next item in rectangle items list given to the algorithm will be
 * selected in case of {@link ItemOrder#NEXT_ITEM}.
 * <li>The first item in rectangle items list given to the algorithm that fits
 * the current segment will be selected in case of {@link ItemOrder#FIRST_FIT}.
 * </ul>
 * Items are placed according to the bottom-left rule. One can try greedy
 * modification of this rule that places the rightmost rectangle items in
 * rightmost segments to the right by using
 * {@link PlacementStrategy#SHIFT_RIGHTMOST_ITEM}.
 * <p>
 * Note, that this class is not thread-safe.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class SimpleFit extends Algorithm<Input, Output> {

  /** Id corresponding to no rectangle. */
  public static final int ID_NO_RECT = -1;

  /** Strategies for placing rectangle items. */
  public enum PlacementStrategy {
    /** Place rectangle items into next possible bottom-left position. */
    DEFAULT,
    /**
     * Works as previous strategy except that the rightmost rectangle item in
     * rightmost segment is shifted to the right if possible.
     */
    SHIFT_RIGHTMOST_ITEM
  }

  /** Order in which rectangle items will be placed. */
  public enum ItemOrder {
    /** The next item in the list of items will be placed. */
    NEXT_ITEM,
    /**
     * The first item in the list of items that fits into current segment will
     * be placed.
     */
    FIRST_FIT
  }

  /** First-In-First-Out queue that stores free segments. */
  protected Deque<Segment> queue;

  /** Priority queue that stores segments occupied by rectangles. */
  protected PriorityQueue<Segment> heap;

  /** List of all rectangles, reference to {@code input.getRactangles()}. */
  protected List<Input.Rectangle> rects;

  /** Strip width. */
  protected int stripWidth;

  /** Positions of rectangles */
  protected List<Output.Point2D> placement;

  /** Total number of placed rectangles correspondingly. */
  protected int placedRects;

  /** Items tree used for fast searching of feasible items. */
  protected ItemsTree itemsTree;

  /** Items selection order. */
  protected final ItemOrder itemOrder;

  /** Items placement strategy. */
  protected final PlacementStrategy placementStrategy;

  /**
   * Constructs {@link SimpleFit} by the given parameters.
   *
   * @param itemOrder items selection order
   * @param placementStrategy items placement strategy
   */
  public SimpleFit(ItemOrder itemOrder, PlacementStrategy placementStrategy) {
    this.itemOrder = itemOrder;
    this.placementStrategy = placementStrategy;
  }

  @Override
  public Output solve(Input input) {
    rects = input.getRectangles();
    stripWidth = input.getStripWidth();
    if (itemOrder == ItemOrder.FIRST_FIT) {
      itemsTree = new ItemsTree();
    }
    placedRects = 0;

    placement = new ArrayList<Output.Point2D>(rects.size());
    for (int i = 0; i < rects.size(); i++) {
      placement.add(new Output.Point2D());
    }

    queue = new LinkedList<Segment>();
    queue.addFirst(new Segment(0, stripWidth, 0, ID_NO_RECT));

    heap = new PriorityQueue<Segment>(rects.size(), new Comparator<Segment>() {
      @Override
      public int compare(Segment o1, Segment o2) {
        return (o1.y != o2.y) ? (o1.y - o2.y) : (o1.xl - o2.xl);
      }
    });

    // current y-coordinate
    int y0 = 0;

    while (placedRects < rects.size()) {
      if (!queue.isEmpty()) {
        Segment freeSegment = queue.pollFirst();
        if (freeSegment.rectId == Segment.ID_INVALID) {
          continue;
        }

        // try to place next rectangle in order to freeSegment
        boolean success = false;
        while (true) {
          int rectId = findFeasibleRectangle(freeSegment.xr - freeSegment.xl);
          if (rectId == ID_NO_RECT) {
            break;
          }

          Segment rectSegment = new Segment(freeSegment.xl,
              freeSegment.xl + rects.get(rectId).width,
              y0 + rects.get(rectId).height,
              rectId);
          freeSegment.xl = rectSegment.xr;

          // Update segments links
          rectSegment.prev = freeSegment.prev;
          if (rectSegment.prev != null) {
            rectSegment.prev.next = rectSegment;
          }
          rectSegment.next = (freeSegment.xr == freeSegment.xl) ? freeSegment.next : freeSegment;
          if (rectSegment.next != null) {
            rectSegment.next.prev = rectSegment;
          }

          // Save coordinates of newly placed rectangle and add it to heap
          placement.get(rectId).x = rectSegment.xl;
          placement.get(rectId).y = y0;
          heap.add(rectSegment);
          success = true;
        }

        // Try to shift rightmost rectangle in rightmost segment to the right
        if (placementStrategy == PlacementStrategy.SHIFT_RIGHTMOST_ITEM && success &&
            freeSegment.xr > freeSegment.xl && freeSegment.next == null) {
          freeSegment.swapWithPrevious();
          placement.get(freeSegment.next.rectId).x = freeSegment.next.xl;
        }
      } else {
        // Change current y-coordinate to the least y-coordinate of segments in
        // heap
        y0 = heap.peek().y;
        // and remove all segments with y-coordinate equal to new current
        // y-coordinate from heap
        while (!heap.isEmpty() && heap.peek().y == y0) {
          Segment segment = heap.poll();
          segment.rectId = ID_NO_RECT;
          // Merge with previous segment
          if (segment.prev != null && segment.prev.rectId == ID_NO_RECT) {
            segment.mergeWithPrevious();
          }
          // Merge with next segment
          if (segment.next != null && segment.next.rectId == ID_NO_RECT) {
            segment.mergeWithNext();
          }
          // Add segment to queue
          queue.add(segment);
        }
      }
    }

    return new Output(input, placement);
  }

  /**
   * Finds the the first rectangle item that fits into the given {@code width}
   * according to {@link #itemOrder} and removes this rectangle from list of
   * available items.
   *
   * @param width maximal possible width of rectangle
   * @return id of rectangle that fits into the given {@code width} or {@link
   *         #ID_NO_RECT} if there is no such rectangle
   */
  protected int findFeasibleRectangle(int width) {
    int ret = ID_NO_RECT;
    switch (itemOrder) {
      case NEXT_ITEM:
        if (placedRects < rects.size() && rects.get(placedRects).width <= width) {
          ret = placedRects;
          placedRects += 1;
        }
        break;
      case FIRST_FIT:
        ret = itemsTree.findFeasibleItem(width);
        if (ret != ID_NO_RECT) {
          itemsTree.removeItem(ret);
          placedRects += 1;
        }
        break;
    }
    return ret;
  }

  @Override
  public String getDisplayName() {
    return (placementStrategy == PlacementStrategy.DEFAULT ? "" : "Greedy") +
        (itemOrder == ItemOrder.NEXT_ITEM ? "Next" : "Fist") + "Fit";
  }

  /**
   * Segment of horizontal slice in a packing. Each segment is either fully
   * occupied by some rectangle or free. Segment is represented by two
   * x-coordinates of leftmost and rightmost points which remains the same for
   * the whole segment's "life". Previous and next segments can change depending
   * on current y-coordinate. Each segment has maximal y-coordinate. If current
   * y-coordinate is greater than it then the segment makes no sense. If
   * {@link #rectId} is equal to {@link #ID_INVALID} then the segment also makes
   * no sense.
   */
  protected static class Segment {
    /** Id corresponding to invalid segment. */
    public static final int ID_INVALID = -2;

    /** x-coordinate of left segment point. */
    public int xl;

    /** x-coordinate of right segment point. */
    public int xr;

    /** segment's maximal y-coordinate. */
    public int y;

    /** Previous segment, may be null. */
    public Segment prev;

    /** Next segment, may be null. */
    public Segment next;

    /**
     * Id of rectangle that occupies segment or {@link SimpleFit#ID_NO_RECT}.
     */
    public int rectId;

    public Segment(int xl, int xr, int y, int rectId) {
      this.xl = xl;
      this.xr = xr;
      this.y = y;
      this.rectId = rectId;
      this.prev = null;
      this.next = null;
    }

    /**
     * Merges this segment with the previous segment, {@link #prev} must be
     * non-null.
     */
    public void mergeWithPrevious() {
      this.xl = this.prev.xl;
      this.prev.rectId = Segment.ID_INVALID;
      this.prev.xr = this.xl;
      this.prev = this.prev.prev;
      if (this.prev != null) {
        this.prev.next = this;
      }
    }

    /**
     * Merges this segment with the next segment, {@link #next} must be
     * non-null.
     */
    public void mergeWithNext() {
      this.xr = this.next.xr;
      this.next.rectId = Segment.ID_INVALID;
      this.next.xl = this.xr;
      this.next = this.next.next;
      if (this.next != null) {
        this.next.prev = this;
      }
    }

    /**
     * Swaps this segment with the previous segment {@link #prev} must be
     * non-null.
     */
    public void swapWithPrevious() {
      Segment previousSegment = this.prev;

      // Update links to previous and next segments
      if (previousSegment.prev != null) {
        previousSegment.prev.next = this;
      }
      if (this.next != null) {
        this.next.prev = previousSegment;
      }
      this.prev = previousSegment.prev;
      previousSegment.next = this.next;
      this.next = previousSegment;
      previousSegment.prev = this;

      // Update positions and placement
      int dxThis = previousSegment.xr - previousSegment.xl;
      int dxPrevious = this.xr - this.xl;
      this.xl -= dxThis;
      this.xr -= dxThis;
      previousSegment.xl += dxPrevious;
      previousSegment.xr += dxPrevious;
    }
  }

  /**
   * Items binary tree that allows to search for feasible rectangle items in
   * {@code O(n log n)} time complexity. Leafs of the tree corresponds to
   * rectangle items, non-leaf nodes store minimal rectangle items width in
   * the corresponding sub-tree. Hence, the root of the tree is the minimal
   * width of all rectangle items.
   */
  protected class ItemsTree {
    private int[] tree;
    private int leafCnt;

    /** Constructs items binary tree. */
    public ItemsTree() {
      leafCnt = 1;
      while (leafCnt < rects.size()) leafCnt <<= 1;

      tree = new int[leafCnt << 1];
      for (int i = 0; i < leafCnt; i++) {
        tree[leafCnt + i] = i < rects.size() ? rects.get(i).width : stripWidth + 1;
      }
      for (int i = leafCnt - 1; i > 0; i--) {
        tree[i] = Math.min(tree[i << 1], tree[(i << 1) | 1]);
      }
    }

    /**
     * Retrieves, but does not remove, the id of first rectangle item that fits
     * into the given {@code width}.
     *
     * @param width maximal possible width of rectangle
     * @return the id of first rectangle item that fits into the given {@code
     *         width} or {@link SimpleFit#ID_NO_RECT} if there is no such
     *         rectangle.
     */
    public int findFeasibleItem(int width) {
      if (tree[1] > width) {
        return ID_NO_RECT;
      }
      int ret = 1;
      while (ret < leafCnt) {
        ret <<= 1;
        if (tree[ret] > width) {
          ret |= 1;
        }
      }
      return ret - leafCnt;
    }

    /**
     * Removes item with given {@code id} from the tree.
     *
     * @param id id of item to remove
     */
    public void removeItem(int id) {
      int i = id + leafCnt;
      tree[i] = stripWidth + 1;
      while (i > 1) {
        i >>= 1;
        tree[i] = Math.min(tree[i << 1], tree[(i << 1) | 1]);
      }
    }
  }
}
